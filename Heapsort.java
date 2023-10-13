// Rename this file Heapsort.java to test it out.
public class Heapsort {
  /**
   * Method to print the array/
   * @param a an array of objects.
   */
  private static <T extends Comparable<? super T>> void printArray(T[] a) {
    for (int i = 0; i < a.length; i++)
      System.out.print(a[i] + " ");
    System.out.println();
  }
  
  private static <T extends Comparable<? super T>> void swap(T[] arr, int i, int j) {
    T save = arr[i];
    arr[i] = arr[j];
    arr[j] = save;
  }
  
  private static int leftChild(int i) {
    return 2*i + 1;
  }
  
  public static <T extends Comparable<? super T>> void sort(T[] arr) {
    // Heapify first log(n) - 1 layers of binary tree
    for (int i = arr.length/2 - 1; i > -1; i--)
      percDown(arr, i, arr.length);
    // Heapsort heaped-array
    for (int i = arr.length - 1; i > 0; i--) {
      swap(arr, 0, i);
      percDown(arr, 0, i);
    }
  }
  
  /**
   * Traverse down array from parent to child, checking and swapping values if necessary 
   * @param i starting index to percolate down from
   * @param j ending index to percolate down to
   */
  private static <T extends Comparable<? super T>> void percDown(T[] arr, int i, int j) {
    int child = 0;
    // Element who will be compared to child indices 
    T save = null;
    for (save = arr[i]; leftChild(i) < j; i = child) {
      // Calculate left child
      child = leftChild(i);
      // Compare it with other child to see which violates opposite of heap order more, increment if it does
      if (child != j - 1 && arr[child].compareTo(arr[child + 1]) > 0)
        child++;
      // Once child is found, if it violates opposite of heap order with parent, switch values
      if (save.compareTo(arr[child]) > 0)
        arr[i] = arr[child];
      // If child does not violate heap order with parent, no need to percDown so we break out of current loop
      else
        break;
    }
    arr[i] = save;
    //System.out.println(i);
    printArray(arr);
  }
  
  public static void main(String[] args) {
    Integer[] a = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    //System.out.println("Array before sorting.");
    //printArray(a);
    Heapsort.sort(a);
    System.out.println("Array after sorting.");
    printArray(a);
  }
  
  
  
  
  

    
}