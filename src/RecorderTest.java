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
  public void recordsRequests() {
    URL url = new URL("http://foo.org");
    r.record(new Request("DELETE", url), "response");
    assertThat(r.replay(new Request("DELETE", url)), is("response"));
  }

  @Test
  public void canRecordMultipleRequests() {
    URL url = new URL("http://bar.org");
    r.record(new Request("GET", url), "anotherResponse");
    assertThat(r.replay(new Request("GET", url)), is("anotherResponse"));
  }

  @Test
  public void recordingRequestRequiresMethod() {
    URL url = new URL("http://bar.org");
    r.record(new Request("POST", url), "postResponse");
    assertThat(r.replay(new Request("POST", url)), is("postResponse"));
  }

  private class Recorder {
    private Map<URL, String> recordings = new HashMap<>();

    public void record(Request request, String response) {
      recordings.put(request.getUrl(), response);
    }

    public String replay(Request request) {
      return recordings.get(request.getUrl());
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
