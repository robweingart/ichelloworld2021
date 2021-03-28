package friendsmakingapp.client;

import friendsmakingapp.util.PlayerData;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class LoadingPanel extends JPanel {
    // while user != limit of number of users in a room
    //   update the current number of users every second (thread.sleep)
    // request the participants info from the server
    // add participants to the participants list in the scoreboard
    // pass the variable to the main panel

    private int numberOfUsers;
    private List<PlayerData> participants;
    public final static int LIMIT = 5;

    public LoadingPanel() {
        this.numberOfUsers = 0;
        this.participants = new ArrayList<>();
    }

    public void update(PlayerData playerAcceptedFromServer) {
        while (numberOfUsers < LIMIT) {
            participants.add(playerAcceptedFromServer);
        }
    }

}
