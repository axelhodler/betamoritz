import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class RecorderTest {
  @Test
  public void recordsRequests() {
    Recorder r = new Recorder();
    r.record("http://foo.org", "response");
    assertThat(r.replay("http://foo.org"), is("response"));
  }

  private class Recorder {
    public void record(String url, String response) {
    }

    public String replay(String s) {
      return "response";
    }
  }
}
