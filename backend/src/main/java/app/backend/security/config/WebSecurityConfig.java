package app.backend.security.config;

import app.backend.security.jwt.AuthEntryPointJwt;
import app.backend.security.jwt.AuthTokenFilter;
import app.backend.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// configures the security sttings for the App.
// defines authentication and authorization mechanisms, sets up password encoding, confifgures security filters
@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

  @Autowired
  UserDetailsServiceImpl userDetailsService;

  @Autowired
  private AuthEntryPointJwt unauthorizedHandler;

  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder());

    return authProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(
    AuthenticationConfiguration authConfig
  ) throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .csrf(csrf -> csrf.disable())
      .exceptionHandling(exception ->
        exception.authenticationEntryPoint(unauthorizedHandler)
      )
      .sessionManagement(session ->
        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      )
      .authorizeHttpRequests(auth ->
        auth
          .requestMatchers("/api/account/**")
          .permitAll()
          .requestMatchers("/api/coffee").permitAll()
          // .requestMatchers("/api/addReview")
          // .permitAll()
          .requestMatchers(HttpMethod.GET, "/api/getAllReviews/**")
          .permitAll() // Permit GET requests to /api/getAllReviews
          .requestMatchers(HttpMethod.GET, "/api/getReviews/**")
          .permitAll() // Permit GET requests to /api/getAllReviews
          .requestMatchers(HttpMethod.POST, "/api/addReview")
          .authenticated() // Require authentication for POST requests to /api/addReview
          .requestMatchers(HttpMethod.POST, "/api/likeReview")
          .authenticated() // Require authentication for POST requests to /api/addReview
          .requestMatchers(HttpMethod.POST, "/api/unlikeReview")
          .authenticated() // Require authentication for POST requests to /api/addReview
          .anyRequest()
          .permitAll() // Permit all other requests
      );

    http.authenticationProvider(authenticationProvider());

    http.addFilterBefore(
      authenticationJwtTokenFilter(),
      UsernamePasswordAuthenticationFilter.class
    );

    return http.build();
  }
}
