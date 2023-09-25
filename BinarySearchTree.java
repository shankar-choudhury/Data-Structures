/**
 * This class represents a binary tree and its various methods
 * @author Shankar Choudhury
 */
public class BinarySearchTree {
  // Root of this BST
  private Node root;
  
  /**
   * Return root of BST
   * @return root The root of this BST
   */
  private Node getRoot() {
    return root;
  }
  
  /**
   * Assign new root for BST
   * @param root New root for BST
   */
  private void setRoot(Node root) {
    this.root = root;
  }
  
  /**
   * Insert a new node with the corresponding key
   * @param key The data to be stored at the new node
   */
  public void insert(int key) {
    if (getRoot() == null)
      setRoot(new Node(key));
    else {
      Node parent = null;
      Node nodeptr = getRoot();
      // Find correct place for new node
      while (nodeptr != null) {
        parent = nodeptr;
        if (key < nodeptr.getKey()) 
          nodeptr = nodeptr.getLeft();
        else
          nodeptr = nodeptr.getRight();
      }
      // Insert new node with key
      if (key < parent.getKey())
        parent.setLeft(new Node(key));
      else
        parent.setRight(new Node(key));
    }
  }
  
  /**
   * Create tree from an array of keys, and overwrite data of this tree
   * @param values Array of keys to create tree from
   */
  public void createTree(int[] values) {
    // Dereference old tree by assiging a new root with no children
    setRoot(new Node(values[0]));
    // Iterate through list of keys, inserting one at a time
    for (int i = 1; i < values.length; i++) 
        insert(values[i]);
  }
  
  /**
   * Search for a key
   * @param key Key to search for in this tree
   * @return true Key was found
   * @return false Key does not exist in tree
   */
  public boolean search(int key) {
    // If tree is empty, return false
    if (getRoot() == null)
      return false;
    else {
      // Look for node with corresponding key, starting from root
      Node nodeptr = getRoot();
      while (nodeptr != null && nodeptr.getKey() != key) {
        if (key < nodeptr.getKey())
          nodeptr = nodeptr.getLeft();
        else
          nodeptr = nodeptr.getRight();
      }
      // If nodeptr is null, then key with corresponding key does not exist in this tree
      if (nodeptr != null)
        return true;
      else
        return false;
    }
  }
  
  /**
   * Delete node with key
   * @param key Key that the deleted node will have
   * @return deletedNode Node that was deleted
   * @return null Key does not exist in tree
   */
  public Node delete(int key) {
    Node parent = null;
    Node nodeptr = getRoot();
    // Look for key
    while (nodeptr != null && nodeptr.getKey() != key) {
      parent = nodeptr;
      if (key < nodeptr.getKey())
        nodeptr = nodeptr.getLeft();
      else
        nodeptr = nodeptr.getRight();
    }
    // If nodeptr is null, then key does not exist in this tree
    if (nodeptr == null)
      return null;
    else {
      Node deletedNode = nodeptr;
      // "Delete" this node by replacing its contents with the smallest key in its subtree
      deleteNode(nodeptr, parent);
      return deletedNode;
    }
  }
  /**
   * Helper method to "delete" specific node by replacing its key contents with 
   * the smallest key in right subtree, and continuing to do so until not necessary
   * @param toDelete The node to remove
   * @param parent The parent node of toDelete
   */
  private void deleteNode(Node toDelete, Node parent) {
    // Case 1: node toDelete has no children or one child
    if (toDelete.getLeft() == null || toDelete.getRight() == null) {
      // If node has one child, save it for reassignment
      Node toDeleteChild = null;
      if (toDelete.getLeft() != null)
        toDeleteChild = toDelete.getLeft();
      else
        toDeleteChild = toDelete.getRight();
      // If the node to delete is the root, assign its child the root
      if (toDelete == getRoot())
        setRoot(toDeleteChild);
      // Reassign child appropriately 
      else if (toDelete.getKey() < parent.getKey())
        parent.setLeft(toDeleteChild);
      else
        parent.setRight(toDeleteChild);
    }
    // Case 2: node has two children
    else {
      // Find smallest data in right subtree
      Node replaceWithParent = toDelete;
      Node replaceWith = toDelete.getRight();
      while (replaceWith.getLeft() != null) {
        replaceWithParent = replaceWith;
        replaceWith = replaceWith.getLeft();
      }
      // Replace the key of the node toDelete with the smallest key 
      toDelete.setKey(replaceWith.getKey());
      // Call method again to actually remove the node we used to replace the info of the node that was "to delete"
      deleteNode(replaceWith, replaceWithParent);
    }
  }
  
