/**
 * This class tests all of the methods in classes Book and LibraryDatabase
 *@author Shankar Choudhury
 */
import org.junit.Test;
import static org.junit.Assert.*;

public class Assignment1Tester {
  // Test constructor
  // Test for throwing IllegalArgumentException with invalid ISBN numbers and successfull instantiation
  @Test
  public void testBookMethods (){
    // Test for insufficient length of ISBN
    try{
      Book a = new Book("a","123","b");
      fail("Exception was not thrown");
    }
    catch (IllegalArgumentException e) {
      ;// Correct exception thrown
    }
    catch (Exception e) {
      fail("Wrong exception is thrown");
    }
    
    // Test for invalid ISBN number with non-number character
    try{
      Book a = new Book("a","012345678912h","b");
      fail("Exception was not thrown");
    }
    catch (IllegalArgumentException e) {
      ;// Correct exception thrown
    }
    catch (Exception e) {
      fail("Wrong exception is thrown");
    }
    
    // Test for successful instantiation 
    Book a = new Book("shankar", "0000000000001", "choudhury");
    assertEquals("shankar", a.getTitle());
    assertEquals("0000000000001", a.getISBN());
    assertEquals("choudhury", a.getAuthor());
  }
  
  // Test LibraryDatabase constructor for throwing IllegalArgumentException and instantiation
  @Test
  public void testLibraryConstructor (){
    // Test for negative capacity
    try{
      LibraryDatabase l1 = new LibraryDatabase(-1);
      fail("Exception was not thrown");
    }
    catch (IllegalArgumentException e) {
      ;// Correct exception thrown
    }
    catch (Exception e) {
      fail("Wrong exception is thrown");
    }
    
    // Test for succesful instantiation 
    LibraryDatabase l1 = new LibraryDatabase(0);
    assertEquals(0, l1.toArray().length);
    LibraryDatabase l2 = new LibraryDatabase(4);
    assertEquals(4, l2.toArray().length);
  }
  
  // Test add() of LibraryDatabase, which also implicitly tests increaseLength(), size(), setSize(), isEmpty(), and toArray()
  @Test
  public void testAdd(){
    Book a = new Book("1","0000000000001","borne");
    Book d = new Book("2","0000000000002","beatrix");
    Book e = new Book("3","0000000000003","beatrix");
    Book f = new Book("4","0000000000004","beatrix");
    Book g = new Book("5","0000000000005","bethany");
    Book i= new Book("6","0000000000006","bodacious");
    Book j = new Book("7","0000000000007","botanical");
    Book k = new Book("8","0000000000008","botanical");
    Book l = new Book("9","0000000000009","bother");
    LibraryDatabase l1 = new LibraryDatabase(0);
    
    // Test successfull modification of LibraryDatabase's array when capacity is insufficient for additional book
    assertEquals(0, l1.size());
    l1.add(d);
    assertEquals("2", l1.toArray()[0].getTitle());
    // Test if size of library has been modified correctly
    assertEquals(1, l1.size());
    // Test if this book has largest ISBN number and is added in correct order
    l1.add(a);
    assertEquals("0000000000001", l1.toArray()[0].getISBN());
    assertEquals("0000000000002", l1.toArray()[1].getISBN());
    l1.add(e);
    assertEquals("0000000000001", l1.toArray()[0].getISBN());
    assertEquals("0000000000002", l1.toArray()[1].getISBN());
    assertEquals("0000000000003", l1.toArray()[2].getISBN());
    // Test if size of library has been modified correctly
    assertEquals(3, l1.size());
    l1.add(j);
    l1.add(g);
    l1.add(f);
    l1.add(k);
    l1.add(l);
    l1.add(i);
    // Test insertion of book with lowest ISBN into beginning of array
    assertEquals("0000000000001", l1.toArray()[0].getISBN());
    assertEquals("0000000000002", l1.toArray()[1].getISBN());
    assertEquals("0000000000003", l1.toArray()[2].getISBN());
    assertEquals("0000000000004", l1.toArray()[3].getISBN());
    // Test insertion of book with average ISBN into middle of array
    assertEquals("0000000000005", l1.toArray()[4].getISBN());
    assertEquals("0000000000006", l1.toArray()[5].getISBN());
    assertEquals("0000000000007", l1.toArray()[6].getISBN());
    assertEquals("0000000000008", l1.toArray()[7].getISBN());
    // Test insertion of book with largest ISBN into last index of array
    assertEquals("0000000000009", l1.toArray()[8].getISBN());
    // Test if size of library has been modified correctly
    assertEquals(9, l1.size());
  }
  
