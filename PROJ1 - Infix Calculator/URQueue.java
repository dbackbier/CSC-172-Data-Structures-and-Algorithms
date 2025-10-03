// Dane Backbier - dbackbie - dbackbie@u.rochester.edu - Project 1 - Infix Calculator

import java.util.EmptyStackException;

public class URQueue<T> {
    private URLinkedList<T> list;

    public URQueue() {
        list = new URLinkedList<T>();
    }

    public void enqueue(T e) {
        list.addLast(e);
    }

    public T dequeue() {
        if (list.size() == 0) {
            throw new EmptyStackException();
        }
        return list.remove(0);
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public int size() {
        return list.size();
    }

    public String toString() {
        return list.toString();
    }

    public Object[] toArray() {
        return list.toArray();
    }
}
