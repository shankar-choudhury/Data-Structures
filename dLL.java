public class dLL<T> {

  private Node head;
  private Node tail;
  
  public Node getHead() {return head;}
  public void setHead(Node head) {this.head = head;}
  public Node getTail() {return tail;}
  public void setTail(Node tail) {this.tail = tail;}
  
  public void insert(T element) {
    if (getHead() == null) {
      setHead(new Node(element, null, null));
      setTail(getHead());
    }
    else {
      Node nodeptr = getHead();
      while (nodeptr.getAft() != null)
        nodeptr = nodeptr.getAft();
      nodeptr.setAft(new Node(element, nodeptr, null));
      setTail(nodeptr.getAft());
    }
  }
  
  public T remove(T element) {
    Node nodeptr = getHead();
    while (nodeptr != null && nodeptr.getElement() != element)
      nodeptr = nodeptr.getAft();
    T deleted = nodeptr.getElement();
    // If element to be removed is head
    if (deleted == getHead().getElement()) {
      setHead(nodeptr.getAft());
      if (getHead().getAft() != null)
        getHead().getAft().setBef(getHead());
    }
    // If element to be removed is at tail
    else if (deleted == getTail().getElement()) {
      setTail(nodeptr.getBef());
      nodeptr.getBef().setAft(null);
      nodeptr.setBef(null);
    }
    // If element is in middle of list
    else {
      nodeptr.getBef().setAft(nodeptr.getAft());
      nodeptr.getAft().setBef(nodeptr.getBef());
    }
    return deleted;
  }
      
  
  public void printList() {
    Node nodeptr = getHead();
    while (nodeptr != null) {
      System.out.println(nodeptr.getElement());
      nodeptr = nodeptr.getAft();
    }
  }
  
  private class Node {
    private Node before;
    private Node after;
    private T element;
    
    private Node(T element, Node before, Node after) {
      this.element = element;
      this.before = before;
      this.after = after;
      if (before != null) 
        before.setAft(this);
      if (after != null)
        after.setBef(this);
    }
    
    public Node getBef() {return before;}
    private void setBef(Node before) {this.before = before;}
    public Node getAft() {return after;}
    private void setAft(Node after) {this.after = after;}
    public T getElement() {return element;}
  }
  
}