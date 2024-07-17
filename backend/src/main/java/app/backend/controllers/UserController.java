package app.backend.controllers;

import app.backend.models.PredefinedRoles;
import app.backend.models.Role;
import app.backend.models.User;
import app.backend.payload.request.LoginRequest;
import app.backend.payload.response.MessageResponse;
import app.backend.payload.response.UserInfoResponse;
import app.backend.repo.RoleRepo;
import app.backend.repo.UserRepo;
import app.backend.security.jwt.JwtUtils;
import app.backend.security.service.UserDetailsImpl;
import app.backend.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/account")
public class UserController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtUtils jwtUtils;

  @Autowired
  private UserService us;

  @Autowired
  private UserRepo userRepository;

  @Autowired
  private RoleRepo roleRepository;

  @Autowired
  private PasswordEncoder encoder;

  @GetMapping("/current-user")
  public ResponseEntity<?> getCurrentUser(Authentication authentication) {
    if (authentication == null || !authentication.isAuthenticated()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    // You can customize what user information to return here
    UserInfoResponse userInfoResponse = new UserInfoResponse(
      userDetails.getId(),
      userDetails.getUsername(),
      userDetails.getEmail(),
      userDetails.getName(),
      userDetails.getCountry(),
      userDetails.getGender(),
      userDetails.getProfileUrl(),
      userDetails
        .getAuthorities()
        .stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList())
    );

    return ResponseEntity.ok(userInfoResponse);
  }

  @PostMapping(path = "/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> addAccount(
    @RequestPart("name") String name,
    @RequestPart("username") String username,
    @RequestPart("email") String email,
    @RequestPart("password") String password,
    @RequestPart("country") String country,
    @RequestPart("gender") String gender,
    @RequestPart("profilePicture") MultipartFile profilePicture
  ) {
    if (userRepository.existsByUsername(username)) {
      return ResponseEntity
        .badRequest()
        .body(new MessageResponse("Error: Username is already taken!"));
    }

    if (userRepository.existsByEmail(email)) {
      return ResponseEntity
        .badRequest()
        .body(new MessageResponse("Error: Email is already in use!"));
    }

    // Create new user's account
    User user = new User(
      username,
      email,
      encoder.encode(password),
      name,
      country,
      gender,
      profilePicture.getOriginalFilename()
    );

    List<Role> roles = new ArrayList<>();
    Role userRole = roleRepository
      .findByRole(PredefinedRoles.ROLE_USER)
      .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
    roles.add(userRole);

    user.setRoles(roles);
    userRepository.save(user);

    // Save account and upload profile picture
    us.addAccount(user, profilePicture);

    return ResponseEntity.ok(
      new MessageResponse("User registered successfully!")
    );
  }

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(
    @Valid @RequestBody LoginRequest loginRequest
  ) {
    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        loginRequest.getUsername(),
        loginRequest.getPassword()
      )
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(
      (UserDetailsImpl) userDetails
    );

    List<String> roles = userDetails
      .getAuthorities()
      .stream()
      .map(GrantedAuthority::getAuthority)
      .collect(Collectors.toList());

    String username = userDetails.getUsername();

    System.out.println(username);

    // returns a response containing the JWT cookie in the header and user information (UserInfoResponse) in the body
    return ResponseEntity
      .ok()
      .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
      .body(
        new UserInfoResponse(
          userDetails.getId(),
          userDetails.getUsername(),
          userDetails.getEmail(),
          userDetails.getName(),
          userDetails.getCountry(),
          userDetails.getGender(),
          userDetails.getProfileUrl(),
          roles
        )
      );
  }

  @PostMapping("/signout")
  public ResponseEntity<Void> logout(
    HttpServletRequest request,
    HttpServletResponse response
  ) {
    HttpSession session = request.getSession(false);
    if (session != null) {
      session.invalidate();
    }

    Authentication auth = SecurityContextHolder
      .getContext()
      .getAuthentication();
    if (auth != null) {
      new SecurityContextLogoutHandler().logout(request, response, auth);
    }

    return ResponseEntity.noContent().build();
  }

  @PutMapping("/update-username/{userId}")
  public ResponseEntity<?> updateUsername(
    @PathVariable("userId") Long userId,
    @RequestBody String newUsername
  ) {
    try {
      User updatedUser = us.updateUsername(userId, newUsername);
      return ResponseEntity.ok(updatedUser);
    } catch (RuntimeException e) {
      return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body("User not found with id: " + userId);
    }
  }

  @PutMapping("/update-password/{userId}")
  public ResponseEntity<?> updatePassword(
    @PathVariable("userId") Long userId,
    @RequestBody String newPassword
  ) {
    try {
      User updatedUser = us.updatePassword(userId, encoder.encode(newPassword));
      return ResponseEntity.ok(updatedUser);
    } catch (RuntimeException e) {
      return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body("User not found with id: " + userId);
    }
  }
}
