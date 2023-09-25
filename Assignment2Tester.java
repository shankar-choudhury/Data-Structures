/**
 * This class tests all of the methods in classes Stack and StringManipulation
 *@author Shankar Choudhury
 */
import org.junit.Test;
import static org.junit.Assert.*;

public class Assignment2Tester {
  @Test
  // Testing these methods also implicitly tests getFirstNode(), setFirstNode(), and all the methods of the private Node class
  public void testStackMethods() {
    // Test constuctor for Stack class
    try {
      Stack<Integer> stack = new Stack<Integer>();
    }
    catch (Exception e) {
      fail("Exception should not have been thrown");
    }
    
    Stack<Integer> stack = new Stack<Integer>();
    // Test push() method
    assertEquals(true, stack.push(1));
    assertEquals(true, stack.push(2));
    assertEquals(true, stack.push(3));
    assertEquals(true, stack.push(4));
    assertEquals(true, stack.push(5));
    assertEquals(true, stack.push(6));
    assertEquals(true, stack.push(7));
    
    // Test peek() method
    assertEquals(7, (int) stack.peek());
    
    // Test pop() and peek() methods
    assertEquals(7, (int) stack.pop());
    assertEquals(6, (int) stack.peek());
    assertEquals(6, (int) stack.pop());
     assertEquals(5, (int) stack.peek());
    assertEquals(5, (int) stack.pop());
    assertEquals(4, (int) stack.peek());
    assertEquals(4, (int) stack.pop());
    assertEquals(3, (int) stack.peek());
    assertEquals(3, (int) stack.pop());
    assertEquals(2, (int) stack.peek());
    assertEquals(2, (int) stack.pop());
    assertEquals(1, (int) stack.peek());
    assertEquals(1, (int) stack.pop());
    assertEquals(null, stack.peek());
    assertEquals(null, stack.pop());
    
    // Test isEmpty() method
    stack.push(1);
    assertEquals(false, stack.isEmpty());
    stack.pop();
    assertEquals(true, stack.isEmpty());
  }
  
  @Test
  public void testToPostfix() { // This implicitly tests the getPrecedence() method
    // Test method with infix string of length 0
    assertEquals("", StringManipulation.toPostfix(""));
    // Test method with infix string of 0 operators
    assertEquals("34", StringManipulation.toPostfix("34"));
    // Test method with infix string of 0 operands
    assertEquals("+", StringManipulation.toPostfix("+"));
    // Test method with infix string of length 1
    assertEquals("1", StringManipulation.toPostfix("1"));
    // Test method with infix string of 1 operator
    assertEquals("12+", StringManipulation.toPostfix("1+2"));
    // Test method with infix string of 1 operand
    assertEquals("1+", StringManipulation.toPostfix("1+"));
    assertEquals("1+", StringManipulation.toPostfix("+1"));
    // Test method with infix string containing operators of various precedence
    assertEquals("345*+", StringManipulation.toPostfix("3+4*5"));
    assertEquals("345/-", StringManipulation.toPostfix("3-4/5"));
    assertEquals("34+5/", StringManipulation.toPostfix("(3+4)/5"));
    // Test method with infix string of many operators and operand
    assertEquals("234*235+/6*/6+2-", StringManipulation.toPostfix("23*4/(2/(3+5)*6)+6-2"));
  }
  
  @Test
  public void testResult() {
    // Test method with simple postfix expressions
    assertEquals(7, StringManipulation.result("3 4 +"));
    assertEquals(-1, StringManipulation.result("3 4 -"));
    assertEquals(12, StringManipulation.result("3 4 *"));
    assertEquals(1, StringManipulation.result("4 3 /"));
    // Test method with complex postfix expressions that cover operations in the first part, middle, and last part of the postfix expression
    assertEquals(10, StringManipulation.result("4 3 9 3 2 * - 2 8 - 4 / + * +"));
    assertEquals(6, StringManipulation.result("10 2 / 45 9 + - 378 9 7 * /"));
    assertEquals(1546451526, StringManipulation.result("1024 1839 10382 291 * - - 90 1000 - *"));
  }
  
  @Test
  public void testSmallestNumber() {
    // Test correct return of null value if method is unable to perform due to incorrect inputs
    // Test method with string number of length 0
    assertEquals(null, StringManipulation.smallestNumber("",0));
    assertEquals(null, StringManipulation.smallestNumber("1",-5));
    // Test method with string number of length 1
    assertEquals("1", StringManipulation.smallestNumber("1",0));
    // Test method with string number of many lengths and no leading zeroes are outputted
    // Test method with leading zeroes in beginning of string number
    assertEquals("0", StringManipulation.smallestNumber("00000100",1));
    // Test method with leading zeroes in middle of string number after modification
    assertEquals("490000003", StringManipulation.smallestNumber("1800490000003", 2));
    // Test method with leading zeroes near end of string number after modification
    assertEquals("0", StringManipulation.smallestNumber("1800490000003",6));
    assertEquals("21300560", StringManipulation.smallestNumber("21300560", 0));
    assertEquals("1800130", StringManipulation.smallestNumber("18900130", 1));
  }
  
  @Test
  public void testUnbelievableString() {
    // Test correct handling of strings that can't be modified
    assertEquals("", StringManipulation.unbelievableString(""));
    assertEquals("a", StringManipulation.unbelievableString("a"));
    assertEquals("A", StringManipulation.unbelievableString("A"));
    // Test correct modification with matching pair of letters in beginning of input string
    assertEquals("bef", StringManipulation.unbelievableString("aAbef"));
    assertEquals("bef", StringManipulation.unbelievableString("Aabef"));
    assertEquals("bef", StringManipulation.unbelievableString("aaAAbef"));
    // Test correct modiciation with matching pair of letters in middle of input string
    assertEquals("abef", StringManipulation.unbelievableString("abbBef"));
    assertEquals("abef", StringManipulation.unbelievableString("abZzef"));
    assertEquals("abef", StringManipulation.unbelievableString("abzZzzZZZzzZZZZZzzzzef"));
    // Test correct modification with matching pair of letters in end of input string
    assertEquals("abef", StringManipulation.unbelievableString("abefGg"));
    assertEquals("abef", StringManipulation.unbelievableString("abefgG"));
    assertEquals("abef", StringManipulation.unbelievableString("abefgggGGGgggggGGGGGggGGgG"));
  }
}
