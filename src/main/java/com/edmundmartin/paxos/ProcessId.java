package com.edmundmartin.paxos;


public class ProcessId implements Comparable {
    String name;

    public ProcessId(String name) {
        this.name = name;
    }

    public boolean equals(Object other) {
        return name.equals(((ProcessId) other).name);
    }

    @Override
    public int compareTo(Object other) {
        return name.compareTo(((ProcessId) other).name);
    }

    @Override
    public String toString() {
        return name;
    }
}

