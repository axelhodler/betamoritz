package co.hodler.boundaries.http;

import co.hodler.models.Request;

public interface HttpGateway {
  String execute(Request response);
}
