package friendsmakingapp.client;

import friendsmakingapp.util.PlayerUpdate;

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
import java.util.stream.Collectors;

public class WhiteboardPanel extends JPanel {

  private LinkedList<LinkedList<Point>> lines = new LinkedList<>();

  private Color color = Color.BLACK;
  private int thickness = 3;
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
            changeData();
          }
        },
        0,
        frequency);

    addMouseListener(
        new MouseAdapter() {
          public void mousePressed(MouseEvent e) {
            mouseP(e);
          }

          public void mouseReleased(MouseEvent e) {
            mouseR(e);
          }
        });
    addMouseMotionListener(
        new MouseMotionAdapter() {
          public void mouseMoved(MouseEvent e) {}

          public void mouseDragged(MouseEvent e) {
            mouseD(e);
          }
        });
  }

  public synchronized void mouseP(MouseEvent e) {
    if (isDrawing) {
      LinkedList<Point> points = new LinkedList<>();
      points.add(e.getPoint());
      lines.add(points);
    }
  }

  public synchronized void mouseR(MouseEvent e) {
    if (isDrawing) {
      repaint();
    }
  }

  public synchronized void mouseD(MouseEvent e) {
    if (isDrawing) {
      lines.getLast().addLast(e.getPoint());
      repaint();
    }
  }

  public synchronized void changeData() {
    try {
      if (isDrawing) {
        output.writeObject(new PlayerUpdate(convert(lines), "", "", "", ""));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String convert(LinkedList<LinkedList<Point>> input) {

    String output =
        input.stream()
            .map(
                line ->
                    line.stream()
                        .map(point -> point.x + "," + point.y)
                        .collect(Collectors.joining(".")))
            .collect(Collectors.joining(":"));
    if (output.equals(":")) {
      return "";
    } else {
      return output;
    }
  }

  public LinkedList<LinkedList<Point>> undoConvert(String input) {
    LinkedList<LinkedList<Point>> result = new LinkedList<>();

    if (!input.equals("")) {
      String[] wholeLines = input.split(":");
      for (int line = 0; line < wholeLines.length; line++) {
        LinkedList<Point> p = new LinkedList<>();
        String[] pointValues = wholeLines[line].split("\\.");

        for (int points = 0; points < pointValues.length; points++) {
          String[] splitPoint = pointValues[points].split(",");
          p.add(new Point(Integer.parseInt(splitPoint[0]), Integer.parseInt(splitPoint[1])));
        }
        result.add(p);
      }
    }
    return result;
  }

  @Override
  public synchronized void paint(Graphics g) {
    super.paint(g);
    this.setBackground(new Color(255, 255, 255));

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

  public synchronized void setDrawing(boolean drawing) {
    isDrawing = drawing;
  }

  public void drawLines(String lines) {

    this.lines = undoConvert(lines);

    repaint();
  }
}
