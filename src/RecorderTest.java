import co.hodler.models.Request;
import co.hodler.models.URL;
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

}
