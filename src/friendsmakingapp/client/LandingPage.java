package friendsmakingapp.client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectOutputStream;

public class LandingPage extends JPanel {

    private JTextField nameTextField;
    private JTextField socialMediaTextField;
    private JButton assignGameButton;
    private JPanel landingPanel;
    private ObjectOutputStream output;
    private MainPanel mainPanel;


    public LandingPage(MainPanel mainPanel) {
        this.mainPanel = mainPanel;

        System.out.println("Im functional I swear!");

        this.add(landingPanel);
        this.revalidate();

        assignGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 1) get text in text field, create player object with assigned fields
                // 2) send request to server (server records the order of users)
                // 3) close the current panel and open the loading panel

                String name = nameTextField.getText();
                String socialMedia = socialMediaTextField.getText();
                mainPanel.setInfo(name, socialMedia);

            }
        });
    }
}
