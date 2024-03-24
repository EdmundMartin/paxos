package com.edmundmartin.paxos;

public class BallotNumber implements Comparable {
    int round;
    ProcessId leaderId;

    public BallotNumber(int round, ProcessId leaderId) {
        this.round = round;
        this.leaderId = leaderId;
    }

    public boolean equals(Object other) {
        return compareTo(other) == 0;
    }

    @Override
    public int compareTo(Object o) {
        BallotNumber bn = (BallotNumber) o;
        if (bn.round != round) {
            return  round - bn.round;
        }
        return leaderId.compareTo(bn.leaderId);
    }

    public String toString() {
        return "BallotNumber(" + round + "," + leaderId + ")";
    }
}
