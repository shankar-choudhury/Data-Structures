public class RecursivePractice {

    // Write your code below this point *^*^*^*^*^*^*^*^*^*^*^*^*^*^*^*^*^*^*^*^

   public void countDown(int n) {
        if (n == 0)
          return;
        else {
          System.out.println(n);
          countDown(n - 1);
        }

    }

    public int sumOfNumbers(int n) {
        if (n == 0)
          return 0;
        else {
          System.out.println(n);
          return n + sumOfNumbers(n - 1);
        }
    }

    public boolean isAscending(int[] arr, int index) {
        // this should recursivley check if an array is ascending
        // the starting index given by the test case is 1
        // the index var should be used to check if that index is more than the previous index
      
      if (index > arr.length - 2) // Have to stop at second to last index
        return true;
      
      if (arr[index + 1] <= arr[index])
        return false;
      
      else
        return isAscending(arr, index + 1);
      
    }
    
    public int sumOfSquareNumber(int num) {
      if (num == 0)
        return 0;
      else 
        return num*num + sumOfSquareNumber(num - 1);
    }

    // Write your code above this point *^*^*^*^*^*^*^*^*^*^*^*^*^*^*^*^*^*^*^*^

    public static void main(String[] args) {
        RecursivePractice N = new RecursivePractice();

        System.out.println("The next few lines of console output should be 5, 4, 3, 2, 1");
        N.countDown(5);

        // I am using the Main method for test cases, you dont need to touch this
        if (N.sumOfNumbers(5) == 15 && N.sumOfNumbers(100) == 5050) {
            System.out.println("\n sumOfNumbers passes all test cases \n");
        } else {
            System.out.println("\n sumOfNumbers doesnt work :( \n");
        }

        int[] arr1 = { 1, 2, 4, 6, 8, 9 };
        int[] arr2 = { 1, 2, 4, 60, 8, 9 };
        if (N.isAscending(arr1, 1) && !N.isAscending(arr2, 1)) {
            System.out.println("\n isAscending passes all test cases \n");
        } else {
            System.out.println("\n isAscending doesnt work :( \n");
        }

    }
}