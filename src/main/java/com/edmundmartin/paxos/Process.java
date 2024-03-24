package com.edmundmartin.paxos;

import com.edmundmartin.paxos.messages.PaxosMessage;

public abstract class Process extends Thread {
    Env env;
    ProcessId me;
    Queue<PaxosMessage> inbox = new Queue<>();

    abstract void action();

    public void run() {
        action();
        env.removeProc(me);
    }

    PaxosMessage getNextMessage() {
        return inbox.dequeue();
    }

    void sendMessage(ProcessId dst, PaxosMessage message) {
        env.sendMessage(dst, message);
    }

    void deliver(PaxosMessage msg) {
        inbox.enqueue(msg);
    }
}
