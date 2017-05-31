package co.hodler.boundaries;

import co.hodler.actions.Recordings;
import co.hodler.models.Recording;
import co.hodler.models.Request;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class FileSystemRecordings implements Recordings {

  @Override
  public boolean hasRecorded(Request request) {
    return false;
  }

  @Override
  public Recording get(Request request) {
    return null;
  }

  @Override
  public void store(Recording recording) {
    try {
      new File("recordings.json").createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException("File to store the recordings could not be created");
    }
  }

  @Override
  public void erase() {

  }

  @Override
  public Set<Request> allRecordedRequests() {
    return null;
  }
}
