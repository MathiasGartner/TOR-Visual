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
        dx = this.sketch.width/330.0f;
        dy = this.sketch.width/330.0f;
        ds = this.sketch.width/170;
        w=this.sketch.width/200;
        h=this.sketch.height/200;
        size = this.sketch.height/150;
    }

    @Override
    public void draw() {

        for (var result : this.resultsToShow) {
            //int r = randInt();
            int r = result.Result;

            switch (r) {
            case 1:
                if (x < area.w)
                    x += dx;
                if (x+dx == area.w)
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

                if (alpha <= 20 & alpha >= 1)
                    alpha -= 1;
                break;
            case 6:
                //size += ds;
                if (h > ds)
                    h -= ds;

                if (alpha >= 1 & alpha <= 20)
                    alpha += 1;
                break;
        }
        }
        sketch.fill(cr, cg, cb, alpha);
        sketch.stroke(cr, cg, cb, alpha);
        sketch.ellipse(x, y, w, h);
    }
}

