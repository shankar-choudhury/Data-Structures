/**
 * This class tests methods Tokenizer, HashTable, and WordStat
 * @author Shankar Choudhury
 */
import org.junit.*;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.NoSuchElementException;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class Assignment4Tester {
  // Asserts that l1 & l2 have the same elements (without regard to order), destroying l2 in the process
  // Test Tokenizer methods
  @Test
  public void testTokenizer() {
      
    // Test constructor taking a file pathway
    try {
      Tokenizer t1 = new Tokenizer("C:\\Users\\shank\\OneDrive\\Desktop\\Data Structures\\Assignment 4\\Samksara.txt");
      Tokenizer t2 = new Tokenizer(new String[0]);
    }
    catch (Exception e) {
      fail("No exception should have been thrown");
    }
    
    // Test constructor taking an array of strings
    String[] text1 = {"ap.Ple", "Ban!ana", "cranbe,rry", "BANA?na", "stra%wbeRR1es", "cHArg3r"};
    Tokenizer t2 = new Tokenizer(text1);
    assertEquals("apple", t2.wordList().get(0));
    assertEquals("banana", t2.wordList().get(1));
    assertEquals("cranberry", t2.wordList().get(2));
    assertEquals("banana", t2.wordList().get(3));
    assertEquals("strawberr1es", t2.wordList().get(4));
    assertEquals("charg3r", t2.wordList().get(5));
  }
  
  // Test HashTable methods
  @Test
  public void testHashTable() {
    // Test constructors
    HashTable<Integer> h1 = new HashTable<Integer>();
    HashTable<Integer> h2 = new HashTable<Integer>(29);
    // Test for invalid HashTable size
    try{
      HashTable<Integer> h3 = new HashTable<Integer>(-1);
      fail("Exception was not thrown");
    }
    catch (IllegalArgumentException e) {
      ;// Correct exception thrown
    }
    catch (Exception e) {
      fail("Incorrect exception thrown");
    }
    assertEquals(31, h1.capacity());
    assertEquals(29, h2.capacity());
    // Test get, put, remove, and size() 
    String[] words = {"a", "c", "a", "b", "d", "b", "c", "a"};
    for (String word : words)
      h1.put(word, 1);
    assertEquals(3, (int) h1.get("a"));
    assertEquals(4, (int) h1.size());
    assertEquals(3, (int) h1.remove("a"));
    assertEquals(3, (int) h1.size());
    assertEquals(2, (int) h1.get("b"));
    assertEquals(2, (int) h1.remove("b"));
    assertEquals(2, (int) h1.size());
    assertEquals(2, (int) h1.get("c"));
    assertEquals(1, (int) h1.get("d"));
    assertEquals(2, (int) h1.size());
    // Test for getting nonexistent key
    try{
      h1.get("e");
      fail("Exception was not thrown");
    }
    catch (NoSuchElementException e) {
      ;// Correct exception thrown
    }
    catch (Exception e) {
      fail("Incorrect exception thrown");
    }
    // Test for removing nonexistent key
    try{
      h1.remove("e");
      fail("Exception was not thrown");
    }
    catch (NoSuchElementException e) {
      ;// Correct exception thrown
    }
    catch (Exception e) {
      fail("Incorrect exception thrown");
    }
  }
  
  
  // Test WordStat methods
  @Test
  public void testWordStat() throws IOException{
    String[] text1 = {"ap.Ple", "Ban!ana", "cranbe,rry", "BANA?na", "stra%wbeRRies", "cHArgEr", "apple", "blueberries,", "rasberries", "strawberries", "apple", "cranberries", "apple", "d", "e", "Banana"};
    String[] text2 = {"a", "b", "c"};
    String file = "C:\\Users\\shank\\OneDrive\\Desktop\\Data Structures\\Assignment 4\\Samksara.txt";
    // Test wordStat constructors
    try {
      WordStat w1 = new WordStat(text1);
      WordStat w2 = new WordStat(file);
    }
    catch (Exception e) {
      fail("No exeption should be thrown");
    }
    WordStat w1 = new WordStat(text1);
    WordStat w2 = new WordStat(file);
    WordStat w3 = new WordStat(text2);
    // Test wordCount and wordRank
    assertEquals(4, w1.wordCount("apple"));
    assertEquals(128, w2.wordCount("the"));
    assertEquals(1, w1.wordRank("apple"));
    assertEquals(2, w1.wordRank("banana"));
    assertEquals(1, w2.wordRank("the"));
    assertEquals(4, w2.wordRank("a"));
    assertEquals(0, w1.wordCount("charlie"));
    assertEquals(0, w2.wordCount("iphone"));
    assertEquals(0, w2.wordRank("Islam"));
    assertEquals(0, w1.wordRank("blackberries"));
    
    // Test mostCommonWords
    String[] expected1 = {"the", "to", "of", "a", "brahmin"};
    String[] result1 = w2.mostCommonWords(5);
    try {
      for (int i = 0; i < result1.length; i++) 
        assertEquals(expected1[i], result1[i]);
    }
    catch (Throwable t) {
      fail("Did not return most common words correctly");
    }
    try {
      w3.mostCommonWords(4);
      fail("Exception should have been thrown");
    }
    catch (IllegalArgumentException i) {
      ; // Correct exception was thrown
    }
    catch (Exception e) {
      fail("Wrong execption was thrown");
    }
    try {
      w3.mostCommonWords(-1);
      fail("Exception should have been thrown");
    }
    catch (IllegalArgumentException i) {
      ; // Correct exception was thrown
    }
    catch (Exception e) {
      fail("Wrong execption was thrown");
    }
    
    // Test leastCommonWords
    String[] expected2 = {"deeply", "against", "contextualizing", "argue", "did", "eat", "inhabitants", "less", "hold", "statusquo"};
    String[] result2 = w2.leastCommonWords(10);
    try {
      for (int i = 0; i < result2.length; i++) 
        assertEquals(expected2[i], result2[i]);
    }
    catch (Throwable t) {
      fail("Did not return most common words correctly");
    }
    try {
      w3.leastCommonWords(4);
      fail("Exception should have been thrown");
    }
    catch (IllegalArgumentException i) {
      ; // Correct exception was thrown
    }
    catch (Exception e) {
      fail("Wrong execption was thrown");
    }
    try {
      w3.leastCommonWords(-1);
      fail("Exception should have been thrown");
    }
    catch (IllegalArgumentException i) {
      ; // Correct exception was thrown
    }
    catch (Exception e) {
      fail("Wrong execption was thrown");
    }
    
    // Test mostCommonCollocations
    String[] expected3 = {"of", "in", "and", "for", "is"};
    String[] result3 = w2.mostCommonCollocations(5, "the", true);
    try {
      for (int i = 0; i < result3.length; i++) 
        assertEquals(expected3[i], result3[i]);
    }
    catch (Throwable t) {
      fail("Did not return most common words correctly");
    }
    String[] expected4 = {"villagers", "brahmin", "samskara", "reader", "village"};
    String[] result4 = w2.mostCommonCollocations(5, "the", false);
    try {
      for (int i = 0; i < result4.length; i++) 
        assertEquals(expected4[i], result4[i]);
    }
    catch (Throwable t) {
      fail("Did not return most common words correctly");
    }
    try {
      w1.mostCommonCollocations(-1, "apple", true);
      fail("Exception should have been thrown, negative input");
    }
    catch (IllegalArgumentException i) {
      ; // Correct exception thrown
    }
    catch (Exception e) {
      fail("Wrong exception thrown");
    }
    try {
      w3.mostCommonCollocations(4, "apple", true);
      fail("Exception should have been thrown, word does not exist");
    }
    catch (IllegalArgumentException i) {
      ; // Correct exception thrown
    }
    catch (Exception e) {
      fail("Wrong exception thrown");
    }
    
    String[] results = w3.mostCommonCollocations(1, "a", true);
    assertEquals(0, results.length);
    
    String[] expected5 = {"b"};
    String[] result5 = w3.mostCommonCollocations(1, "a", false);
    try {
      for (int i = 0; i < result5.length; i++) 
        assertEquals(expected5[i], result5[i]);
    }
    catch (Throwable t) {
      fail("Did not return most common collocations correctly");
    }
    
  }
}