// Dane Backbier - dbackbie - dbackbie@u.rochester.edu - Lab 7 - Heaps

public interface UR_Heap<T extends Comparable<T>> {
    public void insert(T item);
    public boolean isEmpty();
    public int size();
    public T deleteMin();
}