  /**
   * Perform an inorder traversal  (left, parent, right) of this Binary Search Tree
   * Use a stack to avoid recursive methods to avoid recursion
   */
  public void inorderRec() {
    // End method if traversing an empty Binary Search Tree
    if (getRoot() != null) {
      Stack s = new Stack();
      Node nodeptr = getRoot();
      while (nodeptr != null || !s.isEmpty()) {
        // Reach leftmost node of tree and push nodes onto stack
        while (nodeptr != null) {
          s.push(nodeptr);
          nodeptr = nodeptr.getLeft();
        }
        // Reassign nodeptr to be at top of stack, as it will be the leftmost and subsequently smallest node
        nodeptr = s.pop();
        // Print out what is at top of stack
        // What is at top of stack should be the smallest key in subtree
        System.out.print(nodeptr.getKey() + " ");
        // Visit right subtree
        nodeptr = nodeptr.getRight();
      }
      System.out.println("\n");
    }
  }
  
  /**
   * perform a preorder traversal (visit parent, recursively visit left, then right) of this Binary Search Tree
   * Use a stack to avoid recursion and use less stack space
   */
  public void preorderRec() {
    if (getRoot() != null) {
      Stack s = new Stack();
      Node nodeptr = getRoot();
      while (nodeptr != null || !s.isEmpty()) {
        while (nodeptr != null) {
          // Print root node first
          System.out.print(nodeptr.getKey() + " ");
          // Push right node into stack as reference for later
          if (nodeptr.getRight() != null)
            s.push(nodeptr.getRight());
          // Shift to left node and continue until no more left nodes are left
          nodeptr = nodeptr.getLeft();
        }
        if (!s.isEmpty())
          // Reassign nodeptr to be the right node most recently pushed, and continue traversal
          nodeptr = s.pop();
      }
      System.out.println("\n");
    }
  }
  
  /**
   * Perform a postorder traversal (recursively visit left, then recursively visit right, then parent) of this Binary Search Tree
   * Done recursively
   */
  public void postorderRec() {
    if (getRoot() != null) {
      postorder(getRoot());
      System.out.println("\n");
    }
  }
  /**
   * Helper method for postorder traversal
   * @param root The root of this Binary Search Tree
   */
  private void postorder(Node root) {
    if (root.getLeft() != null)
      postorder(root.getLeft());
    if (root.getRight() != null)
      postorder(root.getRight());
    System.out.print(root.getKey() + " ");
  }
    
  /**
   * Find the k'th smallest element in BST using inorder traversal
   * @param k The k'th smallest element in BST
   * @return null Invalid input or searching tree 
   * @precondition k must be greater than 0
   */
  public Node kthSmallest(int k) {
    // End method if traversing an empty Binary Search Tree or invalid key
    if (getRoot() == null || k < 1)
      return null;
    Stack s = new Stack();
    Node nodeptr = getRoot();
    int counter= 0;
    Node prevNode = null;
    while (counter < k && (nodeptr != null || !s.isEmpty())) {
      // Reach leftmost node of tree and push nodes onto stack
      while (nodeptr != null) {
        s.push(nodeptr);
        nodeptr = nodeptr.getLeft();
      }
      // Reassign nodeptr to be at top of stack
      nodeptr = s.pop();
      // Increment counter
      counter++;
      // Reassign prevNode
      prevNode = nodeptr;
      // Visit right subtree
      nodeptr = nodeptr.getRight();
    }
    return prevNode;
  }
    