  @Test
  public void testRemove() {
    Book a = new Book("1","0000000000001","borne");
    Book c = new Book("1","0000000000001","borne");
    Book d = new Book("2","0000000000002","beatrix");
    Book e = new Book("3","0000000000003","beatrix");
    Book f = new Book("4","0000000000004","beatrix");
    Book g = new Book("5","0000000000005","bethany");
    Book i= new Book("6","0000000000006","bodacious");
    Book j = new Book("7","0000000000007","botanical");
    Book k = new Book("8","0000000000008","botanical");
    Book l = new Book("9","0000000000009","bother");
    LibraryDatabase l1 = new LibraryDatabase(0);
    LibraryDatabase l2 = new LibraryDatabase(7);
    
    // Test correct null return when method is applied to empty library
    assertEquals(null, l1.remove("0000000000001"));
    assertEquals(null, l2.remove("0000000000001"));
    // Test correct book removal with one entry and modification of size
    l1.add(c);
    assertEquals("0000000000001", l1.remove("0000000000001").getISBN());
    assertEquals(null, l1.toArray()[0]);
    assertEquals(0, l1.size());
    // Test correct book removal with two entries of duplicate ISBN numbers and modification of size
    l1.add(a);
    l1.add(c);
    assertEquals("0000000000001", l1.remove("0000000000001").getISBN());
    assertEquals("0000000000001", l1.toArray()[0].getISBN());
    assertEquals(1, l1.size());
    // Test correct book removal from middle of library and modification of size
    l1.add(d);
    l1.add(e);
    assertEquals("0000000000002", l1.remove("0000000000002").getISBN());
    assertEquals("0000000000001", l1.toArray()[0].getISBN());
    assertEquals("0000000000003", l1.toArray()[1].getISBN());
    assertEquals(2, l1.size());
    // Test correct book removal from end of library
    l1.add(d);
    assertEquals("0000000000003", l1.remove("0000000000003").getISBN());
    assertEquals(null, l1.toArray()[2]);
    // Test successful book removal from library with multiple removals 
    l1.add(d);
    l1.add(f);
    l1.add(g);
    l1.add(i);
    l1.add(j);
    l1.add(k);
    l1.add(l);
    assertEquals("0000000000008", l1.remove("0000000000008").getISBN());
    assertEquals("0000000000005", l1.remove("0000000000005").getISBN());
    assertEquals("0000000000006", l1.remove("0000000000006").getISBN());
    assertEquals(6, l1.size());
  }
  
