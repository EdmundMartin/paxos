package com.edmundmartin.paxos;

import com.edmundmartin.paxos.messages.DecisionMessage;
import com.edmundmartin.paxos.messages.PaxosMessage;
import com.edmundmartin.paxos.messages.ProposeMessage;
import com.edmundmartin.paxos.messages.RequestMessage;

import java.util.HashMap;
import java.util.Map;


public class Replica extends Process {
    ProcessId[] leaders;
    int slotNumber = 1;
    Map<Integer, Command> proposals = new HashMap<>();
    Map<Integer, Command> decisions = new HashMap<>();

    public Replica(Env env, ProcessId me, ProcessId[] leaders) {
        this.env = env;
        this.me = me;
        this.leaders = leaders;
        env.addProc(me, this);
    }

    void propose(Command command) {
        if (decisions.containsValue(command)) {
            return;
        }
        for (int s = 1;; s++) {
            if (!proposals.containsKey(s) && !decisions.containsKey(s)) {
                proposals.put(s, command);
                for (ProcessId ldr: leaders) {
                    sendMessage(ldr, new ProposeMessage(me, s, command));
                }
                return;
            }
        }
    }

    void perform(Command command) {
        for (int s = 1; s < slotNumber; s++) {
            if (command.equals(decisions.get(s))) {
                slotNumber++;
                return;
            }
        }
        System.out.println("" + me + ": perform: " + command);
        slotNumber++;
    }

    @Override
    void action() {
        for (;;) {
            System.out.println ( " Here I am : " + me );
            System.out.println(decisions);
            PaxosMessage msg = getNextMessage();

            if (msg instanceof RequestMessage) {
                RequestMessage m = (RequestMessage) msg;
                propose(m.command);
            } else if (msg instanceof DecisionMessage) {
                DecisionMessage m = (DecisionMessage) msg;
                decisions.put(m.slotNumber, m.command);

                for (;;) {
                    Command c = decisions.get(slotNumber);
                    if (c == null) {
                        break;
                    }
                    Command c2 = proposals.get(slotNumber);
                    if (c2 != null && !c2.equals(c)) {
                        propose(c2);
                    }
                    perform(c);
                }
            } else {
                System.err.println("Replica: unknown message type");
            }
        }
    }
}
