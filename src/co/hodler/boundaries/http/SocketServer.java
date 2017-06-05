package co.hodler.boundaries.http;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketServer {
  private boolean running = true;
  private ServerSocket serverSocket;
  private ExecutorService executorService;

  public void start(int port, SocketService s) throws Exception {
    executorService = Executors.newSingleThreadExecutor();
    serverSocket = new ServerSocket(port);

    executorService.execute(() -> {
      while (running) {
        try (Socket serviceSocket = serverSocket.accept()) {
          s.serve(serviceSocket);
        } catch (Exception e) {
        }
      }
    });
  }

  public void stop() throws Exception {
    this.running = false;
    serverSocket.close();
    executorService.shutdownNow();
  }
}
