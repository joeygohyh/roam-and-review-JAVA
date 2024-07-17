package app.backend.models;


import java.util.List;

public class FullPark {

  private String url;
  private String fullName;
  private String parkCode;
  private String description;
  private String latitude;
  private String longitude;
  private String latLong;
  private OperatingHours operatingHours;
  private Address address;
  private List<Image> images;

  public FullPark() {}

  public FullPark(
    String url,
    String fullName,
    String parkCode,
    String description,
    String latitude,
    String longitude,
    String latLong,
    OperatingHours operatingHours,
    Address address,
    List<Image> images
  ) {
    this.url = url;
    this.fullName = fullName;
    this.parkCode = parkCode;
    this.description = description;
    this.latitude = latitude;
    this.longitude = longitude;
    this.latLong = latLong;
    this.operatingHours = operatingHours;
    this.address = address;
    this.images = images;
  }

  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getFullName() {
    return this.fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
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

  public OperatingHours getOperatingHours() {
    return this.operatingHours;
  }

  public void setOperatingHours(OperatingHours operatingHours) {
    this.operatingHours = operatingHours;
  }

  public Address getAddress() {
    return this.address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public List<Image> getImages() {
    return this.images;
  }

  public void setImages(List<Image> images) {
    this.images = images;
  }
}
