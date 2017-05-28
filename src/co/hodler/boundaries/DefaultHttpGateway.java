package co.hodler.boundaries;

import co.hodler.models.Request;

public class DefaultHttpGateway implements HttpGateway {
  @Override
  public String execute(Request request) {
    return new ExecuteHttpRequest(request).execute();
  }
}
