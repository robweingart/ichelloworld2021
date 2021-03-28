package friendsmakingapp.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
  // Players per Session = 4

  private static final int PLAYERS = 4;

  // Get a Queue of players trying to connect and create a session for the top 4 players (Create a
  // set number of Sessions?)

  public static void main(String[] args) {

    Queue<ServerThread> clientQueue = new LinkedList<>();

    ArrayList<GameSession> runningSessions = new ArrayList<>();

    try (ServerSocket serverSocket = new ServerSocket(5000)) {

      while (true) {

        Socket socket = serverSocket.accept();
        ServerThread thread = new ServerThread(socket);

        clientQueue.add(thread);
        thread.start();

        if (clientQueue.size() >= PLAYERS) {

          Thread.sleep(1000);

          ServerThread[] threads = new ServerThread[PLAYERS];

          for (int i = 0; i < PLAYERS; i++) {
            threads[i] = clientQueue.remove();
          }

          runningSessions.add(new GameSession(threads));
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
