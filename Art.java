/**
 * This class represents a work of art
 * @author Shankar Choudhury
 */
public class Art {
  // Height of art
  private int height;
  // Price of art
  private int price;
  // Width of art
  private int width;
  // Name of art
  private String name;
  // Artist's name of art
  private String artistName;
  
  /**
   * Constructor for an Art object
   * @param height Height of art
   * @param price Price of art
   * @param width Width of art
   * @param name Name of art
   * @param artistName Artist's name of art
   */
  public Art(int height, int price, int width, String name, String artistName) {
    this.height = height;
    this.price = price;
    this.width = width;
    this.name = name;
    this.artistName = artistName;
  }
  
  // Getters and setters for this art
  public int height() {return height;}
  public void setHeight(int height) {this.height = height;}
  public int price() {return price;}
  public void setPrice(int price) {this.price = price;}
  public int width() {return width;}
  public void setWidth(int width) {this.width = width;}
  public String name() {return name;}
  public void setName(String name) {this.name = name;}
  public String artistName() {return artistName;}
  public void setArtistName(String artistName) {this.artistName = artistName;}
}