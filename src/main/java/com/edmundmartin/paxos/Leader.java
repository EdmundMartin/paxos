package com.edmundmartin.paxos;

import com.edmundmartin.paxos.messages.AdoptedMessage;
import com.edmundmartin.paxos.messages.PaxosMessage;
import com.edmundmartin.paxos.messages.PreemptedMessage;
import com.edmundmartin.paxos.messages.ProposeMessage;

import java.util.HashMap;
import java.util.Map;

public class Leader extends Process {
    ProcessId[] acceptors;
    ProcessId[] replicas;
    BallotNumber ballotNumber;
    boolean active = false;
    Map<Integer, Command> proposals = new HashMap<>();

    public Leader(Env env, ProcessId me, ProcessId[] acceptors, ProcessId[] replicas) {
        this.env = env;
        this.me = me;
        ballotNumber = new BallotNumber(0, me);
        this.acceptors = acceptors;
        this.replicas = replicas;
        env.addProc(me, this);
    }


    @Override
    void action() {
        System.out.println("Leader - here I am: " + me);

        new Scout(env, new ProcessId("scout:" + me + ":" + ballotNumber), me, acceptors, ballotNumber);
        for (;;) {
            PaxosMessage msg = getNextMessage();

            if (msg instanceof ProposeMessage) {
                ProposeMessage m = (ProposeMessage) msg;
                if (!proposals.containsKey(m.getSlotNumber())) {
                    proposals.put(m.getSlotNumber(), m.getCommand());

                    if (active) {
                        new Commander(
                                env,
                                new ProcessId ( " commander :" + me + " :" + ballotNumber + ": " + m.getSlotNumber()),
                                me, acceptors, replicas, ballotNumber, m.getSlotNumber(), m.getCommand()
                        );
                    }
                }
            } else if (msg instanceof AdoptedMessage) {
                AdoptedMessage m = (AdoptedMessage) msg;

                if (ballotNumber.equals(m.getBallotNumber())) {
                    Map<Integer, BallotNumber> max = new HashMap<>();
                    for (PValue pv: m.getAccepted()) {
                        max.put(pv.slotNumber, pv.ballotNumber);
                        proposals.put(pv.slotNumber, pv.command);
                    }
                }

                for (int sn: proposals.keySet()) {
                    new Commander(
                            env,
                            new ProcessId ( " commander :" + me + " :" + ballotNumber + ": " + sn),
                            me, acceptors, replicas, ballotNumber, sn, proposals.get(sn)
                    );
                }
                active = true;
            } else if (msg instanceof PreemptedMessage) {
                PreemptedMessage m = (PreemptedMessage) msg;
                if (ballotNumber.compareTo(m.getBallotNumber()) < 0) {
                    ballotNumber = new BallotNumber(m.getBallotNumber().round + 1, me);
                    new Scout ( env , new ProcessId ( " scout :" + me + ": " + ballotNumber ),
                            me , acceptors , ballotNumber);
                    active = false;
                }
            } else {
                System.err.println("Leader: unknown msg type");
            }
        }
    }
}
