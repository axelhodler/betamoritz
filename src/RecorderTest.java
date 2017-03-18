import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class RecorderTest {
  @Test
  public void recordsRequests() {
    Recorder r = new Recorder();
    r.record("http://foo.org", "response");
    assertThat(r.replay("http://foo.org"), is("response"));
  }

  @Test
  public void canRecordMultipleRequests() {
    Recorder r = new Recorder();
    r.record("http://bar.org", "anotherResponse");
    assertThat(r.replay("http://bar.org"), is("anotherResponse"));
  }

  private class Recorder {
    private Map<String, String> recordings = new HashMap<>();

    public void record(String url, String response) {
      recordings.put(url, response);
    }

    public String replay(String url) {
      return recordings.get(url);
    }
  }
}
