package app.backend.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

// handles unauthorized access by providing a custom response when authentication fails
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    // to log error messages
  private static final Logger logger = LoggerFactory.getLogger(
    AuthEntryPointJwt.class
  );

  // overrides 'commence' method from the AuthenticationEntryPoint interface
  @Override
  public void commence(
    HttpServletRequest request, // the HTTP request that resulted in an AuthenticationException
    HttpServletResponse response, // the HTTP response to be sent
    AuthenticationException authException // the exception thrown when authentication fails
  ) throws IOException, ServletException {
    logger.error("Unauthorized error: {}", authException.getMessage());
    // logs the unauthorized access attempt with the error message

    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    // sets content type of response to application/json
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    // sets HTTP status code to 401 unauthorized


    // creating the response body
    final Map<String, Object> body = new HashMap<>();
    // creates a map to hold the response body with details about the error
    body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
    body.put("error", "Unauthorized"); 
    body.put("message", authException.getMessage()); // exception message
    body.put("path", request.getServletPath()); // requested path that caused the exception

    // writing the response body
    final ObjectMapper mapper = new ObjectMapper();
    // uses objectmapper from the jackson library to write the response body as JSON to the response output stream
    mapper.writeValue(response.getOutputStream(), body);
  }
}
