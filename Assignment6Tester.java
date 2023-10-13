import static org.junit.Assert.*;
import org.junit.Test;
/**
 * This class tests the Graph methods in the Graph class
 * @author Shankar Choudhury
 */
public class Assignment6Tester {
  @Test 
  public void testAddNodeMethods() {
    Graph<String, Integer> g = new Graph<>();
    // Test with empty node
    assertEquals(true, g.addNode("A", null));
    // Test with filled node with same key but different value into original empty node
    assertEquals(true, g.addNode("A", 2));
    assertEquals(1, g.size());
    // Test with filled node with same key but different value into modified full node 
    assertEquals(false, g.addNode("A", 3));
    String[] k1 = {"D", "G", "E", "B", "F", "C"};
    Integer[] v0 = {2, 18, 23, 1, 89};
    // Test with parameters of different lengths
    try {
      g.addNodes(k1, v0);
      fail("Exception should have been thrown because input arrays are of different lengths");
    }
    catch (IllegalArgumentException i) {
      ; // This exception should have been thrown
    }
    catch (Exception e) {
      fail("wrong exception thrown");
    }
    Integer[] v1 = {2, 18, 23, 1, 89, -4};
    // Test with parameters of same lengths
    assertEquals(true, g.addNodes(k1, v1));
    // Test with parameters where node exists and is occupied, meaning not all nodes were added
    String[] k2 = {"A", "H"};
    Integer[] v2 = {1000, 122};
    assertEquals(false, g.addNodes(k2, v2));
  }
  
  @Test
  public void testAddEdgeMethods() {
    Graph<String, Integer> g = new Graph<>();
    String[] k1 = {"D", "G", "E", "B", "F", "C"};
    Integer[] v1 = {2, 18, 23, 1, 89, -4};
    g.addNodes(k1, v1);
    // Test adding edge from a nonexistent node to an existing one
    assertEquals(false, g.addEdge("A", "D"));
    // Test adding edge from an existing node to a nonexistent node
    assertEquals(true, g.addEdge("D", "A"));
    // Test same cases 
    String[] k2 = {"G", "E", "A"};
    assertEquals(false, g.addEdges("I", k2));
    String[] k3 = {"I", "E", "A"};
    assertEquals(false, g.addEdges("G", k3));
    /*
    for (String key : k3) {
      if(!g.get("G").neighbors().contains(key))
        fail("all keys should be present");
    }*/
  }
  
  @Test
  public void testRemoveMethods() {
    Graph<String, Integer> g = new Graph<>();
    String[] k1 = {"D", "G", "E", "B", "F", "C"};
    Integer[] v1 = {2, 18, 23, 1, 89, -4};
    g.addNodes(k1, v1);
    assertEquals(false, g.removeNode("A"));
    assertEquals(true, g.removeNode("G"));
    String[] k2 = {"F", "E", "B"};
    assertEquals(true, g.removeNodes(k2));
    String[] k3 = {"I", "E", "A"};
    assertEquals(false, g.removeNodes(k3));
  }
  
  @Test
  public void testTraversals() {
    Graph<String, Integer> g = new Graph<>();
    String[] k1 = {"D", "G", "E", "B", "F", "C", "A", "J", "W", "Z"};
    Integer[] v1 = {2, 18, 23, 1, 89, -4, 56, 2, 345, 4};
    g.addNodes(k1, v1);
    String[] k2 = {"G", "E", "A", "B"};
    String[] k3 = {"C", "J"};
    g.addEdge("G", "F");
    g.addEdges("D", k2);
    g.addEdges("B", k3);
    g.addEdges("C", "J");
    g.addEdge("J", "Z");
    g.addEdge("Z", "W");
    Comparable[] pathDFS = g.DFS("A", "W");
    Comparable[] expectedDFS = {"A", "D", "B", "C", "J", "Z", "W"};
    for (int i = 0; i < expectedDFS.length; i++) 
      assertEquals(true, pathDFS[i].equals(expectedDFS[i]));
    Comparable[] pathBFS = g.BFS("A", "W");
    Comparable[] expectedBFS = {"A", "D", "B", "J", "Z", "W"};
    for (int i = 0; i < expectedBFS.length; i++) 
      assertEquals(true, pathBFS[i].equals(expectedBFS[i]));
  }
  
  @Test
  public void testRead() {
    try {
      Graph<String, Integer> g1 =Graph.read("C:\\Users\\shank\\OneDrive\\Desktop\\Data Structures\\Assignment 6\\test.txt");
    }
    catch (Exception e) {
      fail("No exception should have been thrown");
    }
  }
  
  @Test
  public void testReadWordGraph() {
    try {
      Graph<Integer, String> g1 =Graph.readWordGraph("C:\\Users\\shank\\OneDrive\\Desktop\\Data Structures\\Assignment 6\\Length3WordGraph");
    }
    catch (Exception e) {
      fail("No exception should have been thrown");
    }
  }
}