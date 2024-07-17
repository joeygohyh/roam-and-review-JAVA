package app.backend.services;

import app.backend.models.User;
import app.backend.repo.S3Repo;
import app.backend.repo.UserRepo;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService {

  @Autowired
  UserRepo userRepository;

  @Autowired
  S3Repo s3Repo;

  public void addAccount(User user, MultipartFile profilePicture) {
    try {
      // Uploads profile image to S3, using S3Repo, retrieves URL of the uploaded file and sets the URL in the User object
      String profileUrl = s3Repo.upload(profilePicture);
      user.setProfileUrl(profileUrl);

      // Saves the User object to the database using UserRepo
      userRepository.save(user);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public User updateUsername(Long userId, String newUsername) {
    User user = userRepository
      .findById(userId)
      .orElseThrow(() ->
        new RuntimeException("User not found with id: " + userId)
      );

    user.setUsername(newUsername);
    return userRepository.save(user);
  }

  public User updatePassword(Long userId, String newPassword) {
    User user = userRepository
      .findById(userId)
      .orElseThrow(() ->
        new RuntimeException("User not found with id: " + userId)
      );

    user.setPassword(newPassword);
    return userRepository.save(user);
  }
}
