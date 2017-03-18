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

  private class Recorder {
    private Map<URL, String> recordings = new HashMap<>();

    public void record(URL url, String response) {
      recordings.put(url, response);
    }

    public String replay(URL url) {
      return recordings.get(url);
    }
  }

  private class URL {
    public URL(String url) {
    }
  }
}
