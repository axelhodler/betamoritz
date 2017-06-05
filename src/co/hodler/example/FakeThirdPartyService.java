package co.hodler.example;

import co.hodler.boundaries.http.SocketServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class FakeThirdPartyService {

  private SocketServer s;

  public void startOnPort(int port) throws Exception {
    s = new SocketServer();
    s.start(port, (clientSocket) -> {
      try (
        BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
      ) {
        String fakeResponse = "someBeers";
        out.println("HTTP/1.1 200 OK");
        out.println("Content-Length: " + fakeResponse.length() + "\n");
        out.println(fakeResponse);
      } catch (IOException e) {
      }
    });
  }

  public void stop() throws Exception {
    s.stop();
  }
}
