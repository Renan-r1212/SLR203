package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Group{

    private ActorRef receiver_A, receiver_B;

    public Group() {
        this.receiver_A = null;
        this.receiver_B = null;
    }

    public Group(ActorRef receiver_A, ActorRef receiver_B){
        this.receiver_A = receiver_A;
        this.receiver_B = receiver_B;
    }


    // Getters
    public ActorRef getReceiver_A(){
        return this.receiver_A;
    }

    public ActorRef getReceiver_B(){
        return this.receiver_B;
    }
    

    // Setters
    public void setReceiver_A(ActorRef receiver_A){
        this.receiver_A = receiver_A;
    }

    public void setReceiver_B(ActorRef receiver_B){
        this.receiver_B = receiver_B;
    }
}
