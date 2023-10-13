// A class representing a priority queue
public class LinkedListQueue<T extends Comparable<? super T>> {
  // Head of queue
  private Node front;
  // Back of queue
  private Node rear;
  // Number of items in queue
  private int size;
  
  private Node getFront() {return front;}
  
  private void setFront(Node front) {this.front = front;}
  
  private Node getRear() {return rear;}
  
  private void setRear(Node rear) {this.rear = rear;}
  
  private int getSize() {return size;}
  
  // Increment the number of elements in queue
  private boolean incrementSize() {
    size += 1;
    return true;
  }
  
  // Decrement number of elements in queue
  private boolean decrementSize() {
    if (getSize() == 0)
      return false;
    size -= 1;
    return true;
  }
  
  // Constructor for queue
  public LinkedListQueue(T element) {
    front = new Node(element, null);
    rear = front;
    size = 1;
  }
  
  // Return if list is empty or not
  public boolean isEmpty() {return getFront() == null;}
  
  // Insert into queue with order/priority
  public boolean insertInOrder(T element) {
    // case 1: inserting at beginning of list
    try {
      Node nodeptr = getFront();
      if (nodeptr.getElement().compareTo(element) < 0) {
        setFront(new Node(element, getFront()));
        incrementSize();
        setRear(getFront());
        return true;
      }
      // case 2: inserting later in list
      else {
        Node prevNode = null;
        while (nodeptr.getNext() != null && nodeptr.getElement().compareTo(element) >= 0) {
          prevNode = nodeptr;
          nodeptr = nodeptr.getNext();
        }
        // case 2.1: inserting at end of queue
        if (nodeptr.getNext() == null) { 
          nodeptr.setNext(new Node(element, null));
          incrementSize();
          setRear(nodeptr.getNext());
          return true;
        }
        // case 2.2: inserting in middle of list
        else { 
          prevNode.setNext(new Node(element, nodeptr.getNext()));
          incrementSize();
          return true;
        }
      }
    }
    catch (Exception e) {
      return false;
    }
  }
  
  // Insert without order
  public boolean insert(T element) {
    Node nodeptr = getFront();
    while (nodeptr.getNext() != null)
      nodeptr = nodeptr.getNext();
    nodeptr.setNext(new Node(element, null));
    setRear(nodeptr.getNext());
    return true;
  }
  
  // Remove element from rear
  public T remove() {
    if (!isEmpty()) {
      T removed = getFront().getElement();
      setFront(getFront().getNext());
      decrementSize();
      return removed;
    }
    return null;
  }
  
  // Print elements
  public void printElements() {
    Node nodeptr = getFront();
    while (nodeptr != null) {
      System.out.println(nodeptr.getElement());
      nodeptr = nodeptr.getNext();
    }
  }
    
  // Private Node class
  private class Node {
    private T element;
    private Node next;
    
    private Node(T element, Node next) {
      this.element = element;
      this.next = next;
    }
    
    private T getElement() {return element;}
    
    private void setElement(T element) {this.element = element;}
    
    private Node getNext() {return next;}
    
    private void setNext(Node next) {this.next = next;}
  }
}