package co.hodler.boundaries;

import co.hodler.models.Recording;
import co.hodler.models.Request;
import co.hodler.models.URL;
import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonValue;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class JsonRecordingTest {
  JsonRecordings jsonRecordings;

  @Before
  public void setUp() {
    jsonRecordings = new JsonRecordings();
  }

  @Test
  public void persistsContentInJsonStructure() throws IOException {
    jsonRecordings.store(
      new Recording(
        new Request("GET",
          new URL("http://example.org")), "{ \"content\": \"content\" }"));

    JsonValue value = Json.parse(new FileReader("recordings.json"));

    assertThat(value.asObject().get("http://example.org").asObject().get("GET").asObject()
      .get("content").asString(), is("content"));
  }
}
