package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import demo.Message;

public class BroadCaster extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	// Actor reference
    private ActorRef thirdActor, secondActor;

	public BroadCaster() {secondActor = null;}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(BroadCaster.class, () -> {
			return new BroadCaster();
		}); 
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		log.info("["+getSelf().path().name()+"] received message from ["+ getSender().path().name() +"]");		
		
		if(message instanceof String){			
			log.info("["+ getSelf().path().name() +"] received command by ["+ getSender().path().name() +"]: {}", (String) message);
			
			if(message == "conexion_SecondActor"){
				this.secondActor = getSender() ; 
				log.info("["+  getSender().path().name() +"] joined the broadcast");
			}
			else if(message == "conexion_ThirdActor") {
				this.thirdActor = getSender() ;
				log.info("["+  getSender().path().name() +"] joined the broadcast");
			}
			else{
				this.secondActor.tell(message, getSelf());
				this.thirdActor.tell(message, getSelf());
				log.info("["+  getSelf().path().name() +"] broadcasted the message: {}", message);
			}
        }
	}
}
