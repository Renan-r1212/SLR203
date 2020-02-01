package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class FloodId{

    private String msg;
    private int flood_id;

    public FloodId() {
        this.msg = null;
        this.flood_id = 0;
    }

    public FloodId(String msg, int flood_id){
        this.msg = msg;
        this.flood_id = flood_id;
    }


    // Getters
    public String getMsg(){
        return this.msg;
    }

    public int getFlood_id(){
        return this.flood_id;
    }
    

    // Setters
    public void setMsg(String msg){
        this.msg = msg;
    }

    public void setFlood_id(int flood_id){
        this.flood_id = flood_id;
    }
}
