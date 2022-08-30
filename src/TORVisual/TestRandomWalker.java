package TORVisual;

import TORVisual.Database.DiceResult;
import TORVisual.Sketches.RandomWalks.*;
import TORVisual.Utils.Utils;
import processing.core.PApplet;

import java.beans.beancontext.BeanContextMembershipEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;


class TestCanvas extends PApplet {

    private int displayId;
    private int screenW;
    private int screenH;
    private boolean fullScreen;

    ArrayList<DiceResult> resultsToShow;
    SketchArea sketchArea;
    private TestGrowing testWalker;

    public void settings() {
        //var renderer = JAVA2D;
        var renderer = P2D;
        this.fullScreen = false;
        this.displayId = 1;
        this.screenW = 800;
        this.screenH = 800;
        if (this.fullScreen) {
            fullScreen(renderer, displayId);
        }
        else {
            size(this.screenW, this.screenH, renderer);
        }
    }
    public void setup() {
        resultsToShow = new ArrayList<DiceResult>();
        sketchArea = new SketchArea(10, 10, 600, 600);
        testWalker = new TestGrowing(this, sketchArea, resultsToShow);
    }


    public void draw() {

        var dr = new DiceResult();
        dr.Id = 0;
        dr.Result = Utils.randDiceResult();
        dr.ClientId = Utils.randClientId();
        dr.Material = "";
        dr.Time = new Date();
        dr.UserGenerated = Utils.randClientId() == 15;
        this.resultsToShow.add(dr);

        testWalker.addNewDiceResults(resultsToShow);
        testWalker.canvas.beginDraw();
        testWalker.draw();
        testWalker.canvas.endDraw();

        this.displayRandomWalkSketch(testWalker);
    }

    public void displayRandomWalkSketch(TestGrowing rw) {
        processing.core.PImage partToDisplay = rw.canvas.get(rw.maxLeftDisplay, rw.maxTopDisplay, rw.maxExtentDisplay, rw.maxExtentDisplay);
        image(partToDisplay, rw.area.x, rw.area.y, rw.area.xw, rw.area.yh);
        textAlign(CENTER);
        text(rw.nameLatin, rw.area.x + rw.area.w / 2.0f, rw.area.yh + 25);
    }
}

public class TestRandomWalker {
    public static void main(String[] args){
        String[] processingArgs = {"Test"};

        System.out.println("Test...");

        TestCanvas testCanvas = new TestCanvas();

        PApplet.runSketch(processingArgs, testCanvas);

    }
}
