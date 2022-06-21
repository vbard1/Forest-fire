import java.io.*;
import java.util.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Forest forest = creationForest(
                "C:/Users/_ghost_/One drive a jour/OneDrive - INSA Lyon/coding adventure/SIMULATION FEU DE FORÊT - CIRIL GROUP/configuration.txt");
        UI affichage = new UI(forest);
        System.out.println(forest.toString());

    }

    /**
     * returns a Forest object used by the GUI (named UI) only to be recalculated
     * and to be printed on screen. relative paths for the param file can cause
     * issues, put the "configuration" file in the same directory
     * 
     * @param file a file path (String) in directory
     * @return a Forest parametrized
     */

    public static Forest creationForest(String file) {

        // attributes
        int width = 0; // the width of the tree grid
        int height = 0; // the height of the tree grid
        double spreadProba = 0; /*
                                 * between 0 inc and 1 exc, the probability that the fire spreads to
                                 * a nearby tree
                                 */
        double frequency = 1;
        ArrayList<Point> fireInit = new ArrayList<Point>(); // the coordinates where initially, there is a tree on fire

        // reading the configuration file
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String ligne;
            int ligneN = 0; // line number
            String[] broken; // the splited line String
            while ((ligne = bufferedReader.readLine()) != null) {

                if (ligneN < 6) {
                    broken = ligne.split("=");
                    if (ligneN == 1)
                        width = Integer.parseInt(broken[1].replaceAll("\\s", ""));

                    else if (ligneN == 2)
                        height = Integer.parseInt(broken[1].replaceAll("\\s", ""));

                    else if (ligneN == 3)
                        spreadProba = Double.parseDouble(broken[1].replaceAll("\\s", ""));

                    else if (ligneN == 4)
                        frequency = Double.parseDouble(broken[1].replaceAll("\\s", ""));
                } else if (ligneN >= 6) {
                    broken = ligne.split("/");
                    fireInit.add(new Point(Integer.parseInt(broken[0].replaceAll("\\s", "")),
                            Integer.parseInt(broken[1].replaceAll("\\s", ""))));
                }
                ligneN++;
            }
            fileReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        // returning the new forest
        return new Forest(width, height, spreadProba, fireInit, frequency);
    }

}