package app.backend.models;


public class Campground {

  private String name;
  private String description;
  private String latitude;
  private String longitude;
  private String latLong;
  private String image;

  public Campground() {}

  public Campground(
    String name,
    String description,
    String latitude,
    String longitude,
    String latLong,
    String image
  ) {
    this.name = name;
    this.description = description;
    this.latitude = latitude;
    this.longitude = longitude;
    this.latLong = latLong;
    this.image = image;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getLatitude() {
    return this.latitude;
  }

  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }

  public String getLongitude() {
    return this.longitude;
  }

  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }

  public String getLatLong() {
    return this.latLong;
  }

  public void setLatLong(String latLong) {
    this.latLong = latLong;
  }

  public String getImage() {
    return this.image;
  }

  public void setImage(String image) {
    this.image = image;
  }
}
