package app.backend.services;

import app.backend.models.Activity;
import app.backend.models.Address;
import app.backend.models.Campground;
import app.backend.models.FullPark;
import app.backend.models.Image;
import app.backend.models.OperatingHours;
import app.backend.models.Park;
import app.backend.models.VisitorCentre;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class APIService {

  // base URL for NPS API
  public static final String SEARCH_URL =
    "https://developer.nps.gov/api/v1/parks";

  public static final String VISITOR_URL =
    "https://developer.nps.gov/api/v1/visitorcenters";

  public static final String ACTIVITY_URL =
    "https://developer.nps.gov/api/v1/thingstodo";

  public static final String CAMPGROUNDS_URL =
    "https://developer.nps.gov/api/v1/campgrounds";

  @Value("${api.key}")
  private String apiKey;

  //////////////////

  // public method that takes a search query string 'q' and returns a list of park names as List<String>
  public List<Park> search(String q) {
    // construct URL to call
    UriComponentsBuilder builder = UriComponentsBuilder
      .fromUriString(SEARCH_URL) // initializes builder with base URL
      .queryParam("api_key", apiKey) // adds API key as query param
      .queryParam("limit", 500) // limits number of results to 20
      .queryParam("start", 0)
      .queryParam("sort", "-relevanceScore"); // starts results from first record
    // starts results from first record
    // .queryParam("sort", "-fullName"); // sorts results by fullname in descending order

    if (q != null && !q.isBlank()) {
      builder.queryParam("q", q);
    }

    String url = builder.build().toUriString();

    System.out.printf(">>> url %s\n", url);

    // make the GET request
    RequestEntity<Void> req = RequestEntity.get(url).build();

    // make the call (construct REST template object to make HTTP requests)
    RestTemplate template = new RestTemplate();
    ResponseEntity<String> resp = template.exchange(req, String.class);

    // Extract .data[]
    JsonReader reader = Json.createReader(new StringReader(resp.getBody()));
    JsonObject payload = reader.readObject();
    JsonArray dataArray = payload.getJsonArray("data");

    List<Park> parks = new ArrayList<>();
    for (JsonObject json : dataArray.getValuesAs(JsonObject.class)) {
      parks.add(parsePark(json));
    }

    return parks;
  }

  ////////////////////////

  // public method that takes a search query string 'q' and returns a list of park names as List<String>
  public FullPark getPark(String parkCode) {
    // construct URL to call
    String url = UriComponentsBuilder
      .fromUriString(SEARCH_URL) // initializes builder with base URL
      .queryParam("api_key", apiKey) // adds API key as query param
      .queryParam("parkCode", parkCode)
      .queryParam("limit", 1) // limits number of results to 20
      .queryParam("start", 0) // starts results from first record
      .build()
      .toUriString();

    System.out.printf(">>> url %s\n", url);

    // make the GET request
    RequestEntity<Void> req = RequestEntity.get(url).build();

    // make the call (construct REST template object to make HTTP requests)
    RestTemplate template = new RestTemplate();
    ResponseEntity<String> resp = template.exchange(req, String.class);

    // Extract .data[]
    JsonReader reader = Json.createReader(new StringReader(resp.getBody()));
    JsonObject payload = reader.readObject();
    JsonArray dataArray = payload.getJsonArray("data");

    FullPark fullPark = null;
    if (dataArray != null && !dataArray.isEmpty()) {
      JsonObject json = dataArray.getJsonObject(0);
      fullPark = parseFullPark(json);
    }

    return fullPark;
  }

  ////////////////////////

  public List<VisitorCentre> getAllVisitorCentres(String parkCode) {
    // construct URL to call
    String url = UriComponentsBuilder
      // .fromUriString(ACTIVITY_URL) // initializes builder with base URL
      .fromUriString(VISITOR_URL) // initializes builder with base URL
      .queryParam("api_key", apiKey) // adds API key as query param
      .queryParam("parkCode", parkCode)
      .queryParam("limit", 100) // limits number of results to 20
      .queryParam("start", 0) // starts results from first record
      .build()
      .toUriString();

    System.out.printf(">>> url %s\n", url);

    // make the GET request
    RequestEntity<Void> req = RequestEntity.get(url).build();

    // make the call (construct REST template object to make HTTP requests)
    RestTemplate template = new RestTemplate();
    ResponseEntity<String> resp = template.exchange(req, String.class);

    // Extract .data[]
    JsonReader reader = Json.createReader(new StringReader(resp.getBody()));
    JsonObject payload = reader.readObject();
    JsonArray dataArray = payload.getJsonArray("data");

    List<VisitorCentre> visitorCentres = new ArrayList<>();
    for (JsonObject json : dataArray.getValuesAs(JsonObject.class)) {
      visitorCentres.add(parseVisitorCentre(json));
    }

    return visitorCentres;
  }

  ////////////////////////

  public List<Activity> getAllActivities(String parkCode) {
    // construct URL to call
    String url = UriComponentsBuilder
      .fromUriString(ACTIVITY_URL) // initializes builder with base URL
      .queryParam("api_key", apiKey) // adds API key as query param
      .queryParam("parkCode", parkCode)
      // .queryParam("limit", 100) // limits number of results to 20
      // .queryParam("start", 0) // starts results from first record
      .build()
      .toUriString();

    System.out.printf(">>> url %s\n", url);

    // make the GET request
    RequestEntity<Void> req = RequestEntity.get(url).build();

    // make the call (construct REST template object to make HTTP requests)
    RestTemplate template = new RestTemplate();
    ResponseEntity<String> resp = template.exchange(req, String.class);

    // Extract .data[]
    JsonReader reader = Json.createReader(new StringReader(resp.getBody()));
    JsonObject payload = reader.readObject();
    JsonArray dataArray = payload.getJsonArray("data");

    List<Activity> activities = new ArrayList<>();
    for (JsonObject json : dataArray.getValuesAs(JsonObject.class)) {
      activities.add(parseActivity(json));
    }

    return activities;
  }

  /////////////////

  public List<Campground> getAllCampgrounds(String parkCode) {
    // construct URL to call
    String url = UriComponentsBuilder
      .fromUriString(CAMPGROUNDS_URL) // initializes builder with base URL
      .queryParam("api_key", apiKey) // adds API key as query param
      .queryParam("parkCode", parkCode)
      // .queryParam("limit", 100) // limits number of results to 20
      // .queryParam("start", 0) // starts results from first record
      .build()
      .toUriString();

    System.out.printf(">>> url %s\n", url);

    // make the GET request
    RequestEntity<Void> req = RequestEntity.get(url).build();

    // make the call (construct REST template object to make HTTP requests)
    RestTemplate template = new RestTemplate();
    ResponseEntity<String> resp = template.exchange(req, String.class);

    // Extract .data[]
    JsonReader reader = Json.createReader(new StringReader(resp.getBody()));
    JsonObject payload = reader.readObject();
    JsonArray dataArray = payload.getJsonArray("data");

    List<Campground> campgrounds = new ArrayList<>();
    for (JsonObject json : dataArray.getValuesAs(JsonObject.class)) {
      campgrounds.add(parseCampground(json));
    }

    return campgrounds;
  }

  private FullPark parseFullPark(JsonObject jo) {
    FullPark park = new FullPark();

    park.setUrl(jo.getString("url", ""));
    park.setFullName(jo.getString("fullName", ""));
    park.setParkCode(jo.getString("parkCode", ""));
    park.setDescription(jo.getString("description", ""));
    park.setLatitude(jo.getString("latitude", ""));
    park.setLongitude(jo.getString("longitude", ""));
    park.setLatLong(jo.getString("latLong", ""));

    // Handling OperatingHours
    JsonArray operatingHoursArray = jo.getJsonArray("operatingHours");
    if (operatingHoursArray != null && !operatingHoursArray.isEmpty()) {
      JsonObject operatingHoursJson = operatingHoursArray.getJsonObject(0);
      OperatingHours operatingHours = new OperatingHours();
      operatingHours.setDescription(
        operatingHoursJson.getString("description", "")
      );
      operatingHours.setMonday(
        operatingHoursJson
          .getJsonObject("standardHours")
          .getString("monday", "")
      );
      operatingHours.setTuesday(
        operatingHoursJson
          .getJsonObject("standardHours")
          .getString("tuesday", "")
      );
      operatingHours.setWednesday(
        operatingHoursJson
          .getJsonObject("standardHours")
          .getString("wednesday", "")
      );
      operatingHours.setThursday(
        operatingHoursJson
          .getJsonObject("standardHours")
          .getString("thursday", "")
      );
      operatingHours.setFriday(
        operatingHoursJson
          .getJsonObject("standardHours")
          .getString("friday", "")
      );
      operatingHours.setSaturday(
        operatingHoursJson
          .getJsonObject("standardHours")
          .getString("saturday", "")
      );
      operatingHours.setSunday(
        operatingHoursJson
          .getJsonObject("standardHours")
          .getString("sunday", "")
      );
      park.setOperatingHours(operatingHours);
    }

    // Handling Address
    JsonArray addressesArray = jo.getJsonArray("addresses");
    if (addressesArray != null && !addressesArray.isEmpty()) {
      JsonObject addressJson = addressesArray.getJsonObject(0);
      Address address = new Address();
      address.setPostalCode(addressJson.getString("postalCode", ""));
      address.setCity(addressJson.getString("city", ""));
      address.setStateCode(addressJson.getString("stateCode", ""));
      address.setCountryCode(addressJson.getString("countryCode", ""));
      // address.setProvinceTerritoryCode(
      //   addressJson.getString("provinceTerritorialCode")
      // );
      address.setLine1(addressJson.getString("line1", ""));
      address.setLine2(addressJson.getString("line2", ""));
      address.setLine3(addressJson.getString("line3", ""));
      park.setAddress(address);
    }

    // Handling Images
    JsonArray imagesArray = jo.getJsonArray("images");
    if (imagesArray != null) {
      List<Image> images = new ArrayList<>();
      for (JsonObject imageJson : imagesArray.getValuesAs(JsonObject.class)) {
        Image image = new Image();
        image.setTitle(imageJson.getString("title", ""));
        image.setAltText(imageJson.getString("altText", ""));
        image.setCaption(imageJson.getString("caption", ""));
        image.setUrl(imageJson.getString("url", ""));
        images.add(image);
      }
      park.setImages(images);
    }

    return park;
  }

  private Park parsePark(JsonObject json) {
    Park park = new Park();
    park.setId(json.getString("id"));
    park.setUrl(json.getString("url"));
    park.setFullName(json.getString("fullName"));
    park.setParkCode(json.getString("parkCode"));
    park.setDescription(json.getString("description"));

    // Parse the first image
    JsonArray imagesArray = json.getJsonArray("images");
    if (!imagesArray.isEmpty()) {
      JsonObject imageJson = imagesArray.getJsonObject(0);
      park.setImage(imageJson.getString("url"));
    } else {
      park.setImage(""); // or null, depending on your preference
    }

    return park;
  }

  //////

  private VisitorCentre parseVisitorCentre(JsonObject json) {
    VisitorCentre visitorCentre = new VisitorCentre();
    visitorCentre.setName(json.getString("name", ""));
    visitorCentre.setDescription(json.getString("description", ""));
    visitorCentre.setLatitude(json.getString("latitude", ""));
    visitorCentre.setLongitude(json.getString("longitude", ""));
    visitorCentre.setLatLong(json.getString("latLong", ""));


    String imageUrl = "";
    if (json.containsKey("images") && json.getJsonArray("images").size() > 0) {
      JsonObject firstImage = json.getJsonArray("images").getJsonObject(0);
      imageUrl = firstImage.getString("url", "");
    }
    visitorCentre.setImage(imageUrl);

    return visitorCentre;
  }

  private Activity parseActivity(JsonObject json) {
    Activity activity = new Activity();
    activity.setTitle(json.getString("title", ""));
    activity.setShortDescription(json.getString("shortDescription", ""));

 

    String imageUrl = "";
    if (json.containsKey("images") && json.getJsonArray("images").size() > 0) {
      JsonObject firstImage = json.getJsonArray("images").getJsonObject(0);
      imageUrl = firstImage.getString("url", "");
    }
    activity.setImage(imageUrl);

    return activity;
  }

  private Campground parseCampground(JsonObject json) {
    Campground campground = new Campground();
    campground.setName(json.getString("name", ""));
    campground.setDescription(json.getString("description", ""));
    campground.setLatitude(json.getString("latitude", ""));
    campground.setLongitude(json.getString("longitude", ""));
    campground.setLatLong(json.getString("latLong", ""));

    String imageUrl = "";
    if (json.containsKey("images") && json.getJsonArray("images").size() > 0) {
      JsonObject firstImage = json.getJsonArray("images").getJsonObject(0);
      imageUrl = firstImage.getString("url", "");
    }
    campground.setImage(imageUrl);

    return campground;
  }
}