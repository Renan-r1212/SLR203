package demo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.ActorSelection;
import akka.actor.ActorIdentity;
import akka.actor.Identify;

public class ControlledFlooding {

	public static void main(String[] args) {
		// Instantiate an actor system
		final ActorSystem system = ActorSystem.create("system");

		int[][] adjacency_matrix_flood = {{0, 1, 1, 0, 0},
								   		  {0, 0, 0, 1, 0},
								   		  {1, 0, 0, 1, 0},
								   		  {1, 0, 0, 0, 1},
								   		  {0, 1, 0, 0, 0}
								   		  };						  
		FloodId floodid = new FloodId("Alahmo", 1);
		// Instantiate first and second actor
		ActorRef b[] = new ActorRef[adjacency_matrix_flood.length];

		// References
		for(int i = 0; i < adjacency_matrix_flood.length; i++){
			b[i] = system.actorOf(FirstActor.createActor(), "b" + (i + 1));
		}
		

		// References
		for(int i = 0; i < adjacency_matrix_flood.length; i++){
			for(int j = 0; j < adjacency_matrix_flood[i].length; j++){
				if( adjacency_matrix_flood[i][j] == 1){
					b[i].tell(b[j], ActorRef.noSender());
				}
			}
		}

		try {
			Thread.sleep(250);
		} catch(Exception e){}

		b[0].tell(floodid, ActorRef.noSender());

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
