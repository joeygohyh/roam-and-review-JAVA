package app.backend.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.backend.models.PredefinedRoles;
import app.backend.models.Role;

@Repository
// extends JpaRepo (and inherits all methods) >> takes 2 params Role (type of entity to be managed), Long (type of entity's PK) )
public interface RoleRepo extends JpaRepository<Role, Long> {
  Optional<Role> findByRole(PredefinedRoles role);
  // Spring Data JPA auto generates implementation based on method name
}
