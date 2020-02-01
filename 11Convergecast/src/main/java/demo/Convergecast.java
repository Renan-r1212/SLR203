package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import java.time.Duration;


public class Convergecast {

	public static void main(String[] args) {
		// Instantiate an actor system
		final ActorSystem system = ActorSystem.create("system");
		
		// Instantiate first and second actor
	    final ActorRef a1 = system.actorOf(FirstActor.createActor(), "a1");
		final ActorRef a2 = system.actorOf(SecondActor.createActor(), "a2");
		final ActorRef a3 = system.actorOf(ThirdActor.createActor(), "a3");
		final ActorRef a4 = system.actorOf(FourthActor.createActor(), "a4");
		final ActorRef merger = system.actorOf(ConvergeCaster.createActor(), "merger");
	    
			// send to a1 the reference of a2 by message
			//be carefull, here it is the main() function that sends a message to a1, 
            //not a1 telling to a2 as you might think when looking at this line:
	   
		// References
		a1.tell(merger, ActorRef.noSender());
		a2.tell(merger, ActorRef.noSender());
		a3.tell(merger, ActorRef.noSender());
		merger.tell(a4, ActorRef.noSender());
		
		
		a1.tell("join", ActorRef.noSender());
		a2.tell("join", ActorRef.noSender());
		a3.tell("join", ActorRef.noSender());

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			system.terminate();
		}

		a1.tell("go", ActorRef.noSender());
		a2.tell("go", ActorRef.noSender());
		a3.tell("go", ActorRef.noSender());
		

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
