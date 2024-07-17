package app.backend.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.backend.models.User;
import app.backend.repo.UserRepo;

// this service is responsible for loading user-specific data during authentication
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  UserRepo userRepository;

  @Override
  @Transactional
  // ensures that the method runs within a transaction, if any exception occurs within this method, transaction will be rolled back
  public UserDetails loadUserByUsername(String username)
  // takes username as param and returns a UserDetails object
    throws UsernameNotFoundException {
    User user = userRepository
      .findByUsername(username)
      .orElseThrow(() ->
        new UsernameNotFoundException(
          "User Not Found with username: " + username
        )
      );

    return UserDetailsImpl.build(user);
  }
}
