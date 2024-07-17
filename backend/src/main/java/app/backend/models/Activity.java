package app.backend.models;



public class Activity {

  private String title;
  private String shortDescription;
  // private String latitude;
  // private String longitude;
  // private String latLong;
  private String image;

  public Activity() {}

  public Activity(String title, String shortDescription, String image) {
    this.title = title;
    this.shortDescription = shortDescription;
    this.image = image;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getShortDescription() {
    return this.shortDescription;
  }

  public void setShortDescription(String shortDescription) {
    this.shortDescription = shortDescription;
  }

  public String getImage() {
    return this.image;
  }

  public void setImage(String image) {
    this.image = image;
  }
}
