package friendsmakingapp.server;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class GameSession {

  private static final String[] QUESTIONS = {"Hello", "Goodbye"};
  private static final int ROUNDS = 4;
  private final ServerThread[] userThreads;
  private int currentDrawer;
  private String correctGuess;
  private String currentQuestion;
  private final Random random = new Random();
  private SessionState state;
  private final Timer timer = new Timer();

  private LinkedList<String> chat = new LinkedList<>();

  public GameSession(ServerThread[] userThreads) {
    this.userThreads = userThreads;

    // Make threads know what game session they're in.
    currentDrawer = 0;
    newQuestion();
    for (int i = 0; i < userThreads.length; i++) {
      ServerThread thread = userThreads[i];
      thread.joinSession(this, i);
    }
    updateStates();
  }

  public synchronized void process(String message, int index) {
    if (index == currentDrawer) {
      if (state == SessionState.CHOOSING) {
        correctGuess = message;
        state = SessionState.DRAWING;
        timer.schedule(new TimerTask() {
          @Override
          public void run() {
            nextPlayer();
            updateStates();
          }
        }, new Date(new Date().getTime() + 60000));
      } else {
        // message is the encoded image data, process it here
      }
    } else {
      addToChat(message);
    }
  }

  // Chat Related Functions

  public void addToChat(String message) {

    // Check if it's the guess.

    System.out.println(message);

    chat.add(message);

    if (message.equalsIgnoreCase(correctGuess)) {
      userThreads[currentDrawer].data.score += 100;
      nextPlayer();
    }

    updateStates();
  }

  public void nextPlayer() {
    currentDrawer = (currentDrawer + 1) % userThreads.length;
    state = SessionState.CHOOSING;
  }

  public void updateStates() {

    GameStateUpdate newUpdate =
        new GameStateUpdate(
            chat,
            currentQuestion,
            userThreads[currentDrawer].data.name,
            Arrays.stream(userThreads)
                .map(t -> t.data)
                .collect(Collectors.toCollection(LinkedList::new)),
            state == SessionState.DRAWING);

    for (ServerThread thread : userThreads) {
      try {
        thread.output.writeObject(newUpdate);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  private void newQuestion() {
    currentQuestion = QUESTIONS[random.nextInt(QUESTIONS.length)];
  }
}
