package co.hodler.actions;

import co.hodler.boundaries.HttpGateway;
import co.hodler.models.Request;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Recorder {
  private Map<Request, String> recordings = new HashMap<>();
  private HttpGateway httpGateway;

  public Recorder(HttpGateway httpGateway) {
    this.httpGateway = httpGateway;
  }

  public String replay(Request request) {
    if (!recordings.containsKey(request)) {
      throw new RuntimeException("The request you are trying to replay was never recorded");
    }
    return recordings.get(request);
  }

  public void record(Request request) {
    recordings.put(request, httpGateway.execute(request));
  }

  public void update() {
    recordings = recordings.entrySet()
      .stream()
      .collect(Collectors.toMap(
        e -> e.getKey(),
        e -> httpGateway.execute(e.getKey())
      ));
  }
}
