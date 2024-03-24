package com.edmundmartin.paxos.messages;

import com.edmundmartin.paxos.BallotNumber;
import com.edmundmartin.paxos.ProcessId;

public class P2bMessage extends PaxosMessage {
    BallotNumber ballotNumber;
    int slotNumber;

    public P2bMessage(ProcessId processId, BallotNumber ballotNumber, int slotNumber) {
        this.src = processId;
        this.ballotNumber = ballotNumber;
        this.slotNumber = slotNumber;
    }

    public BallotNumber getBallotNumber() {
        return ballotNumber;
    }
}
