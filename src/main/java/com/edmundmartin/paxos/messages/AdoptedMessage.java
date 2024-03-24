package com.edmundmartin.paxos.messages;

import com.edmundmartin.paxos.BallotNumber;
import com.edmundmartin.paxos.PValue;
import com.edmundmartin.paxos.ProcessId;

import java.util.Set;

public class AdoptedMessage extends PaxosMessage {
    BallotNumber ballotNumber;
    Set<PValue> accepted;

    public AdoptedMessage(ProcessId src, BallotNumber ballotNumber, Set<PValue> accepted) {
        this.src = src;
        this.ballotNumber = ballotNumber;
        this.accepted = accepted;
    }

    public BallotNumber getBallotNumber() {
        return ballotNumber;
    }

    public Set<PValue> getAccepted() {
        return accepted;
    }
}
