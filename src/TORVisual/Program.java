package TORVisual;

import processing.core.PApplet;

public class Program {
    public static void main(String[] args){
        String[] processingArgs = {"Test"};
        //MainCanvas mainCanvas = new MainCanvas(1, 1920, 1080);
        MainCanvas mainCanvas = new MainCanvas(1, 1000, 600);
        //MainCanvas mainCanvas = new MainCanvas(2, 800, 600);
        PApplet.runSketch(processingArgs, mainCanvas);
    }
}
