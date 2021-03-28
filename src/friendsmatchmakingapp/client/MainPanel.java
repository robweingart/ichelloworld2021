package friendsmatchmakingapp.client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class MainPanel extends JFrame{
    private JPanel panel1;
    private JTextField redundant;
    private JTextField chatFieldTextField;
    private JLabel timerLabel;
    private JTable table1;
    private JPanel whiteboardSpace;
    private String name;
    private WhiteboardPanel whiteboardPanel;

    private List<PlayerData> participants;
    private int current;
    private int currentRound;
    public static final int NUMBER_OF_ROUNDS = 5;

    private Timer timer;
    private int interval;
    int delay = 1000;
    int period = 1000;
    ObjectOutputStream output;

    public void setName(String name) {
        this.name = name;
    }

    public MainPanel() {
        this.participants = participants;
        current = 0;
        whiteboardPanel = new WhiteboardPanel(output);
        // attach whiteboardPanel as child of panel1
        whiteboardSpace.add(whiteboardPanel);


        try (Socket socket = new Socket("localhost", 5000)){
            output = new ObjectOutputStream(socket.getOutputStream());
            ClientThread clientThread = new ClientThread(socket, this);
            clientThread.start();
            setContentPane(new LandingPage(output, this));
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            pack();
            setVisible(true);
        } catch (Exception e){
            System.out.println("Wumps :(, Something went wrong.");
        }

    }

    public void incrementTime() {

    timer.scheduleAtFixedRate(new TimerTask() {

        public void run() {
            System.out.println(setInterval());

        }
    }, delay, period);
}


    private int setInterval() {
        if (interval == 1)
            timer.cancel();
        return --interval;
    }



    public PlayerData getNextPlayer() {
        PlayerData res =  participants.get(current % participants.size());
        current++;
        return(res);

    }

    // implement logic if user leaves game later
    // timer (display the result at the end of each round for 5 seconds when it reaches 0, then start next round)
    // scoreboard (display each users' name and current score)
    // chat log (display chat history)
    // input field (record answers and chat, reports if answer is correct and update scoreboard)
    // drawboard (display drawing)
    // questions (display current question)
    // game ends if a user reaches 3000 points or more


  public static void main(String[] args) {
        new MainPanel();

  }

    public void update(GameStateUpdate readObject) {
        if (getContentPane() instanceof LandingPage) {
            if (readObject.currentDrawer.equals(name)) {
                setContentPane(new AnswerPrompt(output, readObject.currentQuestion));
            }
            else {
                setContentPane(panel1);
            }
        }
        else if (getContentPane() instanceof AnswerPrompt) {
            setContentPane(panel1);
        }
        else if (getContentPane() == (panel1)) {

            // update list of players
            if (readObject.currentDrawer.equals(name)) {
                whiteboardPanel.setDrawing(true);
            }
            else {
                whiteboardPanel.setDrawing(false);
                whiteboardPanel.drawLines(readObject.lines);
            }
        }


    }
}
