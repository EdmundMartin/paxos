package com.edmundmartin.paxos.messages;


import com.edmundmartin.paxos.BallotNumber;
import com.edmundmartin.paxos.ProcessId;

public class PreemptedMessage extends PaxosMessage {
    BallotNumber ballotNumber;

    public PreemptedMessage(ProcessId src, BallotNumber ballotNumber) {
        this.src = src;
        this.ballotNumber = ballotNumber;
    }

    public BallotNumber getBallotNumber() {
        return ballotNumber;
    }
}
