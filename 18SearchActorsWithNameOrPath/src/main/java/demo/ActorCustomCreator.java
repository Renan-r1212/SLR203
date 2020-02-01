package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.Identify;
import akka.actor.ActorIdentity;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class ActorCustomCreator extends UntypedAbstractActor{

	// Logger attached to actor
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    private String std_path;
    ActorRef actor;
    ActorIdentity actor_id;

    private int count;

    // Actor reference
	public ActorCustomCreator() {this.std_path = "actor"; this.count = 1;}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(ActorCustomCreator.class, () -> {
			return new ActorCustomCreator();
		}); 
    }

	@Override
	public void onReceive(Object message) throws Throwable {
					
        
		if(message instanceof String){
            if(message == "create"){
                getContext().actorOf(ActorCustomCreator.createActor(), this.std_path + this.count);
                log.info("["+getSelf().path().name()+"] created actor" + this.count);
                this.count++;
            }
        }

        if(message instanceof ActorRef){
            log.info("["+getSelf().path().name()+"] message received from ["+ getSender().path().name() +"]");
        }

        if(message instanceof ActorIdentity) {
            this.actor_id = (ActorIdentity) message;
            try{
                this.actor = this.actor_id.getActorRef().get();
                log.info("["+getSelf().path().name()+"] received a message from ["+ actor.path().name() +"] with path: [" + actor.path() + "]");
            }catch(Exception e){}
            
        }
	}
}
