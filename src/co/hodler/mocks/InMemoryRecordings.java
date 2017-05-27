package co.hodler.mocks;

import co.hodler.actions.Recordings;
import co.hodler.models.Recording;
import co.hodler.models.Request;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class InMemoryRecordings implements Recordings {
  private Map<Request, Recording> recordings = new HashMap<>();

  @Override
  public boolean hasRecorded(Request request) {
    return recordings.containsKey(request);
  }

  @Override
  public Recording get(Request request) {
    return recordings.get(request);
  }

  @Override
  public void store(Recording recording) {
    recordings.put(recording.getRequest(), recording);
  }

  @Override
  public void erase() {
    recordings.clear();
  }

  @Override
  public Set<Request> allRecordedRequests() {
    return recordings.keySet();
  }

}
