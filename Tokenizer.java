import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.IOException;
/**
 * This class represents a Tokenizer that provides the functionality to read text from a file directory or an array 
 * @author Shankar Choudhury
 */
public class Tokenizer {
  // ArrayList of noramlized words
  private ArrayList<String> wordList;
  
  /**
   * Constructor for Tokenizer that takes a String pathway to text file
   * @param file String pathway to text file to tokenize
   */
  public Tokenizer(String file) {
    try {
      BufferedReader br = new BufferedReader(new FileReader(file));
      String line = null;
      wordList = new ArrayList<String>();
      // Read normalized words into wordList, one character at a time
      while ((line = br.readLine()) != null) {
        StringBuilder word = new StringBuilder();
        int i = 0;
        while (i < line.length()) {
          // Append alphabetical and numerical characters to word
          if (!Character.isWhitespace(line.charAt(i))) {
            if (Character.isLetterOrDigit(line.charAt(i))) 
              word.append(line.charAt(i));
          }
          // If empty space found and word is not empty, append to wordList and reset word
          else if (word.length() != 0) {
            wordList.add(word.toString().toLowerCase());
            word = new StringBuilder();
          }
          i++;
        }
      }
    }
    catch (IOException e) {
      System.out.println("Unable to read file");
    }
  }
  
  /**
   * Constructor for Tokenizer that takes a String array representing text
   * @param text Array of Strings representing text
   */
  public Tokenizer(String[] text) {
    wordList = new ArrayList<String>();
    // Read each word of text into wordList, one character at a time
    for (String entry : text) {
      StringBuilder word = new StringBuilder();
      int i = 0;
      // Normalize word, one character at a time
      while (i < entry.length()) {
        char charac = entry.charAt(i);
        // Append alphabetical and numerical characters to word
        if (!Character.isWhitespace(charac)) {
          if (Character.isLetterOrDigit(charac)) 
            word.append(charac);
          // If at end of word, append to wordList and reset word
          if (i == entry.length() - 1) {
            wordList.add(word.toString().toLowerCase());
            word = new StringBuilder();
          }
        }
        // If empty space found and word is not empty, append to wordList and reset word
        else if (word.length() != 0) {
          wordList.add(word.toString().toLowerCase());
          word = new StringBuilder();
        }
        i++;
      }
    }
  }
  
  /**
   * Return wordList
   * @return wordList List of words
   */
  public ArrayList<String> wordList() {return wordList;}

}