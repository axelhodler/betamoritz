package co.hodler.boundaries;

import co.hodler.actions.Recordings;
import co.hodler.models.Recording;
import co.hodler.models.Request;
import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.util.Set;

public class JsonRecordings implements Recordings {

  private RecordsFile fileSystemAccess;

  public JsonRecordings(RecordsFile fileSystemAccess) {
    this.fileSystemAccess = fileSystemAccess;
  }

  public boolean hasRecorded(Request request) {
    return false;
  }

  @Override
  public Recording get(Request request) {
    return null;
  }

  @Override
  public void store(Recording recording) {
    JsonValue content = Json.parse(recording.getContent());
    JsonObject storage = new JsonObject();
    if (fileSystemAccess.exists()) {
      String recordingContents = fileSystemAccess.readContents();
      storage = Json.parse(recordingContents).asObject();
    }
    String requestUrl = recording.getRequest().getUrl().value();
    if (storage.get(requestUrl) == null) {
      JsonObject method = new JsonObject();
      method.set(recording.getRequest().getMethod(), content);
      storage.set(requestUrl, method);
    } else {
      storage.get(requestUrl).asObject().set(recording.getRequest().getMethod(), content);
    }
    fileSystemAccess.replaceContentsWith(storage.toString());
  }

  @Override
  public void erase() {

  }

  @Override
  public Set<Request> allRecordedRequests() {
    return null;
  }
}
