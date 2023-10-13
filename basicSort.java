public class basicSort {
  
  private static void print(int[] arr) {
    for (int i : arr)
      System.out.print(i + " ");
    System.out.println();
  }
  
  public static void selectionSort(int[] array) {
    // Insert minimum element on each iteration at beginning of subarray 
    int[] arr = array;
    for (int i = 0; i < arr.length - 1; i++) {
      // Find minimum element in subarray
      int j = findMin(arr, i);
      // Swap minimum element with element at current index
      swap(arr, i, j);
    }
  }
  private static int findMin(int[] arr, int lower) {
    int curIndexMin = lower;
    for (int i = lower + 1; i <= arr.length - 1; i++) {
      if (arr[i] < arr[curIndexMin]) 
        curIndexMin = i;
    }
    return curIndexMin;
  }
  private static void swap(int[] arr, int i, int j) {
    int save = arr[i];
    arr[i] = arr[j];
    arr[j] = save;
    print(arr);
  }

  public static void insertionSort(int[] array) {
    int[] arr = array;
    // Find correct place of index, one index at a time
    for (int i = 1; i < arr.length; i++) {
      int toInsert = arr[i];
      // Shift elements right until at index where element is less than element to insert
      int j = i;
      while (j > 0 && toInsert < arr[j - 1]) {
        arr[j] = arr[j - 1];
        j--;
      }
      // Insert toInsert element at index with duplicate element less than toInsert 
      arr[j] = toInsert;
      print(arr);
    }
  }
  
  public static void shellSort(int[] arr) { // Using Hibbard’s sequence
    int incr = 1;
    while (2 * incr <= arr.length)
      incr = 2 * incr; // starting incr = floor(log arr.length)
    incr = incr - 1;
    while (incr >= 1) {
      for (int i = incr; i < arr.length; i++) {
        int toInsert = arr[i];
        int j = 0;
        for(j = i; j > incr-1 && toInsert < arr[j-incr]; j = j - incr)
          arr[j] = arr[j-incr];
        arr[j] = toInsert;
      }
      incr = incr/2;
    }
  }
  
  
}