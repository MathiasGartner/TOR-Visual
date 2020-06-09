package TORVisual;

import processing.core.PApplet;

public class Program {
    public static void main(String[] args){
        String[] processingArgs = {"Test"};
        MainCanvas mainCanvas = new MainCanvas(2, 1920, 1200);
        //MainCanvas mainCanvas = new MainCanvas(2, 1600, 900);
        //MainCanvas mainCanvas = new MainCanvas(2, 800, 600);
        PApplet.runSketch(processingArgs, mainCanvas);
    }
}
