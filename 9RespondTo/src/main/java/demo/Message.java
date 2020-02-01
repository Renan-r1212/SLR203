package demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Message{

    private String msg;
    private ActorRef actorref;

    public Message() {}

    public Message(ActorRef actorref, String msg){
        this.actorref = actorref;
        this.msg = msg;
    }

    public String getMsg(){
        return this.msg;
    }
    
    public ActorRef getActorref(){
        return this.actorref;
    }

    public void setMsg(String msg){
        this.msg = msg;
    }

    public void setActorref(ActorRef actorref){
        this.actorref = actorref;
    }
}
