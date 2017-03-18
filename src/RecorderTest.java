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
    r = new Recorder(null);
  }

  @Test
  public void canRecordMultipleRequests() {
    URL url = new URL("http://bar.org");
    r.record(new Request("GET", url), "anotherResponse");
    r.record(new Request("DELETE", url), "response");
    assertThat(r.replay(new Request("GET", url)), is("anotherResponse"));
  }

  @Test
  public void responseComesFromExternalService() {
    HttpGatewayStub httpGatewayStub = new HttpGatewayStub();
    r = new Recorder(httpGatewayStub);
    URL url = new URL("http://bar.org");
    r.record(new Request("GET", url));
    assertThat(r.replay(new Request("GET", url)), is("stubbedResponse"));
  }

  private class Recorder {
    private Map<Request, String> recordings = new HashMap<>();
    private HttpGatewayStub httpGatewayStub;

    public Recorder(HttpGatewayStub httpGatewayStub) {
      this.httpGatewayStub = httpGatewayStub;
    }

    public void record(Request request, String response) {
      recordings.put(request, response);
    }

    public String replay(Request request) {
      return recordings.get(request);
    }

    public void record(Request get) {
      recordings.put(get, httpGatewayStub.get(get));
    }
  }

  private class HttpGatewayStub {
    public String get(Request r) {
      return "stubbedResponse";
    }
  }
}
