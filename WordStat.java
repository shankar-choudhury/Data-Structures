import java.util.Collections;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.ArrayList;

/**
 * This class represents a set of functions that calculate various statistics about words present in certain texts
 * @author Shankar Choudhury
 */
public class WordStat {
  // List of words used to compute word stats
  private ArrayList<String> wordList;
  // Hash table containing key-value pairs of words and their frequencies
  private HashTable<Integer> hashTable;
  // maxQueue for heapSorting key-value pairs ordered by greatest frequency
  private PriorityQueue<Integer, String> maxQueue;
  // Hash table containing key-value pairs of words and their ranks based on frequency
  private HashTable<Integer> ranksHash;

  
  /**
   * Constructor that takes a file pathway
   * @param file File pathway to file
   */
  public WordStat(String file) {
    // Read Tokenizer into hash table, one word at a time, then heapsort it
    heapSort(read(file));
    // Initialize ranks of each word based on its frequency in maxQueue for wordRank method, the lower the rank, the more frequent it is.
    // Then insert values into hashTable containing words and their ranks
    ranksToHash(rank(queue()));
  }
  
  /**
   * Constructor that takes an array of Strings representing text
   * @param text Array representing text
   */
  public WordStat(String[] text) {
    // Read Tokenizer into hash table, one word at a time, then heapsort it
    heapSort(read(text));
    // Then insert values into hashTable containing words and their ranks
    ranksToHash(rank(queue()));
  }
   
  // Getters and Setters for fields
  private ArrayList<String> wordList() {return wordList;}
  private void setWordList(ArrayList<String> wordList) {this.wordList = wordList;}
  protected HashTable<Integer> hashTable() {return hashTable;}
  private void setHashTable(HashTable<Integer> hashTable) {this.hashTable = hashTable;}
  protected PriorityQueue<Integer, String> queue() {return maxQueue;}
  private void setQueue(PriorityQueue<Integer, String> queue) {maxQueue = queue;}
  protected HashTable<Integer> ranksHash() {return ranksHash;}
  private void setRanksHash(HashTable<Integer> ranksHash) {this.ranksHash = ranksHash;}
  
   // Private helper class to read file -> tokenizer -> hash
  private HashTable<Integer> read(String file) {
    // Read Tokenizer into hash table, one word at a time
    Tokenizer fileT = new Tokenizer(file);
    setWordList(fileT.wordList());
    setHashTable(new HashTable<Integer>());
    for (String word : wordList()) 
      hashTable().put(word, 1);
    return hashTable();
  }
  
  // Private helper class to read arr -> tokenizer -> hash
   private HashTable<Integer> read(String[] text) {
     Tokenizer fileT = new Tokenizer(text);
     setWordList(fileT.wordList());
     setHashTable(new HashTable<Integer>());
     for (String word : wordList()) 
       hashTable().put(word, 1);
     return hashTable();
   }
  
  // Heapsort for mostCommon and leastCommon methods, and for calculating ranks. Keys are word counts, values are words
  private void heapSort(HashTable<Integer> hashTable) {
    Integer[] keys = new Integer[hashTable.size()];
    String[] values = new String[hashTable.size()];
    hashTable().toArrays(keys, values);
    setQueue(new PriorityQueue<Integer, String>(keys, values));
  }
  
  // Initialize ranks of each word based on its frequency in maxQueue for wordRank method, the lower the rank, the more frequent it is.
  private int[] rank(PriorityQueue<Integer, String> maxQueue) {
    int[] ranks = new int[hashTable.size()];
    for (int i = 0, j = 1; i < maxQueue.getKeys().length; i++) {
      ranks[i] = j;
      if (i < maxQueue.getKeys().length - 1 && maxQueue.getKeys()[i] > maxQueue.getKeys()[i + 1]) 
        j++;
    }
    return ranks;
  }
  
