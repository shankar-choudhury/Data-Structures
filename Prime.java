public class Prime {
  // Helper method to find next prime number 
  public static int findNextPrime(int num) {
    // Check if number is prime
    boolean isPrime = true;
    for (int i = 2; i < num/2 + 1 && isPrime; i++) {
      if (num % i == 0)
        isPrime = false;
    }
    // If number is not prime, then increment number until number is prime
    while (!isPrime) {
      isPrime = true;
      num++;
      for (int i = 2; i < num/2 + 1 && isPrime; i++) {
        if (num % i == 0)
          isPrime = false;
      }
    }
    return num;
  }
  
  public static int getPrevPrime(int n) {
      for (int i = n- 1; i >= 1; i--) {
        int cnt = 0;
        for (int j = 2; j * j <= i; j++)
          if (i % j == 0)
          cnt++;
        if (cnt == 0)
          return i;
      }
      return 3;
    }
  
  
}