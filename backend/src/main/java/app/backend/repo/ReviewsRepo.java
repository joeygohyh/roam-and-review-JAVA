package app.backend.repo;

import app.backend.models.Review;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewsRepo {

  @Autowired
  MongoTemplate mt;

  //////////////////////
  // CREATE - INSERT/SAVE
  //////////////////////

  public String addReview(Review review) {
    //
    mt.insert(review);
    String id = review.getId();
    // System.out.println("REVIEW ID (PK):" + id);
    return id;
  }

  //////////////////////
  // READ - FIND
  //////////////////////

  public Optional<List<Review>> getAllReviews() {
    List<Review> result = mt.findAll(Review.class);
    return result.isEmpty() ? Optional.empty() : Optional.of(result);
  }

  // Method to get reviews by park code
  public List<Review> getReviewsByParkCode(String parkCode) {
    Query query = new Query(Criteria.where("parkCode").is(parkCode));
    return mt.find(query, Review.class);
  }

  // Method to get reviews by park code
  public List<Review> getReviewsByUsername(String username) {
    Query query = new Query(Criteria.where("username").is(username));
    return mt.find(query, Review.class);
  }

  // Method to add a like to a review
  public void addLike(String id, String username) {
    Query query = new Query(Criteria.where("_id").is(id));
    Update update = new Update().addToSet("likedBy", username).inc("likes", 1);
    mt.updateFirst(query, update, Review.class);
  }

  // Method to remove a like from a review
  public void removeLike(String id, String username) {
    Query query = new Query(Criteria.where("_id").is(id));
    Update update = new Update().pull("likedBy", username).inc("likes", -1);
    mt.updateFirst(query, update, Review.class);
  }

  // Method to find a review by ID
  public Optional<Review> findById(String id) {
    Query query = new Query(Criteria.where("_id").is(id));
    Review review = mt.findOne(query, Review.class);
    return Optional.ofNullable(review);
  }

  public void save(Review review) {
    mt.save(review);
  }

  // Method to delete a review by ID
  public void deleteReviewById(String id) {
    Query query = new Query(Criteria.where("_id").is(id));
    mt.remove(query, Review.class);
  }
}
