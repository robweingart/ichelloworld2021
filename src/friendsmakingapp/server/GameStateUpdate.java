package friendsmakingapp.server;

import java.io.Serializable;
import java.util.LinkedList;

public class GameStateUpdate implements Serializable {

    LinkedList<String> chat;
    String currentQuestion;
    LinkedList<String> userNames;
    String currentDrawer;
    LinkedList<java.lang.Integer> scores;
    LinkedList<String> contactInfo;
    // boolean isDrawing;

    // Picture Data;


    public GameStateUpdate(LinkedList<String> chat, String currentQuestion, LinkedList<String> userNames, String currentDrawer, LinkedList<Integer> scores, LinkedList<String> contactInfo) {
        this.chat = chat;
        this.currentQuestion = currentQuestion;
        this.userNames = userNames;
        this.currentDrawer = currentDrawer;
        this.scores = scores;
        this.contactInfo = contactInfo;
    }
}
