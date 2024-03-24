package com.edmundmartin.paxos.messages;

import com.edmundmartin.paxos.BallotNumber;
import com.edmundmartin.paxos.Command;
import com.edmundmartin.paxos.ProcessId;

public class P2aMessage extends PaxosMessage {
    public BallotNumber ballotNumber;
    int slotNumber;
    Command command;

    public P2aMessage(ProcessId src, BallotNumber ballotNumber, int slotNumber, Command command) {
        this.src = src;
        this.ballotNumber = ballotNumber;
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
