package com.edmundmartin.paxos.messages;

import com.edmundmartin.paxos.Command;
import com.edmundmartin.paxos.ProcessId;

public class RequestMessage extends PaxosMessage {
    public Command command;

    public RequestMessage(ProcessId src, Command command) {
        this.src = src;
        this.command = command;
    }
}
