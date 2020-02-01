package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Receiver3 extends UntypedAbstractActor {  
    // Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	// Actor reference


	// Empty Constructor
	public Receiver3() {}

	// Static function that creates actor
	public static Props createActor() {
		return Props.create(Receiver3.class, () -> {
			return new Receiver3();
		});
	}
	
    @Override
	public void onReceive(Object message) throws Throwable {
		log.info("["+getSelf().path().name()+"] received message from ["+ getSender().path().name() +"]");			
		if(message instanceof String){
			log.info("["+getSelf().path().name()+"] received: {}", (String) message);
		}
    }
}


