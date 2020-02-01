package demo;

import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.actor.ActorRef;
import akka.actor.Props;

public class SecondActor extends UntypedAbstractActor {
	
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	private Message m1 = new Message();
	// Empty Constructor
	public SecondActor() {}

	// Static function that creates actor
	public static Props createActor() {
		return Props.create(SecondActor.class, () -> {
			return new SecondActor();
		});
	}

    @Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof Message){
        this.m1 = (Message) message;
		log.info("["+getSelf().path().name()+"] received message from ["+ getSender().path().name() +"]");
		log.info("Second Actor received message: ", this.m1.getMsg());
		}
	}
}	