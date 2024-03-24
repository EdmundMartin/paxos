package com.edmundmartin.paxos.messages;

import com.edmundmartin.paxos.BallotNumber;
import com.edmundmartin.paxos.ProcessId;

public class P1aMessage extends PaxosMessage {
    public BallotNumber ballotNumber;

    public P1aMessage(ProcessId src, BallotNumber ballotNumber) {
        this.src = src;
        this.ballotNumber = ballotNumber;
    }
}
