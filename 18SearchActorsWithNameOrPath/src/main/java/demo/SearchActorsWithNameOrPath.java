package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.ActorSelection;
import akka.actor.ActorIdentity;
import akka.actor.Identify;

public class SearchActorsWithNameOrPath {

	public static void main(String[] args) {
		// Instantiate an actor system
		final ActorSystem system = ActorSystem.create("system");
		ActorSelection selection;
		
		// Instantiate first and second actor
		final ActorRef a = system.actorOf(ActorCustomCreator.createActor(), "a");
		final ActorRef actor_path = system.actorOf(ActorCustomCreator.createActor(), "actor_path");
		final Integer identifyId = 1;
		
		// References
		a.tell("create", ActorRef.noSender());	
		
		a.tell("create", ActorRef.noSender());


		selection = system.actorSelection("/user/" + "a");
	    selection.tell(new Identify(identifyId), actor_path);
		selection = system.actorSelection("/user/a/" + "actor1");
		
	    selection.tell(new Identify(identifyId), actor_path);
		selection = system.actorSelection("/user/a/" + "actor2");
		
	    selection.tell(new Identify(identifyId), actor_path);
		selection = system.actorSelection("/user/a");
		
	    selection.tell(new Identify(identifyId), actor_path);
		selection = system.actorSelection("/user/a/actor1");
		
	    selection.tell(new Identify(identifyId), actor_path);
		selection = system.actorSelection("/user/a/actor2");
		
	    selection.tell(new Identify(identifyId), actor_path);
		selection = system.actorSelection("/user/*");
		
	    selection.tell(new Identify(identifyId), actor_path);
		selection = system.actorSelection("/system/*");
		
	    selection.tell(new Identify(identifyId), actor_path);
		selection = system.actorSelection("/deadLetters/*");
		
	    selection.tell(new Identify(identifyId), actor_path);
		selection = system.actorSelection("/temp/*");
		
	    selection.tell(new Identify(identifyId), actor_path);
		selection = system.actorSelection("/remote/*");
		
	    selection.tell(new Identify(identifyId), actor_path);
		
		

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
