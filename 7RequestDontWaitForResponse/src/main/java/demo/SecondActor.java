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
    private ActorRef secondActor;
    private Message m1 = new Message();

	// Empty Constructor
	public SecondActor() {}

	// Static function that creates actor
	public static Props createActor() {
		return Props.create(SecondActor.class, () -> {
			return new SecondActor();
		});
	}
   
    public void forwardMessage(String msg){
        this.m1.setMsg(msg);
        getSender().tell(this.m1, getSelf());
    }

    @Override
	public void onReceive(Object message) throws Throwable {
		if(message instanceof Message){
            this.m1 = (Message) message;
            
            log.info("["+getSelf().path().name()+"] received message from ["+ getSender().path().name() +"]");
            log.info("Message Received by the SecondActor: {}", this.m1.getMsg());
            log.info("SecondActor forwarded message to: [" + getSender().path().name() +"]");  
            Thread.sleep(100);  

            if(this.m1.getMsg() == "Hello"){
                forwardMessage("World");
                log.info("Message sended by the SecondActor: {}", "World" );                  
            }
            else if(this.m1.getMsg() == "Green"){
                forwardMessage("Eggs");
                log.info("Message sended by the SecondActor: {}", "Eggs" );     
            }
            
	    }
    }
}


