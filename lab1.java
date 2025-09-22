// Lab 1 - Generics Review - Parts 1-5
// Dane Backbier - dbackbie@u.rochester.edu


public class lab1 {
    public static void main(String[] args) {
        // Parts 1-3 test code
        Integer [] intArray = {1, 2, 3, 4, 5 };
        Double [] doubArray = {1.1, 2.2, 3.3, 4.4};
        Character [] charArray = {'H','E','L', 'L', 'O' };
        String [] strArray = {"once", "upon", "a", "time" };
        printArray(intArray);
        printArray(doubArray);
        printArray(charArray);
        printArray(strArray);

        //  Parts 4-5 test code
        System.out.println("max Integer is: " + getMax(intArray));
        System.out.println("max Double is: " + getMax(doubArray));
        System.out.println("max Character is: " + getMax(charArray));
        System.out.println("max String is: " + getMax(strArray));
    }
    /*  Part 1: printArray() method using an array of Objects for the parameter
    public static void printArray(Object [] a) {
        for (Object elem : a) {
            System.out.print(elem + ",");
    }
    System.out.println("\n");
    }
    */
    /*  Part 2: printArray() using method overloading
    public static void printArray(Integer [] a) {
        for (Integer elem : a) {
            System.out.print(elem + ",");
        }
        System.out.println("\n");
    }
    public static void printArray(Double [] a) {
        for (Double elem : a) {
            System.out.print(elem + ",");
        }
        System.out.println("\n");
    }
    public static void printArray(Character [] a) {
        for (Character elem : a) {
            System.out.print(elem + ",");
        }
        System.out.println("\n");
    }
    public static void printArray(String [] a) {
        for (String elem : a) {
            System.out.print(elem + ",");
        }
        System.out.println("\n");
    }
    */
    //  Part 3: Generic programming technique
    public static <E> void printArray(E [] a) {
        for (E element : a) {
            System.out.printf(" %s,", element);
        }
        System.out.println();
    }
    /* Part 4: getMax() method using non-generic techniques
    public static Comparable getMax(Comparable [] a) {
        Comparable max = a[0];
        for (Comparable elem : a) {
            if (elem.compareTo(max) > 0) {
                max = elem;
            }
        }
        return max;
    }
    */
    // Part 5: getMax() method using generic techniques
    public static <T extends Comparable<T>> T getMax(T [] a) {
        T max = a[0];
        for (T elem : a) {
            if (elem.compareTo(max) > 0) {
                max = elem;
            }
        }
        return max;
    }
    // Part 6: findMax() method using Function Interface
    // See findMax.java
}