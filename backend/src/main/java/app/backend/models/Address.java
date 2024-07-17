package app.backend.models;


public class Address extends FullPark{

  private String postalCode;
  private String city;
  private String stateCode;
  private String countryCode;
  private String provinceTerritoryCode;
  private String line1;
  private String line2;
  private String line3;

  public Address() {}

  public Address(
    String postalCode,
    String city,
    String stateCode,
    String countryCode,
    String provinceTerritoryCode,
    String line1,
    String line2,
    String line3
  ) {
    this.postalCode = postalCode;
    this.city = city;
    this.stateCode = stateCode;
    this.countryCode = countryCode;
    this.provinceTerritoryCode = provinceTerritoryCode;
    this.line1 = line1;
    this.line2 = line2;
    this.line3 = line3;
  }

  public String getPostalCode() {
    return this.postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public String getCity() {
    return this.city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getStateCode() {
    return this.stateCode;
  }

  public void setStateCode(String stateCode) {
    this.stateCode = stateCode;
  }

  public String getCountryCode() {
    return this.countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  public String getProvinceTerritoryCode() {
    return this.provinceTerritoryCode;
  }

  public void setProvinceTerritoryCode(String provinceTerritoryCode) {
    this.provinceTerritoryCode = provinceTerritoryCode;
  }

  public String getLine1() {
    return this.line1;
  }

  public void setLine1(String line1) {
    this.line1 = line1;
  }

  public String getLine2() {
    return this.line2;
  }

  public void setLine2(String line2) {
    this.line2 = line2;
  }

  public String getLine3() {
    return this.line3;
  }

  public void setLine3(String line3) {
    this.line3 = line3;
  }
}
