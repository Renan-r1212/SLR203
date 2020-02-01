package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import demo.Message;

public class FourthActor extends UntypedAbstractActor {
    
    // Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	// Actor reference
	private Message hi_ax;
	private String hi_merge;


	// Empty Constructor
	public FourthActor() {}

	// Static function that creates actor
	public static Props createActor() {
		return Props.create(FourthActor.class, () -> {
			return new FourthActor();
		});
	}

    @Override
	public void onReceive(Object message) throws Throwable {
		log.info("["+getSelf().path().name()+"] received message from ["+ getSender().path().name() +"]");
		this.hi_ax = (Message) message;
		this.hi_merge = null;
		//a1
		if(this.hi_merge == null && this.hi_ax.getHi_a1() != null){
			this.hi_merge = this.hi_ax.getHi_a1() + " ";
		}else if(this.hi_ax.getHi_a1() != null){
			this.hi_merge = this.hi_merge + this.hi_ax.getHi_a1() + " ";
		}

		//a2
		if(this.hi_merge == null && this.hi_ax.getHi_a2() != null){
			this.hi_merge = this.hi_ax.getHi_a2() + " ";
		}else if(this.hi_ax.getHi_a2() != null){
			this.hi_merge = this.hi_merge + this.hi_ax.getHi_a2() + " ";
		}

		//a3
		if(this.hi_merge == null && this.hi_ax.getHi_a3() != null){
			this.hi_merge = this.hi_ax.getHi_a3() + " ";
		}else if(this.hi_ax.getHi_a3() != null){
			this.hi_merge = this.hi_merge + this.hi_ax.getHi_a3() + " ";
		}

        if(message instanceof Message){
			log.info("["+ getSelf().path().name() +"] received merge by ["+ getSender().path().name() +"]: {}", this.hi_merge);        
	    } 	   
    }
}


