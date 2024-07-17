package app.backend.controllers;

import app.backend.models.Review;
import app.backend.services.ReviewService;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class ReviewController {

  @Autowired
  private ReviewService rs;

  @GetMapping(
    path = "/getAllReviews",
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<List<Review>> getAllReviews() {
    List<Review> reviews = rs.getAllReviews();
    return ResponseEntity.ok(reviews);
  }

  @GetMapping("/getAllReviews/{parkCode}")
  public ResponseEntity<List<Review>> getReviewsByParkCode(
    @PathVariable String parkCode
  ) {
    List<Review> reviews = rs.getReviewsByParkCode(parkCode);
    return ResponseEntity.ok(reviews);
  }

  @GetMapping("/getReviews/{username}")
  public ResponseEntity<List<Review>> getReviewsByUsername(
    @PathVariable String username
  ) {
    List<Review> reviews = rs.getReviewsByUsername(username);
    return ResponseEntity.ok(reviews);
  }

  @PostMapping("/likeReview/{reviewId}")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<String> likeReview(
    Authentication authentication,
    @PathVariable String reviewId
  ) {
    String username = authentication.getName();
    rs.addLike(reviewId, username);
    return ResponseEntity.ok(
      "{\"message\": \"Liked review with ID: " + reviewId + "\"}"
    );
  }

  @PostMapping("/unlikeReview/{reviewId}")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<String> unlikeReview(
    Authentication authentication,
    @PathVariable String reviewId
  ) {
    String username = authentication.getName();
    rs.removeLike(reviewId, username);
    return ResponseEntity.ok(
      "{\"message\": \"Unliked review with ID: " + reviewId + "\"}"
    );
  }

  @PostMapping("/addReview")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<String> addReview(
    Authentication authentication,
    @RequestParam("review") String review,
    @RequestParam("location") String location,
    @RequestParam("parkCode") String parkCode,
    @RequestParam("file") MultipartFile file
  ) throws IOException {
    String username = authentication.getName();
    String profileUrl = ""; // Fetch or construct profile URL based on logged in user

    // Create Review object
    Review r = new Review();
    r.setUsername(username);
    r.setProfileUrl(profileUrl);
    r.setReview(review);
    r.setLocation(location);
    r.setParkCode(parkCode);

    rs.addReview(r, file);
    return ResponseEntity
      .ok()
      .body("{\"message\": \"Review added successfully!\"}");
  }

  @DeleteMapping("/deleteReview/{id}")
  public ResponseEntity<?> deleteReviewById(@PathVariable String id) {
    try {
      rs.deleteReviewById(id);

      return ResponseEntity.ok("Review deleted successfully");
    } catch (RuntimeException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
  }
}
