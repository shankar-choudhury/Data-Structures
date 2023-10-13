import java.util.*;
/**
 * This class represents an Art Museum that can its art sorted in different ways based on its attributes
 * @author Shankar Choudhury
 */
public class ArtMuseum {
  // Name of museum
  private String museumName;
  // This museum's art collection
  private ArrayList<Art> collection;
  
  /**
   * Constructor for an Art Museum that takes a name for the museum
   * @param museumName Name of museum
   */
  public ArtMuseum(String museumName) {
    this.museumName = museumName;
    collection = new ArrayList<Art>();
  }
  
  protected ArrayList<Art> collection(){return collection;}
  
  /**
   * Add art to this museum's collection
   * @param art Art to add to museum's collection
   * @return true Art was successfully added to collection
   * @return false Art was not successfully added to collection
   */
  public boolean add(Art art) {
    return collection().add(art);
  }
  
  /**
   * Return a list of art who was made by a specified artist
   * @param artistName Name of artists for art to return
   * @return list List of art made by specified artist
   */
  public List<Art> findArts(String artistName) {
    ArrayList<Art> list = new ArrayList<Art>();
    for (Art art : collection()) {
      if (art.artistName().equals(artistName))
        list.add(art);
    }
    return list;
  }
  
  /**
   * Return a list of n new art objects with random attributes
   * @param n Number of art objects to return as a list
   * @return list List of randomely generated art
   * @return list Empty list if n is less than 1
   */
  public List<Art> randomizeArts(int n) {
    ArrayList<Art> list = new ArrayList<Art>();
    if (n > 0) {
      // Initialize n Art objects with random attributes
      for (int i = 0; i < n; i++) {
        Random rand = new Random();
        list.add(new Art(rand.nextInt(50), rand.nextInt(700), rand.nextInt(40), 
                         randomString(rand.nextInt(5), rand), randomString(rand.nextInt(5), rand)));
      }
    }
    return list;
  }
  
  // Helper method to generate a random string
  private String randomString(int randNum, Random rand) {
    char[] string = new char[randNum];
    char[] characters = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    for(int i = 0; i < randNum; i++) 
      string[i] = characters[rand.nextInt(characters.length)];
    return new String(string);
  }
  
  /**
   * Return a sorted list of art from this museum based on its attribute and direction
   * @param attribute Attribute of art to sort by
   * @param direction Ordering to sort attributes by
   * @return list Ordered list of art from this museum's collection
   * @return collection Collection is unable to be sorted due to an insufficient size
   */
  public List<Art> sort(String attribute, int direction) {
    // Only sort if collection is at least 2
    if (collection().size() >= 2) {
      Art[] temp = collection().toArray(new Art[collection().size()]);
      mergeSort(temp, 0, temp.length - 1, attribute, direction);
      List<Art> list = Arrays.asList(temp);
      return list;
    }
    return collection();
  }
  
  // Method to recursively call and sort the array using mergeSort
  private void mergeSort (Art[] arr, int front, int rear, String attribute, int direction) {
    if (front < rear) {
      // Find middle index
      int middleIndex = front + (rear - front) / 2;
      // Sort left and right subarrays
      mergeSort(arr, front, middleIndex, attribute, direction);
      mergeSort(arr, middleIndex + 1, rear, attribute, direction);
      // Merge the subarrays
      merge(arr, front, middleIndex, rear, attribute, direction);
    }
  }
  
