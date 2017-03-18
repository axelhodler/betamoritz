package co.hodler.boundaries;

import co.hodler.models.Request;

public interface HttpGateway {
  public String execute(Request response);
}
