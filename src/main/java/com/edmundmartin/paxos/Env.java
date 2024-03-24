package com.edmundmartin.paxos;

import com.edmundmartin.paxos.messages.PaxosMessage;
import com.edmundmartin.paxos.messages.RequestMessage;

import java.util.HashMap;
import java.util.Map;

public class Env {
    Map<ProcessId, Process> procs = new HashMap<>();
    public final static int nAcceptors = 3;
    public final static int nReplicas = 2;
    public final static int nLeaders = 2;
    public final static int nRequests = 100;

    synchronized void sendMessage(ProcessId dst, PaxosMessage msg) {
        Process p = procs.get(dst);
        if (p != null) {
            p.deliver(msg);
        }
    }

    synchronized void addProc(ProcessId pid, Process process) {
        procs.put(pid, process);
        process.start();
    }

    synchronized void removeProc(ProcessId pid) {
        procs.remove(pid);
    }

    void run(String[] args) {
        ProcessId[] acceptors = new ProcessId[nAcceptors];
        ProcessId[] replicas = new ProcessId[nReplicas];
        ProcessId[] leaders = new ProcessId[nLeaders];

        for (int i = 0; i < nAcceptors; i++) {
            acceptors[i] = new ProcessId("acceptor:" + i);
            Acceptor acc = new Acceptor(this, acceptors[i]);
        }
        for (int i = 0; i < nReplicas; i++) {
            replicas[i] = new ProcessId("replica:" + i);
            Replica replica = new Replica(this, replicas[i], leaders);
        }
        for (int i = 0; i < nLeaders; i++) {
            leaders[i] = new ProcessId("leader:" + i);
            Leader leader = new Leader(this, leaders[i], acceptors, replicas);
        }

        for (int i = 1; i < nRequests; i++) {
            ProcessId pid = new ProcessId("client:" + i);
            for (int r = 0; r < nReplicas; r++) {
                sendMessage(replicas[r], new RequestMessage(pid, new Command(pid, 0, "operation" + i)));
            }
        }
    }

    public static void main(String[] args) {
        new Env().run(args);
    }
}
