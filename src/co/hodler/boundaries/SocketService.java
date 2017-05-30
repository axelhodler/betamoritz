package co.hodler.boundaries;

import java.net.Socket;

public interface SocketService {
  void serve(Socket s);
}
