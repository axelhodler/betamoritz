package co.hodler.example;

import co.hodler.actions.Recorder;
import co.hodler.boundaries.DefaultHttpGateway;
import co.hodler.boundaries.ServeRecordings;
import co.hodler.mocks.InMemoryRecordings;
import co.hodler.models.Request;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

public class Showcase {
  public static void main(String args[]) throws Exception {
    /**
     * Record -> Real request (during our tests our own server)
     *
     * Replay -> Proxy -> return recording
     * The http lib implementation of the consumer needs to provide a way of setting a proxy
     */
    InMemoryRecordings inMemoryRecordings = new InMemoryRecordings();
    DefaultHttpGateway httpGateway = new DefaultHttpGateway();
    Recorder recorder = new Recorder(httpGateway, inMemoryRecordings);
    FakeThirdPartyService fakeThirdPartyService = new FakeThirdPartyService();
    fakeThirdPartyService.startOnPort(8041);
    ServeRecordings serveRecordings = new ServeRecordings(recorder);
    serveRecordings.startOnPort(8042);

    recorder.record(new Request("GET", new co.hodler.models.URL("http://localhost:8041/beers")));
    String content = requestViaProxy(8042, "http://localhost:8041/beers");

    System.out.println(content);

    serveRecordings.stop();
    fakeThirdPartyService.stop();
  }

  public static String requestViaProxy(int port, String url) throws Exception {
    URL obj = new URL(url);
    Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("localhost", port));
    HttpURLConnection con = (HttpURLConnection) obj.openConnection(proxy);
    con.setRequestMethod("GET");

    int responseCode = con.getResponseCode();
    System.out.println("\nSending 'GET' request to URL : " + url);
    System.out.println("Response Code : " + responseCode);

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
