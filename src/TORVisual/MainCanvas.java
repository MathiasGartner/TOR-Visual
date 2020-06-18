package TORVisual;

import TORVisual.Sketches.AreaTest;
import TORVisual.Sketches.MCResults;
import TORVisual.Sketches.Overview;
import TORVisual.Sketches.RandomWalker;
import processing.core.PApplet;

import java.util.ArrayList;

public class MainCanvas extends PApplet {

    private int displayId;
    private int screenW;
    private int screenH;

    private AreaTest areaTest1;
    private AreaTest areaTest2;
    private AreaTest areaTest3;
    private ArrayList<EmbeddedSketch> sketches;
    private ArrayList<SketchArea> sketchAreas;
    private int borderPx;

    public MainCanvas(int displayId, int w, int h) {
        this.displayId = displayId;
        this.screenW = w;
        this.screenH = h;

        borderPx = 13;
    }

    public void settings() {
        fullScreen(this.displayId);

        //define sketch areas
        sketchAreas = new ArrayList<SketchArea>();
        sketchAreas.add(new SketchArea(0, 0, (int) (screenW * 0.7), screenH));
        int currentX = sketchAreas.get(0).xw + borderPx;
        sketchAreas.add(new SketchArea(currentX, 0, screenW - currentX, (int) (screenH * 0.6)));
        int currentY = sketchAreas.get(1).yh + borderPx;
        sketchAreas.add(new SketchArea(currentX, currentY, screenW - currentX, screenH - currentY));

        //create sketches
        areaTest1 = new AreaTest(this, sketchAreas.get(0));
        areaTest1.setBackgroundColor(20, 50, 100);
        areaTest2 = new AreaTest(this, sketchAreas.get(1));
        areaTest2.setBackgroundColor(220, 50, 100);
        areaTest3 = new AreaTest(this, sketchAreas.get(2));
        areaTest3.setBackgroundColor(20, 250, 100);

        RandomWalker randomWalkerSketch = new RandomWalker(this, sketchAreas.get(0));

        //add sketches to sketch-list
        sketches = new ArrayList<EmbeddedSketch>();
        sketches.add(randomWalkerSketch);
        sketches.add(areaTest2);
        sketches.add(areaTest3);
    }

    public void setup() {
        for (var sketch : sketches) {
            stroke(sketch.backgroundColor);
            fill(sketch.backgroundColor);
            rect(sketch.area.x, sketch.area.y, sketch.area.w, sketch.area.h);
        }
    }

    public void draw() {
        //draw sketches
        for (var sketch : sketches) {
            pushMatrix();
            translate(sketch.area.x, sketch.area.y);
            sketch.draw();
            popMatrix();
        }
        //draw borders
        for (var area : sketchAreas) {
            stroke(0, 0, 0);
            fill(0, 0, 0);
            rect(area.x - borderPx, area.y - borderPx, area.xw + 2 * borderPx, borderPx);
            rect(area.x - borderPx, area.y - borderPx, borderPx, area.yh + 2 * borderPx);
            rect(area.x - borderPx, area.yh, area.xw + 2 * borderPx, borderPx);
            rect(area.xw, area.y - borderPx , borderPx, area.yh + 2 * borderPx);
        }
    }
}
