package com.edmundmartin.paxos.messages;

import com.edmundmartin.paxos.Command;
import com.edmundmartin.paxos.ProcessId;


public class ProposeMessage extends PaxosMessage {
    int slotNumber;
    Command command;

    public ProposeMessage(ProcessId src, int slotNumber, Command command) {
        this.src = src;
        this.slotNumber = slotNumber;
        this.command = command;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public Command getCommand() {
        return command;
    }
}
