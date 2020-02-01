package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class FirstActor extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	// Actor reference
    private ActorRef actorRef, transmitterRef;
    private Message m1 = new Message();

	public FirstActor() {transmitterRef = null;}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(FirstActor.class, () -> {
			return new FirstActor();
		}); 
	}

    public void forwardMessage(){
        this.m1.setMsg("Hello");
        this.m1.setActorref(this.actorRef);
        this.transmitterRef.tell(this.m1, getSelf());
    }

	@Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof ActorRef){
            if(transmitterRef == null) transmitterRef = (ActorRef) message;
            else this.actorRef = (ActorRef) message;
		log.info("["+getSelf().path().name()+"] received message from ["+ getSender().path().name() +"]");
		log.info("Actor reference updated ! New reference is: {}", this.actorRef);
        }
        if(message instanceof String){
            if(message == "run"){
                forwardMessage();
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
