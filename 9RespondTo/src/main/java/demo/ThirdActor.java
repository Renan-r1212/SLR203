package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class ThirdActor extends UntypedAbstractActor {
    
    // Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
    // Actor reference
    private Message m1 = new Message();

	// Empty Constructor
	public ThirdActor() {}

	// Static function that creates actor
	public static Props createActor() {
		return Props.create(ThirdActor.class, () -> {
			return new ThirdActor();
		});
	}

    @Override
	public void onReceive(Object message) throws Throwable {
        log.info("["+getSelf().path().name()+"] received message from ["+ getSender().path().name() +"]");
        if(message instanceof Message){
            this.m1 = (Message) message;
            log.info("["+getSelf().path().name()+"] received the request: {}", this.m1.getMsg());            
	    }
    }
}


