package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Message{

    private Group group;
    private String msg;

    public Message() {
        this.msg = null;
        this.group = null;
    }

    public Message(String msg, Group group){
        this.msg = msg;
        this.group = group;
    }


    // Getters
    public String getMsg(){
        return this.msg;
    }

    public Group getGroup(){
        return this.group;
    }
    

    // Setters
    public void setMsg(String msg){
        this.msg = msg;
    }

    public void setGroup(Group group){
        this.group = group;
    }
}
