package co.hodler.boundaries;

import co.hodler.models.Recording;
import co.hodler.models.Request;
import co.hodler.models.URL;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class FileSystemRecordingsTest {
  @Test
  public void persistsFilesOnFileSystem() {
    FileSystemRecordings fileSystemRecordings = new FileSystemRecordings();
    fileSystemRecordings.store(
      new Recording(
        new Request("GET",
          new URL("http://example.org")), "content"));

    File file = new File("recordings.json");

    assertThat(file.isFile(), is(true));
  }
}
