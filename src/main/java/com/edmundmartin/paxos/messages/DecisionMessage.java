package com.edmundmartin.paxos.messages;

import com.edmundmartin.paxos.Command;
import com.edmundmartin.paxos.ProcessId;

public class DecisionMessage extends PaxosMessage {
    public int slotNumber;
    public Command command;

    public DecisionMessage(ProcessId src, int slotNumber, Command command) {
        this.src = src;
        this.slotNumber = slotNumber;
        this.command = command;
    }
}
