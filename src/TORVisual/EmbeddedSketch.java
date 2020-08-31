package TORVisual;

import TORVisual.Data.DiceResult;
import TORVisual.Settings.SettingsVisual;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import processing.core.PApplet;
import processing.core.PGraphics;

import java.util.ArrayList;

public abstract class EmbeddedSketch {
    protected PApplet sketch;
    protected SketchArea area;
    protected int backgroundColor;
    protected PGraphics canvas;

    protected CircularFifoQueue<DiceResult> recentDiceResults;

    protected EmbeddedSketch(PApplet sketch, SketchArea area) {
        this.sketch = sketch;
        this.area = area;
        this.backgroundColor = sketch.color(12, 17, 17);
        this.recentDiceResults = new CircularFifoQueue<>(SettingsVisual.StoreLastNDiceResults_Default);
        canvas = sketch.createGraphics(area.w, area.h);
        canvas.beginDraw();
        canvas.background(backgroundColor);
        canvas.endDraw();
    }

    protected EmbeddedSketch(PApplet sketch, int x, int y, int w, int h) {
        this(sketch, new SketchArea(x, y, w, h));
    }

    public abstract void draw();

    protected void setRecentDiceResultsCount(int count) {
        var newRecentDiceResults = new CircularFifoQueue<DiceResult>(count);
        newRecentDiceResults.addAll(this.recentDiceResults);
        this.recentDiceResults = newRecentDiceResults;
    }

    protected void addNewDiceResults(ArrayList<DiceResult> newResults) {
        this.recentDiceResults.addAll(newResults);
    }

    public void setBackgroundColor(int c) {
        this.backgroundColor = c;
    }

    public void setBackgroundColor(int r, int g, int b) {
        this.backgroundColor = sketch.color(r, g, b);
    }

    public void clear() {
        this.canvas.fill(this.backgroundColor);
        this.canvas.rect(0, 0, this.area.w, this.area.h);
    }

    public void setBackgroundColor(int r, int g, int b, int a) {
        this.backgroundColor = sketch.color(r, g, b, a);
    }
}
