package co.hodler.boundaries;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class FileSystemAccessTest {
  FileSystemAccess fileSystem;

  @Before
  public void setUp() {
    fileSystem = new FileSystemAccess();
  }

  @Test
  public void can_read_file_as_string() throws IOException {
    fileSystem.writeToFile("recordings.json", "Hello");

    assertThat(fileSystem.fileAsString("recordings.json"), is("Hello"));

    cleanUpFile("recordings.json");
  }

  @Test
  public void can_write_file_to_disk() throws IOException {
    fileSystem.writeToFile("records.json", "foo");

    assertThat(fileSystem.fileAsString("records.json"), is("foo"));

    cleanUpFile("records.json");
  }

  @Test
  public void knows_which_files_exist() throws IOException {
    fileSystem.writeToFile("records.json", "foo");

    assertThat(fileSystem.fileExists("records.json"), is(true));

    cleanUpFile("records.json");
  }

  @Test
  public void knows_which_files_do_not_exist() {
    assertThat(fileSystem.fileExists("doesntexist.json"), is(false));
  }

  private void cleanUpFile(String fileName) throws IOException {
    Files.delete(Paths.get(fileName));
  }
}
