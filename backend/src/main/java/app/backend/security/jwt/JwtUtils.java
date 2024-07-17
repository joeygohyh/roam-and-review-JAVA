package app.backend.security.jwt;

import app.backend.security.service.UserDetailsImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

// util component for handling JWTs
// methods to generate, validate, extract info from JWTs and manage JWT cookies
@Component
public class JwtUtils {

  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

  @Value("${project.app.jwtSecret}")
  private String jwtSecret;

  // secret key used for signing JWT tokens, injected from app.properties

  @Value("${project.app.jwtExpirationMs}")
  private int jwtExpirationMs;

  // expiration time of JWT tokens in MILISECONDS, injected from app.properties

  @Value("${project.app.jwtCookieName}")
  private String jwtCookie;

  // name of cookie that stores the JWT token, injected from app.properties

  // extracts the JWT token from the specified cookie in the HTTP request
  public String getJwtFromCookies(HttpServletRequest request) {
    Cookie cookie = WebUtils.getCookie(request, jwtCookie);
    // gets cookie with the name specified by jwtCookie
    if (cookie != null) {
      return cookie.getValue();
    } else {
      return null;
    }
  }

  // generates a JWT token for given user and creates an HTTP-only cookie with the token
  public ResponseCookie generateJwtCookie(UserDetailsImpl userPrincipal) {
    String jwt = generateTokenFromUsername(userPrincipal.getUsername());
    // generates JWT token using username of user
    ResponseCookie cookie = ResponseCookie
      .from(jwtCookie, jwt)
      .path("/api")
      .maxAge(24 * 60 * 60)
      .httpOnly(true)
      .build();
    return cookie;
  }

  // creates a response cookie with JWT token, max age 24hrs

  //  generates a cookie with a null value to clear the JWT cookie
  public ResponseCookie getCleanJwtCookie() {
    ResponseCookie cookie = ResponseCookie
      .from(jwtCookie, null)
      .path("/api")
      .build();
    return cookie;
  }

  // extracts the username from the JWT token
  public String getUserNameFromJwtToken(String token) {
    return Jwts
      .parserBuilder()
      .setSigningKey(key())
      .build()
      .parseClaimsJws(token)
      .getBody()
      .getSubject();
  }

  // returns the signing key used to sign and verify JWT tokens
  private Key key() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
  }

  // validates JWT token by checking its signature and expiration
  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
      return true;
    } catch (MalformedJwtException e) {
      logger.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      logger.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      logger.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims string is empty: {}", e.getMessage());
    }

    return false;
  }

  // generate JWT token using provided username
  public String generateTokenFromUsername(String username) {
    return Jwts
      .builder()
      .setSubject(username)
      .setIssuedAt(new Date())
      .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
      .signWith(key(), SignatureAlgorithm.HS256)
      .compact();
  }
}
