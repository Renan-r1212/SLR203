package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.Identify;
import akka.actor.ActorIdentity;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import java.util.*;

public class FirstActor extends UntypedAbstractActor{

	// Logger attached to actor
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	private ArrayList<ActorRef> actor_list;
	private ArrayList<Integer> sequence_id;
	private ActorRef a;
	private FloodId floodin;
	private boolean equal;

    // Actor reference
	public FirstActor() {
		this.floodin = new FloodId(); 
		this.actor_list = new ArrayList<ActorRef>(); 
		this.sequence_id = new ArrayList<Integer>();
	}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(FirstActor.class, () -> {
			return new FirstActor();
		}); 
    }

	@Override
	public void onReceive(Object message) throws Throwable {
        if(message instanceof ActorRef){
			this.a = (ActorRef) message;
            this.actor_list.add(this.a);
            log.info("["+getSelf().path().name()+"] recieved new reference of ["+ this.a.path().name() +"]");
		}

		if(message instanceof FloodId){
			log.info("["+getSelf().path().name()+"] recieved messege");
			this.floodin = (FloodId) message;
			this.equal = false;

				if(sequence_id.contains(this.floodin.getFlood_id())){
					this.equal = true;
					log.info("["+getSelf().path().name()+"] received an equal flood id");
				}
				if(!equal){

					log.info("["+getSelf().path().name()+"] received a new flood id: {}", this.floodin.getFlood_id());
					Iterator<ActorRef> a_iterator = actor_list.iterator();
					sequence_id.add(this.floodin.getFlood_id());
					while(a_iterator.hasNext()){
						ActorRef actor = a_iterator.next();
						actor.tell(this.floodin, getSelf());
						log.info("["+getSelf().path().name()+"] sended message to ["+ actor.path().name() +"]: {}", this.floodin.getMsg());
					}
				}
			
		}
	}
}
