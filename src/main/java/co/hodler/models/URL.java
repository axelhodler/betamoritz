package co.hodler.models;

public class URL {
  private String url;

  public URL(String url) {
    this.url = url;
  }

  public String value() {
    return this.url;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    URL url1 = (URL) o;

    return url != null ? url.equals(url1.url) : url1.url == null;
  }

  @Override
  public int hashCode() {
    return url != null ? url.hashCode() : 0;
  }
}
