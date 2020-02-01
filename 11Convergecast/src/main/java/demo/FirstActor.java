package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import demo.Message;
import java.time.Duration;
import akka.actor.Actor;
import akka.actor.ActorKilledException;
import akka.actor.ActorRefFactory;
import akka.actor.Cancellable;
import akka.actor.OneForOneStrategy;
import akka.actor.Scheduler;
import akka.actor.Status;
import akka.actor.SupervisorStrategy;
import akka.actor.SupervisorStrategy.Directive;
import akka.actor.Terminated;
import akka.actor.UntypedActor;
import akka.japi.Function;
import akka.pattern.Patterns;
import akka.util.Timeout;

public class FirstActor extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	// Actor reference
	private ActorRef merger;

	public FirstActor() {merger = null;}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(FirstActor.class, () -> {
			return new FirstActor();
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
				merger.tell("conexion_FirstActor", getSelf());
			}
			else if(message == "go") {
				log.info("["+getSelf().path().name()+"] sended message to: ["+ merger.path().name() +"]"); 
				forwardMessage("Hello");
			}
        }
	}
}
