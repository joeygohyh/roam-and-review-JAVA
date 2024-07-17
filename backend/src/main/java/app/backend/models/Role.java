package app.backend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
// specifies that this class is an entity and mapped to a DB table
@Table(name = "roles")
// maps to a table named "roles" in the DB and represents the different roles a user can have
public class Role {

  @Id
  // PK of the table, uniquely identifies each record
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  // PK is generated automatically by DB using auto-increment
  private Integer id;

  @Enumerated(EnumType.STRING)
  // specifies that the 'name' field should be persisted as a string in DB >> the enum values will be stored as their string representations
  @Column(length = 20)
  // use enum Roles to ensure that only predefined roles can be assigned
  private PredefinedRoles role;

  // constructors

  public Role() {}

  // create a Role instance with specified role 'name'
  public Role(PredefinedRoles role) {
    this.role = role;
  }

  public Role(Integer id, PredefinedRoles role) {
    this.id = id;
    this.role = role;
  }

  // getters setters

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public PredefinedRoles getRole() {
    return this.role;
  }

  public void setRole(PredefinedRoles role) {
    this.role = role;
  }
}
