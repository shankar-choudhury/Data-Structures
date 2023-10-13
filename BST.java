import java.util.Random;

// This class represents a binary search tree
public class BST<T extends Comparable<? super T>> {
  // Root of tree
  private Node root;
  // Return root
  private Node getRoot() {return root;}
  // Assign root
  private void setRoot(Node root) {this.root = root;}
  // Constructor for BST
  public BST(int key, T data) {root = new Node(key, data);}  
  
  // Insert into BST
  public void insert(int key, T data) {
    // Find parent for new node
    Node parent = null;
    Node nodeptr = root;
    while (nodeptr != null) {
      parent = nodeptr;
      if (key < nodeptr.getKey())
        nodeptr = nodeptr.getLeft();
      else
        nodeptr = nodeptr.getRight();
    }
    // Insert node
    if (key < parent.getKey())
      parent.setLeft(new Node(key, data));
    else
      parent.setRight(new Node(key, data));
  }
  
  
  public void insert(int[] keys, T[] data) {
    if (keys.length != data.length)
      throw new IllegalArgumentException("input arrays must be of same length");
    for (int i = 0; i < keys.length; i++)
      insert(keys[i], data[i]);
  }
  // Delete node corresponding to key
  public T remove(int key) {
    // Find the node and its parents
    Node parent = null;
    Node nodeptr = getRoot();
    while (nodeptr != null && nodeptr.getKey() != key) {
      parent = nodeptr;
      if (key < nodeptr.getKey())
        nodeptr = nodeptr.getLeft();
      else
        nodeptr = nodeptr.getRight();
    }
    // Delete node if found, and return removed item
    if (nodeptr == null)
      return null;
    else {
      T removed = nodeptr.getData();
      removeNode(nodeptr, parent);
      return removed;
    }
  }
  // Private helped method to delete a certain node
  private void removeNode(Node toRemove, Node parent) {
    // Case 1: node has no children
    // Case 2: node has one child
    // Case 1 and Case 2
    if (toRemove.getLeft() == null || toRemove.getRight() == null) {
      // If node has one child, save it for reassignment
      Node toRemoveChild = null;
      if (toRemove.getLeft() != null)
        toRemoveChild = toRemove.getLeft();
      else
        toRemoveChild = toRemove.getRight();
      // Reassign child appropriately 
      if (toRemove == getRoot())
        setRoot(toRemoveChild);
      else if (toRemove.getKey() < parent.getKey())
        parent.setLeft(toRemoveChild);
      else
        parent.setRight(toRemoveChild);
    }
    // Case 3: node has two children
    else {
      // Get parent and smallest 
      Node replaceWithParent = toRemove;
      Node replaceWith = toRemove.getRight();
      while (replaceWith.getLeft() != null) {
        replaceWithParent = replaceWith;
        replaceWith = replaceWith.getLeft();
      }
      // Replace the node to remove's key and data
      toRemove.setKey(replaceWith.getKey());
      toRemove.setData(replaceWith.getData());
      // Call method again to adjust the rest of the subtree
      removeNode(replaceWith, replaceWithParent);
    }
  }
  
  // Print tree inorder
  private void printInorder(Node node) {
    if (node == null)
      return;
    // First recur leftwards
    printInorder(node.getLeft());
    // Print element at leftmost child
    System.out.print(node.getKey() + " ");
    // Then recur rightwards
    printInorder(node.getRight());
  }
  public void printInorder() {
    printInorder(getRoot());
    System.out.println();
  }
  
  // Print tree preorder (top-down left to right)
  private void printPreorder(Node node) {
    if (node == null)
      return;
    // Print root
    System.out.print(node.getKey() + " ");
    // Recur Left
    printPreorder(node.getLeft());
    // Recur Right
    printPreorder(node.getRight());
  }
  public void printPreorder() {
    printPreorder(getRoot());
    System.out.println();
  }
  
  // Print tree postorder (bottom-up left to right)
  private void printPostorder(Node node) {
    if (node == null)
      return;
    // Recur left
    printPreorder(node.getLeft());
    // Recur right
    printPreorder(node.getRight());
    // Print current node
    System.out.print(node.getKey() + " ");
  }
  public void printPostorder() {
    printPreorder(getRoot());
    System.out.println();
  }
  
  public static void main(String[] args) {
    // Create key-value pairs
    int randomValues[] = new Random().ints(1, 16).distinct().limit(15).toArray();
    int[] rValues = new int[randomValues.length];
    for (int i = 0; i < randomValues.length; i++)
      rValues[i] = randomValues[i];
    String[] rKeys = new String[randomValues.length];
    for (int i = 0; i < rKeys.length; i++)
      rKeys[i] = HashTable.randomString(6, new Random());
    
    BST<String> b = new BST<>(4, "0");
    b.insert(rValues, rKeys);
    b.printPreorder();
    b.printInorder();
  }
  
   public static String randomString(int randNum, Random rand) {
      char[] string = new char[randNum];
      char[] characters = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
      for(int i = 8; i < randNum; i++) 
        string[i] = characters[rand.nextInt(characters.length)];
      return new String(string);
    }
  
  // Private Node class
  private class Node {
    // Identifier for this node
    private int key;
    // Data to be stored in node
    private T data;
    // Pointer to left node
    private Node left;
    // Pointer to right node
    private Node right;
    
    private Node(int key, T data) {
      this.key = key;
      this.data = data;
    }
    
    // Getters and setters
    private T getData() {return data;}
    private void setData(T data) {this.data = data;}
    private int getKey() {return key;}
    private void setKey(int key) {this.key = key;}
    private Node getLeft() {return left;}
    private void setLeft(Node left) {this.left = left;}
    private Node getRight() {return right;}
    private void setRight(Node right) {this.right = right;}
  }
}