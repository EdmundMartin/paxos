package com.edmundmartin.paxos;

public class Command {
    ProcessId client;
    int requestId;
    Object operation;

    public Command(ProcessId client, int requestId, Object operation) {
        this.client = client;
        this.requestId = requestId;
        this.operation = operation;
    }

    public boolean equals(Object o) {
        Command other = (Command) o;
        return client.equals(other.client) && requestId == other.requestId && operation.equals(other.operation);
    }

    public String toString() {
        return "Command(" + client + "," + requestId + "," + operation + ")";
    }
}
