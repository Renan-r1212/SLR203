package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;


public class Multicast {

	public static void main(String[] args) {
		// Instantiate an actor system
		final ActorSystem system = ActorSystem.create("system");
		
		// Instantiate first and second actor
	    final ActorRef receiver_1 = system.actorOf(Receiver1.createActor(),  "receiver_1");
		final ActorRef receiver_2 = system.actorOf(Receiver2.createActor(), "receiver_2");
		final ActorRef receiver_3 = system.actorOf(Receiver3.createActor(),  "receiver_3");
		final ActorRef sender = system.actorOf(Sender.createActor(), "sender");
		final ActorRef multicaster = system.actorOf(Multicaster.createActor(), "multicaster");
	    
			// send to a1 the reference of a2 by message
			//be carefull, here it is the main() function that sends a message to a1, 
            //not a1 telling to a2 as you might think when looking at this line:
	   
		// References
		sender.tell(receiver_1, ActorRef.noSender());
		sender.tell(receiver_2, ActorRef.noSender());
		sender.tell(receiver_3, ActorRef.noSender());
		sender.tell(multicaster, ActorRef.noSender());

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			system.terminate();
		}

		sender.tell("go", ActorRef.noSender());
	
	    // We wait 5 seconds before ending system (by default)
	    // But this is not the best solution.
	    try {
			waitBeforeTerminate();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			system.terminate();
		}
	}

	public static void waitBeforeTerminate() throws InterruptedException {
		Thread.sleep(5000);
	}

}
