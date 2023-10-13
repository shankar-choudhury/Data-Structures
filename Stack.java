public class Stack<T extends Comparable<? super T>> {
  private Node top;
  private int size;
  
  private Node getTop() {
    return top;
  }
  
  private void setTop(Node top) {
    this.top = top;
  }
  
  private int getSize() {
    return size;
  }
  
  public boolean isEmpty() {
    return getSize() == 0;
  }
  
  public boolean push(T element) {
    if (isEmpty()) 
      setTop(new Node(element, null));
    else 
      setTop(new Node(element, getTop()));
    return true;
  }
  
  public T pop() {
    T save = getTop().getElement();
    setTop(getTop().getNext());
    return save;
  }
  
  public T peek() {
    return getTop().getElement();
  }
  
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
  
  
  
  
  private class Node {
    private T element;
    private Node next;
    
    private Node(T element, Node next) {
      this.element = element;
      this.next = next;
    }
    
    private T getElement() {
      return element;
    }
    
    private Node getNext() {
      return next;
    }
    
    private void setNext(Node next) {
      this.next = next;
    }                   
  }
  
}