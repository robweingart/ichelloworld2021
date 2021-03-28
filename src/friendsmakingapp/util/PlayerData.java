package friendsmakingapp.util;

import java.io.Serializable;

public class PlayerData implements Serializable {
  public String name;
  public String contactInfo;
  public int score;

  @Override
  public String toString() {
    return "\nName: " + name + ", " + "\nContact Info: " + contactInfo + "\nScore: " + score;
  }

  public PlayerData(String name, String contactInfo, int score) {
    this.name = name;
    this.contactInfo = contactInfo;
    this.score = score;
  }
}
