package co.hodler.boundaries;

import co.hodler.models.Request;

public interface HttpGateway {
  String execute(Request response);
}
