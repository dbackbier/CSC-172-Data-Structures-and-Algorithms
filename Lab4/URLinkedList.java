import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class URLinkedList<elem> implements URList<elem> {
    
    private URNode<elem> head; // Head (first element) of the list
    private URNode<elem> tail; // Tail (last element) of the list
    private int size; // Number of elements in the list

    // Constructor
    public URLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    public void indexCheck(int i) { // make sure index is 0 <= i < size, used anytime parameter includes an index
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException("Index: " + i + " is out of bounds.");
        }
    }

    // Appends the specified element to the end of this list
    public boolean add(elem e) {
        if (head == null) {
            URNode<elem> newNode = new URNode<>(e, null, null);
            head = newNode;
            tail = newNode;
        } else {
            URNode<elem> newNode = new URNode<>(e, tail, null);
            tail.setNext(newNode);
            tail = newNode;
        }
        size++;
        return true; // was able to add e
    }

	// Inserts the specified element at the specified position in this list 
	public void add(int index, elem element) {
        if (size == 0 || index == size) { // if list is empty or index is at the end, just add to end
            add(element);
        } else {
            URNode<elem> current = head;
            indexCheck(index);
            for (int i = 0; i <= index && current != null; i++) {
                if (i == index) {
                    URNode<elem> newNode = new URNode<>(element, current.prev(), current);
                    if (current.prev() != null) { // if current is not the head
                        current.prev().setNext(newNode);
                    } else {
                        head = newNode; // Update head if inserting at the beginning
                    }
                    current.setPrev(newNode);
                    size++;
                    return; // element was added, so return
                }
                current = current.next();
            }
        }
    }

	// Appends all of the elements in the specified collection to the end of this list,
	// in the order that they are returned by the specified collection's iterator 
	public boolean addAll(Collection<? extends elem> c) {
        for (elem e : c) { add(e); }
        return true; // was able to add all e from c
    }

	// Inserts all of the elements in the specified collection into this list 
	// at the specified position
	public boolean addAll(int index, Collection<? extends elem> c) {
        if (size == 0) {
            addAll(c);
            return true; // was able to add all e from c at index
        } else {
            indexCheck(index);
            int i = index;
            for (elem e : c) {
                add(i, e);
                i++;
            }
            return true; // was able to add all e from c at index
        }
        
    }

	// Removes all of the elements from this list 
	public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

	// Returns true if this list contains the specified element.
	public boolean contains(Object o) {
        if (isEmpty()) { return false; } // does not contain, as it's empty
        URNode<elem> current = head;
        for (int i = 0; i < size; i++) {
            if (current.element() == o) { return true; } // o was found, so true
            current = current.next();
        }
        return false; // o was not found, so false
    }

	// Returns true if this list contains all of the elements of the specified collection
	public boolean containsAll(Collection<?> c) {
        if (c.isEmpty()) { return true; } // c is empty, so true
        else if (isEmpty() && !c.isEmpty()) { return false; } // list is empty, but c is not, so false
        else {
            for (Object o : c) {
                if (!contains(o)) { return false; } // list does not contain o from c, so false
            }
            return true; // list contains all elements from c, so true
        }
    }

	// Compares the specified object with this list for equality. 
	// Returns true if both contain the same elements. Ignore capacity
	public boolean equals(Object o) {
        URLinkedList<elem> newList = (URLinkedList<elem>) o;
        URNode<elem> current1 = head;
        URNode<elem> current2 = newList.head;
        for (int i = 0; i < size; i++) {
            if (current1.element() != current2.element()) { return false; } // one of the elements is not the same, so false
            current1 = current1.next();
            current2 = current2.next();
        }
        return true; // all elements are the same, so true
    }

	// Returns the element at the specified position in this list.
	public elem get(int index) {
        URNode<elem> current = head;
        indexCheck(index);
        for (int i = 0; i < size; i++) {
            if (i == index) { return current.element(); } // returns the element at index if found
            current = current.next();
        }
        return null; // not found
    }

	// Returns the index of the first occurrence of the specified element in this list, 
	// or -1 if this list does not contain the element.
	public int indexOf(Object o) {
        URNode<elem> current = head;
        for (int i = 0; i < size; i++) {
            if (current.element() == o) { return i; } // returns the index of first occurrence if found
            current = current.next();
        }
        return -1; // returns -1 if not found
    }

	// Returns true if this list contains no elements.
	public boolean isEmpty() {
        return (size == 0); // returns true if size is 0, false otherwise
    }

	// Returns an iterator over the elements in this list in proper sequence.
	public Iterator<elem> iterator() {
        return new LLIterator();
    }

    public class LLIterator implements Iterator<elem> {
        private URNode<elem> current;

        public LLIterator() {
            current = head;
        }

        public boolean hasNext() {
            return current != null;
        }

        public elem next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements to iterate.");
            }
            elem value = current.element();
            current = current.next();
            return value;
        }
    }

	// Removes the element at the specified position in this list 
	public elem remove(int index) {
        indexCheck(index);
        URNode<elem> current = head;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                if (current.prev() == null) { // if current is the head
                    head = current.next();
                } else {
                    current.prev().setNext(current.next());
                }
                if (current.next() == null) { // if current is the tail
                    tail = current.prev();
                } else {
                    current.next().setPrev(current.prev());
                }
                size--;
                return current.element(); // returns the removed element
            }
            current = current.next();
        }
        return null; // not in list
    }

	// Removes the first occurrence of the specified element from this list,
	// if it is present 
	public boolean remove(Object o) {
        URNode<elem> current = head;
        for (int i = 0; i < size; i++) {
            if (current.element() == o) {
                remove(i);
                return true; // element was found and removed, so true
            }
            current = current.next();
        }
        return false; // can't remove an element that is not in the list, so false
    }

	// Removes from this list all of its elements that are contained
	//  in the specified collection
	public boolean removeAll(Collection<?> c) {
        if (c.isEmpty()) { return true; } // nothing to remove, so true
        else if (isEmpty() && !c.isEmpty()) { return false; } // nothing to remove from, so false
        else if (c.size() > size) { return false; } // can't remove more than is in the list, so false
        else {
            for (Object o : c) {
                if (!remove(o)) { return false; } // didnt remove one of the elements, so instantly return false
            }
            return true; // was able to remove all elements, so true
        }
    }

	// Replaces the element at the specified position in this list
	// with the specified element 
	public elem set(int index, elem element) {
        indexCheck(index);
        URNode<elem> current = head;
        for (int i = 0; i < size; i++) {
            if (i == index) { return current.setElement(element); } // sets the element at index and returns the old element
            current = current.next();
        }
        return null; // not found
    }

	// Returns the number of elements in this list.
	public int size() {
        return size;
    }

	// Returns a view of the portion of this list 
	// between the specified fromIndex, inclusive, and toIndex, exclusive.
	public URList<elem> subList(int fromIndex, int toIndex) {
        indexCheck(toIndex - 1); // toIndex is exclusive, so check toIndex - 1
        indexCheck(fromIndex);
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException("fromIndex: " + fromIndex + " is greater than toIndex: " + toIndex);
        }
        URLinkedList<elem> subList = new URLinkedList<>();
        URNode<elem> current = head;
        for (int i = 0; i < size; i++) {
            if (i >= fromIndex && i < toIndex) {
                subList.add(current.element());
            }
            current = current.next();
        }
        return subList; // returns the completed sub list
    }


	// Returns an array containing all of the elements in this list
	//  in proper sequence (from first to the last element).
	public Object[] toArray() {
        Object [] newArray = new Object[size];
        URNode<elem> current = head;
        for (int i = 0; i < size; i++) {
            newArray[i] = current.element();
            current = current.next();
        }
        return newArray; // returns the list in array form
    }

    // Methods for URLinkedList only
    // Inserts the specified element at the beginning of this list.
    public void addFirst(elem e) {
        add(0, e);
    }
    // Appends the specified element to the end of this list.
    public void addLast(elem e) {
        add(e);
    }
    // Retrieves, but does not remove, the first element of this list, or returns null if this list is empty.
    public elem peekFirst() {
        if (head == null) { return null; } // list is empty so returns null
        return head.element(); // returns the first element
    }
    // Retrieves, but does not remove, the last element of this list, or returns null if this list is empty.
    public elem peekLast() {
        if (tail == null) { return null; } // list is empty so returns null
        return tail.element(); // returns the last element
    }
    // Retrieves and removes the first element of this list, or returns null if this list is empty.
    public elem pollFirst() {
        if (head == null) { return null; } // list is empty so returns null
        elem value = head.element();
        head = head.next();
        if (head != null) { head.setPrev(null); } 
        else { tail = null; } // List is now empty
        size--;
        return value; // returns value of the removed element
    }

    // Retrieves and removes the last element of this list, or returns null if this list is empty.
    public elem pollLast() {
        if (head == null) { return null; } // list is empty so returns null
        elem value = tail.element();
        tail = tail.prev();
        if (tail != null) { tail.setNext(null); } 
        else { head = null; } // List is now empty
        size--;
        return value; // returns value of the removed element
    }
}