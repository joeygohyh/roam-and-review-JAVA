package app.backend.payload.response;

// data transfer object to encapsulate message response from server to client (success/error message back to client)
public class MessageResponse {

  private String message;

  public MessageResponse(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
