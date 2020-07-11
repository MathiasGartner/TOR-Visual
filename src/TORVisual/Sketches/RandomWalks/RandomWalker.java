package TORVisual.Sketches.RandomWalks;

import TORVisual.Data.DiceResult;
import TORVisual.EmbeddedSketch;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class RandomWalker extends EmbeddedSketch {

    protected ArrayList<DiceResult> resultsToShow;

    int startX;
    int startY;
    int x;
    int y;
    int x1, y1, x2, y2, x3, y3;
    float size;
    float dx; //x change
    float dy; //y change
    float ds; //size change
    int cr, cg, cb;
    int alpha = 30;
    int w, h;


    public RandomWalker(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area);

        this.resultsToShow = resultsToShow;

        startX = this.area.w / 2;
        startY = this.area.h / 2;
        size = 5;
        x = startX;
        y = startY;



        x1= startX;
        y1= startY;
        x2= startX+2;
        y2= startY+2;
        x3= startX+4;
        y3= startY+8;

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
                if (x <area.w)
                    x += dx;

                if (cg < 255)  //green + 1
                    cg += 1;
                break;
            case 2:
                if (y < area.h)
                    y += dy;

                if (cg > 0)  //green -1
                    cg -= 1;

                break;
            case 3:
                if (x >1 & x < area.w)
                x -= dx;

              //  if (cb > 0)  //blue -1
              //      cb -= 1;
                if (h<20)
                    h += ds;

                break;
            case 4:
                if (y >1 & y<area.h)
                y -= dy;

             //   if (cb < 255)  //blue + 1
                //cb += 1;

                if (w< 20)
                    w += ds;
                break;

            case 5:

                if (w>ds)
                    w -= ds;
                //if (size > ds)
                //    size -= ds;

                if (alpha <= 100 & alpha >=20)
                    alpha -= 1;
                break;
            case 6:
                //size += ds;
                if (h>ds)
                    h -= ds;

                if (alpha >= 20 & alpha <= 100)
                alpha += 1;
                break;
        }
        sketch.fill(cr, cg, cb, alpha);
        sketch.stroke(cr, cg, cb, alpha);
        //sketch.triangle(x1, y1, x2, y2, x3, y3);
        sketch.ellipse(x,y,w,h);



    }
}
