package co.hodler.boundaries;

import co.hodler.actions.Recordings;
import co.hodler.models.Recording;
import co.hodler.models.Request;
import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

public class JsonRecordings implements Recordings {

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
      try (FileWriter fileWriter = new FileWriter("recordings.json")) {
        JsonValue content = Json.parse(recording.getContent());
        JsonObject method = new JsonObject();
        method.set(recording.getRequest().getMethod(), content);
        JsonObject request = new JsonObject();
        request.set(recording.getRequest().getUrl().value(), method);
        request.writeTo(fileWriter);
      };
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
