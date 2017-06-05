package co.hodler.boundaries.http;

public class ParsedRequest {
  private String method;
  private String path = "";

  public ParsedRequest(String request) {
    if (request != null) {
      String[] splitRequest = request.split(" ");
      if (splitRequest.length > 1) {
        this.method = splitRequest[0];
        this.path = splitRequest[1];
      }
    }
  }

  public String getPath() {
    return this.path;
  }

  public String getMethod() {
    return this.method;
  }
}
