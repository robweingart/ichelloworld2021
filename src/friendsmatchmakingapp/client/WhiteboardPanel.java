package friendsmatchmakingapp.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.LinkedList;
import java.util.List;

public class WhiteboardPanel extends JPanel {

  private LinkedList<LinkedList<Point>> lines = new LinkedList<>();

  private Color color = Color.ORANGE;
  private int thickness = 10;
  private Point pointStart;
  private Point pointEnd;

  public WhiteboardPanel() {


    addMouseListener(
        new MouseAdapter() {
          public void mousePressed(MouseEvent e) {
              LinkedList<Point> points = new LinkedList<>();
              points.add(e.getPoint());
              lines.add(points);

          }

          public void mouseReleased(MouseEvent e) {
              repaint();
          }
        });
    addMouseMotionListener(
        new MouseMotionAdapter() {
          public void mouseMoved(MouseEvent e) {}

          public void mouseDragged(MouseEvent e) {
            lines.getLast().addLast(e.getPoint());
            repaint();
          }
        });

    System.setProperty("sun.awt.noerasebackground", "true");
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
}
