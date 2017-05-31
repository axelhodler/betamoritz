package co.hodler.boundaries;

import co.hodler.models.Recording;
import co.hodler.models.Request;
import co.hodler.models.URL;
import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonValue;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class FileSystemRecordingsTest {
  FileSystemRecordings fileSystemRecordings;

  @Before
  public void setUp() {
    fileSystemRecordings = new FileSystemRecordings();
  }

  @Test
  public void persistsContentInJsonStructure() throws IOException {
    fileSystemRecordings.store(
      new Recording(
        new Request("GET",
          new URL("http://example.org")), "{ \"content\": \"content\" }"));

    byte[] bytes = Files.readAllBytes(Paths.get("recordings.json"));
    String s = new String(bytes, Charset.forName("UTF-8"));

    JsonValue value = Json.parse(s);

    assertThat(value.asObject().get("http://example.org").asObject().get("GET").asObject()
      .get("content").asString(), is("content"));
  }
}