  // Testing demo for BinarySearchTree
  public static void main(String[] args) {
    BinarySearchTree bst = new BinarySearchTree();
    System.out.println("Test insertion of 4 into empty tree");
    bst.insert(4);
    System.out.println("Result: " + bst.getRoot().getKey() + " | Expected: 4" + "\n");
    
    System.out.println("Test insertion of 5 into tree with single node");
    bst.insert(5);
    System.out.println("Result: " + bst.getRoot().getKey() + " | Expected: 4");
    System.out.println("Result of right child: " + bst.getRoot().getRight().getKey() + " | Expected: 5");
    System.out.println("Result of left child: " + bst.getRoot().getLeft() + " | Expected: null \n");
    
    System.out.println("Test insertion into tree with many nodes");
    bst.insert(3);
    System.out.println("After inserting 3 -");
    System.out.println("Result: " + bst.getRoot().getKey() + " | Expected: 4");
    System.out.println("Result of right child: " + bst.getRoot().getRight().getKey() + " | Expected: 5");
    System.out.println("Result of left child: " + bst.getRoot().getLeft().getKey() + " | Expected: 3");
    
    bst.insert(2);
    System.out.println("After inserting 2 -");
    System.out.println("Result of left child: " + bst.getRoot().getLeft().getKey() + " | Expected: 3");
    System.out.println("Result of left child's right child: " + bst.getRoot().getLeft().getLeft().getKey() + " | Expected: 2 \n");
    
    System.out.println("Test insertion into tree with duplicate key of 4");
    bst.insert(4);
    System.out.println("Result of right child: " + bst.getRoot().getRight().getKey() + " | Expected: 5");
    System.out.println("Result of right child's left child: " + bst.getRoot().getRight().getLeft().getKey() + " | Expected: 4 \n\n");
    
    System.out.println("Test createTree with array {11, 7, 14, 9, 10, 8, 5, 6, 4, 13, 17, 19, 13, 16, 12}");
    bst.createTree(new int[]{11, 7, 14, 9, 10, 8, 5, 6, 4, 13, 17, 19, 13, 16, 12});
    System.out.println("Test root has been overwritten, indicating previous BST structure has been dereferenced");
    System.out.println("Result of root's key: " + bst.getRoot().getKey() + " | Expected: 11");
    
    System.out.println("Test successful execution of createTree() with empty BST");
    BinarySearchTree bs = new BinarySearchTree();
    bs.createTree(new int[]{11, 7, 14, 9, 10, 8, 5, 6, 4, 13, 17, 19, 13, 16, 12});
    System.out.println("Result of root's key: " + bs.getRoot().getKey() + " | Expected: 11\n\n");
    
    System.out.println("Test search() with empty tree");
    BinarySearchTree b = new BinarySearchTree();
    System.out.println("Result of search(3) in empty tree: " + b.search(3) + " | Expected: false\n");
    
    b.insert(1);
    System.out.println("After inserting 1 -");
    System.out.println("Result of search(3) in tree with one node: " + b.search(3) + " | Expected: false");
    System.out.println("Result of search(1) in tree with one node: " + b.search(1) + " | Expected: true\n");
    
    b.createTree(new int[]{11, 7, 14, 9, 10, 8, 5, 6, 4, 13, 17, 19, 13, 16, 12});
    System.out.println("After creating tree with {11, 7, 14, 9, 10, 8, 5, 6, 4, 13, 17, 19, 13, 16, 12} -");
    System.out.println("Test search() at beginning of tree with search(11): " + b.search(11) + " | Expected: true");
    System.out.println("Test search() in middle of tree with search(9): " + b.search(9) + " | Expected: true");
    System.out.println("Test search() at end of tree with search(12): " + b.search(12) + " | Expected: true");
    System.out.println("Result of search(15) in tree with many nodes: " + b.search(15) + " | Expected: false\n\n");
    
    BinarySearchTree b1 = new BinarySearchTree();
    System.out.println("Test delete(4) on empty tree");
    System.out.println("Result of delete(4) on empty tree: " + b1.delete(4) + " | Expected: null\n");
    b1.insert(1);
    System.out.println("Test delete(1) on tree after inserting 1");
    System.out.println("Result of delete(1) on empty tree: " + b1.delete(1).getKey() + " | Expected: 1");
    System.out.println("Result of delete(4) on empty tree: " + b1.delete(4) + " | Expected: null");
    b1.insert(1);
    b1.insert(2);
    System.out.println("Result of delete(1) on tree with two nodes, root has one child: " + b1.delete(1).getKey() + " | Expected: 1\n");
    
    b1.createTree(new int[]{11, 7, 14, 9, 10, 8, 5, 6, 4, 13, 17, 19, 13, 16, 12});
    System.out.println("After creating tree with {11, 7, 14, 9, 10, 8, 5, 6, 4, 13, 17, 19, 13, 16, 12} -");
    System.out.println("Test correct node deleted with delete(8) on lowest-level leaf node: " + b1.delete(8).getKey() + " | Expected: 8");
    System.out.println("Test correct null value after delete(8): " + b1.getRoot().getLeft().getRight().getLeft() + " | Expected: null");
    System.out.println("Test correct node replacement upon deletion of middle-level node after delete(17): " + b1.delete(17).getKey() + " | Expected: 19");
    System.out.println("Test correct deletion of node that was used to replace node after delete(17): " + b1.getRoot().getRight().getRight().getRight() + " | Expected: null");
    System.out.println("Test correct key replacement after delete(11) to delete root: "  + b1.delete(11).getKey() + " | Expected: 12\n\n"); 
    
    BinarySearchTree b2 = new BinarySearchTree();
    System.out.println("Test inorderRec() with empty tree:");
    b2.inorderRec();
    System.out.println("Expected result of empty tree traverse: \n");
    System.out.println("Test preorderRec() with empty tree:");
    b2.preorderRec();
    System.out.println("Expected result of empty tree traverse: \n");
    System.out.println("Test postorderRec() with empty tree:");
    b2.postorderRec();
    System.out.println("Expected result of empty tree traverse: \n");
    
    b2.createTree(new int[]{11, 7, 14, 9, 10, 8, 5, 6, 4, 13, 17, 19, 13, 16, 12});
    System.out.println("Test inorderRec() with empty tree:");
    b2.inorderRec();
    System.out.println("Expected result of inorder traverse:");
    System.out.println("4 5 6 7 8 9 10 11 12 13 13 14 16 17 19 \n");
    System.out.println("Test preorderRec() with empty tree:");
    b2.preorderRec();
    System.out.println("Expected result of preorder traverse:");
    System.out.println("11 7 5 4 6 9 8 10 14 13 12 13 17 16 19 \n");
    System.out.println("Test postorderRec() with empty tree:");
    b2.postorderRec();
    System.out.println("Expected result of preorder traverse:");
    System.out.println("4 6 5 8 10 9 7 12 13 13 16 19 17 14 11\n\n");
    
    BinarySearchTree b3 = new BinarySearchTree();
    System.out.println("Test kthSmallest(1) on empty tree: " + b3.kthSmallest(1) + " | Expected: null");
    System.out.println("Test kthSmallest(1) on filled tree: " + b2.kthSmallest(1).getKey() + " | Expected: 4");
    System.out.println("Test kthSmallest(7) on filled tree: " + b2.kthSmallest(7).getKey() + " | Expected: 10");
    System.out.println("Test kthSmallest(15) on empty tree: " + b2.kthSmallest(15).getKey() + " | Expected: 19");
    System.out.println("Test invalid input kthSmallest(0) on filled tree: " + b3.kthSmallest(0) + " | Expected: null");
  }
    
  
  // Private Node class for Binary Search Tree
  private class Node {
    // Data to be stored at node
    private int key;
    // Left child of this node, contains data greater than or equal to this node's data
    private Node left;
    // Right child of this node, contains data less than this node's data
    private Node right;
    
