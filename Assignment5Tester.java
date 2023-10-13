import static org.junit.Assert.*;
import org.junit.Test;
import java.util.*;
/**
 * This class tests methods in the Art and ArtMuseum class
 * @author Shankar Choudhury
 */
public class Assignment5Tester {
  
  // Test art class methods
  @Test
  public void testArtMethods() {
    Art a = new Art(12, 1900, 64, "Whodunnit", "shankar");

    assertEquals(12, a.height());
    assertEquals(1900, a.price());
    assertEquals(64, a.width());
    assertEquals("Whodunnit", a.name());
    assertEquals("shankar", a.artistName());
    
    a.setHeight(3);
    a.setPrice(200);
    a.setWidth(5);
    a.setName("Whodun");
    a.setArtistName("ShankaR");
    
    assertEquals(3, a.height());
    assertEquals(200, a.price());
    assertEquals(5, a.width());
    assertEquals("Whodun", a.name());
    assertEquals("ShankaR", a.artistName());
  }
  
  // Test ArtMuseum methods
  // Test constructor method
  @Test
  public void testConstructor() {
    try {
      ArtMuseum a = new ArtMuseum("a");
    }
    catch (Exception e) {
      fail("No exception should have been thrown");
    }
  }
  
  // Test add and randomizeArts method
  @Test
  public void testAddandRandomize() {
    // Test addition of small amount of art
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
    Art[] arts = {a3, a2, a1, a6, a5, a4, a9, a8, a7, a12, a11, a10, a17, a16, a15, a14, a13};
    ArtMuseum a = new ArtMuseum("a");
    for (Art art : arts) {
      if (a.add(art) == false)
        fail("Museum should have unlimited space to store art");
    }
    // Test addition of large amount of art
    List<Art> l1 = a.randomizeArts(9000);
    for (Art art : l1) {
      if (a.add(art) == false)
        fail("Museum should have unlimited space to store art");
    }
  }
  
  // Test sort method
  @Test
  public void testSort() {
    Art a1 = new Art(6, 78, 10, "a1", "aa1");
    Art a2 = new Art(8, 88, 15, "a2", "aa2");
    Art a3 = new Art(10, 98, 20, "a3", "aa3");
    Art a4 = new Art(12, 108, 25, "a4", "aa4");
    Art a5 = new Art(14, 118, 30, "a5", "aa5");
    Art a6 = new Art(16, 128, 35, "a6", "aa6");
    Art a7 = new Art(18, 138, 40, "a7", "aa7");
    Art a8 = new Art(20, 148, 45, "a8", "aa8");
    Art a9 = new Art(22, 158, 50, "a9", "aa9");
    Art[] arts = {a3, a9, a4, a7, a1, a5, a8, a2, a6};
    ArtMuseum a = new ArtMuseum("a");
    // Test return of empty list with an empty collection
    List<Art> l1 = a.sort("height", 1);
    assertEquals(true, l1.size() == 0);
    for (Art art : arts)
      a.add(art);
    // Test directional sorting with height
    List<Art> l2 = a.sort("height", 0);
    for (int i = 1; i < l2.size(); i++) {
      if (l2.get(i).height() < l2.get(i - 1).height())
        fail("List should be sorted in ascending order");
    }
    l2 = a.sort("height", -1);
    for (int i = 1; i < l2.size(); i++) {
      if (l2.get(i).height() > l2.get(i - 1).height())
        fail("List should be sorted in descending order");
    }
    // Test directional sorting with price
    l2 = a.sort("price", 1);
    for (int i = 1; i < l2.size(); i++) {
      if (l2.get(i).price() < l2.get(i - 1).price())
        fail("List should be sorted in ascending order");
    }
    l2 = a.sort("price", -9000);
    for (int i = 1; i < l2.size(); i++) {
      if (l2.get(i).price() > l2.get(i - 1).price())
        fail("List should be sorted in descending order");
    }
    // Test directional sorting with width
    l2 = a.sort("width", 9000);
    for (int i = 1; i < l2.size(); i++) {
      if (l2.get(i).width() < l2.get(i - 1).width())
        fail("List should be sorted in ascending order");
    }
    l2 = a.sort("price", -2);
    for (int i = 1; i < l2.size(); i++) {
      if (l2.get(i).width() > l2.get(i - 1).width())
        fail("List should be sorted in descending order");
    }
    // Test directional sorting with name
    l2 = a.sort("name", 1);
    for (int i = 1; i < l2.size(); i++) {
      if (l2.get(i).name().compareTo(l2.get(i - 1).name()) < 0)
        fail("List should be sorted in ascending order");
    }
    l2 = a.sort("price", -1);
    for (int i = 1; i < l2.size(); i++) {
      if (l2.get(i).name().compareTo(l2.get(i - 1).name()) > 0)
        fail("List should be sorted in descending order");
    }
    // Test directional sorting with artist's name
    l2 = a.sort("artistName", 1);
    for (int i = 1; i < l2.size(); i++) {
      if (l2.get(i).artistName().compareTo(l2.get(i - 1).artistName()) < 0)
        fail("List should be sorted in ascending order");
    }
    l2 = a.sort("price", -1);
    for (int i = 1; i < l2.size(); i++) {
      if (l2.get(i).artistName().compareTo(l2.get(i - 1).artistName()) > 0)
        fail("List should be sorted in descending order");
    }
  }
  
