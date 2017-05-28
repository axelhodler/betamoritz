package co.hodler.boundaries;

import co.hodler.models.Request;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

public class ExecuteHttpRequest {
  private Request request;

  public ExecuteHttpRequest(Request request) {
    this.request = request;
  }

  public String execute() {
    try {
      URL obj = new URL(request.getUrl().value());
      HttpURLConnection con = (HttpURLConnection) obj.openConnection();
      return readResponse(con);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  public String executeThroughProxyOnPort(int proxyPort) {
    try {
      Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("localhost", proxyPort));
      URL obj = new URL(request.getUrl().value());
      HttpURLConnection con = (HttpURLConnection) obj.openConnection(proxy);
      return readResponse(con);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  private String readResponse(HttpURLConnection con) throws Exception {
    con.setRequestMethod(request.getMethod());

    try (BufferedReader in = new BufferedReader(
      new InputStreamReader(con.getInputStream()))) {
      String inputLine;
      StringBuffer response = new StringBuffer();

      while ((inputLine = in.readLine()) != null) {
        response.append(inputLine);
      }

      return response.toString();
    }
  }
}