    /**
     * Constructor for a new Node
     * @param key The int data to be stored at this node
     * @left Left child of this tree
     * @right Right child of this tree
     */
    private Node(int key, Node left, Node right) {
      this.key = key;
      this.left = left;
      this.right = right;
    }
    
    /**
     * Constructor for a new Node that takes just a key
     * @param key The int data to be stored at this node
     */
    private Node(int key) {
      this.key = key;
    }
    
    /**
     *Return key
     * @return key The data being stored at this node
     */
    public int getKey() {
      return key;
    }
    
    /**
     * Assign new key
     * @param key New data to be assigned at node
     */
    private void setKey(int key) {
      this.key = key;
    }
    
    /**
     * Return left child
     * @return left Left child of this node
     */
    private Node getLeft() {
      return left;
    }
    
    /**
     * Assign left child
     * @param left New left child of this node
     */
    private void setLeft(Node left) {
      this.left = left;
    }
    
    /**
     * Return right child
     * @return right Right child of this node
     */
    private Node getRight() {
      return right;
    }
    
    /**
     * Assign right child
     * @param right New right child of this node
     */
    private void setRight(Node right) {
      this.right = right;
    }
  }
  
  
  /**
   * This class represents a Stack as a Linked List to use for traversals in BinarySearchTree
   * @author Shankar Choudhury
   */
  private class Stack {
    // first node of the list, or null if the list is empty 
    private StackNode firstNode;
    
