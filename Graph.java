import java.util.ArrayList;
import java.util.Collections;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * This class represents an unweighted and undirected graph
 * @author Shankar Choudhury
 */
public class Graph<K extends Comparable<? super K>, V> {
  // List of Nodes this graph has
  private ArrayList<Node> nodes;
  // List of keys already present in graph
  private ArrayList<K> keys;
  // Hash table of nodes used for word ladder
  HashTable hashTable;
  
  // Basic constructor for graph
  public Graph() {
    nodes = new ArrayList<>();
    keys = new ArrayList<>();
  }
  
  // Return size of nodes
  public int size() {
    return nodes.size();
  }
  
  /**
   * Construct a graph from a .txt file
   * @param fileName Path of .txt file to be read and construct graph from
   */
  public static <V> Graph<String, V> read (String fileName) {
    Graph<String, V> g = new Graph<>();
    try {
      BufferedReader br = new BufferedReader(new FileReader(fileName));
      String line = null;
      // Read alphanumeric keys into nodes and keys, one line at a time
      while ((line = br.readLine()) != null) {
        // List containing node and its neighbors of this line
        ArrayList<String> keyList = new ArrayList<>();
        int i = 0;
        // Read all valid keys 
        while (i < line.length()) {
          StringBuilder key = new StringBuilder();
          if (!Character.isWhitespace(line.charAt(i))) {
            // Check that key is valid
            int j = i;
            boolean append = true;
            while (j < line.length() && !Character.isWhitespace(line.charAt(j))) {
              if (!Character.isLetterOrDigit(line.charAt(i)))
                append = false;
              j++;
            }
            if (append) {
              while (i < line.length() && !Character.isWhitespace(line.charAt(i))) {
                key.append(line.charAt(i));
                i++;
              }
              keyList.add(key.toString());
            }
            else 
              i = j - 1;
          }
          i++;
        }
        // Add nodes from this line
        g.addNodes(keyList.toArray(new String[keyList.size()]));
        // Add edges from first node
        for (int j = 1; j < keyList.size(); j++) 
          g.addEdge(keyList.get(0), keyList.get(j));
      }
    }
    catch (IOException e) {
      System.out.println("Unable to read file");
    }
    finally {
      return g;
    }
  }

  public static Graph<Integer, String> readWordGraph(String fileName) {
    Graph<Integer, String> g = new Graph<>();
    try{
      BufferedReader br = new BufferedReader(new FileReader(fileName));
      String line = null;
      while ((line = br.readLine()) != null) { 
        int i = 0;
        ArrayList<Integer> keyList = new ArrayList<>();
        StringBuilder value = new StringBuilder();
        // Read keys and values into nodes for Graph g
        while (i < line.length()) {
          StringBuilder key = new StringBuilder();
          // If character is a digit, build key, and then add key to keyList
          if (Character.isDigit(line.charAt(i))) {
            while (i < line.length() && Character.isDigit(line.charAt(i))) {
              key.append(line.charAt(i));
              i++;
            }
            keyList.add(Integer.parseInt(key.toString()));
          }
          // If character is a letter, build value
          else if (Character.isLetter(line.charAt(i))) {
            while (i < line.length() && Character.isLetter(line.charAt(i))) {
              value.append(line.charAt(i));
              i++;
            }
          }
          i++;
        }
        // Add first node with key-value
        g.addNode(keyList.get(0), value.toString());
        // Add list of nodes on this line
        g.addNodes(keyList.toArray(new Integer[keyList.size()]));
        // Add edges from this list of nodes to the first node
        for (int j = 1; j < keyList.size(); j++)
          g.addEdge(keyList.get(0), keyList.get(j));
      }
      // After nodes have been read in, add them to a hash table
      g.hash(g.size());
    }
    catch (IOException e) {
      System.out.println("Unable to read file");
    }
    finally {
      return g;
    }
  }
  
  // Add all nodes of this graph to a hash table
  private void hash(int size) {
    hashTable = new HashTable(size);
    for (Node node : nodes)
      hashTable.add(node.value(), node);
  }
  
