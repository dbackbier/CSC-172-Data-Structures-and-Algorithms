// Dane Backbier - dbackbie - dbackbie@u.rochester.edu - Project 1 - Infix Calculator

import java.util.EmptyStackException;

public class URStack<T> {
	private URLinkedList<T> list;

    public URStack() {
        list = new URLinkedList<T>();
    }

    public void push(T e) {
        list.addFirst(e);
    }

    public T pop() {
        if (list.size() == 0) {
            throw new EmptyStackException();
        }
        return list.remove(0);
    }

    public boolean isEmpty() {
        return (list.isEmpty());
    }

    public int size() {
        return list.size();
    }

    public T peek() {
        return list.peekFirst();
    }

    public String toString() {
        return list.toString();
    }
}
