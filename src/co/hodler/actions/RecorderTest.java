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
  public void canRecordMultipleRequests() {
    URL url = new URL("http://bar.org");
    httpGatewayStub.respondWith = "anotherResponse";
    recorder.record(new Request("GET", url));
    httpGatewayStub.respondWith = "response";
    recorder.record(new Request("DELETE", url));

    assertThat(recorder.replay(new Request("GET", url)), is("anotherResponse"));
  }

}
