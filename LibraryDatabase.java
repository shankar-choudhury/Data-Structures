/**
 * This class represents a Library 
 * @author Shankar Choudhury
 */
public class LibraryDatabase {
  // Number of books currently in library
  private int size;
  // Database of library
  private Book[] library;
  // Last index of library with book
  private int lastIndex;
  
  /**
   * Constructor for a Library Database
   * @param capacity Number of books library database can hold
   */
  public LibraryDatabase(int capacity) {
    if (capacity >= 0) 
      this.library = new Book[capacity];
    else
      throw new IllegalArgumentException();
  }
  
  /**
   * Return number of books currently in library
   * @return size Number of books currently in library
   */
  public int size() {
    return size;
  }
  
  /**
   * Set the number of books currently in library
   * @param size New number of books currently in library
   */
  private void setSize(int size) {
    this.size = size;
  }
  
  /**
   * Return library, the array of books     
   * @return library Array of books
   */
  public Book[] toArray() {
    return library;
  }
  
  /**
   * Set current library to this library
   * @param library Library to be assigned to
   */
  private void setLibrary(Book[] library) {
    this.library = library;
  }
  
  /** 
   * Return last index of library with book
   * @return lastIndex The last index of library with a book
   */
  private int getLastIndex() {
    return lastIndex;
  }
  
  /**
   * Set last index of library with book
   * @param index The last index of library with a book
   */
  private void setLastIndex(int index) {
    lastIndex = index;
  }
  
  /**
   * Increment number of books that can be held in library
   */
  private void increaseLength() {
    Book[] temp = new Book[toArray().length + 1];
    for (int i = 0; i < toArray().length; i++)
      temp[i] = toArray()[i];
    setLibrary(temp);
  }
  
  /**
   * Determine if library is empty or not
   * @param libraryArray Array of library
   * @return isEmpty Whether library is empty or not
   */
  private boolean isEmpty(Book[] libraryArray) {
    boolean isEmpty = true;
    for (int i = 0; i < libraryArray.length && isEmpty; i++) {
      if (libraryArray[i] != null)
        isEmpty = false;
    }
    return isEmpty;
  }
  
  /**
   * Add book to library in order of ISBN
   * @param book The book to be added
   */
  public void add(Book book) {
    if (isEmpty(toArray())) {
      increaseLength();
      setSize(size() + 1);
      toArray()[0] = book;
    }
    // If library isn't empty, add book in correct place
    else {
      // If current size is equal to current library length, increase length of new library array 
      if (size() >= toArray().length)
        increaseLength();
      // Look for sequentially correct place by order of ISBN number in library for book to be inserted
      // Mark whether this book has largest ISBN number or not
      boolean isLargest = true;
      int libraryIndex = 0;
      long thisISBN = Long.parseLong(book.getISBN());
      long isbn = 0;
      while (libraryIndex < toArray().length - 1 && isLargest && toArray()[libraryIndex] != null) {
        isbn = Long.parseLong(toArray()[libraryIndex].getISBN());
        if (thisISBN < isbn)
          isLargest = false;
        libraryIndex++;
      }
      // If this book has largest ISBN, then assign it to first null index.
      if (isLargest)
        toArray()[size()] = book;
      // If this book doesn't have largest ISBN, shift books one index to right, and then insert book at libraryIndex
      if (!isLargest) {
        int counter = size();
        while (counter > libraryIndex - 1) {
          toArray()[counter] = toArray()[counter - 1];
          counter--;
        }
        toArray()[libraryIndex - 1] = book;
      }
      // Increase size of library
      setSize(size() + 1);
      // Modify lastIndex accordingly
      if (toArray()[size() - 1] != null)
        setLastIndex(size() - 1);
      else
        setLastIndex(size() - 2);
    }
  }
  