  /**
   * Print a word ladder of two words
   * @param from Word to trace word ladder from
   * @param to Word to trace word ladder to
   */
  public void wordLadder(V from, V to) {
    if (from instanceof String && to instanceof String) {
      // Find nodes from hash table
      Node fromNode = hashTable.get(from);
      Node toNode = hashTable.get(to);
      // Create a list of keys using BFS 
      K[] keyList = BFS(fromNode.key(), toNode.key());
      // Create a list of nodes with corresponding keys
      ArrayList<Node> nodeList = new ArrayList<>();
      for (K k : keyList)
        nodeList.add(get(k));
      // Print results, which will be a word ladder!
      for (int i = 0; i < nodeList.size(); i++) {
        if ( i < nodeList.size() - 1) 
          System.out.print(nodeList.get(i).value() + " -> ");
        else {
          System.out.print(nodeList.get(i).value());
          System.out.println();
        }
      }
    }
    else 
      throw new IllegalArgumentException("Words must be of type String");
  }
  
  public static void main(String[] args) {
    Graph<Integer, String> g1 =Graph.readWordGraph("C:\\Users\\shank\\OneDrive\\Desktop\\Data Structures\\Assignment 6\\LargeWordGraph");
    g1.wordLadder("squish", "abator");
  }
  
  /**
   * Add node to graph if key isn't already present, or add data to node if node corresponding to key exists and has empty data
   * @param name Name of node to add to graph
   * @return true Node was successfully added to graph
   * @return false Node was unsuccessfully added to graph because node's key already exists and node's data is occupied
   */
  public boolean addNode(K name, V data) {
    if (!keys.contains(name)) {
      keys.add(name);
      return nodes.add(new Node(name, data));
    }
    else if (keys.contains(name) && get(name).value() == null) {
      get(name).setValue(data);
      return true;
    }
    return false;
  }
  
  // Helper method to return a Node based on its key
  protected Node get(K key) {
    return nodes.get(keys.indexOf(key));
  }
  
  /**
   * Add list of nodes to graph if their keys aren't already present
   * @param names Names of nodes to add to graph
   * @param data Data of nodes to add to graph
   * @return true All nodes were successfully added to graph
   * @return true Not all or none of nodes were added to graph because node's key already exists
   */
  public boolean addNodes(K[] names, V[] data) {
    if (names.length != data.length)
      throw new IllegalArgumentException("Names and Data must have same number of elements");
    boolean allAdded = true;
    int i = 0;
    // Add nodes with corresponding keys if key doesn't exist
    while (i < names.length) {
      if (!addNode(names[i], data[i])) 
        allAdded = false;
      i++;
    }
    return allAdded;
  }
  
  // Helper method used to read in a .txt file containing just keys
  private boolean addNodes(K[] names) {
    boolean allAdded = true;
    int i = 0;
    // Add nodes with corresponding keys if key doesn't exist
    while (i < names.length) {
      if (!addNode(names[i], null)) 
        allAdded = false;
      i++;
    }
    return allAdded;
  }
  
  /**
   * Add an edge from node "from" to node "to"
   * @param from Node to add neighbor node "to"
   * @param to Node that will be neighbor for "from"
   * @return true Edge was successfully added to graph
   * @return false Edge was unsuccessfully added to graph because "from" doesn't exist or unable to add more neighboring nodes to "from"
   */
  public boolean addEdge(K from, K to) {
    // If "from" doesn't exist, then return false
    if (!keys.contains(from))
      return false;
    // If "to" exists, then add it to from's nieghbors and add from to to's neighbors
    Node fromNode = get(from);
    if (keys.contains(to)) {
      Node toNode = get(to);
      return fromNode.add(toNode) && toNode.add(fromNode);
    }
    // If not, create a new node with "to" as key, add it to from's neighbors, add to this graph's collection of nodes and keys
    else {
      Node toNode = new Node(to, null);
      toNode.add(fromNode);
      fromNode.add(toNode);
      return nodes.add(toNode) && keys.add(to);
    }
  }
  
  /**
   * Add edge from node "from" to list of nodes in "toList"
   * @param from Node to add neighbor node "to"
   * @param toList List of nodes to add to from's neighbors
   * @return true All nodes from toList were successfully added to "from"'s neighbors
   * @return false From doesn't exist or not all nodes from toList were successfully added
   */
  @SuppressWarnings("unchecked")
  public boolean addEdges(K from, K... toList) {
    // If "from" doesn't exist, then return false
    if (!keys.contains(from))
      return false;
    boolean allAdded = true;
    Node fromNode = get(from);
    for (K to : toList) {
      // If "to" exists, then add it to from's nieghbors and add from to to's neighbors
      if (keys.contains(to)) {
        Node toNode = get(to);
        fromNode.add(toNode);
        toNode.add(fromNode);
        allAdded = false;
      }
      // If not, create a new node with "to" as key, add it to from's neighbors, add to this graph's collection of nodes and keys
      else {
        Node toNode = new Node(to, null);
        toNode.add(fromNode);
        fromNode.add(toNode);
        keys.add(to);
      }
    }
    return allAdded;
  }
  
