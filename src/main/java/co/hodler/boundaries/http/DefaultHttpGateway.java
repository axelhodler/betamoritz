package co.hodler.boundaries.http;

import co.hodler.models.Request;

public class DefaultHttpGateway implements HttpGateway {
  @Override
  public String execute(Request request) {
    return new ExecuteHttpRequest(request).execute();
  }
}
