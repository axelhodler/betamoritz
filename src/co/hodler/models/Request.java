package co.hodler.models;

public class Request {
  private String post;
  private final URL url;

  public Request(String method, URL url) {
    this.post = method;
    this.url = url;
  }

  public URL getUrl() {
    return url;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Request request = (Request) o;

    if (post != null ? !post.equals(request.post) : request.post != null)
      return false;
    return url != null ? url.equals(request.url) : request.url == null;
  }

  @Override
  public int hashCode() {
    int result = post != null ? post.hashCode() : 0;
    result = 31 * result + (url != null ? url.hashCode() : 0);
    return result;
  }
}