  /**
   * Remove node and all of its edges (references to neighboring nodes) from graph
   * @param name Key of node to remove from graph
   * @return true Node was successfully removed
   * @return false Node was unsuccessfully removed
   */
  public boolean removeNode(K name) {
    if (!keys.contains(name))
      return false;
    // Find node to remove
    Node toRemove = get(name);
    // Remove itself from its neighbor's nieghbor list
    for (Node neighbor : toRemove.neighbors()) {
      // Remove itself from neighbor's neighbor list
      neighbor.neighbors().remove(neighbor.neighborKeys().indexOf(name));
      // Remove its key from neighbor's neighborKeys list
      neighbor.neighborKeys().remove(name);
    }
    // Remove node from this graph's list of nodes and keys
    nodes.remove(keys.indexOf(name));
    return keys.remove(name);
  }
  
  /**
   * Remove a list of nodes and their edges from graph
   * @param nodeList List of keys of node to remove from graph
   * @return true All nodes were successfully removed
   * @return false None or not all the nodes were successfully removed from graph
   */
  @SuppressWarnings("unchecked")
  public boolean removeNodes(K... nodeList) {
    boolean allRemoved = true;
    for (K key : nodeList) {
      if (!keys.contains(key))
        allRemoved = false;
      // If key exists, remove key from keys and node corresponding to key from nodes
      else {
        // Go through all nodes, and remove node from their neighbors list if it has it
        Node toRemove = get(key);
        // Remove itself from its neighbor's nieghbor and neighborKey lists
        for (Node neighbor : toRemove.neighbors()) {
          neighbor.neighbors().remove(neighbor.neighborKeys().indexOf(key));
          neighbor.neighborKeys().remove(key);
        }
        nodes.remove(keys.indexOf(key));
        keys.remove(key);
      }
    }
    return allRemoved;
  }
  
  /**
   * Print all nodes and their neighbors alphabetically 
   */
  public void printGraph() {
    ArrayList<Node> toOrder = new ArrayList<>(nodes);
    // Sort nodes by alphabetical order of their keys using quickSort
    quickSort(toOrder, 0, toOrder.size() - 1);
    // Go through each node, sort its neighbors alphabetically, and then print the current node and its neighbors
    for (Node node : toOrder) {
      // Sort this node's nieghbors alphabetically by their key's
      quickSort(node.neighbors(), 0, node.neighbors().size() - 1);
      // Print out node's key and its neighbors
      System.out.print("<" + node.key() + "> ");
      for (Node neighbor : node.neighbors()) 
        System.out.print("<" + neighbor.key() + "> ");
      System.out.println();
    }
  }
  
  // Private method of quickSorting
  private void quickSort(ArrayList<Node> nodes, int low, int high) {
    if (low < high) {
      // Rearrange outside of pivot using Hoare's scheme
      int pivotIndex = partition(nodes, low, high);
      // Recur on subarray containing elements less than pivot's index
      quickSort(nodes, low, pivotIndex);
      // Recur on subarray containing elements greater than pivot's index
      quickSort(nodes, pivotIndex + 1, high);
    }
  }
  
  // Private method of partitioning for quickSort using Hoare's partitioning scheme
  private int partition(ArrayList<Node> nodes, int low, int high) {
    Node pivot = nodes.get(low + high / 2);
    int left = low - 1;
    int right = high + 1;
    // Swap values of i and j until i is greater than or equal to j
    while (true) {
      // Find leftmost element greater than or equal to pivot
      do {
        left++;
      }
      while (nodes.get(left).key().compareTo(pivot.key()) < 0);
      // Find rightmost element smaller than or equal to pivot
      do {
        right--;
      }
      while (nodes.get(right).key().compareTo(pivot.key()) > 0);
      // If values don't meet, swap values at left and right;
      if (left < right)
        swap(nodes, left, right);
      // If two pointers meet, return j since it will be the end of the left array
      else
        return right;
    }
  }
  
  // Private method for swapping in partition
  private void swap (ArrayList<Node> nodes, int i, int j) {
    Node temp = nodes.get(i);
    nodes.set(i,nodes.get(j));
    nodes.set(j, temp);
  }
  
