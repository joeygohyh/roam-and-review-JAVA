package app.backend.security.service;

import com.fasterxml.jackson.annotation.JsonIgnore;

import app.backend.models.User;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

// implements the UserDetails interface
// >> represents the authenticated user's details, including credentials, roles and account status
public class UserDetailsImpl implements UserDetails {

  private static final long serialVersionUID = 1L;
  // defines a unique version identifier for the class, useful during the serialization and deserialization processes.

  private Long id;
  private String username;
  private String email;

  @JsonIgnore
  // used to mark a field in a Java object to be ignored during serialization and deserialization
  // when an instance of UserDetailsImpl is converted to JSON string, the password field will not be included in the JSON output
  // >>> security purposes
  private String password;

  private Collection<? extends GrantedAuthority> authorities;

  private String name;
  private String country;
  private String gender;
  private String profileUrl;

  public UserDetailsImpl(
    Long id,
    String username,
    String email,
    String password,
    Collection<? extends GrantedAuthority> authorities,
    String name,
    String country,
    String gender,
    String profileUrl
  ) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.password = password;
    this.authorities = authorities;
    this.name = name;
    this.country = country;
    this.gender = gender;
    this.profileUrl = profileUrl;
  }

  // build method creates an instance of UserDetailsImpl from a User object >> maps user roles to GrantedAuthority objects
  public static UserDetailsImpl build(User user) {
    List<GrantedAuthority> authorities = user
      .getRoles()
      .stream()
      .map(role -> new SimpleGrantedAuthority(role.getRole().name()))
      .collect(Collectors.toList());

    return new UserDetailsImpl(
      user.getId(),
      user.getUsername(),
      user.getEmail(),
      user.getPassword(),
      authorities,
      user.getName(),
      user.getCountry(),
      user.getGender(),
      user.getProfileUrl()
    );
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public Long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  public String getName() {
    return name;
  }

  public String getCountry() {
    return country;
  }

  public String getGender() {
    return gender;
  }

  public String getProfileUrl() {
    return profileUrl;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  // equals method compares UserDetailsImpl objects based on their 'id' field for equality
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserDetailsImpl user = (UserDetailsImpl) o;
    return Objects.equals(id, user.id);
  }
}
