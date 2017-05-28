package co.hodler.boundaries;

import co.hodler.actions.Recorder;
import co.hodler.e2e.ParsedRequest;
import co.hodler.e2e.SocketServer;
import co.hodler.models.Request;
import co.hodler.models.URL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class ServeRecordings {
  private SocketServer s;
  private Recorder recorder;

  public ServeRecordings(Recorder recorder) {
    this.recorder = recorder;
  }

  public void startOnPort(int port) throws Exception {
    s = new SocketServer();
    s.start(port, (clientSocket) -> {
      try (
        BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
      ) {
        ParsedRequest parsedRequest = new ParsedRequest(reader.readLine());
        String response = recorder.replay(new Request("GET", new URL(parsedRequest.getPath())));
        out.println("HTTP/1.1 200 OK");
        out.println("Content-Length: " + response.length() + "\n");
        out.println(response);
      } catch (IOException e) {
      }
    });
  }

  public void stop() throws Exception {
    s.stop();
  }
}
