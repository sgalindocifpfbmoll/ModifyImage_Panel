package Canvas;

import javax.swing.*;
import java.awt.*;

public class MyPanel extends JPanel {
    private Color backgroundColor;

    //<editor-fold desc="CONSTRUCTORS">
    public MyPanel(LayoutManager layout) {
        //this.rectangle = this.getBounds();
        this.setLayout(layout);
    }

    public MyPanel(LayoutManager layout, Color color) {
        //this.rectangle = this.getBounds();
        this.setLayout(layout);
        this.setBackgroundColor(color);
    }
    //</editor-fold>

    //<editor-fold desc="GETTERS">
    public Color getBackgroundColor() {
        return backgroundColor;
    }

//    @Override
//    public Dimension getPreferredSize() {
//        return new Dimension(this.getWidth(), this.getHeight());
//    }
    //</editor-fold>

    //<editor-fold desc="SETTERS">
    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setBorder(int top, int left, int bottom, int right) {
        this.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
    }
    //</editor-fold>

    @Override
    public void paintComponent(Graphics g2) {
        // Cast graphics to 2D
        Graphics2D g = (Graphics2D) g2;
        paintBackground(g);
    }

    private void paintBackground(Graphics2D g){
        g.setColor(backgroundColor);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
    }
}
