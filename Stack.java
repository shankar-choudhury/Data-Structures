/**
 * This class represents a Stack as a Linked List
 * @author Shankar Choudhury
 */
public class Stack<T extends Comparable<? super T>> {
  // first node of the list, or null if the list is empty 
  private Node firstNode;
  
  /**
   * Return the first node of the stack
   * @return firstNode The head of the stack
   */
  private Node getFirstNode() {
    return firstNode;
  }
    
  /**
   * Change the front node of the stack to a new node
   * @param node The node that will be the first node of the new stack
   */
  private void setFirstNode(Node node) {
    this.firstNode = node;
  }
  
  /**
   * Return whether the stack is empty
   * @return true if the list is empty
   */
  protected boolean isEmpty() {
    return (getFirstNode() == null);
  }
  
  /**
   * Add an element to the front of the stack
   * @param element The element to be added to the stack
   * @return true A boolean value to indicate operation performed successfully and element was added to top of stack
   * @return false A boolean value to indicate operation was not performed successfully because some exception was thrown
   */
  public boolean push(T element) {
    try {
      setFirstNode(new Node(element, getFirstNode()));
      return true;
    }
    catch (Exception e) {
      return false;
    }
  }
  
  /**
   * Removes and returns the element at the front of the stack
   * @return he element removed from the front of the stack
   */
  public T pop() {
    if (!isEmpty()) {
      T save = getFirstNode().getElement();
      setFirstNode(getFirstNode().getNext());
      return save;
    }
    return null;
  }
  
  /**
   * Return the element of the first node of the stack
   * @return currentElement The element of the first node of the stack
   */
  public T peek() {
    if (!isEmpty()) {
      return getFirstNode().getElement();
    }
    return null;
  }
  
  /** 
   * The nested class for nodes    
   */
  private class Node {
    // The element stored in the node 
    private T element;
    // The next node to point to
    private Node next;
    
    /**
     * Constructor for a new node
     * @param element The element to store in the node
     * @param next A reference to the next node of the list 
     */
    private Node(T element, Node next) {
      this.element = element;
      this.next = next;
    }
    
    /**
     * Returns the element stored in the node
     * @return the element stored in the node
     */
    private T getElement() {
      return element;
    }
    
    /**
     * Changes the element stored in this node
     * @param element the new element to store
     */
    private void setElement(T element) {
      this.element = element;
    }
    
    /**
     * Returns the next node of the list
     * @return the next node of the list
     */
    private Node getNext() {
      return next;
    }
    
    /**
     * Changes the next pointer for this node
     * @param next the node that should come after this node in the list
     */
    private void setNext(Node next) {
      this.next = next;
    }
  }
  
}
