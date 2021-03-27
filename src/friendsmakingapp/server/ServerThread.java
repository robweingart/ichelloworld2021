package friendsmakingapp.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread extends Thread {

  public ObjectOutputStream output;
  public PlayerData data;
  private Socket socket;
  private GameSession session;
  private int index;

  public ServerThread(Socket socket) {
    this.socket = socket;
  }

  public void joinSession(GameSession session, int index) {
    this.session = session;
    this.index = index;
  }

  @Override
  public void run() {
    try {
      BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

      output = new ObjectOutputStream(socket.getOutputStream());

      while (true) {
        String message = input.readLine();
        if (session == null) {
          String[] parts = message.split(";");
          this.data.name = parts[0];
          this.data.contactInfo = parts[1];
        } else {
          session.process(message, index);
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    // super.run();
  }
}
