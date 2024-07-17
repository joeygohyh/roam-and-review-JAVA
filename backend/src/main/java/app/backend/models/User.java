package app.backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.ArrayList;
import java.util.List;

@Entity
// maps to table named 'users' in the DB >> user info in the system
@Table(
  name = "users",
  uniqueConstraints = {
    @UniqueConstraint(columnNames = "username"),
    @UniqueConstraint(columnNames = "email"),
    // constraints to ensure 'username' and 'email' fields are unique
  }
)
public class User {

  @Id
  // marks PK
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  // PK auto generated and auto incremented
  private Long id;

  //   @NotBlank
  //   @Size(max = 20)
  private String username;

  //   @NotBlank
  //   @Size(max = 50)
  //   @Email
  private String email;

  //   @NotBlank
  //   @Size(max = 120)
  private String password;

  //   @NotBlank
  //   @Size(max = 50)
  private String name;

  //   @NotBlank
  //   @Size(max = 50)
  private String country;

  //   @NotBlank
  //   @Size(max = 10)
  private String gender;

  //   @Size(max = 255)
  private String profileUrl;

  // stores roles assigned to the user
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
    name = "user_roles",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  private List<Role> roles = new ArrayList<>();

  // constructors

  public User() {}

  public User(
    String username,
    String email,
    String password,
    String name,
    String country,
    String gender,
    String profileUrl
  ) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.name = name;
    this.country = country;
    this.gender = gender;
    this.profileUrl = profileUrl;
  }

  public User(
    Long id,
    String username,
    String email,
    String password,
    String name,
    String country,
    String gender,
    String profileUrl,
    List<Role> roles
  ) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.password = password;
    this.name = name;
    this.country = country;
    this.gender = gender;
    this.profileUrl = profileUrl;
    this.roles = roles;
  }

  // getters setters

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCountry() {
    return this.country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getGender() {
    return this.gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getProfileUrl() {
    return this.profileUrl;
  }

  public void setProfileUrl(String profileUrl) {
    this.profileUrl = profileUrl;
  }

  public List<Role> getRoles() {
    return this.roles;
  }

  public void setRoles(List<Role> roles) {
    this.roles = roles;
  }
}
