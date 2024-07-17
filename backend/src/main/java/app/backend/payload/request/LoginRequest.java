package app.backend.payload.request;

import jakarta.validation.constraints.NotBlank;

// data transfer object used to capture and validate login credentials sent from FE to server >> for authentication
// when user attempts to log in, credentials captured in an instance of LoginRequest >> credentials to be validated and used to authenticate the user
public class LoginRequest {

  @NotBlank
  private String username;

  @NotBlank
  private String password;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
