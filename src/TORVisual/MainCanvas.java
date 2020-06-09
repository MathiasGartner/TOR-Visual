package TORVisual;

import TORVisual.Sketches.MCResults;
import TORVisual.Sketches.Overview;
import processing.core.PApplet;

import java.util.ArrayList;

public class MainCanvas extends PApplet {

    private int displayId;
    private int screenW;
    private int screenH;

    private Overview overview;
    private MCResults mcResults;
    private MCResults mcResults2;
    private ArrayList<EmbeddedSketch> sketches;
    private ArrayList<SketchArea> sketchAreas;
    private int borderPx;

    public MainCanvas(int displayId, int w, int h) {
        this.displayId = displayId;
        this.screenW = w;
        this.screenH = h;

        borderPx = 3;
    }

    public void settings() {
        fullScreen(this.displayId);

        sketchAreas = new ArrayList<SketchArea>();
        sketchAreas.add(new SketchArea(0, 0, (int)(screenW * 0.8), screenH));
        int currentX = sketchAreas.get(0).xw + borderPx;
        sketchAreas.add(new SketchArea(currentX, 0, screenW - currentX, (int)(screenH * 0.6)));
        int currentY = sketchAreas.get(1).yh + borderPx;
        sketchAreas.add(new SketchArea(currentX, currentY, screenW - currentX, screenH - currentY));
        overview = new Overview(this, sketchAreas.get(0));
        mcResults = new MCResults(this, sketchAreas.get(1));
        mcResults2 = new MCResults(this, sketchAreas.get(2));

        sketches =  new ArrayList<EmbeddedSketch>();
        sketches.add(overview);
        sketches.add(mcResults);
        sketches.add(mcResults2);
    }

    public void draw() {
        background(0x000000);
        for (var sketch : sketches) {
            fill(200, 100, 20);
            rect(sketch.area.x, sketch.area.y, sketch.area.w, sketch.area.h);
            pushMatrix();
            translate(sketch.area.x, sketch.area.y);
            sketch.draw();
            popMatrix();
        }
    }
}
