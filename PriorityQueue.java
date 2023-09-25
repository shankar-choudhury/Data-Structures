import java.util.NoSuchElementException;
import java.util.ArrayList;
/**
 * This class represents a priority queue using a max heap
 * @author Shankar Choudhury
 */

public class PriorityQueue<K extends Comparable<? super K>, V> {
  // Array holding keys for values in queue, will be in maxHeap structure
  private K[] keys;
  // Array holding values 
  private V[] values;
  // Number of elements stored in queue
  private int size;
  
  // Constructor for an empty queue
  @SuppressWarnings("unchecked")
  public PriorityQueue() {
    keys = (K[]) new Comparable[0];
    values = (V[]) new Object[0];
  }
  
  public static void main(String[] args) {
    Integer[] keys = {7, 10, 2, 4, 1, 3, 9};
    String[] values = {"a", "b", "c", "d", "e", "f", "g"};
    PriorityQueue<Integer, String> p = new PriorityQueue<>(keys, values);
    Object[] values1 = p.peek(4);
    for (Object o : values1)
      System.out.print(o + " ");
    System.out.println();
  }
  
  public void printQueue() {
    for (int i = 0; i < size; i++) {
      System.out.print(keys[i] + " ");
      System.out.print(values[i] + "  ");
    }
    System.out.println();
  }
  /**
   * Constructor for a queue with input key and value arrays
   * @param keys Array of keys. Will be heapsorted.
   * @param values Array of values associated with keys. Will still match with initial corresponding keys after heapsort.
   */
  @SuppressWarnings("unchecked")
  public PriorityQueue(K[] keys, V[] values) {
    if (values.length != keys.length)
      throw new IllegalArgumentException();
    // Assign keys and values
    this.keys = keys;
    this.values = values;
    // Set size
    int size1 = 0;
    for (int i = 0; i < keys.length && keys[i] != null; i++)
      size1++;
    size = size1;
    // Heapsort keys, ensuring corresponding values are in correct place as well
    heapSort(keys, values);
  }
  
  // Getters and setters for fields
  protected K[] getKeys() {return keys;}
  private void setKeys(K[] keys) {this.keys = keys;}
  protected V[] getValues() {return values;}
  private void setValues(V[] values) {this.values = values;}
  public int size() {return size;}
  private void setSize(int size) {this.size = size;}
  
  /** Helper method
   * Swap two values of keys and values simultaneously
   * @param keys Array of keys to swap keys between its indices
   * @param values Array of values to swap values between its indices
   * @param i1 First index to swap with second index
   * @param i2 Second index to swap with first index
   */
  private void swap(K[] keys, V[] values, int i1, int i2) {
    K temp1 = keys[i1];
    V temp2 = values[i1];
    keys[i1] = keys[i2];
    values[i1] = values[i2];
    keys[i2] = temp1;
    values[i2] = temp2;
  }
  
  private int leftChild(int i) {
    return 2 * i + 1;
  }
  
  /**
   * Heapify current subtree of input array by comparing input parent to its children and swapping values accordingly
   * Used with initializing array and removing element
   * @param keys Array of keys to heapify
   * @param values Array of values, whose index will be swapped alongside keys to ensure original matching pairs 
   * @param i Position from which to heapify downwards
   * @param n Size of binary heap
   */
  private void heapifyDown(K[] keys, V[] values, int i, int n) {
    int child = 0;
    K temp1 = null;
    V temp2 = null;
    for (temp1 = keys[i], temp2 = values[i]; leftChild(i) < n; i = child) {
      child = leftChild(i);
      if (child != n - 1 && keys[child].compareTo(keys[child + 1]) > 0)
        child++;
      if (temp1.compareTo(keys[child]) > 0) {
        keys[i] = keys[child];
        values[i] = values[child];
      }
      else
        break;
    }
    keys[i] = temp1;
    values[i] = temp2;
  }
  
  /**
   * Create a heap from input arrays, used with initialization lists
   * @param keys Array of keys to heapify
   * @param values Array of values whose indices will be switched around 
   */
  private void heapSort(K[] keys, V[] values) {
    // Find first nonnull index
    int firstNonNull = keys.length - 1;
    while (keys[firstNonNull] == null && firstNonNull > 0) 
      firstNonNull--;
    // Index halfway between length of array that isis the first index of the second-to-last level, so begin comparison there
    int begIndex = firstNonNull/2 - 1;
    // Build heap
    for (int i = begIndex; i > -1; i--) 
      heapifyDown(keys, values, i, firstNonNull + 1);
    // Swap front with rear and move swapping up to front and heapSort each duration to order
    for (int i = firstNonNull; i > 0; i--) {
      swap(keys, values, 0, i);
      heapifyDown(keys, values, 0, i);
    }
  }
  
