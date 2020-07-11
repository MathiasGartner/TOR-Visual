package TORVisual.Sketches.RandomWalks;

import TORVisual.Data.DiceResult;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;

public class EllipseCotton extends RandomWalker{

    public EllipseCotton(PApplet sketch, SketchArea area,  ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);

        cr = 255;
        cg = 255;
        cb = 255;
        dx = (float) 4;
        dy = (float) 4;
        ds = (float) 4;
        w=4;
        h=4;
        size = 10;
    }

    @Override
    public void draw() {
        int r = randInt();
        switch (r) {
            case 1:
                if (x < area.w)
                    x += dx;
                if (x+1 == area.w)
                    x -= dx;
                break;
            case 2:
                if (y < area.h)
                    y += dy;
                break;
            case 3:
                if (x > 1)
                    x -= dx;
                if (x == 1)
                    x += dx;

                if (h < size)
                    h += ds;

                break;
            case 4:
                if (y > 1 )
                    y -= dy;
                if (y+1 == area.h)
                    x += dy;

                if (w < size)
                    w += ds;
                break;

            case 5:

                if (w > ds)
                    w -= ds;

                if (alpha <= 60 & alpha >= 10)
                    alpha -= 1;
                break;
            case 6:
                //size += ds;
                if (h > ds)
                    h -= ds;

                if (alpha >= 10 & alpha <= 60)
                    alpha += 1;
                break;
        }
        sketch.fill(cr, cg, cb, alpha);
        sketch.stroke(cr, cg, cb, alpha);
        sketch.ellipse(x, y, w, h);
    }
}

