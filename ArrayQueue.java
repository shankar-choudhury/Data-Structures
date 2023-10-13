// A class representing a regular queue
public class ArrayQueue<T extends Comparable<? super T>> {
  // Queue 
  private T[] queue;
  // Maximum size of queue
  private int maxSize;
  // Front index of queue
  private int front;
  // Rear index of queue
  private int rear;
  // Number of elements in queue
  private int size;

  // Constructor for an ArrayQueue
  @SuppressWarnings("unchecked")
  public ArrayQueue(int maxSize) {
    queue = (T[]) new Comparable[maxSize];
    this.maxSize = maxSize;
    front = 0;
    rear = -1;
    size = 0;
  }
  
  // Getters and setters
  protected T[] getQueue() {return queue;}
  private void setQueue(T[] queue) {this.queue = queue;}
  private int getMaxSize() {return maxSize;}
  protected int getFront() {return front;}
  private void setFront(int index) {front = index;}
  private void incrementFront() {front = ((getFront()+1) % getQueue().length);}
  protected int getRear() {return rear;}
  private void setRear(int index) {rear = index;}
  private void incrementRear() {rear = ((getRear()+1) % getQueue().length);}
  private int getSize() {return size;}
  private void setSize(int size) {this.size = size;}
  protected boolean isFull() {return getSize() == getMaxSize();}
  protected boolean isEmpty() {return getSize() == 0;}

  // Insert element into rear of arrayQueue
  public T enqueue(T element) {
    if(isFull()) {
      T removedElement = getQueue()[getFront()];
      getQueue()[getFront()] = element;
      incrementFront();
      incrementRear();
      return removedElement;
    }
    else {
      incrementRear();
      getQueue()[getRear()] = element;
      setSize(getSize() + 1);
      return element;
    }
  }
  
  // Remove element at front index
  public T dequeue() {
    if (isEmpty()) 
      throw new RuntimeException("Removing from empty queue");
    T removedElement = getQueue()[getFront()];
    getQueue()[getFront()] = null;
    incrementFront();
    setSize(getSize() - 1);
    return removedElement;
  }
  
  // Merge two subarrays into a single array for merge-sort algorithm
  @SuppressWarnings("unchecked")
  private void merge(T[] arr, int front, int middle, int rear) {
    // Find sizes of two subarrays to be merged
    int leftSize = middle - front + 1;
    int rightSize = rear - middle;
    
    /* Create temp arrays */
    T[] left = (T[]) new Comparable[leftSize];
    T[] right = (T[]) new Comparable[rightSize];
    
    /*Copy data to temp arrays*/
    for (int i = 0; i < leftSize; ++i)
      left[i] = arr[front + i];
    for (int j = 0; j < rightSize; ++j)
      right[j] = arr[middle + 1 + j];
    
    /* Merge the temp arrays */
    // Initial indexes of first and second subarrays
    int leftIndex = 0, rightIndex = 0;
    // Initial index of merged subarray array
    int mergedIndex = front;
    
    // Merge subarrays 
    while (leftIndex < leftSize && rightIndex < rightSize) {
      if (left[leftIndex].compareTo(right[rightIndex]) >= 0) {
        arr[mergedIndex] = left[leftIndex];
        leftIndex++;
      }
      else {
        arr[mergedIndex] = right[rightIndex];
        rightIndex++;
      }
      mergedIndex++;
    }
    
    /* Copy remaining elements of L[] if any */
    while (leftIndex < leftSize) {
      arr[mergedIndex] = left[leftIndex];
      leftIndex++;
      mergedIndex++;
    }
    
    /* Copy remaining elements of R[] if any */
    while (rightIndex < rightSize) {
      arr[mergedIndex] = right[rightIndex];
      rightIndex++;
      mergedIndex++;
    }
  }
  
  // Method to recursively call and sort the array
  private void sort (T[] arr, int front, int rear) {
    if (front < rear) {
      // Find middle index
      int middleIndex = front + (rear - front) / 2;
      // Sort left and right subarrays
      sort(arr, front, middleIndex);
      sort(arr, middleIndex + 1, rear);
      // Merge the subarrays
      merge(arr, front, middleIndex, rear);
    }
  }
    
  // Rearrange arrayQueue for elements to be in-order using merge sort
  // Create temporary array, copy elements from queue to array, sort array, and then copy elements from temp back to queue
  @SuppressWarnings("unchecked")
  public T[] orderQueue() {
    if (isEmpty()) 
      throw new RuntimeException("Ordering empty queue");
    T[] temp = (T[]) new Comparable[getQueue().length];
    // Case 1: front index is ahead of rear index
    if (getRear() >= getFront()) {
      for (int i = getFront(); i <= getRear(); i++)
        temp[i] = getQueue()[i];
      // Sort temp
      sort(temp, 0, temp.length - 1);
      // Reassign this queue's array to sorted temp array
      setQueue(temp);
    }
    // Case 2: front index is behind rear index
    else {
      int tempIndex = 0;
      // Copy from front to end of array of queue first
      for (int i = getFront(); i < getQueue().length; i++) {
        temp[tempIndex] = getQueue()[i];
        tempIndex++;
      }
      // Then copy from rear to beginning of array of queue
      for (int i = 0; i < getFront(); i++) {
        temp[tempIndex] = getQueue()[i];
        tempIndex++;
      }
      // Sort temp
      sort(temp, 0, temp.length - 1);
      // Reassign this queue's array to sorted temp array
      setQueue(temp);
      setFront(0);
      setRear(getQueue().length - 1);
    }
    return getQueue();
  }
  
}

