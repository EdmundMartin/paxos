package com.edmundmartin.paxos;

import com.edmundmartin.paxos.messages.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Acceptor extends Process {
    BallotNumber ballotNumber = null;
    Set<PValue> accepted = new HashSet<>();

    public Acceptor(Env env, ProcessId me) {
        this.env = env;
        this.me = me;
        env.addProc(me, this);
    }

    public void action() {
        System.out.println("Acceptor: Here I am: " + me);
        for (;;) {
            PaxosMessage msg = getNextMessage();

            if (msg instanceof P1aMessage) {
                P1aMessage m = (P1aMessage) msg;

                if (ballotNumber == null || ballotNumber.compareTo(m.ballotNumber) < 0) {
                    ballotNumber = m.ballotNumber;
                }
                sendMessage(m.src, new P1bMessage(me, ballotNumber, new HashSet<PValue>(accepted)));
            } else if (msg instanceof P2aMessage) {
                P2aMessage m = (P2aMessage) msg;

                if (ballotNumber == null || ballotNumber.compareTo(m.ballotNumber) <= 0) {
                    ballotNumber = m.ballotNumber;
                    accepted.add(new PValue(ballotNumber, m.getSlotNumber(), m.getCommand()));
                }
                sendMessage(m.src, new P2bMessage(me, ballotNumber, m.getSlotNumber()));
            }
        }
    }
}