  // Initialize hashTable containing words and their ranks
  private void ranksToHash(int[] ranks) {
    setRanksHash(new HashTable<Integer>());
    for (int i = 0; i < ranks.length; i++) 
      ranksHash().put(queue().getValues()[i], ranks[i]);
  }
  
  /**
   * Return number of times specified word appears in text
   * @return Number of times word appears in text
   */
  public int wordCount(String word) {
    try {
      return hashTable().get(word);
    }
    catch (NoSuchElementException n) {
      return 0;
    }
  }
  
  /**
   * Return the rank of the specified word in respect to how often it occurs relative to other words
   * @return Rank of specified word 
   */
  public int wordRank(String word) {
    try {
      return ranksHash.get(word);
    }
    catch (NoSuchElementException n) {
      return 0;
    }
  }
  
  /**
   * Return an array containing the k most common words that appear in text by frequency
   * @param k Specified amount of most common words
   * @return Array containing k most common words
   */
  public String[] mostCommonWords(int k) {
    if (k < 0 || k > queue().size() - 1)
      throw new IllegalArgumentException();
    String[] mostCommonWords = new String[k];
    for (int i = 0; i < k; i++)
      mostCommonWords[i] = queue().getValues()[i];
    return mostCommonWords;
  }
  
  /**
   * Return an array containing the k least common words that appear in text by frequency
   * @param k Specified amount of least common words
   * @return Array containing k least common words
   */
  public String[] leastCommonWords(int k) {
    if (k < 0 || k > queue().size() - 1)
      throw new IllegalArgumentException();
    String[] leastCommonWords = new String[k];
    for (int i = queue().size() - 1, i2 = 0; i > -1 && k > 0; i--) {
      leastCommonWords[i2] = queue().getValues()[i];
      i2++;
      k--;
    }
    return leastCommonWords;
  }
  
  /**
   * Return the k most common words that precede or is after the base word
   * @param k K most common collocations
   * @param baseWord Word to find collocations of
   * @param precede Whether collocations are directly before or after baseWord
   * @return K most common collocations
   */
  public String[] mostCommonCollocations(int k, String baseWord, boolean precede) {
    if (k < 1 || k > queue().size() || !wordList().contains(baseWord))
      throw new IllegalArgumentException();
    HashTable<Integer> collocations = new HashTable<Integer>();
    // Append collocations corresponding to precede value to collocations
    return heapSortColl(k, collocations, hashColl(precede, baseWord, collocations));
  }
  
   // Helper method to hash collocations from 
  private int hashColl(boolean precede, String baseWord, HashTable<Integer> collocations) {
    int numWords = 0;
    // Append one way if looking for preceding collocations
    if (precede) {
      for (int i = 1; i < wordList().size(); i++) {
        if (wordList().get(i).equals(baseWord)) {
          collocations.put(wordList.get(i - 1), 1);
          numWords++;
        }
      }
    }
    // Append another way if looking for anteceding collocations
    else {
      for (int i = 0; i < wordList().size(); i++) {
        if (i < wordList.size() - 1 && wordList().get(i).equals(baseWord)) {
          collocations.put(wordList.get(i + 1), 1);
          numWords++;
        }
      }
    }
    return numWords;
  }
  
  // Helper method to maxHeapsort elements from collocation hash in terms of frequency and return first k
  private String[] heapSortColl(int k, HashTable<Integer> collocations, int numWords) {
    ArrayList<String> colloc = new ArrayList<String>();
    if (numWords > 0) {
      Integer[] keys = new Integer[numWords];
      String[] values = new String[numWords];
      collocations.toArrays(keys, values);
      PriorityQueue<Integer, String> maxQueueCol = new PriorityQueue<Integer, String>(keys, values);
      // Append first k words from maxQueueCol. These should be the words that are next to the baseWord the most, from 1'st to k'th
      for (int i = 0; i < k && i < maxQueueCol.size(); i++)
        colloc.add(maxQueueCol.getValues()[i]);
      return colloc.toArray(new String[k]);
    }
    return colloc.toArray(new String[0]);
  }
  
}