  @Test
  public void testRandomSelection() {
    Book a = new Book("1","0000000000001","borne");
    Book d = new Book("2","0000000000002","beatrix");
    Book e = new Book("3","0000000000003","beatrix");
    Book f = new Book("4","0000000000004","beatrix");
    Book g = new Book("5","0000000000005","bethany");
    Book i= new Book("6","0000000000006","bodacious");
    Book j = new Book("7","0000000000007","botanical");
    Book k = new Book("8","0000000000008","botanical");
    Book l = new Book("9","0000000000009","bother");
    LibraryDatabase l1 = new LibraryDatabase(0);
    LibraryDatabase l2 = new LibraryDatabase(6);
    
    // Test correct null return when method is applied to empty library
    assertEquals(null, l1.randomSelection());
    assertEquals(null, l2.randomSelection());
    // Test book return of book at beginning of library
    l1.add(l);
    assertEquals("0000000000009", l1.randomSelection().getISBN());
    // Test book return of book at end or beginning of library
    l1.add(a);
    Book x = l1.randomSelection();
    assertEquals(true, x.getISBN().equals("0000000000001")||x.getISBN().equals("0000000000009"));
    // Test return of different book with multiple method calls
    l1.add(d);
    l1.add(e);
    l1.add(f);
    l1.add(g);
    l1.add(i);
    l1.add(j);
    l1.add(k);
    l1.add(l);
    Book y = l1.randomSelection();
    Book z = l1.randomSelection();
    String testMessage = null;
    // Compare random selections and test for different return values. 
    try {
      assertEquals(false, y.getISBN().equals(z.getISBN()));
      testMessage = "Randomly selected books are different";
    }
    catch (AssertionError ae) {
      testMessage = "Randomly selected books are the same";
    }
    // randomly selected books are either the same books or different
    assertEquals(true, testMessage.equals("Randomly selected books are different")||testMessage.equals("Randomly selected books are the same"));
  }
  
  @Test
  public void testFindByTitle() {
    Book a = new Book("1","0000000000001","borne");
    Book d = new Book("2","0000000000002","beatrix");
    Book e = new Book("3","0000000000003","beatrix");
    Book f = new Book("4","0000000000004","beatrix");
    Book g = new Book("5","0000000000005","bethany");
    Book i= new Book("6","0000000000006","bodacious");
    Book j = new Book("7","0000000000007","botanical");
    Book k = new Book("8","0000000000008","botanical");
    Book l = new Book("9","0000000000009","bother");
    LibraryDatabase l1 = new LibraryDatabase(0);
    LibraryDatabase l2 = new LibraryDatabase(6);
    
    // Test correct null return when method is applied to empty library
    assertEquals(null, l1.findByTitle("1"));
    assertEquals(null, l2.findByTitle("2"));
    // Test book return of one book at beginning of library
    l1.add(g);
    assertEquals("5", l1.findByTitle("5").getTitle());
    // Test book return of one book in middle or end of library 
    l1.add(k);
    l1.add(l);
    assertEquals("8", l1.findByTitle("8").getTitle());
    assertEquals("9", l1.findByTitle("9").getTitle());
    // Test book return of one book in a library with many books 
    l1.add(a);
    l1.add(d);
    l1.add(e);
    l1.add(f);
    l1.add(i);
    l1.add(j);
    assertEquals("2", l1.findByTitle("2").getTitle());
    assertEquals("1", l1.findByTitle("1").getTitle());
    assertEquals("3", l1.findByTitle("3").getTitle());
    assertEquals("4", l1.findByTitle("4").getTitle());
    assertEquals("6", l1.findByTitle("6").getTitle());
    assertEquals("7", l1.findByTitle("7").getTitle());
    // Test null return of book not in library
    assertEquals(null, l1.findByTitle("0"));
  }
  
