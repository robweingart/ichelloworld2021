package friendsmakingapp.util;

import java.io.Serializable;

public class PlayerUpdate implements Serializable {
  public String lines;
  public String message;
  public String username;
  public String social;
  public String correctAnswer;

  public PlayerUpdate(
      String lines, String message, String username, String social, String correctAnswer) {
    this.lines = lines;
    this.message = message;
    this.username = username;
    this.social = social;
    this.correctAnswer = correctAnswer;
  }
}