  /**
   * Return an array describing the path from one node to another using an iterative depth-first search
   * @param from Key of node to start path from
   * @param to Key of node to find path to
   * @return result Path between node "from" and "to", returns an empty array if no path exists
   */
  @SuppressWarnings("unchecked")
  public K[] DFS(K from, K to) {
    Node fromNode = get(from);
    Node toNode = get(to);
    ArrayList<Node> visited = new ArrayList<>();
    Stack stack = new Stack();
    ArrayList<K> path = new ArrayList<>();
    // Initialized stack and path with starting node
    stack.push(fromNode);
    visited.add(fromNode);
    path.add(fromNode.key());
    // Perform DFS traversal while stack is not empty
    while (!stack.isEmpty()) {
      // If current node is the "to" node, then return path
      Node current = stack.peek();
      if (current.key().equals(toNode.key())) 
        return (K[]) path.toArray(new Comparable[path.size()]);
      // If current node is not the "to" node, add its neighbors to the stack until 
      // a visited neighbor is encountered or until all neighbors have been added
      boolean hasUnvisited = false;
      for (int i = 0; i < current.neighbors().size() && !hasUnvisited; i++) {
        if (!visited.contains(current.neighbors().get(i))) {
          stack.push(current.neighbors().get(i));
          visited.add(current.neighbors().get(i));
          path.add(current.neighbors().get(i).key());
          hasUnvisited = true;
        }
      }
      // If current node has no neighbors or all neighbors have been visited, pop it from the stack and look at the next node
      if (!hasUnvisited) {
        stack.pop();
        path.remove(path.size() - 1);
      }
    }
    return (K[]) new Comparable[0];
  }
  
  /**
   * Return an array describing the path from one node to another using a recursive depth-first search
   * @param from Key of node to start path from
   * @param to Key of node to find path to
   * @return result Path between node "from" and "to", returns an empty array if no path exists
   */
  @SuppressWarnings("unchecked")
  public K[] recDFS(K from, K to) {
    // List to keep track of visited arrays
    boolean[] visited = new boolean[nodes.size()];
    // Current path from "from" node to the "to" node
    ArrayList<K> path = new ArrayList<>();
    DFSFinder(nodes.get(keys.indexOf(from)), to, visited, path);
    return (K[]) path.toArray(new Comparable[path.size()]);
  }
  
  // Private helper method for DFS to find connecting nodes to "to" node
  private boolean DFSFinder(Node node, K to, boolean[] visited, ArrayList<K> path) {
    // Mark current node as visited and add to path
    visited[keys.indexOf(node.key())] = true;
    path.add(node.key());
    // If current node is the 'to' node, path has been found so return true
    if (node.key().equals(to)) 
      return true;
    // Recurse on neighbors of current node
    for (Node neighbor : node.neighbors()) {
      if (!visited[keys.indexOf(neighbor.key())]) {
        // If path found, return true
        if (DFSFinder(neighbor, to, visited, path)) 
          return true;
      }
    }
    // If no path found, backtrack and remove current node from path, and return false
    path.remove(path.size() - 1);
    return false;
  }
  
  /**
   * Return an array describing the shortest path from one node to another using a breadth-first search
   * @param from Key of node to start path from
   * @param to Key of node to find path to
   * @return result Shortest path between node "from" and "to", returns an empty array if no path exists
   */
  @SuppressWarnings("unchecked")
  public K[] BFS(K from, K to) {
    if (!keys.contains(from) || !keys.contains(to))
      throw new NoSuchElementException("Input keys must exist in graph");
    // Do BFS on node from
    K[] fromBFS = (K[]) BFSfinder(get(from));
    // Return a reconstructed path from Node from to Node to
    return buildPath(from, to, fromBFS);
  }
  
  // Private helper method to return a BFS traversal from node "from"
  private Comparable[] BFSfinder(Node from) {
    // Create queue of visited nodes to add and remove from as we traverse graph
    Queue q = new Queue(from);
    boolean[] visited = new boolean[nodes.size()];
    visited[keys.indexOf(from.key())] = true;
    // List of nodes visited via BFS starting from the "from" node
    Comparable[] fromBFS = new Comparable[nodes.size()];
    // Index of fromBFS to place visited node for BFS path from Node "from"
    while (!q.isEmpty()) {
      // Dequeue current node to read current neighbors into queue
      Node node = q.dequeue();
      // Visit each node, add it to queue, add current node to path, and then visit those nodes again 
      for (Node neighbor : node.neighbors) {
        if (!visited[keys.indexOf(neighbor.key())]) {
          q.enqueue(neighbor);
          visited[keys.indexOf(neighbor.key())] = true;
          fromBFS[keys.indexOf(neighbor.key())] = node.key();
        }
      }
    }
    return fromBFS;
  }
  
