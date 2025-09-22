/******************************************************************************
 *  Compilation:  javac Sorting.java
 *  Execution:    java Sorting input.txt AlgorithmUsed
 *  Dependencies: StdOut.java In.java Stopwatch.java
 *  Data files:   http://algs4.cs.princeton.edu/14analysis/1Kints.txt
 *                http://algs4.cs.princeton.edu/14analysis/2Kints.txt
 *                http://algs4.cs.princeton.edu/14analysis/4Kints.txt
 *                http://algs4.cs.princeton.edu/14analysis/8Kints.txt
 *                http://algs4.cs.princeton.edu/14analysis/16Kints.txt
 *                http://algs4.cs.princeton.edu/14analysis/32Kints.txt
 *                http://algs4.cs.princeton.edu/14analysis/1Mints.txt
 *
 *  A program to play with various sorting algorithms. 
 *
 *
 *  Example run:
 *  % java Sorting 2Kints.txt  2
 *
 ******************************************************************************/
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Arrays;
import java.io.FileWriter;
import java.io.IOException;

public class Sorting {
/**
     *
     * Sorts the numbers present in the file based on the algorithm provided.
     * 0 = Arrays.sort() (Java Default)
     * 1 = Bubble Sort
     * 2 = Selection Sort
     * 3 = Insertion Sort
     * 4 = Mergesort
     * 5 = Quicksort
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) { { // i = algorithm (0,1,2,3), array = a,b,c,d
        In in = new In(args[0]);

		  // Storing file input in an array
        int[] a = in.readAllInts();

        // TODO: Generate 3 other arrays, b, c, d where
        // b contains sorted integers from a (You can use Java Arrays.sort() method)
        // c contains all integers stored in reverse order 
        // (you can have your own O(n) solution to get c from b
        // d contains almost sorted array 
        //(You can copy b to a and then perform (0.1 * d.length)  many swaps to acheive this.   
        int[] b = a;
        Arrays.sort(b); // sorted b

        int[] c = reverseArray(b, b.length); // get c from b

        int[] d = a;
        int swapCount = (int) (0.2 * d.length); // number of swaps to perform to perform (0.1 * d.length) swaps because reverseArray does length/2 swaps
        d = reverseArray(d, swapCount);
        
        //TODO:
        // Read the second argument and based on input select the sorting algorithm
        //  * 0 = Arrays.sort() (Java Default)
        //  * 1 = Bubble Sort
        //  * 2 = Selection Sort
        //  * 3 = Insertion Sort
        //  * 4 = Mergesort
        //  * 5 = Quicksort
        //  Perform sorting on a,b,c,d. Printing runtime for each case along with timestamp and record those. 
        // For runtime and priting, you can use the same code from Lab 4 as follows:
        
        
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
          //TODO: Replace with your own netid
        String netID = "dbackbie";
          //TODO: Replace with the algorithm used
        String algorithmUsed = "Quick Sort";
          //TODO: Replace with the  array used
        String arrayUsed = "a";
        for (int i = 0; i < 4; i++) {
          if (i == 0) {
            Stopwatch timer = new Stopwatch();
            a = quickSort(a, 0, a.length-1);
            double time = timer.elapsedTimeMillis();
            arrayToFile(a, arrayUsed);
            StdOut.printf("%s %s %8.1f   %s  %s  %s\n", algorithmUsed, arrayUsed, time, timeStamp, netID, args[0]);
          } else if (i == 1) {
            Stopwatch timer2 = new Stopwatch();
            b = quickSort(b, 0, b.length-1);
            double time2 = timer2.elapsedTimeMillis();
            arrayUsed = "b";
            arrayToFile(b, arrayUsed);
            StdOut.printf("%s %s %8.1f   %s  %s  %s\n", algorithmUsed, arrayUsed, time2, timeStamp, netID, args[0]);
          } else if (i == 2) {
            Stopwatch timer3 = new Stopwatch();
            c = quickSort(c, 0, c.length-1);
            double time3 = timer3.elapsedTimeMillis();
            arrayUsed = "c";
            arrayToFile(c, arrayUsed);
            StdOut.printf("%s %s %8.1f   %s  %s  %s\n", algorithmUsed, arrayUsed, time3, timeStamp, netID, args[0]);
          } else if (i == 3) {
            Stopwatch timer4 = new Stopwatch();
            d = quickSort(d, 0, d.length-1);
            double time4 = timer4.elapsedTimeMillis();
            arrayUsed = "d";
            arrayToFile(d, arrayUsed);
            StdOut.printf("%s %s %8.1f   %s  %s  %s\n", algorithmUsed, arrayUsed, time4, timeStamp, netID, args[0]);
          }
        }
    }
  } 
  
  public static int[] reverseArray(int[] arr, int length) {
      for (int i = 0; i < length / 2; i++) {
        swap(arr, i, length - i - 1);
      }
      return arr;
  }

  public static void swap(int[] arr, int i, int j) {
      int temp = arr[i];
      arr[i] = arr[j];
      arr[j] = temp;
  }

  public static int[] bubbleSort(int [] arr) {
    for (int i = 0; i < arr.length - 1; i++) {
        for (int j = 0; j < arr.length - i - 1; j++) {
            if (arr[j] > arr[j + 1]) {
                swap(arr, j, j + 1);
            }
        }
    }
    return arr;
  } // O(n^2)

  public static int[] selectionSort(int [] arr) {
    for (int i = 0; i < arr.length - 1; i++) {
      int minI = i;
      for (int j = i + 1; j < arr.length; j++) {
        if (arr[j] < arr[minI]) {
          minI = j;
        }
      }
      swap(arr, i, minI);
    }
    return arr;
  } // O(n^2)

  public static int[] insertionSort(int [] arr, int size) {
    int j = 0;
    
    for (int i = 1; i < size; ++i) {
      j = i;
      while (j > 0 && arr[j] < arr[j - 1]) {
        swap(arr, j, j - 1);
        --j;
      }
    }
    return arr;
  } // O(n^2)

  public static int[] mergeSort(int[] arr, int min, int max) {
    if (min < max) {
      int mid = (min + max) / 2;
      mergeSort(arr, min, mid);
      mergeSort(arr, mid + 1, max);
      merge(arr, min, mid, max);
    }
    return arr;
  } // O(n log n)

  public static void merge(int [] arr, int min, int mid, int max) {
    int mergedSize = max - min + 1;
    int mergePos = 0;
    int [] merged = new int[mergedSize];
    int left = min;
    int right = mid + 1;

    while (left <= mid && right <= max) {
      if (arr[left] <= arr[right]) {
        merged[mergePos] = arr[left];
        left++;
      }
      else {
        merged[mergePos] = arr[right];
        right++;
      }
      mergePos++;
    }
    while (left <= mid) {
      merged[mergePos] = arr[left];
      left++;
      mergePos++;
    }
    while (right <= max) {
      merged[mergePos] = arr[right];
      right++;
      mergePos++;
    }
    for (mergePos = 0; mergePos < mergedSize; ++mergePos) {
      arr[min + mergePos] = merged[mergePos];
    }
  }

  public static int[] quickSort(int[] arr, int low, int high) {
    if (high <= low) {
      return null;
    }

    int lowEndIndex = partition(arr, low, high);

    quickSort(arr, low, lowEndIndex);
    quickSort(arr, lowEndIndex + 1, high);

    return arr;
  } // O(n log n) on average, O(n^2) worst case

  public static int partition(int[] arr, int low, int high) {
   // Pick middle element as pivot
    int mid = low + (high - low) / 2;
    int pivot = arr[mid];
    
    boolean done = false;
    while (!done) {
      // Increment lowIndex while numbers[lowIndex] < pivot
      while (arr[low] < pivot) {
          low += 1;
      }
      
      // Decrement highIndex while pivot < numbers[highIndex]
      while (pivot < arr[high]) {
        high -= 1;
      }
      
      // If zero or one elements remain, then all numbers are 
      // partitioned. Return highIndex.
      if (low >= high) {
        done = true;
      }
      else {
         // Swap numbers[lowIndex] and numbers[highIndex]
        swap(arr, low, high);
         // Update lowIndex and highIndex
        low += 1;
        high -= 1;
      }
    }
    return high;
  }

  public static void arrayToFile(int[] arr, String fileName) { // filename will be a,b,c, or d
    try {
      String file = fileName + ".txt";
      FileWriter writer = new FileWriter(file);
      for (int i = 0; i < arr.length; i++) {
        writer.write(arr[i] + "\n");
      }
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}