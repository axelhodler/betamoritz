package co.hodler.boundaries;

import co.hodler.models.Request;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DefaultHttpGateway implements HttpGateway {
  @Override
  public String execute(Request request) {
    try {
      String requestUrl = request.getUrl().value();
      URL obj = new URL(requestUrl);
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
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException();
    }
  }
}
