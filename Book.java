/**
 * This class represents a book
 * @author Shankar Choudhury
 */
public class Book {
  // Title of book
  private String title;
  // ISBN of book
  private String isbn;
  // Author of book
  private String author;
  
  /**
   * Constructor for Book object
   * @param title The title of book
   * @param isbn The ISBN of book
   * @param author The author of book
   */
  public Book(String title, String isbn, String author) {
    // Check for fully numeric characters in isbn and correct length
    try {
      int isbnInt = Integer.parseInt(isbn);
      if (isbn.length() == 13) {
        this.title = title;
        this.isbn = isbn;
        this.author = author;
      }
      else
        throw new IllegalArgumentException();
    }
    catch (NumberFormatException e) {
      throw new IllegalArgumentException();
    }
  }
  
  /**
   * return title of book
   * @return title The title of book
   */
  public String getTitle() {
    return title;
  }
  
  /**
   * return ISBN of book
   * @return isbn The ISBN of book
   */
  public String getISBN() {
    return isbn;
  }
  
  /**
   * return author of book
   * @return author The author of book
   */
  public String getAuthor() {
    return author;
  }
}