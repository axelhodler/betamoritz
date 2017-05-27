package co.hodler.e2e;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class SocketServerIntegrationTest {
  @Test
  public void servesStubbedResponses() throws Exception {
    SocketServer s = new SocketServer();
    s.start(8042, (clientSocket) -> {
      try (
        BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
      ) {
        String response = "Hello";
        out.println("HTTP/1.1 200 OK");
        out.println("Content-Length: " + response.length() + "\n");
        out.println(response);
      } catch (IOException e) {
      }
    });
    assertThat(getRequest("http://localhost:8042"), is("Hello"));
    s.stop();
  }

  private String getRequest(String url) throws IOException {
    URL obj = new URL(url);
    HttpURLConnection con = (HttpURLConnection) obj.openConnection();
    con.setRequestMethod("GET");

    int responseCode = con.getResponseCode();

    BufferedReader in = new BufferedReader(
      new InputStreamReader(con.getInputStream()));
    String inputLine;
    StringBuffer response = new StringBuffer();

    while ((inputLine = in.readLine()) != null) {
      response.append(inputLine);
    }
    in.close();

    return response.toString();
  }
}
