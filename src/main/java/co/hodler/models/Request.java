package co.hodler.models;

public class Request {
  private String method;
  private final URL url;

  public Request(String method, URL url) {
    this.method = method;
    this.url = url;
  }

  public URL getUrl() {
    return url;
  }

  public String getMethod() {
    return method;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Request request = (Request) o;

    if (method != null ? !method.equals(request.method) : request.method != null)
      return false;
    return url != null ? url.equals(request.url) : request.url == null;
  }

  @Override
  public int hashCode() {
    int result = method != null ? method.hashCode() : 0;
    result = 31 * result + (url != null ? url.hashCode() : 0);
    return result;
  }
}