  // Merge two subarrays into a single array for merge-sort algorithm
  private void merge(Art[] arr, int front, int middle, int rear, String attribute, int direction) {
    // Find sizes of two subarrays to be merged
    int leftSize = middle - front + 1;
    int rightSize = rear - middle;
    /* Create temp arrays */
    Art[] left = new Art[leftSize];
    Art[] right =  new Art[rightSize];
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
      // Merge and sort according to attribute and direction
      if (attribute.equals("height")) {
        boolean ordering = false;
        if (direction >= 0)
          ordering = left[leftIndex].height() <= right[rightIndex].height();
        else
          ordering = left[leftIndex].height() >= right[rightIndex].height();
        if (ordering) {
          arr[mergedIndex] = left[leftIndex];
          leftIndex++;
        }
        else {
          arr[mergedIndex] = right[rightIndex];
          rightIndex++;
        }
        mergedIndex++;
      }
      else if (attribute.equals("price")) {
        boolean ordering = false;
        if (direction >= 0)
          ordering = left[leftIndex].price() <= right[rightIndex].price();
        else
          ordering = left[leftIndex].price() >= right[rightIndex].price();
        if (ordering) {
          arr[mergedIndex] = left[leftIndex];
          leftIndex++;
        }
        else {
          arr[mergedIndex] = right[rightIndex];
          rightIndex++;
        }
        mergedIndex++;
      }
      else if (attribute.equals("width")) {
        boolean ordering = false;
        if (direction >= 0)
          ordering = left[leftIndex].width() <= right[rightIndex].width();
        else
          ordering = left[leftIndex].width() >= right[rightIndex].width();
        if (ordering) {
          arr[mergedIndex] = left[leftIndex];
          leftIndex++;
        }
        else {
          arr[mergedIndex] = right[rightIndex];
          rightIndex++;
        }
        mergedIndex++;
      }
      else if (attribute.equals("name")) {
        boolean ordering = false;
        if (direction >= 0)
          ordering = left[leftIndex].name().compareTo(right[rightIndex].name()) <= 0;
        else
          ordering = left[leftIndex].name().compareTo(right[rightIndex].name()) >= 0;
        if (ordering) {
          arr[mergedIndex] = left[leftIndex];
          leftIndex++;
        }
        else {
          arr[mergedIndex] = right[rightIndex];
          rightIndex++;
        }
        mergedIndex++;
      }
      else if (attribute.equals("artistName")) {
        boolean ordering = false;
        if (direction >= 0)
          ordering = left[leftIndex].name().compareTo(right[rightIndex].name()) <= 0;
        else
          ordering = left[leftIndex].name().compareTo(right[rightIndex].name()) >= 0;
        if (ordering) {
          arr[mergedIndex] = left[leftIndex];
          leftIndex++;
        }
        else {
          arr[mergedIndex] = right[rightIndex];
          rightIndex++;
        }
        mergedIndex++;
      }
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
  
  /**
   * Sort the given list where first 5'th is sorted by name, second is sorted by price, third is
   * sorted by width,fourth is sorted by name, and remaining elements sorted by artistName 
   * @param arts List of art to be sorted
   * @return arts Sorted list of art
   * @return arts Unsorted list of art because of insufficient size
   */
  public List<Art> randomSort(List<Art> arts) {
    // Only sort if size of arts is greater than 5
    if (arts.size() > 5) {
      Art[] temp = arts.toArray(new Art[arts.size()]);
      // If arts size is at least 10, sort first four groups and then sort rest
      if (arts.size() >= 10) {
        // Calculate length of sublist for sorting first four groupings of element of arts
        int subLength = arts.size() / 5;
        int index = 0;
        int nextIndex = subLength - 1;
        for (int i = 0; i < 4; i++) {
          // Sort first group by height 
          if (i == 0) {
            quickSort(temp, index, nextIndex, "height"); 
          }
          // Sort second group by price 
          else if (i == 1) {
            quickSort(temp, index, nextIndex, "price"); 
          }
          // Sort third group by width 
          else if (i == 2) {
            quickSort(temp, index, nextIndex, "width"); 
          }
          // Sort fourth group by name 
          else if (i == 3) {
            quickSort(temp, index, nextIndex, "name"); 
          }
          index += subLength;
          nextIndex += subLength;
        }
        // Sort rest of list by artistName
        quickSort(temp, index, arts.size() - 1, "artistName");
      }
      // If arts size is less than 10, skip first four indices of arts, and then sort rest of list
      else {
        // Sort last group of arts by artistName
        quickSort(temp, 4, arts.size() - 1, "artistName");
      }
      arts = Arrays.asList(temp);
    }
    return arts;
  }
  
  // Private method that uses quick sort to order Art by height in ascending order
  private void quickSort(Art[] arts, int start, int end, String attribute) {
    if (start < end) {
      // pi is partitioning index, arr[p] is now at right place
      int pi = partition(arts, start, end, attribute);
      // Separately sort elements before partition and after partition
      quickSort(arts, start, pi - 1, attribute);
      quickSort(arts, pi + 1, end, attribute);
    }
  }
  
  // Private method used to swap indices of array
  void swap(Art[] arts, int i, int j) {
    Art temp = arts[i];
    arts[i] = arts[j];
    arts[j] = temp;
  }

  // private method used to calculate partition index
  private int partition(Art[] arts, int start, int end, String attribute) {
    // pivot
    Art pivot = arts[end];
    // Index of smaller element and the right position of pivot found so far
    // If sorting by height
    int i = (start - 1);
    for (int j = start; j <= end - 1; j++) {
      // If current element is smaller than the pivot
      if (attribute.equals("height")) {
        if (arts[j].height() < pivot.height()) {
          // Increment index of smaller element
          i++;
          swap(arts, i, j);
        }
      }
      // If sorting by price
      else if (attribute.equals("price")) {
        if (arts[j].price() < pivot.price()) {
          // Increment index of smaller element
          i++;
          swap(arts, i, j);
        }
      }
      // If sorting by width
      else if (attribute.equals("width")) {
        if (arts[j].width() < pivot.width()) {
          // Increment index of smaller element
          i++;
          swap(arts, i, j);
        }
      }
      // If sorting by name
      else if (attribute.equals("name")) {
        if (arts[j].name().compareTo(pivot.name()) <= 0)  {
          // Increment index of smaller element
          i++;
          swap(arts, i, j);
        }
      }
      // If sorting by artistName
      else if (attribute.equals("artistName")) {
        if (arts[j].artistName().compareTo(pivot.artistName()) <= 0)  {
          // Increment index of smaller element
          i++;
          swap(arts, i, j);
        }
      }
    }
    swap(arts, i + 1, end);
    return (i + 1);
  }
  
  
  public static void main(String[] args) {
    Art a1 = new Art(6, 78, 10, "a1", "aa1");
    Art a2 = new Art(8, 88, 15, "a2", "aa2");
    Art a3 = new Art(10, 98, 20, "a3", "aa3");
    Art a4 = new Art(12, 108, 25, "a4", "aa4");
    Art a5 = new Art(14, 118, 30, "a5", "aa5");
    Art a6 = new Art(16, 128, 35, "a6", "aa6");
    Art a7 = new Art(18, 138, 40, "a7", "aa7");
    Art a8 = new Art(20, 148, 45, "a8", "aa8");
    Art a9 = new Art(22, 158, 50, "a9", "aa9");
    Art a10 = new Art(24, 168, 55, "a10", "aa10");
    Art a11 = new Art(26, 178, 60, "a11", "aa11");
    Art a12 = new Art(28, 188, 65, "a12", "aa12");
    Art a13 = new Art(30, 198, 70, "a13", "aa13");
    Art a14 = new Art(32, 208, 75, "a14", "aa14");
    Art a15 = new Art(34, 218, 80, "a15", "aa15");
    Art a16 = new Art(36, 228, 85, "a16", "aa16");
    Art a17 = new Art(38, 238, 90, "a17", "aa17");
    
    ArtMuseum a = new ArtMuseum("a");
    ArrayList<Art> list = new ArrayList<Art>();
    Art[] arts = {a3, a2, a1, a6, a5, a4, a9, a8, a7, a12, a11, a10, a17, a16, a15, a14, a13};
    for (Art art : arts)
      list.add(art);
    List<Art> list2 = a.randomSort(list);
    
    // Check that heights are sorted correctly 
    for (Art art : list) 
      System.out.print(art.height() + " ");
    System.out.println();
    for (Art art : list2) 
      System.out.print(art.height() + " ");
    System.out.println();
    System.out.println();
    
    // Check that price are sorted correctly 
    for (Art art : list) 
      System.out.print(art.price() + " ");
    System.out.println();
    for (Art art : list2)
      System.out.print(art.price() + " ");
    System.out.println();
    System.out.println();
    
    // Check that width are sorted correctly 
    for (Art art : list) 
      System.out.print(art.width() + " ");
    System.out.println();
    for (Art art : list2)
      System.out.print(art.width() + " ");
    System.out.println();
    System.out.println();
    
    // Check that name are sorted correctly 
    for (Art art : list) 
      System.out.print(art.name() + " ");
    System.out.println();
    for (Art art : list2)
      System.out.print(art.name() + " ");
    System.out.println();
    System.out.println();
    
    // Check that artistName are sorted correctly 
    for (Art art : list) 
      System.out.print(art.artistName() + " ");
    System.out.println();
    for (Art art : list2)
      System.out.print(art.artistName() + " ");
    System.out.println();
    System.out.println();
  }
  
}