  /**
   * Remove a book from the database by looking for its ISBN
   * @param isbn The ISBN of the book to be removed from database
   * @return Book The Book to be removed from database
   * @return null Book was not found
   */
  public Book remove(String isbn) {
    Book removedBook = null;
    // If library isn't empty, look for book with ISBN corresponding to this ISBN
    if (!isEmpty(toArray())) {
      int removedIndex = 0;
      boolean found = false;
      // Look for book with corresponding ISBN
      for (int i = 0; i < size() && !found; i++) {
        if (toArray()[i].getISBN().equals(isbn)) {
          removedIndex = i;
          found = true;
        }
      }
      // If book is found, assign index null value, and shift over books until next null value or end of library
      if (found) {
        removedBook = toArray()[removedIndex];
        toArray()[removedIndex] = null;
         // Shift indices left by one index
         for (int i = removedIndex; i < size() - 1; i++) {
           toArray()[i] = toArray()[i + 1];
         }
         // Make last index with book null to avoid duplicates and modify last index value and size of library accordingly 
         toArray()[size() - 1] = null;
         setLastIndex(getLastIndex() - 1);
         setSize(size() - 1);
      }
    }
    return removedBook;
  }
  
  /**
   * Randomly return a book from the library array
   * @return toArray()[randomIndex] The random book chosen to be returned
   * @return null If no books are in library
   */
  public Book randomSelection() {
    // If list is not empty, randomly choose an index between and inclusive of first and last index with books, and return that book
    if (!isEmpty(toArray())) {
      int randomIndex = (int)(Math.random() * (getLastIndex() + 1));
      return toArray()[randomIndex];
    }
    return null;
  }
  
  /**
   * Return a book with the input title
   * @param title The title of book to be found
   * @return toArray()[index] The book corresponding with this title
   * @return null If no book matches this title
   */
  public Book findByTitle(String title) {
    // If list is not empty, search for matching title of book
    if (!isEmpty(toArray())) {
      int index = 0;
      while (index < getLastIndex() + 1) {
        if (toArray()[index].getTitle().equals(title))
          return toArray()[index];
        index++;
      }
    }
    return null;
  }
  
  /**
   * Return a book corresponding with this ISBN 
   * @param isbn String representation of 13-digit ISBN
   * @return toArray()[mid] The book with this ISBN
   * @return null If no book has this ISBN
   */
  public Book findByISBN(String isbn) {
    // If list is not empty, search for matching ISBN of book using binary search and modifying the search range accordingly
    if (!isEmpty(toArray())) {
      int front = 0;
      int back = getLastIndex();
      long isbnToFind = Long.parseLong(isbn);
      while (front <= back) {
        int mid = (front + back) / 2;
        long midISBN = Long.parseLong(toArray()[mid].getISBN());
        if (isbnToFind < midISBN)
          back = mid - 1;
        else if (isbnToFind > midISBN)
          front = mid + 1;
        else
          return toArray()[mid];
      }
    }
    return null;
  }
  
  /**
   * Return an array of Books containing all books written by this author
   * @param author Author to be looked for
   * @return authorsBooks Array of books written by author
   */
  public Book[] getAllByAuthor(String author) {
    Book[] authorsBooks = new Book[size()];
    if (!isEmpty(toArray())) {
      int authorsBooksIndex = 0;
      int libraryIndex = 0;
      // Add books by author to author's book array
      while (libraryIndex <= getLastIndex()) {
        if (toArray()[libraryIndex].getAuthor().equals(author)) {
          authorsBooks[authorsBooksIndex] = toArray()[libraryIndex];
          authorsBooksIndex++;
        }
        libraryIndex++;
      }
    }
    return authorsBooks;
  }
  
  /**
   * Remove books with duplicate ISBN
   */
  public void removeDuplicates() {
    if (!isEmpty(toArray()) && toArray().length > 1) {
      // Remove duplicate books
      int index = 0;
      int ifCopyIndex = 1;
      while (ifCopyIndex <= getLastIndex()) {
        if (toArray()[index].getISBN().equals(toArray()[ifCopyIndex].getISBN())) {
          toArray()[ifCopyIndex] = null;
          setSize(size() - 1);
        }
        else 
          index = ifCopyIndex;
        ifCopyIndex++;
      }
      // Create new Book array, copy over non-null indices, adjust last non-null index, and repoint library to new array
      Book[] newLibrary = new Book[toArray().length];
      for (int i = 0, j = 0; i <= getLastIndex(); i++) {
        if (toArray()[i] != null) {
          newLibrary[j] = toArray()[i];
          j++;
        }
      }
      setLastIndex(size() - 1);
      setLibrary(newLibrary);
    }
  }
        
}
      
  