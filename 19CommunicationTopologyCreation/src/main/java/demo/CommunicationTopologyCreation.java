package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.ActorSelection;
import akka.actor.ActorIdentity;
import akka.actor.Identify;

public class CommunicationTopologyCreation {

	public static void main(String[] args) {
		// Instantiate an actor system
		final ActorSystem system = ActorSystem.create("system");
		int[][] adjacency_matrix = {{0, 1, 1, 0},
									{0, 0, 0, 1},
									{1, 0, 0, 1},
									{1, 0, 0, 1}
									};

		// Instantiate first and second actor
		ActorRef a[] = new ActorRef[adjacency_matrix.length];

		/*
		final ActorRef a1 = system.actorOf(FirstActor.createActor(), "a1");
		final ActorRef a2 = system.actorOf(FirstActor.createActor(), "a2");
		final ActorRef a3 = system.actorOf(FirstActor.createActor(), "a3");
		final ActorRef a4 = system.actorOf(FirstActor.createActor(), "a4");
		*/
		for(int i = 0; i < adjacency_matrix.length; i++){
			a[i] = system.actorOf(FirstActor.createActor(), "a" + (i + 1));
		}
		

		// References
		for(int i = 0; i < adjacency_matrix.length; i++){
			for(int j = 0; j < adjacency_matrix[i].length; j++){
				if( adjacency_matrix[i][j] == 1){
					a[i].tell(a[j], ActorRef.noSender());
				}
			}
		}
		

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