  // Private helper method to determine if path from node "from" can be traced to node "to" 
  @SuppressWarnings("unchecked")
  private K[] buildPath(K from, K to, K[] fromBFS) {
    // Path to build backwards from "to"
    ArrayList<K> path = new ArrayList<>();
    // Build path from "to" until no path can be built or if node "from" is found
    for (K currentKey = to; currentKey != null; currentKey = fromBFS[keys.indexOf(currentKey)])
      path.add(currentKey);
    // If the end node is the "from" node, then path was successful
    if (path.get(path.size() - 1).equals(from)) {
      Collections.reverse(path);
      return (K[]) path.toArray(new Comparable[path.size()]);
    }
    // If not, then return a new empty array 
    return (K[]) new Comparable[0];
  }
  

  
  /**
   * Below are private classes used in this graph
   * Node is a node for this graph
   * Stack is a stack used for the iterative DFS method
   * Queue is a queue used for the BFS method
   * HashTable is a hash table used for WordLadder
   */
  // Private class for nodes of this graph
  private class Node {
    // Key of node
    private K key;
    // Information stored at this node
    private V value;
    // Nodes that this node is connected to
    private ArrayList<Node> neighbors;
    // List of keys of nodes that this node has neighbors for
    private ArrayList<K> neighborKeys;
    // Constructor for a node
    private Node(K key, V value) {
      this.key = key;
      this.value = value;
      neighbors = new ArrayList<Node>();
      neighborKeys = new ArrayList<K>();
    }
    // Getters for fields of node 
    protected K key() {return key;}
    private V value() {return value;}
    private void setValue(V value) {this.value = value;}
    private ArrayList<Node> neighbors() {return neighbors;}
    private ArrayList<K> neighborKeys() {return neighborKeys;}
    // Add neighboring node to this node if it isn't already a neighbor
    private boolean add(Node neighbor) {
      if (!neighborKeys.contains(neighbor.key())) {
        neighborKeys.add(neighbor.key());
        return neighbors.add(neighbor);
      }
      return false;
    }
  }
  
  // Private Stack class used in iterative DFS approach
  private class Stack {
    // Data structure of stack as ArrayList
    private ArrayList<Node> nodeStack;
    // Private constructor for a new stack
    private Stack() {nodeStack = new ArrayList<Node>();}
    // Stack methods
    private void push(Node node) {nodeStack.add(node);}
    private Node pop() {return nodeStack.remove(nodeStack.size() - 1);}
    private Node peek() {return nodeStack.get(nodeStack.size() - 1);}
    private boolean isEmpty() {return nodeStack.size() == 0;}
  }

  // Private class for queue used in BFS
  private class Queue {
    // Head of queue
    private Qnode head;
    // Constructor for a Queue
    private Queue(Node element) {head = new Qnode(element, null);}
    // Determine if Queue is empty
    private boolean isEmpty() {return head == null;}
    // Add element to queue
    private void enqueue(Node node) {
      // case 1: head doesn't exist
      if (isEmpty()) 
        head = new Qnode(node, null);
      // case 2: head exists
      else{
        Qnode nodeptr = head;
        while (nodeptr.next() != null)
          nodeptr = nodeptr.next();
        // Add new Qnode containing element
        nodeptr.setNext(new Qnode(node, null));
      }
    }
    // Remove and return front node's element
    private Node dequeue() {
      if (!isEmpty()) {
        Node save = head.element();
        head = head.next();
        return save;
      }
      return null;
    }
    
    private class Qnode {
      // Element of Qnode
      private Node element;
      // Pointerer to next node
      private Qnode next;
      //Constructor for a Qnode
      private Qnode(Node element, Qnode next) {
        this.element = element;
        this.next = next;
      }
      // Getters and setters for fields
      private Node element() {return element;}
      private Qnode next() {return next;}
      private void setNext(Qnode next) {this.next = next;}
    }
  }
  
