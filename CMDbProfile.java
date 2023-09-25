import java.util.NoSuchElementException;
import java.util.ArrayList;
/**
 * This class represents a Case Movie Database profile
 * @author Shankar Choudhury
 */
public class CMDbProfile {
  // Name of profile
  private String userName;
  // Profile's list of movies with ratings
  private PriorityQueue<Integer, String> list;
  // Highest rating given by user in their profile
  private int highestRating;
  // List of movies with highest rating given by user
  private ArrayList<String> favoriteMovies;
  
  // Constructor for a CMDb profile
  public CMDbProfile(String userName) {
    this.userName = userName;
    list = new PriorityQueue<Integer, String>();
    favoriteMovies = new ArrayList<String>();
  }
  
  // Getters and setters for fields
  protected String getUserName() {return userName;}
  public void changeUserName(String userName) {this.userName = userName;}
  protected PriorityQueue<Integer, String> getList() {return list;}
  private void setList(PriorityQueue<Integer, String> list) {this.list = list;}
  protected int getHighestRating() {return highestRating;}
  private void setHighestRating(int highestRating) {this.highestRating = highestRating;}
  protected ArrayList<String> getFavoriteMovies() {return favoriteMovies;}
  private void setFavoriteMovies(ArrayList<String> favoriteMovies) {this.favoriteMovies = favoriteMovies;}
               
  
  /**
   * Add a movie and its rating to this profile's list
   * @param movie Move to be added to list
   * @param rating Rating of movie to be added to list
   * @return true Movie and its rating were added to list
   * @return false Movie and its rating were not added to list because of invalid rating
   */
  public boolean rate(String movie, int rating) {
    if (rating < 1 || rating > 10)
      return false;
    getList().add(rating, movie);
    if (rating > getHighestRating()) {
      setHighestRating(rating);
      setFavoriteMovies(new ArrayList<String>());
      getFavoriteMovies().add(movie);
    }
    else if (rating == getHighestRating())
      getFavoriteMovies().add(movie);
    return true;
  }
  
  /**
   * Change rating of a specific movie
   * @param movie Movie whose rating will be changed to new input rating
   * @param newRating New rating to replace old rating with
   * @return true Movie's rating was changed
   * @return false Movie's rating was not changed due to invalid 
   */
  public boolean changeRating(String movie, int newRating) {
    if (newRating < 0 || newRating > 10)
      return false;
    try {
      getList().update(newRating, movie);
      if (newRating > getHighestRating()) {
        setHighestRating(newRating);
        setFavoriteMovies(new ArrayList<String>());
        getFavoriteMovies().add(movie);
      }
      else if (newRating == getHighestRating())
        getFavoriteMovies().add(movie);
      return true;
    }
    catch (NoSuchElementException n) {
      return false;
    }
  }
  
  /**
   * Remove movie and its rating from this profile's list
   * @param movie Movie and its corresponding rating to remove from list
   * @return true Movie and its rating were successfully removed from list
   * @return false Movie and its rating were not removed from list due to movie not existing in list
   */
  public boolean removeRating(String movie) {
    try {
      getList().poll(movie);
      return true;
    }
    catch (NoSuchElementException n) {
      return false;
    }
  }
  
  /**
   * Return all of the movies that have the highest rating given by this user
   * @return getFavoriteMovies().toArray() Array of movies that have the highest rating from this user
   */
  public String[] favorite() {
    return getFavoriteMovies().toArray(new String[getFavoriteMovies().size()]);
  }
  
  /**
   * Return user's k highest rated movies from their profile
   * @param k K highest rated movies from user's profile
   * @return getList().peek(k) An array containing the k highest rated movies from this user's profile
   */
  @SuppressWarnings("unchecked")
  public String[] favorite(int k) {
    String[] favMovies = new String[k];
    try {
      Object[] o = getList().peek(k);
      int i = 0;
      for (Object t : o) {
        favMovies[i] = (String) t;
        i++;
      }
      return favMovies;
    }
    catch (IllegalArgumentException i) {
      return favMovies;
    }
  }
  
  /**
   * Return a String with this profile's information
   * @return A string of this profile's information
   */
  public String profileInformation() {
    return getUserName() + ' ' + '(' + getList().size() + ')' + "\n" + "Favorite Movie: " + getFavoriteMovies().toArray()[0];
  }
  
  /**
   * Return whether this profile has the same username as another CMDbProfile
   * @param o Other CMDbProfile
   * @return true The profile has the same name as this profile
   * @return false The profile has a different name or is not a CMDbProfile
   */
  public boolean equals(Object o) {
    if (o instanceof CMDbProfile) {
      CMDbProfile p = (CMDbProfile) o;
      return p.getUserName().equals(getUserName());
    }
    return false;
  }
  
  public static void main(String[] args) {
    CMDbProfile c1 = new CMDbProfile("Shankar");
    String[] movies = {"a", "b", "c", "d", "e", "f", "g", "h", "i"};
    Integer[] ratings = {5, 3, 8, 3, 4, 9, 10, 1, 3};
    for (int i = 0; i < 9; i++)
      c1.rate(movies[i], ratings[i]);
    String[] fav = c1.favorite(3);
    System.out.println(c1.profileInformation());
  }
}