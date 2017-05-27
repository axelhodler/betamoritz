package co.hodler.models;

public class Recording {
  private Request request;
  private String content;

  public Recording(Request request, String content) {
    this.request = request;
    this.content = content;
  }

  public Request getRequest() {
    return request;
  }

  public String getContent() {
    return content;
  }
}
