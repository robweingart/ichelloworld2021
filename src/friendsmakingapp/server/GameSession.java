package friendsmakingapp.server;

import friendsmakingapp.util.GameStateUpdate;
import friendsmakingapp.util.PlayerUpdate;

import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameSession {

  private static final String[] QUESTIONS = {"Hello", "Goodbye"};
  private static final int ROUNDS = 1;
  private final ServerThread[] userThreads;
  private final Random random = new Random();
  private final Timer timer = new Timer();
  private int currentDrawer;
  private String correctGuess;
  private String currentQuestion;
  private SessionState state = SessionState.CHOOSING;
  private String lines = "";
  private boolean isTimerRunning;
  private TimerTask task;
  private int round;

  private String chat = "";

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

  public synchronized void process(PlayerUpdate update, int index) {
    if (index == currentDrawer) {
      if (state == SessionState.CHOOSING) {
        correctGuess = update.correctAnswer;
        state = SessionState.DRAWING;
        updateStates();
        System.out.println("States");

        isTimerRunning = true;
        task =
            new TimerTask() {
              @Override
              public void run() {
                nextPlayer();
                updateStates();
              }
            };
        timer.schedule(task, new Date(new Date().getTime() + 60000));
      } else {
        lines = update.lines;
        updateStates();
        System.out.println(lines);
      }
    } else {
      addToChat(update.message, index);
    }
  }

  // Chat Related Functions

  public void addToChat(String message, int index) {

    // Check if it's the guess.

    System.out.println(message);
    if (message != "") {
      chat += (message + "\n");
    }

    if (message.equalsIgnoreCase(correctGuess)) {
      userThreads[currentDrawer].data.score += 50;
      userThreads[index].data.score += 100;
      nextPlayer();
    }

    updateStates();
  }

  public void nextPlayer() {
    currentDrawer++;
    if (currentDrawer == userThreads.length) {
      round++;
      currentDrawer = 0;
      if (round == ROUNDS) {
        // end of game
        StringBuilder res = new StringBuilder();
        res.append("Final results: ");
        for (ServerThread user : userThreads) {
          res.append(user.data.toString() + '\n');
        }
        res.append("Thanks for playing!");
        chat += res;
        task.cancel();
        return;
      }
    }
    state = SessionState.CHOOSING;
    lines = "";
    if (task != null) {
      task.cancel();
    }
  }

  public void updateStates() {

    GameStateUpdate newUpdate =
        new GameStateUpdate(
            chat,
            currentQuestion,
            userThreads[currentDrawer].data.name,
            state == SessionState.DRAWING,
            lines);

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
