package friendsmatchmakingapp.client;

import java.awt.*;
import java.io.Serializable;
import java.util.LinkedList;

public class PlayerUpdate implements Serializable {
    public LinkedList<LinkedList<Point>> lines = new LinkedList<>();
    public String message;
    public String username;
    public String social;
    public String correctAnswer;

    public PlayerUpdate(LinkedList<LinkedList<Point>> lines, String message, String username, String social, String correctAnswer) {
        this.lines = lines;
        this.message = message;
        this.username = username;
        this.social = social;
        this.correctAnswer = correctAnswer;
    }
}
