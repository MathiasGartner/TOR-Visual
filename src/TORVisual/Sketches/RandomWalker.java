package TORVisual.Sketches;

import TORVisual.EmbeddedSketch;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.concurrent.ThreadLocalRandom;

public class RandomWalker extends EmbeddedSketch {

    int startX;
    int startY;
    int x;
    int y;
    float size;
    int dx; //x change
    int dy; //y change
    float ds; //size change

    public RandomWalker(PApplet sketch, SketchArea area) {
        super(sketch, area);
        startX = this.area.w / 2;
        startY = this.area.h / 2;
        size = 5;
        x = startX;
        y = startY;
        dx = 6;
        dy = 6;
        ds = (float) 0.1;
    }

    public int randInt() {
        int randomNum = ThreadLocalRandom.current().nextInt(1, 6+1);
        return randomNum;
    }

    @Override
    public void draw() {
        int r = randInt();
        switch(r) {
            case 1:
                x += dx;
                break;
            case 2:
                y += dy;
                break;
            case 3:
                x -= dx;
                break;
            case 4:
                y -= dy;
                break;
            case 5:
                if (size > ds)
                    size -= ds;
                break;
            case 6:
                size += ds;
                break;
        }
        sketch.fill(255, 255, 255, 20);
        sketch.stroke(255, 255, 255, 20);
        sketch.circle(x, y, size);
    }
}
