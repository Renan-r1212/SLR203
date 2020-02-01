package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import demo.Message;
import java.time.Duration;

public class Receiver1 extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	// Actor reference

	public Receiver1() {}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(Receiver1.class, () -> {
			return new Receiver1();
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
