package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import demo.Message;
import java.time.Duration;

public class FirstActor extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	// Actor reference
    private ActorRef broadcaster;

	public FirstActor() {broadcaster = null;}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(FirstActor.class, () -> {
			return new FirstActor();
		}); 
	}

    public void forwardMessage(String msg){
		this.broadcaster.tell(msg, getSelf());
		log.info("Request sended by ["+ getSelf().path().name() +"] to ["+this.broadcaster.path().name()+"]: {}", msg);
    }

	@Override
	public void onReceive(Object message) throws Throwable {
		log.info("["+getSelf().path().name()+"] received message from ["+ getSender().path().name() +"]");			
		if(message instanceof ActorRef){
			this.broadcaster = (ActorRef) message;
			log.info("["+getSelf().path().name()+"] reference updated ! New reference is: ["+ this.broadcaster.path().name() +"]");
		} 
		
		if(message instanceof String){
			log.info("["+getSelf().path().name()+"] received command by ["+ getSender().path().name() +"]: {}", (String) message);
			if(message == "run"){
				Thread.sleep(1000);
			//	getContext().system().scheduler().scheduleOnce(Duration.ofMillis(1000), getSelf(), "go", getContext().system(), ActorRef.noSender());
            } else if (message == "go"){
				forwardMessage("Sim_ok");
			} 
        }
	}
}
