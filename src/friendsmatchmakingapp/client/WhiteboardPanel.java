package friendsmatchmakingapp.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class WhiteboardPanel extends JPanel {

  private LinkedList<LinkedList<Point>> lines = new LinkedList<>();

  private Color color = Color.ORANGE;
  private int thickness = 10;
  private ObjectOutputStream output;
  private Timer timer = new Timer();
  private int frequency = 500;
  private boolean isDrawing;

  public WhiteboardPanel(ObjectOutputStream output) {

    this.output = output;
    timer.scheduleAtFixedRate(
        new TimerTask() {
          @Override
          public void run() {
            try {
              output.writeObject(new PlayerUpdate(lines, "", "", "", ""));
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        },
        0,
        frequency);

    addMouseListener(
        new MouseAdapter() {
          public void mousePressed(MouseEvent e) {
            if (isDrawing) {
              LinkedList<Point> points = new LinkedList<>();
              points.add(e.getPoint());
              lines.add(points);
            }
          }

          public void mouseReleased(MouseEvent e) {
            if (isDrawing) {
              repaint();
            }
          }
        });
    addMouseMotionListener(
        new MouseMotionAdapter() {
          public void mouseMoved(MouseEvent e) {}

          public void mouseDragged(MouseEvent e) {
            if (isDrawing) {
              lines.getLast().addLast(e.getPoint());
              repaint();
            }
          }
        });
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);

    for (List<Point> i : lines) {
      Point previous = null;
      for (Point j : i) {
        if (previous == null) {
          previous = j;
        }

        g.setColor(color);
        ((Graphics2D) g).setStroke(new BasicStroke(thickness));
        g.drawLine(previous.x, previous.y, j.x, j.y);
        previous = j;
      }
    }
  }

    public void setDrawing(boolean drawing) {
        isDrawing = drawing;
    }

    public void drawLines(LinkedList<LinkedList<Point>> lines) {
      this.lines = lines;
      repaint();
    }
}
