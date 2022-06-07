package TORVisual;

import processing.core.PApplet;

public class Program {
    public static void main(String[] args){
        String[] processingArgs = {"Test"};
        //MainCanvas mainCanvas = new MainCanvas(1, 1920, 1080);
        int screen = 0;
        int w = 1000;
        int h = 600;
        w = 1920;
        h = 1080;
        MainCanvas mainCanvas;
        System.out.println("Start...");
        System.out.println("w:" + w + ", h:" + h);
        if (args.length == 1 || true) {
            try {
                //screen = Integer.parseInt(args[0]);
                screen = Integer.parseInt("2");
            }
            catch (NumberFormatException ex) {
                System.out.println("First argument needs to be the screen number.");
                System.exit(1);
            }
            mainCanvas = new MainCanvas(screen);
        }
        else if (args.length == 2) {
            try {
                w = Integer.parseInt(args[0]);
                h = Integer.parseInt(args[1]);
            }
            catch (NumberFormatException ex) {
                System.out.println("Error parsing sketch resolution");
                System.exit(1);
            }
            mainCanvas = new MainCanvas(screen, w, h);
        }
        else {
            mainCanvas = new MainCanvas(screen, w, h);
            //mainCanvas = new MainCanvas(2);
        }
        //MainCanvas mainCanvas = new MainCanvas(2, 800, 600);

        /*
        DBManager db = new DBManager();
        try {
            db.CreateDummyResults(79000);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        */

        PApplet.runSketch(processingArgs, mainCanvas);
    }
}
