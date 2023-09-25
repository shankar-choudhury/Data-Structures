/**
 * This class represents a set of methods that can do things with a String input
 * @author Shankar Choudhury
 */

public class StringManipulation {
  
  /**
   * Return a number to indicate precedence of character
   * @param c The character to be read for precedence
   * @return 1 Input character has lower precedence
   * @return 2 Input character has higher precedence
   * @return -1 Input character has no relevant precedence
   */
  private static int getPrecedence(char c) {
    if (c == '+' || c == '-')
      return 1;
    else if (c == '*' || c == '/')
      return 2;
    else
      return 0;
  }
  
  /**
   * Return a postfix expression from an infix expression
   * @param infix The infix expression to be turned into a postfix expression. 
   * Must only contain numbers and operators +, -, *, /, and () from least to greatest precedence
   * @return newS.toString() The modified infix expression that is now a postfix expression
   */
  public static String toPostfix(String infix) {
    Stack<Character> stack = new Stack<Character>();
    StringBuilder newS = new StringBuilder();
    // Read characters in infix one index at a time, and append to newS or push into sack when appropriate
    for (int i = 0; i < infix.length(); i++) {
      // case 1: character is a non-operator, append directly to newS
      if (infix.charAt(i) >= '0' && infix.charAt(i) <= '9')
        newS.append(infix.charAt(i));
      // case 2: character is a left-paranthesis indicating priority of expression onwards, push 
      // into stack to mark priority of any operators pushed afterwards
      else if (infix.charAt(i) == '(')
        stack.push(infix.charAt(i));
      // case 3: character is a right-paranthesis indicating priority of expression has ended, pop 
      // all elements of stack into newS until left-paranthesis is encountered, then pop the left-paranthesis
      // to indicate all expression with priority has been appended to newS appropriately 
      else if (infix.charAt(i) == ')') {
        while (!stack.isEmpty() && stack.peek() != '(') 
          newS.append(stack.pop());
        stack.pop();
      }
      // case 4: character is an operator, compare for precedence against top of stack, pop if precedence
      // of top is greater than or equal to this operator's precedence until lower precedence is met or stack 
      // is empty. Then push this operator into the stack
      else {
        while (!stack.isEmpty() && getPrecedence(infix.charAt(i)) <= getPrecedence(stack.peek())) 
          newS.append(stack.pop());
        stack.push(infix.charAt(i));
      }
    }
    // Empty stack and append what is left to pop
    while (!stack.isEmpty())
      newS.append(stack.pop());
    return newS.toString();
  }
  
  /**
   * Return the result of a postfix expression
   * @param postfix The postfix expression to be read. Numbers and operators are separated by one space
   * Postfix expression must only contain numbers, operations, and spaces in between numbers and operations
   * Postfix expression must not have negative numbers
   * @return stack.pop() The final result of the postfix expression
   */
  public static int result(String postfix) {
    Stack<Integer> stack = new Stack<Integer>();
    for (int i = 0; i < postfix.length(); i++) {
      // If at an integer, read current integer and push into stack
      if (postfix.charAt(i) >= '0' && postfix.charAt(i) <= '9') {
        StringBuilder newNum = new StringBuilder();
        // Read whole integer and append appropriately
        while(postfix.charAt(i) >= '0' && postfix.charAt(i) <= '9') {
          newNum.append(postfix.charAt(i));
          i++;
        }
        // Decrement i to account for extra increment of i in while loop
        i--;
        // Push number into stack
        stack.push(Integer.parseInt(newNum.toString()));
      }
      // If at operator +, push sum of two top values popped from stack
      else if (postfix.charAt(i) == '+') {
        int num1 = stack.pop();
        int num2 = stack.pop();
        stack.push(num1 + num2);
      }
      // If at operator -, push difference of second top value from first top value, both popped from stack
      else if (postfix.charAt(i) == '-') {
        int num1 = stack.pop();
        int num2 = stack.pop();
        stack.push(num2 - num1);
      }
      // If at operator *, push product of first top value and second top value, both popped from stack
      else if (postfix.charAt(i) == '*') {
        int num1 = stack.pop();
        int num2 = stack.pop();
        stack.push(num1 * num2);
      }
      // If at operator /, push division result of second top value and first top value, both popped from stack
      else if (postfix.charAt(i) == '/') {
        int num1 = stack.pop();
        int num2 = stack.pop();
        stack.push(num2 / num1);
      }
    }
    return stack.pop();
  }
  
