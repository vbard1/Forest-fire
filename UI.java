
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class UI extends JFrame implements ActionListener {
    // attributes
    JFrame window;
    JPanel drawing;
    Timer timer;
    Forest forest;
    int screenSideSize;
    int top;// call to actionPeroformed itearation counter
    Dimension screenDimension;
    String state = "RUNNING";

    /**
     * creates a new JFrame based interface and calls simulation(), a method of
     * Forest, to run the calculation of witch tree is burning at next step
     * 
     * @param frst a Forest object to show on screen
     */
    public UI(Forest frst) {
        this.forest = frst;

        screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        screenSideSize = (int) screenDimension.getHeight() - 150;

        window = new JFrame();
        window.getInsets().set(10, 0, 0, 0);
        window.setTitle("Feu de Foret - entretien technique CIRIL GROUP");
        window.setSize(screenSideSize, screenSideSize);
        window.setResizable(false);
        window.setLocation((int) (screenDimension.getWidth() - screenSideSize) / 2,
                (int) (screenDimension.getHeight() - screenSideSize) / 2);
        window.setDefaultCloseOperation(EXIT_ON_CLOSE);

        timer = new Timer((int) (1000 / forest.frequency), this);
        timer.start();
        top = 0;

        drawing = new drawing(this);
        window.add(drawing);
        window.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (forest.onFire.isEmpty()) {
            state = "NOT RUNNING";
        }
        window.setTitle("[Feu de Foret] [" + state + "] [Iteration n°" + (top++)
                + "] [n en feu : " + forest.nOnFire
                + "]");
        window.repaint();
    }

    class drawing extends JPanel {
        UI ui;

        public drawing(UI ui) {
            this.ui = ui;
        }

        @Override
        public void paint(Graphics g) {
            g.setColor(new Color(99, 78, 25));
            g.fillRect(0, 0, window.getWidth(), window.getHeight()); // new background
            forest.draw(g, ui);

            if (!forest.onFire.isEmpty() && top > 0) {
                forest.simulation();
            } else if (top > 0) {
                timer.stop();
            }
        }
    }
}