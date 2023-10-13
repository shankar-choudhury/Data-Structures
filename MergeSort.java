import java.lang.reflect.Array;
public class MergeSort<T> {
  // Main sorting method
  // Recursively divide array in half until left with individual indices, and then merge the sorted halves
  public static <T extends Comparable<? super T>> void sort(T[] arr, int left, int right) {
    if (left < right) {
      int middle = left + (right - left) / 2;
      // Recursively sort left half 
      sort(arr, left, middle);
      // Recursively sort right half
      sort(arr, middle + 1, right);
      // Merge the sorted halves
      merge(arr, left, middle, right);
    }
  }
  
  private static <T extends Comparable<? super T>> void merge(T[] arr, int left, int middle, int right) {
    // Calculate logical size of and create subarrays
    // Left subarray is arr[left to middle]
    int subLSize =  middle - left + 1;
    @SuppressWarnings("unchecked") T[] subL = (T[]) Array.newInstance((Class<T>) arr[0].getClass(), subLSize); 
    for (int i = 0; i < subLSize; i++)
      subL[i] = arr[left + i];
    // Right subarray is arr[middle + 1 to right]
    int subRSize = right - middle;
    @SuppressWarnings("unchecked") T[] subR = (T[]) Array.newInstance((Class<T>) arr.getClass().getComponentType(), subRSize); 
    for (int i = 0; i < subRSize; i++)
      subR[i] = arr[middle + 1 + i];
    
    // Merge the two arrays by adding them back to original array in-order
    int subLindex = 0;
    int subRindex = 0;
    int ogArrIndex = left;
    while (subLindex < subLSize && subRindex < subRSize) {
      if (subL[subLindex].compareTo(subR[subRindex]) < 0) {
        arr[ogArrIndex] = subL[subLindex];
        subLindex++;
      }
      else {
        arr[ogArrIndex] = subR[subRindex];
        subRindex++;
      }
      ogArrIndex++;
    }
    // Add remaining elements of left and right subarrays back to original array if any are left
    while (subLindex < subLSize) {
      arr[ogArrIndex] = subL[subLindex];
      subLindex++;
      ogArrIndex++;
    }
    while (subRindex < subRSize) {
      arr[ogArrIndex] = subR[subRindex];
      subRindex++;
      ogArrIndex++;
    }
  }
  
  public static void main(String[] args) {
    Integer[] arr = {7, 3, 2, 18, 9, 5, 2, 10, 12, 25, 23, 21, 21, 19, 12};
    MergeSort.sort(arr, 0, arr.length - 1);
    for (int i : arr)
      System.out.print(i + " ");
    System.out.println();
  }
}