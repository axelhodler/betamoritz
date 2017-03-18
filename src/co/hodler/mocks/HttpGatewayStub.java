package co.hodler.mocks;

import co.hodler.boundaries.HttpGateway;
import co.hodler.models.Request;

public class HttpGatewayStub implements HttpGateway {
  public String respondWith;

  @Override
  public String execute(Request request) {
    return respondWith;
  }
}
