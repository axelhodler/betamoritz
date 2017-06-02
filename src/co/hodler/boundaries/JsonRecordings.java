package co.hodler.boundaries;

import co.hodler.actions.Recordings;
import co.hodler.models.Recording;
import co.hodler.models.Request;
import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.util.Set;

public class JsonRecordings implements Recordings {

  private FileSystemAccess fileSystemAccess;

  public JsonRecordings(FileSystemAccess fileSystemAccess) {
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
    JsonObject method = new JsonObject();
    method.set(recording.getRequest().getMethod(), content);
    JsonObject request = new JsonObject();
    request.set(recording.getRequest().getUrl().value(), method);
    fileSystemAccess.writeToFile("recordings.json", request.toString());
  }

  @Override
  public void erase() {

  }

  @Override
  public Set<Request> allRecordedRequests() {
    return null;
  }
}