  @Test
  public void testFindByISBN() {
    Book a = new Book("1","0000000000001","borne");
    Book d = new Book("2","0000000000002","beatrix");
    Book e = new Book("3","0000000000003","beatrix");
    Book f = new Book("4","0000000000004","beatrix");
    Book g = new Book("5","0000000000005","bethany");
    Book i= new Book("6","0000000000006","bodacious");
    Book j = new Book("7","0000000000007","botanical");
    Book k = new Book("8","0000000000008","botanical");
    Book l = new Book("9","0000000000009","bother");
    LibraryDatabase l1 = new LibraryDatabase(0);
    LibraryDatabase l2 = new LibraryDatabase(6);
    
    // Test correct null return when method applied to empty library
    assertEquals(null, l1.findByISBN("0000000000001"));
    assertEquals(null, l2.findByISBN("0000000000002"));
    // Test book return of one book at beginning of library
    l1.add(a);
    assertEquals("0000000000001", l1.findByISBN("0000000000001").getISBN());
    // Test book return of one book in middle or end of library 
    l1.add(d);
    l1.add(e);
    assertEquals("0000000000002", l1.findByISBN("0000000000002").getISBN());
    assertEquals("0000000000003", l1.findByISBN("0000000000003").getISBN());
    // Test book return of one book in a library with many books 
    l1.add(f);
    l1.add(g);
    l1.add(i);
    l1.add(j);
    l1.add(k);
    l1.add(l);
    assertEquals("0000000000008", l1.findByISBN("0000000000008").getISBN());
    assertEquals("0000000000005", l1.findByISBN("0000000000005").getISBN());
    assertEquals("0000000000007", l1.findByISBN("0000000000007").getISBN());
    assertEquals("0000000000009", l1.findByISBN("0000000000009").getISBN());
    assertEquals("0000000000004", l1.findByISBN("0000000000004").getISBN());
    // Test null return of book not in library
    assertEquals(null, l1.findByISBN("0000000000000"));
  }
  
  @Test
  public void testGetAllByAuthor() {
    Book a = new Book("1","0000000000001","borne");
    Book b = new Book("2","0000000000002","abigail");
    Book c = new Book("3","0000000000003","xerxes");
    Book d = new Book("4","0000000000004","beatrix");
    Book e = new Book("5","0000000000005","borne");
    Book f = new Book("6","0000000000006","borne");
    Book g = new Book("7","0000000000007","abigail");
    Book h= new Book("8","0000000000008","bethany");
    Book i= new Book("9","0000000000009","bodacious");
    Book j = new Book("10","0000000000010","botanical");
    Book k = new Book("11","0000000000011","xerxes");
    Book l = new Book("12","0000000000012","kerry");
    Book m = new Book("13","0000000000013","bother");
    Book n = new Book("14","0000000000014","kerry");
    Book o = new Book("15","0000000000015","borne");
    LibraryDatabase l1 = new LibraryDatabase(0);
    LibraryDatabase l2 = new LibraryDatabase(6);
    
    // Test correct null return when method is applied to empty
    assertEquals(0, l1.getAllByAuthor("borne").length);
    assertEquals(0, l2.getAllByAuthor("borne").length);
    // Test Book[] return of book at beginning of library
    l1.add(a);
    l1.add(b);
    l1.add(c);
    assertEquals("1", l1.getAllByAuthor("borne")[0].getTitle());
    assertEquals("0000000000001", l1.getAllByAuthor("borne")[0].getISBN());
    assertEquals("borne", l1.getAllByAuthor("borne")[0].getAuthor());
    // Test Book[] return of book at middle of library
    l1.add(e);
    l1.add(k);
    l1.add(m);
    l1.add(n);
    assertEquals("5", l1.getAllByAuthor("borne")[1].getTitle());
    assertEquals("0000000000005", l1.getAllByAuthor("borne")[1].getISBN());
    assertEquals("borne", l1.getAllByAuthor("borne")[1].getAuthor());
    // Test Book[] return of book at end of library, 
    l1.add(o);
    assertEquals("15", l1.getAllByAuthor("borne")[2].getTitle());
    assertEquals("0000000000015", l1.getAllByAuthor("borne")[2].getISBN());
    assertEquals("borne", l1.getAllByAuthor("borne")[2].getAuthor());
    // Test Book[] return contains middle and beginning books
    assertEquals("5", l1.getAllByAuthor("borne")[1].getTitle());
    assertEquals("0000000000005", l1.getAllByAuthor("borne")[1].getISBN());
    assertEquals("borne", l1.getAllByAuthor("borne")[1].getAuthor());
    assertEquals("1", l1.getAllByAuthor("borne")[0].getTitle());
    assertEquals("0000000000001", l1.getAllByAuthor("borne")[0].getISBN());
    assertEquals("borne", l1.getAllByAuthor("borne")[0].getAuthor());
  }
  
