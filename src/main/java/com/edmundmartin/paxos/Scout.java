package com.edmundmartin.paxos;

import com.edmundmartin.paxos.messages.*;

import java.util.HashSet;
import java.util.Set;

public class Scout extends Process {
    ProcessId leader;
    ProcessId[] acceptors;
    BallotNumber ballotNumber;

    public Scout(Env env, ProcessId me, ProcessId leader, ProcessId[] acceptors, BallotNumber ballotNumber) {
        this.env = env;
        this.me = me;
        this.acceptors = acceptors;
        this.leader = leader;
        this.ballotNumber = ballotNumber;
        env.addProc(me, this);
    }


    @Override
    void action() {
        P1aMessage message = new P1aMessage(me, ballotNumber);
        Set<ProcessId> waitFor = new HashSet<>();
        for (ProcessId pid: acceptors) {
            sendMessage(pid, message);
            waitFor.add(pid);
        }

        Set<PValue> pValues = new HashSet<>();
        while (2 * waitFor.size() >= acceptors.length) {
            PaxosMessage msg = getNextMessage();

            if (msg instanceof P1bMessage) {
                P1bMessage m = (P1bMessage) msg;

                int cmp = ballotNumber.compareTo(m.getBallotNumber());
                if (cmp != 0) {
                    sendMessage(leader, new PreemptedMessage(me, m.getBallotNumber()));
                    return;
                }
                if (waitFor.contains(m.src)) {
                    waitFor.remove(m.src);
                    pValues.addAll(m.getAccepted());
                }
            } else {
                System.err.println("Scout: unexpected msg");
            }
        }

        sendMessage(leader, new AdoptedMessage(me, ballotNumber, pValues));
    }
}
