import java.util.*;
import java.awt.*;

public class Forest {

    // attributs
    int width;
    int height;
    int nTree;
    double propagationProba;
    int coteArbre;
    int espacementArbre;
    Color Csapin = new Color(40, 158, 43);
    Color Crouge = new Color(255, 0, 0);
    double frequency = 0;
    int[][] grille; // 0=alive, 1=on fire, 2 =in ashes
    ArrayList<Point> onFire; // trees on fire at t
    ArrayList<Point> prochainFeu; // trees on fire at t+1
    ArrayList<Point> inAshes; // trees in ashes
    int nOnFire; // number of trees on fire
    int nAshes;

    /**
     * creates an object Forest
     * 
     * @param l        width of the grid
     * @param h        height of the grid
     * @param p        probability to a nearby tree to be set on fire
     * @param fireInit initial trees on fire (coordinates)
     * @param frq      calculus frequency
     */
    public Forest(int l, int h, double p, ArrayList<Point> fireInit, double frq) {
        width = l;
        height = h;
        nTree = l * h;
        propagationProba = p;
        frequency = frq;
        onFire = new ArrayList<Point>();
        inAshes = new ArrayList<Point>();

        nOnFire = 0;
        nAshes = 0;

        int x;
        int y;
        System.out.println();
        for (int i = 0; i < fireInit.size(); i++) {
            x = (int) (fireInit.get(i).getX());
            y = (int) (fireInit.get(i).y);
            if ((x > -1 && x < width) && (y > -1 && y < height)) {
                onFire.add(new Point(x, y));
                // test //System.out.println("ajout de " + getX() + ":" + y + " déjà en feu");
            } else {
                // error feedback to the user : wrong coordinates for the initial on fire tree
                System.out.println(
                        "La case en feu de coordonnees[" + x + ":" + y
                                + "] est hors de la Forest : ABANDON DE L'AJOUT");
            }
        }
        System.out.println();

    }

    public void simulation() {

        prochainFeu = new ArrayList<Point>();
        LinkedList<Point> toTest = new LinkedList<Point>();
        for (Point p : onFire) {
            toTest.addAll(
                    Arrays.asList(
                            new Point((int) p.getX() + 1, (int) p.getY()), // left tree of the burning tree
                            new Point((int) p.getX() - 1, (int) p.getY()), // tree above the burning tree
                            new Point((int) p.getX(), (int) p.getY() + 1), // tree below of the burning tree
                            new Point((int) p.getX(), (int) p.getY() - 1)/*
                                                                          * , // right tree of the burning tree
                                                                          * new Point((int) p.getX() + 1, (int) p.getY()
                                                                          * - 1), // below right
                                                                          * new Point((int) p.getX() - 1, (int) p.getY()
                                                                          * - 1), // below left
                                                                          * new Point((int) p.getX() + 1, (int) p.getY()
                                                                          * + 1), // above right
                                                                          * new Point((int) p.getX() - 1, (int) p.getY()
                                                                          * + 1)
                                                                          */)); // above left

            for (Point point : toTest) {
                if ((Math.random() <= propagationProba) && !prochainFeu.contains(point) && !inAshes.contains(point)
                        && (point.getX() < width && point.getX() >= 0)
                        && (point.getY() >= 0 && point.getY() < height)) {
                    prochainFeu.add(point);
                    nOnFire++;
                    nAshes++;
                }
            }
            inAshes.add(p); // on fire tree at t is ashes at t+1
        }
        onFire.clear();
        for (Point p : prochainFeu) {
            onFire.add(p);

        }
        nOnFire = prochainFeu.size();
        prochainFeu.clear();

    }

    public void draw(Graphics g, UI window) {

        espacementArbre = 3;
        coteArbre = (int) ((window.screenSideSize - espacementArbre)
                / (Math.max(height, width) + 1) - espacementArbre);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {

                Point point = new Point(i, j);
                if (onFire.contains(point)) {
                    g.setColor(Crouge);
                } else if (inAshes.contains(point)) {
                    g.setColor(Color.darkGray);
                } else {
                    g.setColor(Csapin);
                }

                g.fillRoundRect(i * coteArbre + (i + 1) * espacementArbre, j * coteArbre + (j + 1) * espacementArbre,
                        coteArbre,
                        coteArbre,
                        40,
                        40);
            }
        }
    }

    public String toString() {
        return ("Je suis une Foret de " + width + " arbres de large et de " + height
                + " arbres de haut, soit un total de " + height * width + " arbres.");
    }
}