package app.backend.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import app.backend.security.service.UserDetailsServiceImpl;

// custom filter that ensures it executes once per request >> responsible for handling the authentication logic by validating JWT tokens in incoming requests and setting the user authentiation in the security context if the token is valid

// >>>> filter ensures that requests with valid JWT token are authenticated, recognizing the user and granting appropriate access based on roles and permissions
public class AuthTokenFilter extends OncePerRequestFilter {

  @Autowired
  private JwtUtils jwtUtils;

  // a util class for handling JWT operations like parsing, validating, and extracting info from JWT tokens

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  // a service for loading user-specific data

  private static final Logger logger = LoggerFactory.getLogger(
    AuthTokenFilter.class
  );

  @Override
  // authenticaition logic is implemented
  protected void doFilterInternal(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain
  ) throws ServletException, IOException {
    try {
      String jwt = parseJwt(request);
      // calls parseJwt method to extract JWT token from the request
      if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
        // if JWT token found and valid, method proceeds to extract the username from the token
        String username = jwtUtils.getUserNameFromJwtToken(jwt);

        // loads user details using the extracted username
        UserDetails userDetails = userDetailsService.loadUserByUsername(
          username
        );

        // setting authentication
        // creates an Authentication Object using the user details
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
          userDetails,
          null,
          userDetails.getAuthorities()
        );

        // sets additional details about the authentication request
        authentication.setDetails(
          new WebAuthenticationDetailsSource().buildDetails(request)
        );

        // sets the authentication object in the security context, marking the user as authenticated
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (Exception e) {
      logger.error("Cannot set user authentication: {}", e);
    }

    filterChain.doFilter(request, response);
    // passes the request and the response to the next filter in the filter chain
  }

  // extracts JWT token from the cookies in the HTTP request
  private String parseJwt(HttpServletRequest request) {
    String jwt = jwtUtils.getJwtFromCookies(request);
    return jwt;
  }
}
