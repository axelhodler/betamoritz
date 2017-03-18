package co.hodler.actions;

import co.hodler.mocks.HttpGatewayStub;
import co.hodler.models.Request;
import co.hodler.models.URL;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class RecorderTest {
  private Recorder r;
  private HttpGatewayStub httpGatewayStub;

  @Before
  public void initialize() {
    httpGatewayStub = new HttpGatewayStub();
    r = new Recorder(httpGatewayStub);
  }

  @Test
  public void canRecordMultipleRequests() {
    URL url = new URL("http://bar.org");
    httpGatewayStub.respondWith = "anotherResponse";
    r.record(new Request("GET", url));
    httpGatewayStub.respondWith = "response";
    r.record(new Request("DELETE", url));

    assertThat(r.replay(new Request("GET", url)), is("anotherResponse"));
  }

  @Test
  public void responseComesFromExternalService() {
    httpGatewayStub.respondWith = "stubbedResponse";
    URL url = new URL("http://bar.org");
    r.record(new Request("GET", url));

    assertThat(r.replay(new Request("GET", url)), is("stubbedResponse"));
  }

}
