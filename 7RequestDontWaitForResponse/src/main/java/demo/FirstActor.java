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
    private ActorRef actorRef, secondActor;
    private Message m1 = new Message();
    private Message m2 = new Message();

	public FirstActor() {secondActor = null;}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(FirstActor.class, () -> {
			return new FirstActor();
		}); 
	}

    public void forwardMessage(String msg){
        this.m1.setMsg(msg);
        this.m1.setActorref(this.actorRef);
        this.secondActor.tell(this.m1, getSelf());
    }

	@Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof ActorRef){
            if(secondActor == null) secondActor = (ActorRef) message;
            else this.actorRef = (ActorRef) message;
		    log.info("["+getSelf().path().name()+"] received message from ["+ getSender().path().name() +"]");
		    log.info("Actor reference updated ! New reference is: {}", this.secondActor);
        }
        if(message instanceof Message){
            m1 = (Message) message;
            if(m1 != m2 && this.m1.getMsg() != "Hello" && this.m1.getMsg() != "Green"){
                m2 = m1;
                log.info("["+getSelf().path().name()+"] received message from ["+ getSender().path().name() +"]");
                log.info("Message received by FirstActor: {} ", this.m2.getMsg());
                Thread.sleep(10);  
            }             

        }
        if(message instanceof String){
            if(message == "run"){
                forwardMessage("Hello");
                Thread.sleep(10);             
                forwardMessage("Green");    
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
