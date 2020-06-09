package TORVisual;

import processing.core.PApplet;

public class Program {
    public static void main(String[] args){
        String[] processingArgs = {"Test"};
        MainCanvas mainCanvas = new MainCanvas();
        PApplet.runSketch(processingArgs, mainCanvas);
    }
}
