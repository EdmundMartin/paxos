package com.edmundmartin.paxos;

import java.util.LinkedList;

public class Queue<T> {
    LinkedList<T> linkedList = new LinkedList<>();

    public synchronized void enqueue(T obj) {
        linkedList.add(obj);
        notify();
    }

    public synchronized T dequeue() {
        while (linkedList.size() == 0) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        return linkedList.removeFirst();
    }
}
