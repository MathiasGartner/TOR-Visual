package TORVisual.Sketches.RandomWalks;

import TORVisual.Data.DiceResult;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;

public class WoodCircle extends RandomWalker{

    float dh,dw,sizemax,sd,swmax;
    float sw;
    float swd;
    public WoodCircle(PApplet sketch, SketchArea area,  ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);
        x = startX;
        y = startY;
        cr = 52; //rgb color value red
        cg = 130; //rgb color value green
        cb = 47;  //rgb color value blue
        alpha =51;
        dx = this.area.w/100.0f*0.9f; //difference x
        dy = this.area.w/100.0f*0.9f; //difference y
        sw= this.area.w/100.0f*0.4f; //stroke weight
        swd= this.area.w/100.0f*0.05f; //stroke weight difference
        swmax=this.area.w/100.0f*1f; //stroke weight maximum
        size =this.area.h/100.0f*2f; //circle size
        sizemax=this.area.h/100.0f*4f; //circle size maximum
        sd=this.area.h/100.0f*0.4f; //size difference
    }

    @Override
    public void draw() {

       for (var result : this.resultsToShow) {
           //int r = randInt();
           int r = result.Result;

           switch (r) {
            case 1:
                if (y+dy >= area.h)
                    y -= dy;

                if (y+dy < area.h)
                    y += dy;   //+ difference y

                if (sw > swd & sw<swmax)
                    sw -= swd;  //stroke weight - stroke weight distance

                if (cg > 134)
                    cg -= 1;  //green -1
                break;

            case 2:
                if (alpha >= 40 & alpha <= 100)
                    alpha += 1;

                if (size > sd & size<sizemax)
                    size -= sd;  //circle size - difference size
                break;


            case 3:
                if (alpha <= 100 & alpha >= 40)
                    alpha -= 1;  //alpha -1
                break;

            case 4:
                if (y > dy & y < area.h)
                    y -= dy; //- difference y

            case 5:

                if (x > dx & x < area.w)
                    x -= dx; //- difference x

                if (size+sd < sizemax)
                    size += sd; //+size difference
                break;


            case 6:

                if (x+dx >= area.w)
                    x -= dx;

                if (x < area.w)
                    x += dx;   //+ difference x

                if (sw+swd < swmax)
                    sw += swd; //stroke weight + stroke weight distance

                if (cg < 255)
                    cg += 1;   //green + 1
                break;

        }
        //sketch.fill(cr, cg, cb, alpha);
        sketch.fill(cr, cg, cb, 0);
      //     sketch.stroke(32,79,17,31);
        sketch.strokeWeight((float) sw);
        sketch.stroke(cr, cg, cb, alpha);
        sketch.circle(x, y, (float) size);
    }
    }
}
