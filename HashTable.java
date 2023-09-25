import java.util.Arrays; 
import java.lang.reflect.Array;
import java.util.NoSuchElementException;
/**
 * This class represents a Hash Table, storing String keys and T values by chaining key-value pairs
 * @author Shankar Choudhury
 */
public class HashTable<T> {
  // Hash table's array of chains
  private HashNode[] buckets;
  // Capacity of hash table
  private int capacity;
  // Number of buckets in use in hash table
  private int size;
  
  // Default constructor for hash table 
  public HashTable() {
    HashNode sample = new HashNode(null, null, 0, null);
    @SuppressWarnings("unchecked") HashNode[] buckets = (HashNode[]) Array.newInstance((Class<HashNode>)sample.getClass(), 31);
    this.buckets = buckets;
    capacity = 31;
  }
  
  /**
   * Constructor for hash table with a specified capacity
   * @param capacity Number of initial buckets in table
   */
  public HashTable(int capacity) {
    if (capacity < 0)
      throw new IllegalArgumentException();
    HashNode sample = new HashNode(null, null, 0, null);
    @SuppressWarnings("unchecked") HashNode[] buckets = (HashNode[]) Array.newInstance((Class<HashNode>) sample.getClass(), capacity);
    this.capacity = capacity;
  }
  
  // Getters and setters for fields
  protected HashNode[] table() {return buckets;}
  private void setTable(HashNode[] buckets) {this.buckets= buckets;}
  protected int capacity() {return capacity;}
  private void setCapacity(int capacity) {this.capacity = capacity;}
  public int size() {return size;}
  private void setSize(int size) {this.size = size;}
  
  /**
   * Return if hash table is empty
   * @return if hash table is empty
   */
  private boolean isEmpty() {return size() == 0;}
  
  /**
   * Return bucket index of key
   * @param key Key to find element's bucket index
   */
  private int index(String key) {
    return Math.abs(key.hashCode() % capacity());
  }
  
  // Helper method for rehashing
  private void rehash(int size) {
    if ((1.0 * size) / capacity() >= 1) {
      HashNode[] oldTable = table();
      setCapacity(capacity() * 2);
      @SuppressWarnings("unchecked") HashNode[] newTable = (HashNode[]) Array.newInstance((Class<HashNode>) table().getClass().getComponentType(), capacity);
      setTable(newTable);
      // Reset capacity and size
      
      setSize(0);
      // Re-add elements over from temp to new table
      for (HashNode headNodeptr: oldTable) {
        while (headNodeptr != null) {
          put(headNodeptr.key(), headNodeptr.value());
          headNodeptr = headNodeptr.next();
        }
      }     
    }
  }
  
  /**
   * Return value corresponding to input key
   * @param key Key of key-value pair
   * @return value Value of key-value pair
   */
  public T get(String key) {
    int index = index(key);
    int hashCode = key.hashCode();
    // Find head of corresponding chain by calculating index
    HashNode nodeptr = table()[index];
    // Search for key in chain
    while (nodeptr != null) {
      if (nodeptr.key().equals(key) && nodeptr.getHashCode() == hashCode)
        return nodeptr.value();
      nodeptr = nodeptr.next();
    }
    throw new NoSuchElementException();
  }
  
  /**
   * Insert a key-value pair into the hash table
   * @param key Key of key-value pair
   * @param value Value of key-value pair
   */
  @SuppressWarnings("unchecked")
  public void put(String key, T value) {
    // Find head
    int index = index(key);
    int hashCode = key.hashCode();
    HashNode nodeptr = table()[index];
    // Find place to insert and check if key is already present
    while (nodeptr != null) {
      if (nodeptr.key().equals(key) && nodeptr.getHashCode() == hashCode) {
        // If key already exists and value is an Integer, increase value of current key by input value
        if (value instanceof Integer && nodeptr.value() instanceof Integer) {
          Integer sum = (Integer) nodeptr.value() + (Integer) value;
          nodeptr.setValue((T) sum); 
        }
        // If key already exists and value is not an integer, replace value of current key with input value
        else
          nodeptr.setValue(value);
        return;
      }
      nodeptr = nodeptr.next();
    }
    // Insert key into current chain
    setSize(size() + 1);
    nodeptr = table()[index];
    HashNode newNode = new HashNode(key, value, hashCode, nodeptr);
    table()[index] = newNode;
    // Rehash table by doubling size of table if load factor reaches 1
    rehash(size);
  }
  
  /**
   * Remove key-value pair corresponding to input key
   * @param key Key of key-value pair to remove
   * @return nodeptr.value() Value of key-value pair being removed
   */
  public T remove(String key) {
    if (isEmpty())
      throw new NoSuchElementException();
    // Find head
    int index = index(key);
    int hashCode = key.hashCode();
    HashNode nodeptr = table()[index];
    // Find place to insert and check if key is already present
    HashNode prev = null;
    while (nodeptr != null && !nodeptr.key().equals(key) && hashCode != nodeptr.getHashCode()) {
      prev = nodeptr;
      nodeptr = nodeptr.next();
    }
    // If key not found
    if (nodeptr == null)
      throw new NoSuchElementException();
    // Decrement size
    setSize(size() - 1);
    // Remove key
    if (prev != null)
      prev.setNext(nodeptr.next());
    else
      table()[index] = nodeptr.next();
    return nodeptr.value();
  }
  
  // Helper method used in WordStat class
  protected void toArrays(T[] keys, String[] values) {
    int i = 0;
    for (HashNode headNodeptr: table()) {
      while (headNodeptr != null) {
        keys[i] = headNodeptr.value();
        values[i] = headNodeptr.key();
        headNodeptr = headNodeptr.next();
        i++;
      }
    }
  }
  
  // Helper method used for checking all stored values
  protected void print() {
    for (HashNode headNodeptr: table()) {
      while (headNodeptr != null) {
        System.out.print(headNodeptr.value() + " ");
        System.out.print(headNodeptr.key());
        System.out.println();
        headNodeptr = headNodeptr.next();
      }
    }
  }
  
  // Private class for this hash table's nodes
  private class HashNode {
    // Key of key-value pair this hash node
    private String key;
    // Value for this hash node
    private T value;
    // Final int value for this hash node's hash code
    final int hashCode;
    // Pointer to next hash node
    private HashNode next;
    
    /**
     * Constructor for a HashNode
     * @param key Key of key-value pair
     * @param value Value of key-value pair
     * @param hashCode Unique calculaleted value for hash placement 
     * @param next Pointer for next hashNode
     */
    private HashNode(String key, T value, int hashCode, HashNode next) {
      this.key = key;
      this.value = value;
      this.hashCode = hashCode;
      this.next = next;
    }
    
    // Getters and setters for HashNode fields
    private String key() {return key;}
    private T value() {return value;}
    private void setValue(T value) {this.value = value;}
    private int getHashCode() {return hashCode;}
    private HashNode next() {return next;}
    private void setNext(HashNode next) {this.next = next;}
  }
}