  // Private class for a hash table used in word ladders
  // Keys for each entry will be String data of graph node
  // Values for each entry will be Integer key of graph node
 private class HashTable {
    // Constructor for entry into hash table
    private class Entry {
      // Key of entry
      private V key;
      // Value of entry to tell if it has been removed or not
      private boolean removed;
        // What entry is holding, in this case a graph node
      private Node value;
      // Constructor for an entry
      private Entry(V key, Node value) {
        this.key = key;
        this.value = value;
        removed = false;
      }
      private V key() {return key;}
      private void setKey(V key) {this.key = key;}
      private Node value() {return value;}
      private void setValue(Node value) {this.value = value;}
      private boolean removed() {return removed;}
      private void setRemoved(boolean removed) {this.removed = removed;}
    }
    
    // Table used for storing values
    private ArrayList<Entry> table;
    // Number of elements currently in table
    private int size;
    // Number of elements table can hold, will always be a prime number
    private int capacity;
    
    // Constructor for hash table
    private HashTable(int capacity) {
      //ArrayList<Entry> a = new ArrayList<Entry>(19);
      table = new ArrayList<>();
      for (int i = 0; i < capacity; i++)
        table.add(null);
      this.capacity = capacity;
      size = 0;
    }
    
    // Add a graph node to this hash table
    private void add(V key, Node value) {
      int i = probe(key);
      // Check if rehashing is necessary after addition of node
      rehash();
      if (i == -1)
        throw new IllegalArgumentException("Cannot insert node into hash table");
      else {
        if (table.get(i) == null)
          table.set(i, new Entry(key, value));
        else {
          table.get(i).setKey(key);
          table.get(i).setValue(value);
          table.get(i).setRemoved(false);
        }
        size++;
      }
    }
    
    // Return node based on key
    private Node get(V key) {
      int i = find(key);
      if (i == -1)
        throw new NoSuchElementException("Node with key does not exist");
      return table.get(i).value();
    }
    
    // Remove node based on key
    private void remove(V key) {
      int i = find(key);
      if (i == -1)
        throw new NoSuchElementException("Node with key does not exist");
      table.get(i).setRemoved(true);
    }
    
    // Hash function 1
    private int hashVal1(String key) {
      return Math.abs(key.hashCode() % capacity);
    }
    // Hash function 2
    private int hashVal2(String key) {
      int newMod = getPrevPrime(capacity);
      return Math.abs(newMod - (key.hashCode() % newMod));
    }
    // Helper method for second hash function to find nearest prime number that is smaller than input number
    private int getPrevPrime(int n) {
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
    
    // Helper method used in add to probe for available index in hash table 
    private int probe(V key) {
      if (key instanceof String) {
        int i = hashVal1((String) key);
        int j = hashVal2((String) key);
        int iterations = 0;
        while (table.get(i) != null && !table.get(i).removed()) {
          i = (i + (iterations + j)) % capacity;
          iterations++;
          // If unable to find a spot in table to put node, return -1
          if (iterations > capacity) 
            return -1;
        }
        // Return index of hash table available to put node in table
        return i;
      }
      throw new IllegalArgumentException("Node must have a value of type String");
    }
    
    // Helper method to find next prime number 
    // Used in rehashing method and constructor
    private int findNextPrime(int num) {
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
    
    // Helper method to rehash old table if load is >= .5 to ensure constant time to return key when putting entry into table
    private void rehash() {
      if ((1.0 * size) / capacity >= .5) {
        // Save old table
        ArrayList<Entry> oldTable = new ArrayList<>(table);
        // Create new table with new prime greater than 2x of initial capacity
        table = new ArrayList<>();
        capacity = findNextPrime(2 * capacity);
        for (int i = 0; i < capacity; i++)
          table.add(null);
        size = 0;
        // Re-add elements over from old table to new table
        for (Entry entry : oldTable) {
          if (entry != null)
            add(entry.key(), entry.value());
        }
      }
    }
    
    // Helper method for get and remove to return place of associated with key in table
    private int find(V key) {
      //System.out.println(key);
      if (key instanceof String) {
        int i = hashVal1((String) key);
        int j = hashVal2((String) key);
        int iterations = 0;
        while (table.get(i) != null) {
          if (!table.get(i).removed() && table.get(i).key().equals(key))
            return i;
          i = (i + (iterations + j)) % capacity;
          iterations++;
          if (iterations > capacity)
            return -1;
        }
        return -1;
      }
      throw new IllegalArgumentException("Key must be a String type");
    }
  }
  
  
  
}