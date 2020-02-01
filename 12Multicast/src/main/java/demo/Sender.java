package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import demo.Message;

public class Sender extends UntypedAbstractActor {
    
    // Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	// Actor reference
	private ActorRef receiver_1, receiver_2, receiver_3;
	private ActorRef multicaster;
	private Group group_1, group_2;
	private Message msg_1, msg_2;

	// Empty Constructor
	public Sender() {
		this.receiver_1  = null; 
		this.receiver_2  = null; 
		this.receiver_3  = null; 
		this.multicaster = null;

	//	group_1 = new Group();
	//	group_2 = new Group();
	}

	// Static function that creates actor
	public static Props createActor() {
		return Props.create(Sender.class, () -> {
			return new Sender();
		});
	}

    @Override
	public void onReceive(Object message) throws Throwable {
		log.info("["+getSelf().path().name()+"] received message from ["+ getSender().path().name() +"]");
		
		if(message instanceof ActorRef ){
			if(this.multicaster == null){
				this.multicaster = (ActorRef) message;
				log.info("["+getSelf().path().name()+"] received new reference: ["+ this.multicaster.path().name() +"]");
			}
			else if(this.receiver_1 == null){
				this.receiver_1 = (ActorRef) message;
				log.info("["+getSelf().path().name()+"] received new reference: ["+ this.multicaster.path().name() +"]");
			}
			else if(this.receiver_2 == null){
				this.receiver_2 = (ActorRef) message;
				log.info("["+getSelf().path().name()+"] received new reference: ["+ this.multicaster.path().name() +"]");
			}
			else{
				this.receiver_3 = (ActorRef) message;
				log.info("["+getSelf().path().name()+"] received new reference: ["+ this.multicaster.path().name() +"]");
			}
		}
		if(message instanceof String){
			if(message == "go"){
				this.multicaster.tell(this.group_1 = new Group(this.receiver_1, this.receiver_2), getSelf());
				log.info("["+getSelf().path().name()+"] defined Group 1 to ["+ this.multicaster.path().name() +"]");
				Thread.sleep(250);
				this.multicaster.tell(this.group_2 = new Group(this.receiver_2, this.receiver_3), getSelf());
				log.info("["+getSelf().path().name()+"] defined Group 2 to ["+ this.multicaster.path().name() +"]");
				Thread.sleep(1000);

				this.multicaster.tell(this.msg_1 = new Message("Hello", this.group_1), getSelf());
				log.info("["+getSelf().path().name()+"] sended Hello to Group 1");
				Thread.sleep(500);
				this.multicaster.tell(this.msg_2 = new Message("World", this.group_2), getSelf());
				log.info("["+getSelf().path().name()+"] sended World to Group 2");			
			}
		}	   
    }
}