  /**
   * Find the smallest number in string after deleting any n-number of digits
   * @param number The string of a number containing only digits 0 - 9
   * @param n The number of digits to delete from the number
   * @return newNumber.toString() The modified number after method has been applied to it
   * @return null Return null if modification could not be applied
   * @precondition n must be less than or equal to the length of the number prior to modification
   */
  public static String smallestNumber(String number, int n) {
    // Only modify number if n is a valid number
    if(number.length() > 0 && n >= 0 && n <=number.length()) {
      StringBuilder newNum = new StringBuilder(number);
      // Modify number appropriately n-number of times
      for (int i = 0; i < n; i++) {
        int lookAheadIndex = 0;
        // If this digit is less than the next digit, ignore and continue looking for digit that is greater than the next digit
        while (lookAheadIndex < newNum.length() - 1 && newNum.charAt(lookAheadIndex) <= newNum.charAt(lookAheadIndex + 1))
          lookAheadIndex++;
        // Delete digit that is greater than next digit
        newNum.delete(lookAheadIndex, lookAheadIndex + 1);
      }
      // Delete leading zeroes
      int newNumLength = newNum.length() - 1;
      int counter = 0;
      while (counter < newNumLength && newNum.charAt(0) == '0') {
        newNum.deleteCharAt(0);
        counter++;
      }
      return newNum.toString();
    }
    return null;
  }
  
  /**
   * Modifies a string where there are no adjacent characters that are a matching pair, 
   * such that one is lower case and the other is upper case of the same letter
   * @param s The string to be checked and modified
   * @return s The modified or unmodified input string
   * @precondition Input string s must be at least characters long for the checking to be possible
   */
  public static String unbelievableString(String s) {
    StringBuilder newS = null;
    // If s is a string with a valid length to check for matching pairs, proceed with modification
    if (s.length() >= 2) {
      // Look for pairs of lowercase and uppercase letters of the same letter in modified string
      // If pair is not found, return the input String
      boolean pairFound = false;
      int index = 0;
      while (index < s.length() && !pairFound){
        if (index <= s.length() - 2) {
          if (s.charAt(index) == (char) (s.charAt(index + 1) - 32) || (char) (s.charAt(index) - 32) == s.charAt(index + 1)) 
            pairFound = true;
        }
        index++;
      }
      if (!pairFound)
        return s;
      // If pair is found, remove pair, and call method on string to check for more pairs after modification
      else {
        newS = new StringBuilder();
        // Append every character except pairs of lowercase and uppercase letters of the same letter
        int sIndex = 0;
        while (sIndex < s.length()){
          // case 1: sIndex is not at end of string, check this index with next index 
          if (sIndex <= s.length() - 2) {
            if (s.charAt(sIndex) != (char) (s.charAt(sIndex + 1) - 32) && (char) (s.charAt(sIndex) - 32) != s.charAt(sIndex + 1)) {
              newS.append(s.charAt(sIndex));
              sIndex++;
            }
            // If there is a matching pair with this index, advance by 2 to look at next pair of indices
            else 
              sIndex += 2;
          }
          // case 2: sIndex is at end of string
          else if (sIndex == s.length() - 1) {
            newS.append(s.charAt(sIndex));
            sIndex++;
          }
        }
        return unbelievableString(newS.toString());
      }
    }
    // If s isn't a string with a valid length, return the string unmodified
    else
      return s;
  }
  
}