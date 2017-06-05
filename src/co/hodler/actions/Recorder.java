package co.hodler.actions;

import co.hodler.boundaries.http.HttpGateway;
import co.hodler.models.Recording;
import co.hodler.models.Request;

public class Recorder {
  private HttpGateway httpGateway;
  private Recordings recordings;

  public Recorder(HttpGateway httpGateway, Recordings recordings) {
    this.httpGateway = httpGateway;
    this.recordings = recordings;
  }

  public String replay(Request request) {
    if (!recordings.hasRecorded(request)) {
      throw new RuntimeException("The request you are trying to replay was never recorded");
    }
    return recordings.get(request).getContent();
  }

  public void record(Request request) {
    recordings.store(new Recording(request, httpGateway.execute(request)));
  }

  public void update() {
    recordings.allRecordedRequests()
      .stream()
      .forEach(e -> recordings.store(new Recording(e, httpGateway.execute(e))));
  }
}
