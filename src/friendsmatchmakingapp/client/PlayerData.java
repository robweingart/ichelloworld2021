package friendsmatchmakingapp.client;

import java.io.Serializable;

public class PlayerData implements Serializable {
    public String name;
    public String contactInfo;
    public int score;

    public PlayerData(String name, String contactInfo, int score) {
        this.name = name;
        this.contactInfo = contactInfo;
        this.score = score;
    }
}
