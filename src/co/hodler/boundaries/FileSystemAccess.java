package co.hodler.boundaries;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileSystemAccess {
  public String fileAsString(String fileName) {
    try {
      return new String(Files.readAllBytes(Paths.get(fileName)), "UTF-8");
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  public void writeToFile(String fileName, String contents) {
    try {
      try (FileWriter writer = new FileWriter(fileName)) {
        writer.write(contents);
      }
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }
}
