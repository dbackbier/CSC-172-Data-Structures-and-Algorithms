// Lab 1 - Generics Review - Part 6
// Dane Backbier - dbackbie@u.rochester.edu

import java.util.function.Function;

// Part 6: findMax() method using Function Interface
public class findMax {
    public static void main(String[] args) {
        Function<Character [], Character> getMax = (Character [] a) -> {
            Character max = a[0];
            for (Character elem : a) {
                if (elem.compareTo(max) > 0) {
                    max = elem;
                }
            }
            return max;
        };

        Character [] charArray = {'H','E','L', 'L', 'O' };
        System.out.println("max Character is: " + getMax.apply(charArray));
    }
}

