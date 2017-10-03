package co.hodler.boundaries.storage;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class RecordsFileTest {
  private final String RECORDING_FILE = "recordings.json";

  RecordsFile fileSystem;
  @Before
  public void setUp() {
    fileSystem = new RecordsFile(RECORDING_FILE);
  }

  @Test
  public void can_read_file_as_string() throws IOException {
    fileSystem.replaceContentsWith("Hello");

    assertThat(fileSystem.readContents(), is("Hello"));

    cleanUpFile();
  }

  @Test
  public void can_write_file_to_disk() throws IOException {
    fileSystem.replaceContentsWith("foo");

    assertThat(fileSystem.readContents(), is("foo"));

    cleanUpFile();
  }

  @Test
  public void knows_which_files_exist() throws IOException {
    fileSystem.replaceContentsWith("foo");

    assertThat(fileSystem.exists(), is(true));

    cleanUpFile();
  }

  @Test
  public void knows_which_files_do_not_exist() {
    assertThat(fileSystem.exists(), is(false));
  }

  private void cleanUpFile() throws IOException {
    Files.delete(Paths.get(RECORDING_FILE));
  }
}
