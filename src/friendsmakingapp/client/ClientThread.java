package friendsmakingapp.client;

import friendsmakingapp.util.GameStateUpdate;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientThread extends Thread {
  private MainPanel mainPanel;
  private Socket socket;
  private ObjectInputStream input;

  public ClientThread(Socket s, MainPanel mainPanel) throws IOException {
    this.socket = s;
    this.input = new ObjectInputStream(socket.getInputStream());
    this.mainPanel = mainPanel;
  }

  @Override
  public void run() {
    try {
      while (true) {
        mainPanel.update((GameStateUpdate) input.readObject());
      }
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    } finally {
      try {
        input.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
