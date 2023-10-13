// This class represents an AVL tree
// Assume no duplicate elements are inserted into tree
public class AVL<T extends Comparable<? super T>> {
  // Root node of this AVL tree
  private Node root;
  
  // Constructor for AVL tree
  public AVL(T element) {
    root = new Node(element);
  }
  
  // Return root node of this AVL tree
  private Node getRoot() {
    return root;
  }
  // Set root node of this AVL tree
  private void setRoot(Node root) {
    this.root = root;
  }
  
  /* To account for possible rotations, assign root to be the result of anorher insert method that returns a 
   * Node, that might return a different root if adjustments after rotations have been applied are necessary
   */
  // Add element into tree
  public void insert(T element) {
    setRoot( insertNode(element, getRoot()));
  }
  // Private overloaded insert method that returns a new node at the correct position
  // Then rotate accordingly with balance() method
  private Node insertNode(T element, Node parent) {
    // When empty spot is found, where parent is null effectively making it a child, insert new Node at location
    if (parent == null)
      return new Node(element);
    // If element is less than parent's element, insert on left 
    if (element.compareTo(root.getElement()) < 0)
      root.setLeft( insert(element, root.getLeft()));
    // If element is greater than parent's element, insert on right
    else if (element.compareTo(root.getElement()) > 0)
      root.setRight( insert(element, root.getRight()));
    // Element's value and parent's element are equal, do not insert
    else
      ;
    // Call balance() for correct rotation procedure, which will recursively adjust the position 
    // and height of nodes all the way back up to the root 
    return rotate(parent);
  }
  // Perform correct rotation on input node
  private Node rotate(Node node) {
    if (node == null)
      return node;
  }
  
  // Private node class
  private class Node {
    // Element of node
    private T element;
    // Left node of this node, stored with larger value
    private Node left;
    // Right node of this node, stored with smaller value
    private Node right;
    // Height of this node
    private int height = -1;
    
    // Constructor for node when given just element
    private Node(T element) {
      this.element = element;
    }
    // Constructor for node when given all appropriate values
    private Node(T element, Node left, Node right) {
      this.element = element;
      this.left = left;
      this.right = right;
    }
    
    // Return element of this node
    private T getElement() {
      return element;
    }
    // Return node left of this node
    private Node getLeft() {
      return left;
    }
    // Set node left of this node
    private void setLeft(Node left) {
      this.left = left;
    }
    // Return node right of this node
    private Node getRight() {
      return right;
    }
    // Set node right of this node
    private void setRight(Node right) {
      this.right = right;
    }
    // Return height of this node
    private int getHeight() {
      return height;
    }
    // Set height of this node
    private void setHeight(int height) {
      this.height = height;
    }
  }
}
