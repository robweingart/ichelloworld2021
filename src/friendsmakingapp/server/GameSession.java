package friendsmakingapp.server;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class GameSession {

    private ServerThread[] userThreads;

    private static final String[] QUESTIONS = {"Hello", "Goodbye"};
    private static final int ROUNDS = 4;

    private LinkedList<String> userNames;
    private LinkedList<String> contactInfo;
    private LinkedList<Integer> scores;
    private String currentDrawer;
    private String correctGuess;
    private String currentQuestion;

    private LinkedList<String> chat = new LinkedList<>();

    public GameSession(ServerThread[] userThreads) {
        this.userThreads = userThreads;

        // Make threads know what game session they're in.

        for (ServerThread thread : userThreads){

            thread.setSession(this);

        }

    }

    // Chat Related Functions

    public synchronized void addToChat(String message){

        // Check if it's the guess.

        System.out.println(message);

        chat.add(message);

        if (message.equalsIgnoreCase(correctGuess)) {

        }

        // updateStates();

        // Update chat


    }

    public void updateStates(){

        GameStateUpdate newUpdate = new GameStateUpdate(chat, currentQuestion, userNames, currentDrawer, scores, contactInfo);


        for (ServerThread thread : userThreads){
            try {
                thread.output.writeObject(newUpdate);
            }catch(Exception e){
                System.out.println(e.getStackTrace());
            }
        }
    }
}
