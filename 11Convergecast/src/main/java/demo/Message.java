package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Message{

    private String hi_a1, hi_a2, hi_a3;

    public Message() {
        this.hi_a1 = null;
        this.hi_a2 = null;
        this.hi_a3 = null;
    }

    public Message(String hi_a1, String hi_a2, String hi_a3){
        this.hi_a1 = hi_a1;
        this.hi_a2 = hi_a2;
        this.hi_a3 = hi_a3;
    }


    // Getters
    public String getHi_a1(){
        return this.hi_a1;
    }

    public String getHi_a2(){
        return this.hi_a2;
    }

    public String getHi_a3(){
        return this.hi_a3;
    }
    

    // Setters
    public void setHi_a1(String hi_a1){
        this.hi_a1 = hi_a1;
    }

    public void setHi_a2(String hi_a2){
        this.hi_a2 = hi_a2;
    }

    public void setHi_a3(String hi_a3){
        this.hi_a3 = hi_a3;
    }
}
