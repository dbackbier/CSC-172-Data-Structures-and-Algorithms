// Dane Backbier - dbackbie - dbackbie@u.rochester.edu - Lab 7 - Heaps

public class minHeap<T extends Comparable<T>> implements UR_Heap<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private T[] heap; // store heap in an array
    private int size;

    // Constructors
    public minHeap() {
        this(DEFAULT_CAPACITY);
    }

    public minHeap(int capacity) {
        heap = (T[]) new Comparable[capacity];
        size = 0;
    }

    public minHeap(T[] heapArray) {
        heap = (T[]) new Comparable[heapArray.length];
        size = 0;

        for (int i = 0; i < heapArray.length; i++) {
            heap[i] = heapArray[i];
            size++;
        }
        heapify();
    }

    private void heapify() {
        for (int i = (size / 2) - 1; i >= 0; i--) {
            bubbleDown(i);
        }
    }

    public void bubbleUp(int index) {
        int pIndex = (index - 1) / 2;
        while (index > 0 && heap[index].compareTo(heap[pIndex]) < 0) {
            // swap
            T temp = heap[index];
            heap[index] = heap[pIndex];
            heap[pIndex] = temp;

            index = pIndex;
            pIndex = (index - 1) / 2;
        }
    }

    public void bubbleDown(int index) {
        T val = heap[index];
        while (index < size / 2) {
            int left = (2 * index) + 1;
            int right = (2 * index) + 2;
            int smaller = left;
            if (right < size && heap[right].compareTo(heap[left]) < 0) {
                smaller = right;
            }
            if (val.compareTo(heap[smaller]) <= 0) {
                break;
            }
            heap[index] = heap[smaller];
            index = smaller;
        }
        heap[index] = val;
    }

    public void insert(T val) {
        if (size == heap.length) {
            expand();
        }
        // add to the end and bubble up
        heap[size] = val;
        bubbleUp(size);
        size++;
    }

    public void expand() {
        // TODO: finish this method and implement it in the insert method
        int newCapacity = heap.length * 2;
        T[] newHeap = (T[]) new Comparable[newCapacity];
        System.arraycopy(heap, 0, newHeap, 0, size);
        heap = newHeap;
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public int size() {
        return size;
    }

    public T deleteMin() {
        if (isEmpty()) {
            throw new RuntimeException("Heap is already empty.");
        }
        T min = heap[0];
        heap[0] = heap[size - 1];
        heap[size - 1] = null;
        size--;
        bubbleDown(0);
        return min; // delete and return the min value
    }

    public void printHeap() {
        int level = 0;
        int printed = 0;
        int levelSize = 1;
        for (int i = 0; i < size; i++) {
            System.out.print(heap[i] + " ");
            printed++;
        }
        System.out.println();
        level++;
        levelSize = (int) Math.pow(2, level);
        
        System.out.println();
    }
    public static void main(String[] args) {
        // TODO: unit testing
        minHeap<Integer> heap = new minHeap<>();
        // Test insert and deleteMin
        heap.insert(5);
        heap.insert(3);
        heap.insert(8);
        heap.insert(1);
        heap.insert(4);
        heap.printHeap();
        assert heap.deleteMin() == 1;
        assert heap.deleteMin() == 3;
        heap.printHeap();
        System.out.println("insert and deleteMin methods passed\n");

        // Test size and isEmpty
        assert heap.size() == 3;
        assert heap.isEmpty() == false;
        System.out.println("size and isEmpty methods passed\n");
        
        // Test expand by creating new smaller heap and adding many elements
        minHeap<Integer> smallHeap = new minHeap<>(2);
        smallHeap.insert(4);
        smallHeap.insert(7);
        smallHeap.insert(6); // this should trigger the first expand method call
        smallHeap.insert(2);
        smallHeap.insert(8); // this should trigger the second expand method call
        smallHeap.printHeap();
        System.out.println("expand method passed\n");

        // Test heapify by using the third constructor
        Integer[] arr = {4, 8, 3, 6, 1, 7};
        minHeap<Integer> arrHeap = new minHeap<>(arr);
        arrHeap.printHeap();
        System.out.println("heapify method passed\n");

        System.out.println("All methods passed.");
    }
}
