import java.util.ArrayList;
import java.util.NoSuchElementException;
/** 
 * This class consolidates a set of CMDb profiles and provides useful information about the group's preferences
 * @author Shankar Choudhury
 */
public class CMDbGroup {
  // Names of members of this group
  private ArrayList<String> groupNames;
  // All members of this group
  private ArrayList<CMDbProfile> groupMembers;
  // Hash table of movies from all members and their frequency of appearing on lists
  private HashTable<CMDbProfile> groupRatings;
  // All historic members of any CMDbGroup
  private static HashTable<CMDbProfile> allMembers = new HashTable<CMDbProfile>();
  
  // Default constructor for an empty CMDbGroup 
  public CMDbGroup() {
    groupNames = new ArrayList<String>();
    groupMembers = new ArrayList<CMDbProfile>();
    groupRatings = new HashTable<CMDbProfile>();
  }
  
  // Getters and setters for fields 
  private HashTable<CMDbProfile> groupRatings() {return groupRatings;}
  private ArrayList<String> groupNames() {return groupNames;}
  private ArrayList<CMDbProfile> groupMembers() {return groupMembers;}
  private static HashTable<CMDbProfile> registeredUsers() {return allMembers;}
  
  /**
   * Return array holding all user names of this group's members
   * @return Array of all user names
   */
  public String[] group() {return (String[]) groupNames().toArray();}
  
  /**
   * Add a CMDbProfile member to this group
   * @param member Member to add to this group
   */
  public void addMember(CMDbProfile member) {
    groupNames().add(member.getUserName());
    groupMembers().add(member);
    groupRatings().put(member.getUserName(), member);
    registeredUsers().put(member.getUserName(), member);
  }
  
  /**
   * Add an array of CMDbProfile members to this group
   * @param member Members to add to this group
   */
  public void addMember(CMDbProfile[] members) {
    for (int i = 0; i < members.length; i++) {
      groupNames().add(members[i].getUserName());
      groupMembers().add(members[i]);
      groupRatings().put(members[i].getUserName(), members[i]);
      registeredUsers().put(members[i].getUserName(), members[i]);
    }
  }
  
  /**
   * Return a user's favorite movie from any past CMDbGroups
   * @param userName User to find favorite's movie
   * @return User's favorite movie
   * @return null User could not be found
   */
  public static String favorite(String userName) {
    try {
      return registeredUsers().get(userName).getList().peek();
    }
    catch (NoSuchElementException n) {
      return null;
    }
  }
    
  /**
   * Return an array holding this group's favorite movies 
   * @return Array holding favorite movies of this group
   */
  public String[] groupFavorites() {
    HashTable<Integer> groupFavHash = new HashTable<Integer>();
    // Go through each member and add their favorite movies to the groupFavHash, one movie at a time
    for (CMDbProfile member : groupMembers()) {
      String[] favMovies = member.getFavoriteMovies().toArray(new String[member.getFavoriteMovies().size()]);
      for (String movie : favMovies)
        groupFavHash.put(movie, 1);
    }
    // Add keys and values of groupFavHash to separate arrays
    Integer[] keys = new Integer[groupFavHash.size()];
    String[] values = new String[groupFavHash.size()];
    groupFavHash.toArrays(keys, values);
    // maxHeap groupFavList
    PriorityQueue<Integer, String> maxHeap = new PriorityQueue<Integer, String> (keys, values);
    // Append all elements with keys equivalent to first element in maxHeap
    Object[] o = maxHeap.getGroupFavs();
    String[] s = new String[o.length];
    int i = 0;
    for (Object t : o) {
      s[i] = (String) t;
      i++;
    }
    return s;
  }
  
  /**
   * Return a random movie selected out of k possibilities
   * @param k Number of possible movies to select from
   * @return Random movie
   */
  public String randomMovie(int k) {
    // ArrayList to hold group members randomely chosen
    ArrayList<CMDbProfile> selectedMembers = new ArrayList<CMDbProfile>();
    // Choose a random group member and add it to selectedMembers k times
    for (int i = 0; i < k; i++) {
      int randIndex = (int)(Math.random() * ((groupMembers.size() - 1) + 1));
      selectedMembers.add(groupMembers().get(randIndex));
    }
    // Hash table to count number of times a group member has been selected
    HashTable<Integer> selectedCount = new HashTable<Integer>();
    for (CMDbProfile member : selectedMembers) 
      selectedCount.put(member.getUserName(), 1);
    // Remove duplicate members from selectedMembers
    selectedMembers = removeDuplicates(selectedMembers);
    // Find name from selectedCount in selectedMembers, and assign their favorite count's movies to ArrayList<String> movies
    ArrayList<String> movies = new ArrayList<String>();
    for (CMDbProfile member : selectedMembers) {
      for (String movie : member.favorite(selectedCount.get(member.getUserName())))
        movies.add(movie);
    }
    int randIndex = (int)(Math.random() * ((movies.size() - 1) + 1));
    return movies.get(randIndex);
  }
  
  // Helper method to remove duplicate profiles from a list of profiles
  private ArrayList<CMDbProfile> removeDuplicates(ArrayList<CMDbProfile> list) {
    ArrayList<CMDbProfile> newList = new ArrayList<CMDbProfile>();
    for (CMDbProfile member : list) {
      if (!newList.contains(member))
        newList.add(member);
    }
    return newList;        
  }
  
  public static void main(String[] args) {
    CMDbProfile c1 = new CMDbProfile("Shankar");
    String[] movies1 = {"a", "b", "c", "d", "e", "f", "g", "h", "i"};
    Integer[] ratings1 = {5, 3, 8, 10, 4, 9, 10, 1, 3};
    for (int i = 0; i < 9; i++)
      c1.rate(movies1[i], ratings1[i]);
    
    CMDbProfile c2 = new CMDbProfile("Kerry");
    String[] movies2 = {"j", "k", "l", "m", "n", "o", "a", "b", "c"};
    Integer[] ratings2 = {5, 3, 8, 3, 4, 9, 10, 1, 3};
    for (int i = 0; i < 9; i++)
      c2.rate(movies2[i], ratings2[i]);
    
    CMDbProfile c3 = new CMDbProfile("Moose");
    String[] movies3 = {"b", "k", "i", "a", "q"};
    Integer[] ratings3 = {5, 3, 8, 8, 4};
    for (int i = 0; i < 5; i++)
      c3.rate(movies3[i], ratings3[i]);
    
    CMDbProfile c4 = new CMDbProfile("Zach");
    String[] movies4 = {"b", "o", "d"};
    Integer[] ratings4 = {8, 3, 10};
    for (int i = 0; i < 3; i++)
      c4.rate(movies4[i], ratings4[i]);
    
    CMDbProfile c5 = new CMDbProfile("Devin");
    String[] movies5 = {"u", "d", "r", "c", "q", "f", "a", "a", "b", "z", "i", "l"};
    Integer[] ratings5 = {5, 10, 7, 8, 9, 5, 1, 10, 2, 5, 2, 1};
    for (int i = 0; i < 12; i++)
      c5.rate(movies5[i], ratings5[i]);
    
    CMDbProfile[] members = {c1, c2, c3, c4, c5};
    CMDbGroup g1 = new CMDbGroup();
    g1.addMember(members);
    
    System.out.println(g1.randomMovie(3));
  }
  
}