package co.hodler.boundaries;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class FileSystemAccessTest {
  FileSystemAccess fileSystem;

  @Before
  public void setUp() {
    fileSystem = new FileSystemAccess();
  }

  @Test
  public void can_read_file_as_string() {
    fileSystem.writeToFile("recordings.json", "Hello");

    assertThat(fileSystem.fileAsString("recordings.json"), is("Hello"));
  }

  @Test
  public void can_write_file_to_disk() {
    fileSystem.writeToFile("records.json", "foo");

    assertThat(fileSystem.fileAsString("records.json"), is("foo"));
  }
}
