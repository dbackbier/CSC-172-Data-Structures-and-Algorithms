import java.util.*;

public class URALTest {
    public static void main(String[] args) {
        URArrayList<Integer> list = new URArrayList<>();

        // Test add(elem e)
        list.add(10);
        list.add(20);
        list.add(30);
        System.out.println("Elements: " + Arrays.toString(list.toArray()) + "\n"); // also tests toArray()

        // Test add(int index, elem element)
        list.add(0, 5);
        list.add(2, 15);
        list.add(4, 25);
        System.out.println("Elements after adding at indices: " + Arrays.toString(list.toArray()) + "\n"); // expect: [5, 10, 15, 20, 25, 30]

        // Test clear()
        list.clear();
        System.out.println("Elements after clear: " + Arrays.toString(list.toArray()) + "\n"); // expect: []

        // Test addAll(Collection<? extends elem> c)
        list.addAll(Arrays.asList(10, 20, 30, 40, 50));
        System.out.println("Elements after addAll: " + Arrays.toString(list.toArray()) + "\n"); // expect: [10, 20, 30, 40, 50]

        // Test addAll(int index, Collection<? extends elem> c)
        list.addAll(1, Arrays.asList(11, 12, 13, 14, 15));
        System.out.println("Elements after addAll at index 1: " + Arrays.toString(list.toArray()) + "\n"); // expect: [10, 11, 12, 13, 14, 15, 20, 30, 40, 50]

        // Test contains(Object o)
        System.out.println("Contains 20 is " + list.contains(20)); // expect: true
        System.out.println("Contains 0 is " + list.contains(0) + "\n"); // expect: false

        // Test containsAll(Collection<?> c)
        System.out.println("Contains all [10, 20, 30] is " + list.containsAll(Arrays.asList(10, 20, 30))); // expect: true
        System.out.println("Contains all [] is " + list.containsAll(Arrays.asList())); // expect: true
        System.out.println("Contains all [10, 20, 30, 40, 50, 60] is " + list.containsAll(Arrays.asList(10, 20, 30, 40, 50, 60)) + "\n"); // expect: false

        // Test equals(Object o)
        URArrayList<Integer> list2 = new URArrayList<>();
        list2.addAll(Arrays.asList(10, 11, 12, 13, 14, 15, 20, 30, 40, 50));
        System.out.println("List equals list2 is " + list.equals(list2)); // expect: true
        list2.remove(1); // remove 11
        list2.add(11); // add 11 at end
        System.out.println("Modified list2: " + Arrays.toString(list2.toArray())); // expect: [10, 12, 13, 14, 15, 20, 30, 40, 50, 11]
        System.out.println("List equals modified list2 is " + list.equals(list2) + "\n"); // expect: false

        // Test get(int index)
        System.out.println("Element at index 3 is " + list.get(3)); // expect: 13
        System.out.println("Element at index 0 is " + list.get(0) + "\n"); // expect: 10

        // Test indexOf(Object o)
        System.out.println("Index of object 30 is " + list.indexOf(30)); // expect: 7
        System.out.println("Index of 67 is " + list.indexOf(100) + "\n"); // expect: -1

        // Test isEmpty()
        System.out.println("List is empty is " + list.isEmpty()); // expect: false
        list.clear();
        System.out.println("List is empty after clear is " + list.isEmpty() + "\n"); // expect: true

        // Test iterator()
        System.out.print("Elements using iterator: ");
        for (Integer i : list2) {
            System.out.print(i + " ");
        }
        System.out.println(); // expect: 10 12 13 14 15 20 30 40 50 11

        // Test remove(int index)
        list.addAll(Arrays.asList(10, 20, 30, 40, 50)); // re-add elements to list
        System.out.println("Elements before removing index 2: " + Arrays.toString(list.toArray())); // expect: [10, 20, 30, 40, 50]
        list.remove(2);
        list.remove(3);
        System.out.println("Elements after removing index 2 and 4: " + Arrays.toString(list.toArray()) + "\n"); // expect: [10, 20, 40]

        // Test remove(Object o)
        list.remove((Integer) 20);
        list.remove((Integer) 15); // not in list, so nothing should happen
        System.out.println("Elements after removing object 20: " + Arrays.toString(list.toArray()) + "\n"); // expect: [10, 40]

        // Test removeAll(Collection<?> c)
        list.addAll(Arrays.asList(20, 30, 50));
        System.out.println("Elements before removeAll: " + Arrays.toString(list.toArray())); // expect: [10, 40, 20, 30, 50]
        list.removeAll(Arrays.asList(10, 30, 50, 60)); // 60 not in list, so nothing should only remove 10, 30, and 50
        System.out.println("Elements after removeAll [10, 30, 50, 60]: " + Arrays.toString(list.toArray()) + "\n"); // expect: [40, 20]

        // Test set(int index, elem element)
        list.set(0, 20);
        list.set(1, 40);
        System.out.println("Elements after setting index 0 to 20 and index 1 to 40: " + Arrays.toString(list.toArray()) + "\n"); // expect: [20, 40]

        // Test size()
        System.out.println("Size of list is " + list.size()); // expect: 2
        System.out.println("Size of list2 is " + list2.size() + "\n"); // expect: 10

        // Test subList(int fromIndex, int toIndex)
        URList<Integer> subList1 = list2.subList(6, 7);
        URList<Integer> subList2 = list2.subList(4, list2.size());
        System.out.println("Sub list of list2 from index 6 to 7: " + Arrays.toString(subList1.toArray())); // expect: [30]
        System.out.println("Sub list of list2 from index 4 to end: " + Arrays.toString(subList2.toArray()) + "\n"); // expect: [15, 20, 30, 40, 50, 11]
    }
}
