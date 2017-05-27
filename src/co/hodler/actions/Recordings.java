package co.hodler.actions;

import co.hodler.models.Recording;
import co.hodler.models.Request;

import java.util.Set;

public interface Recordings {

  boolean hasRecorded(Request request);

  Recording get(Request request);

  void store(Recording recording);

  void erase();

  Set<Request> allRecordedRequests();

}
