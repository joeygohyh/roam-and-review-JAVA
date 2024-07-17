package app.backend.repo;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public class S3Repo {

  @Autowired
  private AmazonS3 s3;

  //Upload to S3
  public String upload(MultipartFile file) throws IOException {
    // user metadata creation > map to store custom metadata for the file
    Map<String, String> userData = new HashMap<>();
    userData.put("upload-timestamp", (new Date()).toString());
    //metadata for the file
    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentType(file.getContentType());
    metadata.setContentLength(file.getSize());
    metadata.setUserMetadata(userData);

    // generating unique key for the file
    String key = UUID.randomUUID().toString().substring(0, 8);
    // PutObjectRequest, specifying the details of the upload
    //take 4 parameter (bucketname, keyname | filename, inputstream, metadata)
    PutObjectRequest putReq = new PutObjectRequest(
      "tfip-project",
      key,
      file.getInputStream(),
      metadata
    );

    // setting access control for the object
    // make object publically available (ppl without key also can access)
    putReq.withCannedAcl(CannedAccessControlList.PublicRead);

    // uploading the file
    // PutObjectResult result = s3.putObject(putReq);
    s3.putObject(putReq);

    // returning the URL
    return s3.getUrl("tfip-project", key).toString();
  }
}
