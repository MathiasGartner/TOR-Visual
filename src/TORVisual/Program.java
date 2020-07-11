package TORVisual;

import TORVisual.Data.DBManager;
import processing.core.PApplet;

import java.sql.SQLException;

public class Program {
    public static void main(String[] args){
        String[] processingArgs = {"Test"};
        //MainCanvas mainCanvas = new MainCanvas(1, 1920, 1080);
        MainCanvas mainCanvas = new MainCanvas(1, 1000, 600);
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
