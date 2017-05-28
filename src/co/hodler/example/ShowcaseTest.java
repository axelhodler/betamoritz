package co.hodler.example;

import co.hodler.actions.Recorder;
import co.hodler.boundaries.DefaultHttpGateway;
import co.hodler.boundaries.ExecuteHttpRequest;
import co.hodler.boundaries.ServeRecordings;
import co.hodler.mocks.InMemoryRecordings;
import co.hodler.models.Request;
import co.hodler.models.URL;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ShowcaseTest {
  @Test
  public void show() throws Exception {
    /**
     * Record -> Real request (during our tests our own server)
     *
     * Replay -> Proxy -> return recording
     * The http lib implementation of the consumer needs to provide a way of setting a proxy
     */
    InMemoryRecordings inMemoryRecordings = new InMemoryRecordings();
    DefaultHttpGateway httpGateway = new DefaultHttpGateway();
    Recorder recorder = new Recorder(httpGateway, inMemoryRecordings);
    FakeThirdPartyService fakeThirdPartyService = new FakeThirdPartyService();
    fakeThirdPartyService.startOnPort(8041);
    int proxyPort = 8042;
    ServeRecordings serveRecordings = new ServeRecordings(recorder);
    serveRecordings.startOnPort(proxyPort);

    String endpoint = "http://localhost:8041/beers";
    recorder.record(new Request("GET", new URL(endpoint)));
    String content = new ExecuteHttpRequest(
      new Request("GET", new URL(endpoint)))
      .executeThroughProxyOnPort(proxyPort);

    assertThat(content, is("someBeers"));

    serveRecordings.stop();
    fakeThirdPartyService.stop();
  }

}
