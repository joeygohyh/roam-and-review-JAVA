package app.backend.models;


public class Image extends FullPark {

  private String title;
  private String altText;
  private String caption;
  private String url;

  public Image() {}

  public Image(String title, String altText, String caption, String url) {
    this.title = title;
    this.altText = altText;
    this.caption = caption;
    this.url = url;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAltText() {
    return this.altText;
  }

  public void setAltText(String altText) {
    this.altText = altText;
  }

  public String getCaption() {
    return this.caption;
  }

  public void setCaption(String caption) {
    this.caption = caption;
  }

  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
