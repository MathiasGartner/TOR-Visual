package TORVisual.Sketches.RandomWalks;

import TORVisual.Data.DiceResult;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;


// Lavendel: draw purple, blue Ellipse with strokes, fill /3

public class Lavendel extends RandomWalker
{

    public Lavendel(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);
        cr = 71;
        cg = 55;
        cb = 119;
        dx = this.area.w/100*1.5f;
        dy = this.area.w/100*1.5f;
        ds = this.area.w/100*0.5f;
        w=this.area.w/100*1f;
        h=this.area.h/100*1f;
        size = 10;
        x=startX;
        y=startY;
    }

    @Override
    public void draw() {
        int r = randInt();


        switch (r) {
            case 1:
                if (x+dx < area.w)
                    x += dx;

                if (cg < 70)  //green + 1
                    cg += 1;
                break;
            case 2:
                /*if (y+dy >= area.h)
                    y -= dy;*/

                if (y+dy < area.h)
                    y += dy;

                if (cg > 0 && cg <70)  //green -1
                    cg -= 1;

                break;
            case 3:
                if (x > dx & x < area.w)
                    x -= dx;

                //  if (cb > 0)  //blue -1
                //      cb -= 1;
                if (h < size)
                    h += ds;

                if (cr > 70 && cg <100)  //red -1
                    cr -= 1;

                break;
            case 4:
                if (y > dy & y < area.h)
                    y -= dy;

                if (cb <145)  //blue +1
                    cb += 1;

                if (w < size)
                    w += ds;
                break;

            case 5:

                if (w > ds)
                    w -= ds;
                //if (size > ds)
                //    size -= ds;

                if (alpha <= 100 & alpha >= 20)
                    alpha -= 1;

                if (cr <100)  //red +1
                    cr += 1;

                break;
            case 6:
                //size += ds;
                if (h > ds)
                    h -= ds;

                if (alpha >= 20 & alpha <= 100)
                    alpha += 1;

                if (cb > 120 && cb <145)  //blue -1
                    cb -= 1;

                break;
        }
        sketch.fill(cr/3, cg/3, cb/3, alpha/3);
        sketch.stroke(cr, cg, cb, alpha);
        sketch.ellipse(x, y, w, h);
        sketch.fill(255, 255, 255, 255);
        sketch.textSize(20);
        sketch.text("Lavendula", 10, 30);
    }
}
