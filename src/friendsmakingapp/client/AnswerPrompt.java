package friendsmakingapp.client;

import friendsmakingapp.util.PlayerUpdate;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class AnswerPrompt extends JPanel {
  private JPanel panel1;
  private JTextField textField1;
  private JButton submitAnswerButton;
  private ObjectOutputStream output;
  private String question;

  // 1) Submit request to the server (start timer countdown for drawing), with field being the
  // answer
  // 2) Post request to all users
  // 3) Close the window and enable drawing for the current user

  public AnswerPrompt(ObjectOutputStream output, String question) {
    this.output = output;
    this.question = question;

    this.add(panel1);
    this.revalidate();

    System.out.println(question);
    submitAnswerButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            // 1) get text in text field, create player object with assigned fields
            // 2) send request to server (server records the order of users)
            // 3) close the current panel and open the loading panel

            String answer = textField1.getText();
            try {
              System.out.println("Answer1");
              output.writeObject(new PlayerUpdate(null, "", "", "", answer));
            } catch (IOException ioException) {
              ioException.printStackTrace();
            }
          }
        });
  }
}
