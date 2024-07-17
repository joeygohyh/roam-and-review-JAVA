package app.backend.controllers;

import app.backend.models.Activity;
import app.backend.models.Campground;
import app.backend.models.FullPark;
import app.backend.models.Park;
import app.backend.models.VisitorCentre;
import app.backend.services.APIService;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class APIController {

  @Autowired
  APIService apiService;

  @GetMapping("/search")
  public ResponseEntity<String> searchAPI(
    @RequestParam(required = true) String q
  ) {
    System.out.println("query >>>" + q);
    List<Park> parks = apiService.search(q);
    JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

    for (Park park : parks) {
      JsonObject parkJson = Json
        .createObjectBuilder()
        .add("id", park.getId())
        .add("url", park.getUrl())
        .add("fullName", park.getFullName())
        .add("parkCode", park.getParkCode())
        .add("description", park.getDescription())
        .add("image", park.getImage() != null ? park.getImage() : "")
        .build();
      jsonArrayBuilder.add(parkJson);
    }

    return ResponseEntity.ok(jsonArrayBuilder.build().toString());
  }

  @GetMapping("/visitor_centres")
  public ResponseEntity<List<VisitorCentre>> getVisitorCentres(
    @RequestParam String parkCode
  ) {
    List<VisitorCentre> visitorCentres = apiService.getAllVisitorCentres(
      parkCode
    );
    return ResponseEntity.ok(visitorCentres);
  }

  @GetMapping("/activities")
  public ResponseEntity<List<Activity>> getActivities(
    @RequestParam String parkCode
  ) {
    List<Activity> activities = apiService.getAllActivities(parkCode);
    return ResponseEntity.ok(activities);
  }

  @GetMapping("/campgrounds")
  public ResponseEntity<List<Campground>> getCampgrounds(
    @RequestParam String parkCode
  ) {
    List<Campground> campgrounds = apiService.getAllCampgrounds(parkCode);
    return ResponseEntity.ok(campgrounds);
  }

  @GetMapping("/park/{parkCode}")
  public ResponseEntity<FullPark> getParkAPI(@PathVariable String parkCode) {
    System.out.println("parkCode >>> " + parkCode);
    FullPark fullPark = apiService.getPark(parkCode);
    return ResponseEntity.ok(fullPark);
  }
}
