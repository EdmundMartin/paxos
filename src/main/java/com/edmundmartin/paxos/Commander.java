package com.edmundmartin.paxos;

import com.edmundmartin.paxos.messages.*;

import java.util.HashSet;
import java.util.Set;

public class Commander extends Process {
    ProcessId leader;
    ProcessId[] acceptors, replicas;
    BallotNumber ballotNumber;
    int slotNumber;
    Command command;

    public Commander(Env env, ProcessId me, ProcessId leader, ProcessId[] acceptors,
                     ProcessId[] replicas, BallotNumber ballotNumber, int slotNumber, Command command) {
        this.env = env;
        this.me = me;
        this.leader = leader;
        this.acceptors = acceptors;
        this.replicas = replicas;
        this.ballotNumber = ballotNumber;
        this.slotNumber = slotNumber;
        this.command = command;
        env.addProc(me, this);
    }


    @Override
    void action() {
        P2aMessage m2 = new P2aMessage(me, ballotNumber, slotNumber, command);
        Set<ProcessId> waitFor = new HashSet<>();
        for (ProcessId pid: acceptors) {
            sendMessage(pid, m2);
            waitFor.add(pid);
        }

        while (2 * waitFor.size() >= acceptors.length) {
            PaxosMessage msg = getNextMessage();

            if (msg instanceof P2bMessage) {
                P2bMessage m = (P2bMessage) msg;

                if (ballotNumber.equals(m.getBallotNumber())) {
                    waitFor.remove(m.src);
                } else {
                    sendMessage(leader, new PreemptedMessage(me, m.getBallotNumber()));
                    return;
                }
            }
        }

        for (ProcessId r: replicas) {
            sendMessage(r, new DecisionMessage(me, slotNumber, command));
        }
    }
}
