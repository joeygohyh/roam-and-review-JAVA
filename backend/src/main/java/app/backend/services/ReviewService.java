package app.backend.services;

import app.backend.models.Review;
import app.backend.repo.ReviewsRepo;
import app.backend.repo.S3Repo;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class ReviewService {

  @Autowired
  ReviewsRepo rr;

  @Autowired
  S3Repo s3Repo;

  @Autowired
  private SimpMessagingTemplate messagingTemplate;

  @PreAuthorize("isAuthenticated()")
  public void addReview(Review review, MultipartFile file) {
    messagingTemplate.convertAndSend("/topic/reviews", "New review uploaded!");

    try {
      String reviewUrl = s3Repo.upload(file);
      review.setReviewUrl(reviewUrl);
      rr.addReview(review);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public List<Review> getAllReviews() {
    Optional<List<Review>> employeesOptional = rr.getAllReviews();
    // If employeesOptional is present, return the list; otherwise return an empty list
    return employeesOptional.orElseGet(() -> List.of());
  }

  // Method to get reviews by park code
  public List<Review> getReviewsByParkCode(String parkCode) {
    return rr.getReviewsByParkCode(parkCode);
  }

    // Method to get reviews by park code
    public List<Review> getReviewsByUsername(String username) {
      return rr.getReviewsByUsername(username);
    }

  public void addLike(String reviewId, String username) {
    Review review = rr
      .findById(reviewId)
      .orElseThrow(() ->
        new RuntimeException("Review not found with id: " + reviewId)
      );

    if (!review.getLikedBy().contains(username)) {
      review.getLikedBy().add(username);
      review.setLikes(review.getLikes() + 1);
      rr.save(review);
    } else {
      throw new RuntimeException(
        "User '" + username + "' has already liked this review."
      );
    }
  }

  public void removeLike(String reviewId, String username) {
    Review review = rr
      .findById(reviewId)
      .orElseThrow(() ->
        new RuntimeException("Review not found with id: " + reviewId)
      );

    if (review.getLikedBy().contains(username)) {
      review.getLikedBy().remove(username);
      review.setLikes(review.getLikes() - 1);
      rr.save(review);
    } else {
      throw new RuntimeException(
        "User '" + username + "' has not liked this review."
      );
    }
  }

  public void deleteReviewById(String reviewId) {
    rr.deleteReviewById(reviewId);
    messagingTemplate.convertAndSend("/topic/reviews", "Review deleted!");
  }
}
