package friendsmakingapp.server;

import java.io.Serializable;
import java.util.LinkedList;

public class GameStateUpdate implements Serializable {

  LinkedList<String> chat;
  String currentQuestion;
  String currentDrawer;
  LinkedList<PlayerData> players;
  boolean isDrawing;

  // Picture Data;

  public GameStateUpdate(
      LinkedList<String> chat,
      String currentQuestion,
      String currentDrawer,
      LinkedList<PlayerData> players,
      boolean isDrawing) {
    this.chat = chat;
    this.currentQuestion = currentQuestion;
    this.currentDrawer = currentDrawer;
    this.players = players;
    this.isDrawing = isDrawing;
  }
}
