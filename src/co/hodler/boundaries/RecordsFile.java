package co.hodler.boundaries;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RecordsFile {
  private String fileName;

  public RecordsFile(String fileName) {
    this.fileName = fileName;
  }

  public String readContents() {
    try {
      return new String(Files.readAllBytes(Paths.get(this.fileName)), "UTF-8");
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  public void replaceContentsWith(String contents) {
    try {
      try (FileWriter writer = new FileWriter(this.fileName)) {
        writer.write(contents);
      }
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  public boolean exists() {
    return Files.isRegularFile(Paths.get(this.fileName));
  }
}