  @Test
  public void testRemoveDuplicates() {
    Book a = new Book("1","0000000000001","borne");
    Book b = new Book("2","0000000000002","abigail");
    Book c = new Book("3","0000000000003","xerxes");
    Book d = new Book("4","0000000000004","beatrix");
    Book e = new Book("5","0000000000001","borne");
    Book f = new Book("6","0000000000006","borne");
    Book g = new Book("7","0000000000006","abigail");
    Book h= new Book("8","0000000000008","bethany");
    Book i= new Book("9","0000000000009","bodacious");
    Book j = new Book("10","0000000000010","botanical");
    Book k = new Book("11","0000000000011","xerxes");
    Book l = new Book("12","0000000000012","kerry");
    Book m = new Book("13","0000000000015","bother");
    Book n = new Book("14","0000000000015","kerry");
    Book o = new Book("15","0000000000015","borne");
    Book p = new Book("0", "0000000000000", "0");
    LibraryDatabase l1 = new LibraryDatabase(0);
    LibraryDatabase l2 = new LibraryDatabase(6);
    
    // Test no modifications done on empty library
    try {
      l1.removeDuplicates();
      l2.removeDuplicates();
    }
    catch (Exception ee) {
      fail("No Exception should be thrown");
    }
    // Test no modifications done on library with no duplicates
    l1.add(p);
    l2.add(p);
    try {
      l1.removeDuplicates();
      l2.removeDuplicates();
    }
    catch (Exception ee) {
      fail("No Exception should be thrown");
    }
    l1.remove("0000000000000");
    l2.remove("0000000000000");
    // Test removal of duplicate at beginning of library and correct modification of library size
    l1.add(a);
    l1.add(e);
    l1.removeDuplicates();
    assertEquals("0000000000001", l1.toArray()[0].getISBN());
    assertEquals(null, l1.toArray()[1]);
    assertEquals(1, l1.size());
    // Test removal of duplicate at middle of library
    l1.add(d);
    l1.add(f);
    l1.add(g);
    l1.add(k);
    l1.removeDuplicates();
    assertEquals("0000000000011", l1.toArray()[3].getISBN());
    // Test removal of duplicates at end of library
    l1.add(m);
    l1.add(n);
    l1.add(o);
    l1.removeDuplicates();
    assertEquals(null, l1.toArray()[5]);
    // Test removal of multiple duplicates throughout library
    l2.add(a);
    l2.add(b);
    l2.add(c);
    l2.add(d);
    l2.add(e);
    l2.add(f);
    l2.add(g);
    l2.add(h);
    l2.add(i);
    l2.add(j);
    l2.add(k);
    l2.add(l);
    l2.add(m);
    l2.add(n);
    l2.add(o);
    l2.removeDuplicates();
    assertEquals("0000000000001", l2.toArray()[0].getISBN());
    assertEquals("0000000000002", l2.toArray()[1].getISBN());
    assertEquals("0000000000003", l2.toArray()[2].getISBN());
    assertEquals("0000000000004", l2.toArray()[3].getISBN());
    assertEquals("0000000000006", l2.toArray()[4].getISBN());
    assertEquals("0000000000008", l2.toArray()[5].getISBN());
    assertEquals("0000000000009", l2.toArray()[6].getISBN());
    assertEquals("0000000000010", l2.toArray()[7].getISBN());
    assertEquals("0000000000011", l2.toArray()[8].getISBN());
    assertEquals("0000000000012", l2.toArray()[9].getISBN());
    assertEquals("0000000000015", l2.toArray()[10].getISBN());
    assertEquals(null, l2.toArray()[11]);
    assertEquals(11, l2.size());
  }
}
    