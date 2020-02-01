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
	private ActorRef broadcaster;


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
		if(message instanceof ActorRef){
				this.broadcaster = (ActorRef) message;
				log.info("["+getSelf().path().name()+"] reference updated ! New reference is: ["+ this.broadcaster.path().name() +"]");
		} 
		
        if(message instanceof String){
			log.info("["+ getSelf().path().name() +"] received message by ["+ getSender().path().name() +"]: {}", (String) message);
			if(message == "join"){
				log.info("["+getSelf().path().name()+"] sended connexiot request to ["+ broadcaster.path().name() +"]"); 
				broadcaster.tell("conexion_ThirdActor", getSelf());
			}
			if( message == "Sim_ok"){
				log.info("["+getSelf().path().name()+"] received broadcast: {}",(String) message); 
			}          
	    }
    }
}