  /**
   * Insert a key-value pair into heap
   * @param key Key of value to insert
   * @param value Value that key is associated with
   */
  @SuppressWarnings("unchecked")
  public void add(K key, V value) {
    setSize(size() + 1);
    // If current queue is full, create a new array one index larger, copy indices, and assign to be new keys and values
    if (size() > getKeys().length) {
      K[] temp1 = (K[]) new Comparable[getKeys().length * 2 + 1];
      V[] temp2 = (V[]) new Object[getValues().length * 2 + 1];
      for (int i = 0; i < getKeys().length; i++) {
        temp1[i] = getKeys()[i];
        temp2[i] = getValues()[i];
      }
      setKeys(temp1);
      setValues(temp2);
    }
    // Insert key and value into last available index, then heapify last index to ensure array is heap
    getKeys()[size() - 1] = key;
    getValues()[size() - 1] = value;
    heapSort(getKeys(), getValues());
  }
  
  /**
   * Find input value and change its key
   * @param key Key info to change value's key to
   * @param value Value to change its key
   */
  public void update(K key, V value) {
    // Search for value
    int i = 0;
    while (i < size() && !getValues()[i].equals(value))
      i++;
    // If i is same as size of queue, then value was not found
    if (i == size())
      throw new NoSuchElementException();
    // Else change corresponding key, then heapify arrays to ensure keys is a heap
    getKeys()[i] = key;
    heapSort(getKeys(), getValues());
  }
  
  /**
   * Return value at top of heap, the value corresponding to greatest key
   * return getValues()[0] Value with greatest key
   */
  public V peek() {
    return getValues()[0];
  }
  
  /**
   * Return and remove value from top of heap, and maintain heap structure
   */
  public V poll() {
    if (size() == 0)
      throw new NoSuchElementException();
    // Save top of heap
    V topOfHeap = getValues()[0];
    // Reassign last element to be top of heap
    getValues()[0] = getValues()[size() - 1];
    getKeys()[0] = getKeys()[size() - 1];
    // "Remove" last index by making it null
    getValues()[size() - 1] = null;
    getKeys()[size() - 1] = null;
    // Adjust size
    setSize(size() - 1);
    // Heapsort first index to restore queue order
    heapSort(getKeys(), getValues());
    // Return value removed
    return topOfHeap;
  }
  
  /**
   * Return an array containing k values corresponding to the greatest k keys
   * @param k K values from heap corresponding to greatest k keys
   */
  @SuppressWarnings("unchecked")
  public V[] peek(int k) {
    if (k > size())
      throw new IllegalArgumentException();
    V[] values = (V[]) new Object[k];
    // Collect largest keys and corresponding values 
    for (int i = 0; i < k; i++) 
      values[i] = getValues()[i];
    return values;
  }
  
  /**
   * Remove a value and its corresponding key, and return said key
   * @param value Value to remove from queue
   * @return key Key of value that was removed
   */
  public K poll(V value) {
    // Search for value
    int i = 0;
    while (i < size() && !getValues()[i].equals(value))
      i++;
    // If value not found, throw new NoSuchElementException
    if (i == size())
      throw new NoSuchElementException();
    // Save key of value to remove
    K returnedKey = getKeys()[i];
    getKeys()[i] = null;
    getValues()[i] = null;
    // Shift keys and values over to replace index, effectively "removing" key and value
    while (i + 1 < size()) {
      getKeys()[i] = getKeys()[i + 1];
      getValues()[i] = getValues()[i + 1];
      i++;
    }
    // Remove last key and value copy pair, and modify size
    getValues()[size() - 1] = null;
    getKeys()[size() - 1] = null;
    setSize(size() - 1);
    // Return key of removed element
    return returnedKey;
  }
  
  // Helper method for CMDbGroup method groupFavorites
  @SuppressWarnings("unchecked")
  protected Object[] getGroupFavs() {
    ArrayList<V> groupFavs = new ArrayList<V>();
    groupFavs.add(getValues()[0]);
    int i = 1;
    K save = getKeys()[0];
    boolean diff = false;
    while (i < getKeys().length && getKeys()[i].equals(save)) {
      groupFavs.add(getValues()[i]);
      i++;
    }
    return groupFavs.toArray(new Object[i]);
  }
  
}