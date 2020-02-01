package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import demo.Message;

public class ConvergeCaster extends UntypedAbstractActor{

	// Logger attached to actor
	private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	// Actor reference
	private Message hi_ax;
	private ActorRef firstActor, secondActor, thirdActor, fourthActor;
	private int count, count_old, joined_count;


	public ConvergeCaster() {
		this.count       = 0; 
		this.count_old   = 0;
		this.firstActor  = null; 
		this.secondActor = null;   
		this.thirdActor  = null; 
		this.hi_ax = new Message();
	}

	// Static function creating actor
	public static Props createActor() {
		return Props.create(ConvergeCaster.class, () -> {
			return new ConvergeCaster();
		}); 
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		
		log.info("["+getSelf().path().name()+"] received message from ["+ getSender().path().name() +"]");			
		if(message instanceof ActorRef){
				this.fourthActor = (ActorRef) message;
				log.info("["+getSelf().path().name()+"] reference updated ! New reference is: ["+ this.fourthActor.path().name() +"]");
		}
		
		if(message instanceof String){			
			log.info("["+ getSelf().path().name() +"] received STRING by ["+ getSender().path().name() +"]: {}", (String) message);
			
			if(message == "conexion_FirstActor"){
				this.firstActor = getSender() ; 
				log.info("["+  getSender().path().name() +"] joined the merge");
				this.joined_count++;
				log.info("Actors in section: {}", this.joined_count);
			}

			//first			
			if(getSender() == this.firstActor){
				if(message == "unjoin"){
					log.info("["+  getSelf().path().name() +"] unjoined");
					this.joined_count--;
					hi_ax.setHi_a1(null);
					this.count--;
					log.info("Actors in section: {}", this.joined_count);
				}
				else if(message != "conexion_FirstActor" && message != "unjoin"){
					if(hi_ax.getHi_a1() == null){
						this.count++;
					}
					hi_ax.setHi_a1((String) message);
					log.info("["+  getSelf().path().name() +"] received message from ["+ getSender().path().name() +"] to merge: {}", this.hi_ax.getHi_a1());
				}
			}

			//second
			if(message == "conexion_SecondActor"){
				this.secondActor = getSender() ; 
				log.info("["+  getSender().path().name() +"] joined the merge");
				this.joined_count++;
				log.info("Actors in section: {}", this.joined_count);
			}
			if(getSender() == this.secondActor){

				if(message == "unjoin"){
					log.info("["+  getSelf().path().name() +"] unjoined");
					this.joined_count--;
					hi_ax.setHi_a2(null);
					this.count--;
					log.info("Actors in section: {}", this.joined_count);
				}
				else if(message != "conexion_SecondActor" && message != "unjoin"){
					if(hi_ax.getHi_a2() == null){
						this.count++;
					}
					hi_ax.setHi_a2((String) message);
					log.info("["+  getSelf().path().name() +"] received message from ["+ getSender().path().name() +"] to merge: {}", this.hi_ax.getHi_a1());
				}
			}

			//third
			if(message == "conexion_ThirdActor") {
				this.thirdActor = getSender() ;
				log.info("["+  getSender().path().name() +"] joined the merge");
				this.joined_count++;
				log.info("Actors in section: {}", this.joined_count);
			}

			if(getSender() == this.thirdActor){
				if(message == "unjoin"){
					// RECEBER E COMPUTAR O UNJOINE
					
					log.info("["+  getSelf().path().name() +"] unjoined");
					this.joined_count--;
					hi_ax.setHi_a3(null);
					this.count--;
					log.info("Actors in section: {}", this.joined_count);
				}
				else if(message != "conexion_ThirdActor" && message != "unjoin"){
					if(hi_ax.getHi_a3() == null){
						this.count++;
					}
					hi_ax.setHi_a3((String) message);
					log.info("["+  getSelf().path().name() +"] received message from ["+ getSender().path().name() +"] to merge: {}", this.hi_ax.getHi_a1());
				}
			}

			if(this.count == this.joined_count ){
				if(this.count != this.count_old && this.count != 0){
						this.fourthActor.tell(hi_ax, getSelf());
						log.info("Merged the sent");
						this.count_old = this.count;
				}
        	}
		}	
	}
}
