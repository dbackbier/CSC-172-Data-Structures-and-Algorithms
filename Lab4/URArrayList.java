import java.util.Collection;
import java.util.Iterator;

public class URArrayList <elem> implements URList<elem>{ 
    
    private elem[] array;
    private int size; // Number of elements in the list
    private int max;

    // Constructor
    public URArrayList() {
        max = 4;
        size = 0;
        array = (elem[]) new Object[max];
    }

    public void resize(int newMax) {
        Object[] newArray = new Object[newMax];
        for (int i = 0; i < size; i++) {
            newArray[i] = array[i];
        }
        array = (elem[]) newArray;
        max = newMax;
    }

    public void indexCheck(int i) { // make sure index is 0 <= i < size, used anytime parameter includes an index
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException("Index: " + i + " is out of bounds.");
        }
    }

    // Appends the specified element to the end of this list
    public boolean add(elem e) {
        if (size == max) { resize(max * 2); }
        array[size] = e;
        size++;
        return true; // was able to add e, so true
    }

	// Inserts the specified element at the specified position in this list 
	public void add(int index, elem element) {
        indexCheck(index);
        if (size == max) { resize(max*2); }
        for (int i = size; i > index; i--) {
            array[i] = array[i - 1];
        }
        array[index] = element;
        size++;
    }

	// Appends all of the elements in the specified collection to the end of this list,
	// in the order that they are returned by the specified collection's iterator 
	public boolean addAll(Collection<? extends elem> c) {
        for (elem e : c) { add(e); }
        return true; // was able to add all e, so true
    }

	// Inserts all of the elements in the specified collection into this list 
	// at the specified position
	public boolean addAll(int index, Collection<? extends elem> c) {
        indexCheck(index);
        for (elem e : c) {
            add(index, e);
            index++;
        }
        return true; // was able to add all e at index, so true
    }

	// Removes all of the elements from this list 
	public void clear() {
        size = 0;
        array = (elem[]) new Object[max];
    }

	// Returns true if this list contains the specified element.
	public boolean contains(Object o) {
        if (isEmpty()) { return false; } // does not contain, as it's empty
        for (int i = 0; i < size; i++) {
            if (array[i] == o) { return true; } // o was found, so true
        }
        return false; // o was not found, so false
    }

	// Returns true if this list contains all of the elements of the specified collection
	public boolean containsAll(Collection<?> c) {
        if (c.isEmpty()) {
            return true; // c is empty, so true
        } else if (isEmpty() && !c.isEmpty()) {
            return false; // list is empty, but c is not, so false
        } else {
            for (Object o : c) {
                if (!contains(o)) { return false; } // list does not contain o from c, so false
            }
            return true; // list contains all elements from c, so true
        }
    }

	// Compares the specified object with this list for equality. 
	// Returns true if both contain the same elements. Ignore capacity
	public boolean equals(Object o) {
        URArrayList<elem> newList = (URArrayList<elem>) o;
        if (newList.size() != size) { return false; }
        for (int i = 0; i < size; i++) {
            if (newList.get(i) != array[i]) { return false; } // one of the elements is not the same, so false
        }
        return true; // all elements are the same, so true
    }

	// Returns the element at the specified position in this list.
	public elem get(int index) {
        indexCheck(index);
        return array[index]; // returns the value at index
    }

	// Returns the index of the first occurrence of the specified element in this list, 
	// or -1 if this list does not contain the element.
	public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (array[i] == o) { return i; } // returns the index of first occurrence if found
        }
        return -1; // returns -1 if not found
    }

	// Returns true if this list contains no elements.
	public boolean isEmpty() {
        return (size == 0);
    }

	// Returns an iterator over the elements in this list in proper sequence.
	public Iterator<elem> iterator() {
        return new ALIterator();
    }

    public class ALIterator implements Iterator<elem> {
        private int index;

        public ALIterator() {
            index = 0;
        }

        public boolean hasNext() {
            return (index < size);
        }

        public elem next() {
            if (!hasNext()) {
                throw new IndexOutOfBoundsException("No more elements to iterate.");
            }
            return array[index++];
        }
    }

	// Removes the element at the specified position in this list 
	public elem remove(int index) {
        indexCheck(index);
        for (int i = index; i < size - 1; i++) {
            array[i] = array[i + 1];
        }
        size--;
        return array[index]; // returns the value of the removed element
    }

	// Removes the first occurrence of the specified element from this list,
	// if it is present 
	public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (array[i] == o) {
                remove(i);
                return true; // element was found and removed, so true
            }
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
        elem old = array[index];
        array[index] = element;
        return old; // returns the old element that was replaced
    }

	// Returns the number of elements in this list.
	public int size() {
        return size;
    }

	// Returns a view of the portion of this list 
	// between the specified fromIndex, inclusive, and toIndex, exclusive.
	public URList<elem> subList(int fromIndex, int toIndex) {
        indexCheck(toIndex - 1);
        indexCheck(fromIndex);
        if (fromIndex > toIndex) {
            throw new IndexOutOfBoundsException("fromIndex: " + fromIndex + " is greater than toIndex: " + toIndex);
        }
        URArrayList<elem> subList = new URArrayList<>();
        for (int i = fromIndex; i < toIndex; i++) {
            subList.add(array[i]);
        }
        return subList; // returns the completed sub list
    }


	// Returns an array containing all of the elements in this list
	//  in proper sequence (from first to the last element).
	public Object[] toArray() {
        Object[] newArray = new Object[size];
        for (int i = 0; i < size; i++) { newArray[i] = array[i]; }
        return newArray; // returns the array
    }

}
