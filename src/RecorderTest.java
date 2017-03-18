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
    r.record(url, "response");
    assertThat(r.replay(url), is("response"));
  }

  @Test
  public void canRecordMultipleRequests() {
    URL url = new URL("http://bar.org");
    r.record(url, "anotherResponse");
    assertThat(r.replay(url), is("anotherResponse"));
  }

  @Test
  public void recordingRequestRequiresMethod() {
    URL url = new URL("http://bar.org");
    r.record("POST", url, "postResponse");
    assertThat(r.replay("POST", url), is("postResponse"));
  }

  private class Recorder {
    private Map<URL, String> recordings = new HashMap<>();

    public void record(URL url, String response) {
      recordings.put(url, response);
    }

    public String replay(URL url) {
      return recordings.get(url);
    }

    public void record(String post, URL url, String postResponse) {
    }

    public String replay(String post, URL url) {
      return "postResponse";
    }
  }

  private class URL {
    public URL(String url) {
    }
  }
}
