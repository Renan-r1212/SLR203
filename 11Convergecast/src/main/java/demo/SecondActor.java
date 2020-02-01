package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class SecondActor extends UntypedAbstractActor {
    
    // Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	// Actor reference
	private ActorRef merger;

	// Empty Constructor
	public SecondActor() {}

	// Static function that creates actor
	public static Props createActor() {
		return Props.create(SecondActor.class, () -> {
			return new SecondActor();
		});
	}

	public void forwardMessage(String msg){
		this.merger.tell(msg, getSelf());
		log.info("Request sended by ["+ getSelf().path().name() +"] to ["+this.merger.path().name()+"]: {}", msg);
	}
	
    @Override
	public void onReceive(Object message) throws Throwable {
		log.info("["+getSelf().path().name()+"] received message from ["+ getSender().path().name() +"]");			
		if(message instanceof ActorRef){
				this.merger = (ActorRef) message;
				log.info("["+getSelf().path().name()+"] reference updated ! New reference is: ["+ this.merger.path().name() +"]");
		} 
		
		if(message instanceof String){
			log.info("["+ getSelf().path().name() +"] received message by ["+ getSender().path().name() +"]: {}", (String) message);
			if(message == "join"){
				log.info("["+getSelf().path().name()+"] sended connexiot request to ["+ merger.path().name() +"]"); 
				merger.tell("conexion_SecondActor", getSelf());
			}
			else if(message == "go") {
				log.info("["+getSelf().path().name()+"] sended message to: ["+ merger.path().name() +"]"); 
				forwardMessage("WAZZZAAAAAAA");
			}
        }
    }
}


