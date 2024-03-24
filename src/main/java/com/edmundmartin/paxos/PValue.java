package com.edmundmartin.paxos;

public class PValue {
    BallotNumber ballotNumber;
    int slotNumber;
    Command command;

    public PValue(BallotNumber ballotNumber, int slotNumber, Command command) {
        this.ballotNumber = ballotNumber;
        this.slotNumber = slotNumber;
        this.command = command;
    }

    @Override
    public String toString() {
        return "PValue{" +
                "ballotNumber=" + ballotNumber +
                ", slotNumber=" + slotNumber +
                ", command=" + command +
                '}';
    }
}
