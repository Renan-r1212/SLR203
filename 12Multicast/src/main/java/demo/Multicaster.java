package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import demo.Message;

public class Multicaster extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	// Actor reference
	private Group group_1, group_2;
	private Message msg;

	public Multicaster() {
		this.group_1 = new Group()  ;
		this.group_2 = new Group()  ;
		this.msg     = new Message();
	}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(Multicaster.class, () -> {
			return new Multicaster();
		});
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		log.info("["+getSelf().path().name()+"] received message from ["+ getSender().path().name() +"]");
		if(message instanceof Group){
			if(this.group_1 == null){
				log.info("["+getSelf().path().name()+"] received Group 1 from ["+ getSender().path().name() +"]");
				this.group_1 = (Group) message;
			}
			else{
				log.info("["+getSelf().path().name()+"] received Group 2 from ["+ getSender().path().name() +"]");
				this.group_2 = (Group) message;
			}
		}

		if(message instanceof Message){
			this.msg = (Message) message;
			if(this.msg.getGroup() == this.group_1 ){
				log.info("["+getSelf().path().name()+"] multicasted message to Group 1");
				this.group_1.getReceiver_A().tell(this.msg.getMsg(), getSender());
				this.group_1.getReceiver_B().tell(this.msg.getMsg(), getSender());
			}
			else{
				log.info("["+getSelf().path().name()+"] multicasted message to Group 2");
				this.group_2.getReceiver_A().tell(this.msg.getMsg(), getSender());
				this.group_2.getReceiver_B().tell(this.msg.getMsg(), getSender());
			}
		}
	}
}