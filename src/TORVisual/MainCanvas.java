package TORVisual;

import TORVisual.Sketches.MCResults;
import TORVisual.Sketches.Overview;
import processing.core.PApplet;

import java.util.ArrayList;

public class MainCanvas extends PApplet {

    private Overview overview;
    private MCResults mcResults;
    private MCResults mcResults2;
    private ArrayList<EmbeddedSketch> sketches;
    private ArrayList<SketchArea> sketchAreas;
    private int borderPx;

    public MainCanvas() {
        borderPx = 3;
    }

    public void settings() {
        fullScreen(2);

        sketchAreas = new ArrayList<SketchArea>();
        sketchAreas.add(new SketchArea(0, 0, (int)(displayWidth * 0.8), displayHeight));
        int currentX = sketchAreas.get(0).xw + borderPx;
        sketchAreas.add(new SketchArea(currentX, 0, displayWidth - currentX, (int)(displayHeight * 0.6)));
        int currentY = sketchAreas.get(1).yh + borderPx;
        sketchAreas.add(new SketchArea(currentX, currentY, displayWidth - currentX, displayHeight - currentY));
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
