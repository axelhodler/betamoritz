import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class RecorderTest {
  private Recorder r;

  @Before
  public void initialize() {
    r = new Recorder();
  }

  @Test
  public void canRecordMultipleRequests() {
    URL url = new URL("http://bar.org");
    r.record(new Request("GET", url), "anotherResponse");
    r.record(new Request("DELETE", url), "response");
    assertThat(r.replay(new Request("GET", url)), is("anotherResponse"));
  }

  private class Recorder {
    private Map<Request, String> recordings = new HashMap<>();

    public void record(Request request, String response) {
      recordings.put(request, response);
    }

    public String replay(Request request) {
      return recordings.get(request);
    }
  }

  private class URL {
    public URL(String url) {
    }
  }

  private class Request {
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
}
