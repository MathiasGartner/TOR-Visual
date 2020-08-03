package TORVisual;

import TORVisual.Data.DBManager;
import processing.core.PApplet;

import java.sql.SQLException;

public class Program {
    public static void main(String[] args){
        String[] processingArgs = {"Test"};
        //MainCanvas mainCanvas = new MainCanvas(1, 1920, 1080);
        int screen = 1;
        MainCanvas mainCanvas;
        if (args.length > 0) {
            try {
                screen = Integer.parseInt(args[0]);
            }
            catch (NumberFormatException ex) {
                System.out.println("First argument needs to be the screen number.");
                System.exit(1);
            }
            mainCanvas = new MainCanvas(screen);
        }
        else {
            mainCanvas = new MainCanvas(screen, 1000, 600);
        }
        //MainCanvas mainCanvas = new MainCanvas(2, 800, 600);

        /*DBManager db = new DBManager();
        try {
            db.CreateDummyResults(10000);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }*/

        PApplet.runSketch(processingArgs, mainCanvas);
    }
}
