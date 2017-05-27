package co.hodler.actions;

import co.hodler.mocks.HttpGatewayStub;
import co.hodler.models.Request;
import co.hodler.models.URL;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class RecorderTest {
  private Recorder recorder;
  private HttpGatewayStub httpGatewayStub;

  @Before
  public void initialize() {
    httpGatewayStub = new HttpGatewayStub();
    recorder = new Recorder(httpGatewayStub);
  }

  @Test
  public void records_multiple_requests() {
    URL url = new URL("http://bar.org");
    httpGatewayStub.respondWith = "anotherResponse";
    recorder.record(new Request("GET", url));
    httpGatewayStub.respondWith = "response";
    recorder.record(new Request("DELETE", url));

    assertThat(recorder.replay(new Request("GET", url)), is("anotherResponse"));
  }

  @Test(expected = RuntimeException.class)
  public void throws_runtime_exception_if_trying_to_replay_unrecorded_request() {
    Request unrecordedRequest = new Request("GET", new URL("http://WILLFAIL.com"));

    recorder.replay(unrecordedRequest);
  }

  @Test
  public void can_update_recordings() {
    URL url = new URL("http://beerapi.org/beers");
    httpGatewayStub.respondWith = "someBeers";
    recorder.record(new Request("GET", url));
    httpGatewayStub.respondWith = "updatedBeers";

    recorder.update();

    assertThat(recorder.replay(new Request("GET", url)), is("updatedBeers"));
  }
}
