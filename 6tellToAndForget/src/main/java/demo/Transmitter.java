package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Transmitter extends UntypedAbstractActor {
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	private Message m1 = new Message();

	// Empty Constructor
	public Transmitter() {}

	// Static function that creates actor
	public static Props createActor() {
		return Props.create(Transmitter.class, () -> {
			return new Transmitter();
		});
	}

    @Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof Message){
        this.m1 = (Message) message;
        this.m1.getActorref().tell( this.m1.getMsg(), getSender());
		log.info("["+getSelf().path().name()+"] received message from ["+ getSender().path().name() +"]");
		log.info("Transmitter received message: ", this.m1.getMsg());
		log.info("Transmitter forwarded message to: ", this.m1.getActorref());
		}
    }
}
