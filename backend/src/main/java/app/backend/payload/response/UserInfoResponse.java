package app.backend.payload.response;

import java.util.List;

// data transfer object used to send detailed user info from server to client
// to return user info in response body of a HTTP request >> send detailed user info back to client after a successful login
public class UserInfoResponse {

  private Long id;
  private String username;
  private String email;
  private String name;
  private String country;
  private String gender;
  private String profileUrl;
  private List<String> roles;

  public UserInfoResponse(
    Long id,
    String username,
    String email,
    String name,
    String country,
    String gender,
    String profileUrl,
    List<String> roles
  ) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.name = name;
    this.country = country;
    this.gender = gender;
    this.profileUrl = profileUrl;
    this.roles = roles;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public List<String> getRoles() {
    return roles;
  }

  public void setRoles(List<String> roles) {
    this.roles = roles;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getProfileUrl() {
    return profileUrl;
  }

  public void setProfileUrl(String profileUrl) {
    this.profileUrl = profileUrl;
  }
}
