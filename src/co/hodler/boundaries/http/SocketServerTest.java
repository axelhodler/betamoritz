package co.hodler.boundaries.http;

import org.junit.After;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class SocketServerTest {

  private SocketServer socketServer;

  @After
  public void clean_up() throws Exception {
    socketServer.stop();
  }

  @Test
  public void free_the_port_when_stopping() throws Exception {
    socketServer = new SocketServer();
    socketServer.start(4444, (s) -> {
    });
    socketServer.stop();
    socketServer.start(4444, (s) -> {
    });
  }

  @Test
  public void provides_response_body() throws Exception {
    String content = "<html><body><p>Hello World</p></body></html>";
    socketServer = new SocketServer();
    socketServer.start(4444, (s) -> {
      fakeSocketService(content, s);
    });
    URL localhost = new URL("http://localhost:4444/");
    URLConnection con = localhost.openConnection();
    String response = readResponseBody(con);

    assertThat(response, containsString("Hello World"));
  }

  @Test
  public void is_not_hardcoding_content_length() throws Exception {
    String content = "<html><body><p>Hallo Welt</p></body></html>";
    socketServer = new SocketServer();
    socketServer.start(4444, (s) -> {
      fakeSocketService(content, s);
    });
    URL localhost = new URL("http://localhost:4444/");
    URLConnection con = localhost.openConnection();
    String response = readResponseBody(con);

    assertThat(response, containsString("Hallo Welt"));
  }

  private String readResponseBody(URLConnection con) throws IOException {
    String response = "";
    try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
      response = in.lines().collect(Collectors.joining("\n"));
    }
    return response;
  }

  private void fakeSocketService(String content, Socket s) {
    try (
      PrintWriter out = new PrintWriter(s.getOutputStream());
    ) {
      out.println("HTTP/1.1 200 OK");
      out.println("Content-Length: " + content.length() + "\n");
      out.println(content);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