    /**
     * Return the first node of the stack
     * @return firstNode The head of the stack
     */
    private StackNode getFirstNode() {
      return firstNode;
    }
    
    /**
     * Change the front node of the stack to a new node
     * @param node The node that will be the first node of the new stack
     */
    private void setFirstNode(StackNode node) {
      this.firstNode = node;
    }
    
    /**
     * Return whether the stack is empty
     * @return true if the list is empty
     */
    private boolean isEmpty() {
      return (getFirstNode() == null);
    }
    
    /**
     * Add an element to the front of the stack
     * @param element The element to be added to the stack
     * @return true A boolean value to indicate operation performed successfully and element was added to top of stack
     * @return false A boolean value to indicate operation was not performed successfully because some exception was thrown
     */
    private boolean push(Node element) {
      try {
        setFirstNode(new StackNode(element, getFirstNode()));
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
    private Node pop() {
      if (!isEmpty()) {
        Node save = getFirstNode().getElement();
        setFirstNode(getFirstNode().getNext());
        return save;
      }
      return null;
    }
    
    /**
     * Return the element of the first node of the stack
     * @return currentElement The element of the first node of the stack
     */
    private Node peek() {
      if (!isEmpty()) {
        return getFirstNode().getElement();
      }
      return null;
    }
    
    /** 
     * The nested class for nodes for a stack 
     */
    private class StackNode {
      // The element stored in the node 
      private Node element;
      // The next node to point to
      private StackNode next;
      
      /**
       * Constructor for a new node
       * @param element The element to store in the node
       * @param next A reference to the next node of the list 
       */
      private StackNode(Node element, StackNode next) {
        this.element = element;
        this.next = next;
      }
      
      /**
       * Returns the element stored in the node
       * @return the element stored in the node
       */
      private Node getElement() {
        return element;
      }
      
      /**
       * Changes the element stored in this node
       * @param element the new element to store
       */
      private void setElement(Node element) {
        this.element = element;
      }
      
      /**
       * Returns the next node of the list
       * @return the next node of the list
       */
      private StackNode getNext() {
        return next;
      }
      
      /**
       * Changes the next pointer for this node
       * @param next the node that should come after this node in the list
       */
      private void setNext(StackNode next) {
        this.next = next;
      }
    }
  }

  
}
    