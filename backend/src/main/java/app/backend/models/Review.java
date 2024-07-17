package app.backend.models;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reviews")
public class Review {

  @Id
  private String id;

  private String username;
  private String profileUrl;
  // private User user;

  private String reviewUrl;
  // private List<String> reviewUrls;

  private String review;

  private String location;
  private String parkCode;
  private int likes;
  private List<String> likedBy;

  // public Review() {}

    public Review() {
        this.likedBy = new ArrayList<>();
    }

  public Review(
    String id,
    String username,
    String profileUrl,
    String reviewUrl,
    String review,
    String location,
    String parkCode,
    int likes,
    List<String> likedBy
  ) {
    this.id = id;
    this.username = username;
    this.profileUrl = profileUrl;
    this.reviewUrl = reviewUrl;
    this.review = review;
    this.location = location;
    this.parkCode = parkCode;
    this.likes = likes;
    this.likedBy = likedBy;
  }

  public int getLikes() {
    return this.likes;
  }

  public void setLikes(int likes) {
    this.likes = likes;
  }

  public List<String> getLikedBy() {
    return this.likedBy;
  }

  public void setLikedBy(List<String> likedBy) {
    this.likedBy = likedBy;
  }

  public String getParkCode() {
    return this.parkCode;
  }

  public void setParkCode(String parkCode) {
    this.parkCode = parkCode;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getProfileUrl() {
    return this.profileUrl;
  }

  public void setProfileUrl(String profileUrl) {
    this.profileUrl = profileUrl;
  }

  public String getReviewUrl() {
    return this.reviewUrl;
  }

  public void setReviewUrl(String reviewUrl) {
    this.reviewUrl = reviewUrl;
  }

  public String getReview() {
    return this.review;
  }

  public void setReview(String review) {
    this.review = review;
  }

  public String getLocation() {
    return this.location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public void addLike(String username) {
    if (!likedBy.contains(username)) {
        likedBy.add(username);
        likes++;
    }
}

public void removeLike(String username) {
    if (likedBy.contains(username)) {
        likedBy.remove(username);
        likes--;
    }
}
}
