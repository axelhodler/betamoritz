package co.hodler.boundaries;

import co.hodler.models.Recording;
import co.hodler.models.Request;
import co.hodler.models.URL;
import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonValue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class JsonRecordingTest {
  JsonRecordings jsonRecordings;

  private final String RECORDS_FILE = "recordings.json";

  @Before
  public void set_up() {
    jsonRecordings = new JsonRecordings(new RecordsFile(RECORDS_FILE));
  }

  @After
  public void clean_up() throws IOException {
    cleanUpRecordings();
  }

  @Test
  public void persists_content_in_json_structure() throws IOException {
    jsonRecordings.store(
      new Recording(
        new Request("GET",
          new URL("http://example.org")), "{ \"content\": \"content\" }"));

    JsonValue storage = readStorage();

    assertThat(storage.asObject().get("http://example.org").asObject().get("GET").asObject()
      .get("content").asString(), is("content"));
  }

  @Test
  public void persists_content_with_request_meta_infos() throws IOException {
    jsonRecordings.store(
      new Recording(
        new Request("POST",
          new URL("http://foo.org")), "{ \"bar\": \"foo\" }"));

    JsonValue storage = readStorage();

    assertThat(storage.asObject().get("http://foo.org").asObject().get("POST").asObject()
      .get("bar").asString(), is("foo"));
  }

  @Test
  public void can_persist_multiple_requests() throws IOException {
    jsonRecordings.store(
      new Recording(
        new Request("POST",
          new URL("http://foo.org")), "{ \"bar\": \"foo\" }"));
    jsonRecordings.store(
      new Recording(
        new Request("DELETE",
          new URL("http://foos.org")), "{ \"response\": \"deleted\" }"));

    JsonValue storage = readStorage();

    assertThat(storage.asObject().get("http://foo.org").asObject().get("POST").asObject()
      .get("bar").asString(), is("foo"));
    assertThat(storage.asObject().get("http://foos.org").asObject().get("DELETE").asObject()
      .get("response").asString(), is("deleted"));
  }

  @Test
  public void can_persist_multiple_methods_on_same_request() throws IOException {
    jsonRecordings.store(
      new Recording(
        new Request("POST",
          new URL("http://foo.org")), "{ \"bar\": \"foo\" }"));
    jsonRecordings.store(
      new Recording(
        new Request("GET",
          new URL("http://foo.org")), "{ \"response\": \"deleted\" }"));

    JsonValue storage = readStorage();

    assertThat(storage.asObject().get("http://foo.org").asObject().get("POST").asObject()
      .get("bar").asString(), is("foo"));
    assertThat(storage.asObject().get("http://foo.org").asObject().get("GET").asObject()
      .get("response").asString(), is("deleted"));
  }

  private JsonValue readStorage() throws IOException {
    return Json.parse(new FileReader("recordings.json"));
  }

  private void cleanUpRecordings() throws IOException {
    Files.delete(Paths.get(RECORDS_FILE));
  }
}
