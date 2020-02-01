package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import demo.Message;

public class FirstActor extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	// Actor reference
    private ActorRef thirdActor, secondActor;
	private Message m1 = new Message();
	private String command; 

	public FirstActor() {secondActor = null;}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(FirstActor.class, () -> {
			return new FirstActor();
		}); 
	}

    public void forwardMessage(String msg){
        this.m1.setMsg(msg);
        this.m1.setActorref(this.thirdActor);
		this.secondActor.tell(this.m1, getSelf());
		log.info("Request sended by ["+ getSelf().path().name() +"] to ["+this.secondActor.path().name()+"]: {}", msg);
    }

	@Override
	public void onReceive(Object message) throws Throwable {
		log.info("["+getSelf().path().name()+"] received message from ["+ getSender().path().name() +"]");		
		
		if(message instanceof ActorRef){
            if(this.secondActor == null){
				this.secondActor = (ActorRef) message;
				log.info("Actor reference updated ! New reference is: ["+ this.secondActor.path().name() +"]");
			} 
			else {
				this.thirdActor = (ActorRef) message;
				log.info("Actor reference updated ! New reference is: ["+ this.thirdActor.path().name() +"]");
			}
        }
		
		if(message instanceof String){
			log.info("Command received by ["+ getSender().path().name() +"]: {}", (String) message);
			if(message == "run"){
				forwardMessage("Sim_ok");
            } 
        }
	}


	/**
	 * alternative for AbstractActor
	 * @Override
	public Receive createReceive() {
		return receiveBuilder()
				// When receiving a new message containing a reference to an actor,
				// Actor updates his reference (attribute).
				.match(ActorRef.class, ref -> {
					this.actorRef = ref;
					log.info("Actor reference updated ! New reference is: {}", this.actorRef);
				})
				.build();
	}
	 */
}
