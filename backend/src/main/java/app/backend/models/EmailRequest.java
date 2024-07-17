package app.backend.models;
public class EmailRequest {

  private String name;
  private String email;

  // Getters and setters

  public EmailRequest() {
  }

  public EmailRequest(String name, String email) {
      this.name = name;
      this.email = email;
  }

  public String getName() {
      return name;
  }

  public void setName(String name) {
      this.name = name;
  }

  public String getEmail() {
      return email;
  }

  public void setEmail(String email) {
      this.email = email;
  }
}
