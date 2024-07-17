package app.backend.models;



public class Park {

  private String id;
  private String url;
  private String fullName;
  private String parkCode;
  private String description;
  private String image;

  public Park() {}

  public Park(
    String id,
    String url,
    String fullName,
    String parkCode,
    String description,
    String image
  ) {
    this.id = id;
    this.url = url;
    this.fullName = fullName;
    this.parkCode = parkCode;
    this.description = description;
    this.image = image;
  }

  public String getFullName() {
    return this.fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getParkCode() {
    return this.parkCode;
  }

  public void setParkCode(String parkCode) {
    this.parkCode = parkCode;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getImage() {
    return this.image;
  }

  public void setImage(String image) {
    this.image = image;
  }
}