  // Test finding arts by artist method
  @Test
  public void testfindArts() {
    Art a1 = new Art(6, 78, 10, "a1", "aa1");
    Art a2 = new Art(8, 88, 15, "a2", "aa2");
    Art a3 = new Art(10, 98, 20, "a3", "aa1");
    Art a4 = new Art(12, 108, 25, "a4", "aa4");
    Art a5 = new Art(14, 118, 30, "a5", "aa5");
    Art a6 = new Art(16, 128, 35, "a6", "aa1");
    Art a7 = new Art(18, 138, 40, "a7", "aa7");
    Art a8 = new Art(20, 148, 45, "a8", "aa1");
    Art a9 = new Art(22, 158, 50, "a9", "aa9");
    Art[] arts = {a1, a2, a3, a4, a5, a6, a7, a8, a9};
    ArtMuseum a = new ArtMuseum("a");
    for (Art art : arts) 
      a.add(art);
    // Test return of empty list if artist's work doesn't exist in museum
    List<Art> l1 = a.findArts("aa3");
    assertEquals(0, l1.size());
    l1 = a.findArts("aa1");
    assertEquals(4, l1.size());
    assertEquals(true, l1.get(0).name().equals("a1"));
    assertEquals(true, l1.get(1).name().equals("a3"));
    assertEquals(true, l1.get(2).name().equals("a6"));
    assertEquals(true, l1.get(3).name().equals("a8"));
  }
  
  //Test randomSort
  @Test
  public void testRandomSort() {
    Art a1 = new Art(14, 78, 10, "a1", "aa1");
    Art a2 = new Art(12, 88, 15, "a2", "aa2");
    Art a3 = new Art(10, 98, 20, "a1", "aa1");
    Art a4 = new Art(8, 108, 25, "a4", "aa4");
    Art a5 = new Art(6, 118, 30, "a5", "aa5");
    // Test return of unsorted list due to insufficient size to sort by any attributes
    Art[] arts1 = {a1, a2, a3, a4, a5};
    Art[] arts2 = {a1, a2};
    ArtMuseum a = new ArtMuseum("a");
    List<Art> l1 = Arrays.asList(arts2);
    a.randomSort(l1);
    int[] arr = {14, 12, 10, 8, 6};
    for (int i = 0; i < l1.size() - 1; i++) {
      if (l1.get(i).height() != arr[i])
        fail("List should be unsorted");
    }
    l1 = Arrays.asList(arts1);
    for (int i = 0; i < l1.size() - 1; i++) {
      if (l1.get(i).height() != arr[i])
        fail("List should be unsorted");
    }
    // Test return of properly sorted list 
    a1 = new Art(6, 78, 10, "a1", "aa1");
    a2 = new Art(8, 88, 15, "a2", "aa2");
    a3 = new Art(10, 98, 20, "a3", "aa3");
    a4 = new Art(12, 108, 25, "a4", "aa4");
    a5 = new Art(14, 118, 30, "a5", "aa5");
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
    Art[] arts = {a3, a2, a1, a6, a5, a4, a9, a8, a7, a12, a11, a10, a17, a16, a15, a14, a13};
    Art[] sortedArts = {a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16, a17};
    List<Art> list = Arrays.asList(arts);
    List<Art> list2 = a.randomSort(list);
    for (int i = 0; i < list2.size(); i++) {
      // Check that first group is sorted correctly by height
      if (i < 3) {
        if (sortedArts[i].height() != list2.get(i).height())
          fail("First three should be sorted in ascending order by height");
      }
      // Check that second group is sorted correctly by price
      else if (i < 6) {
        if (sortedArts[i].price() != list2.get(i).price())
          fail("Second three should be sorted in ascending order by price");
      }
      // Check that third group is sorted correctly by width
      else if (i < 9) {
        if (sortedArts[i].width() != list2.get(i).width())
          fail("Third three should be sorted in ascending order by width");
      }
      // Check that fourth group is sorted correctly by name
      else if (i < 12) {
        if (!sortedArts[i].name().equals(list2.get(i).name()))
          fail("Fourth three should be sorted in ascending order by name");
      }
      // Check that last group is sortec correctly by artistName
      else {
        if (!sortedArts[i].artistName().equals(list2.get(i).artistName()))
          fail("Fifth group should be sorted in ascending order by name");
      }
    }
  }
  
}