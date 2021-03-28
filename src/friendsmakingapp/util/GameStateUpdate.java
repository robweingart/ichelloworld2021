package friendsmakingapp.util;

import java.awt.*;
import java.io.Serializable;
import java.util.LinkedList;

public class GameStateUpdate implements Serializable {

    public String chat;
    public String currentQuestion;
    public String currentDrawer;
    public LinkedList<PlayerData> players;
    boolean isDrawing;
    public String lines;

    // Picture Data;

    public GameStateUpdate(
            String chat,
            String currentQuestion,
            String currentDrawer,
            LinkedList<PlayerData> players,
            boolean isDrawing,
            String lines){
        this.chat = chat;
        this.currentQuestion = currentQuestion;
        this.currentDrawer = currentDrawer;
        this.players = players;
        this.isDrawing = isDrawing;
        this.lines = lines;
    }

}

