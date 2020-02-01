package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.Identify;
import akka.actor.ActorIdentity;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import java.util.ArrayList;

public class FirstActor extends UntypedAbstractActor{

	// Logger attached to actor
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	private ArrayList<ActorRef> actor_list;
	private ActorRef a;
    // Actor reference
	public FirstActor() {this.a = null; this.actor_list = new ArrayList<ActorRef>();}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(FirstActor.class, () -> {
			return new FirstActor();
		}); 
    }

	@Override
	public void onReceive(Object message) throws Throwable {
        if(message instanceof ActorRef){
			this.a = (ActorRef) message;
            this.actor_list.add(this.a);
            log.info("["+getSelf().path().name()+"] recieved new reference of ["+ this.a.path().name() +"]");
        }
	}
}
