package friendsmakingapp.server;

import friendsmakingapp.util.PlayerData;
import friendsmakingapp.util.PlayerUpdate;
import java.io.ObjectInputStream;
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
      ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

      output = new ObjectOutputStream(socket.getOutputStream());

      while (true) {
        PlayerUpdate update = (PlayerUpdate) input.readObject();
        if (session == null) {

          data = new PlayerData(update.username, update.social, 0);

        } else {
          session.process(update, index);
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    // super.run();
  }
}
