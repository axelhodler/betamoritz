package co.hodler.boundaries.http;

import java.net.Socket;

public interface SocketService {
  void serve(Socket s);
}
