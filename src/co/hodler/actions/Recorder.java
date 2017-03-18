package co.hodler.actions;

import co.hodler.boundaries.HttpGateway;
import co.hodler.models.Request;

import java.util.HashMap;
import java.util.Map;

public class Recorder {
  private Map<Request, String> recordings = new HashMap<>();
  private HttpGateway httpGateway;

  public Recorder(HttpGateway httpGateway) {
    this.httpGateway = httpGateway;
  }

  public String replay(Request request) {
    return recordings.get(request);
  }

  public void record(Request get) {
    recordings.put(get, httpGateway.execute(get));
  }
}
