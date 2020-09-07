package TORVisual.Sketches.RandomWalks;

import TORVisual.Database.DiceResult;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;

public class Line extends RandomWalker{

    float dh,dw,sizemax,sd,swmax, xdistance, ydistance, distancemax;
    float sw;
    float swd;
    public Line(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);
        x1 = startX;
        y1 = startY;

        dx = this.area.w/100.0f*0.8f; //difference x
        dy = this.area.w/100.0f*0.8f; //difference y

        xdistance = this.area.w/100.0f*0.5f;
        ydistance = this.area.w/100.0f*0.5f;

        distancemax = this.area.w/100.0f*2f;

        x2 = x1+xdistance;
        y2= y1+ydistance;


        cr = 52; //rgb color value red
        cg = 130; //rgb color value green
        cb = 47;  //rgb color value blue
        alpha =100;
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
                if (x1+dx <= area.w && x2+dx <= area.w && (Math.abs((x2+dx)-x1)<distancemax)) {
                    x1 += dx;
                    x2 += dx;
                }
                break;
/*
                if (x1+dx > area.w)
                    x1 -= dx;

                if (x2+dx > area.w)
                    x2 -= dx;


                if (x1+dx > area.w && x2+dx > area.w && y1+dy > area.h && y2+dy > area.h)
                    x1 -= dx;
                    x2 -= dx;
                    y1 -= dy;
                    y2 -= dy;


                if (y+dy < area.h)
                    y += dy;   //+ difference y

                if (sw > swd & sw<swmax)
                    sw -= swd;  //stroke weight - stroke weight distance

                if (cg > 134)
                    cg -= 1;  //green -1
                break;
*/
            case 2:
                if ((x1-dx >= 0) && (x2-dx >= 0))
                {
                    x1 -= dx;
                    x2 -= dx;
                }
/*
                if (x1-dx < 0)
                    x1 += dx;

                if  (x2-dx < 0)
                    x2 += dx;


                if ((x1-dx < 0) && (x2-dx < 0) && (y1-dy < 0) && (y2-dy < 0))
                    x1 += dx;
                    x2 += dx;
                    y1 += dy;
                    y2 += dy;

              if (alpha >= 40 & alpha <= 100)
                    alpha += 1;

                if (size > sd & size<sizemax)
                    size -= sd;  //circle size - difference size

   */
                break;


            case 3:

                if ((y1+dy <= area.h) && (y2+dy <= area.h) && Math.abs((y2+dy)-y1)<distancemax)
                {
                    y1 += dy;
                    y2 += dy;
                }
                break;
/*
                if (y1+dy > area.h)
                    y1 -=dy;

                if (y2+dy > area.h)
                    y2 -=dy;

                if (x1+dx > area.w || x1+dx > area.h)
                    x1 -=dx;

                if (x2+dx > area.w || x2+dx > area.h)
                    x2 -=dx;

                if ((y1+dy > area.h) && (y2+dy > area.h) && (x1+dx > area.w) && (x2+dx > area.w))
                    y1 -=dy;
                    y2 -=dy;
                    x1 -=dx;
                    x2 -=dx;

                if (alpha <= 100 & alpha >= 40)
                    alpha -= 1;  //alpha -1
                break;*/

            case 4:
                if ((y1-dy >= 0) && (y2-dy >= 0)) {
                    y1 -= dy;
                    y2 -= dy;
                }
                break;
/*
                if (y1-dy < 0)
                    y1 +=dy;

                if (y2-dy < 0)
                    y2 +=dy;

                if ((y1-dy < 0) && (y2-dy < 0) && (x1-dx < 0) && (x2-dx < 0))
                    y1 +=dy;
                    y2 +=dy;
                    x1 +=dx;
                    x2 +=dx;


                if (y > dy & y < area.h)
                    y -= dy; //- difference y
*/
            case 5:
              /*  if (Math.abs((x2+dx)-x1)<distancemax && x1+dx <= area.w)
                {
                    x1 += dx;
                }

                if (Math.abs((y2+dy)-y1)<distancemax && (y1+dy <= area.h))
                {
                    y1 += dy;
                }*/


                /*if (x > dx & x < area.w)
                    x -= dx; //- difference x

                if (size+sd < sizemax)
                    size += sd; //+size difference
              */  break;


            case 6:
/*
                if (x+dx >= area.w)
                    x -= dx;

                if (x < area.w)
                    x += dx;   //+ difference x

                if (sw+swd < swmax)
                    sw += swd; //stroke weight + stroke weight distance

                if (cg < 255)F
                    cg += 1;   //green + 1

 */
                break;

        }
        //sketch.fill(cr, cg, cb, alpha);
      //  sketch.fill(cr, cg, cb, 0);
      //     sketch.stroke(32,79,17,31);
      //  sketch.strokeWeight((float) sw);
      //  sketch.stroke(cr, cg, cb, alpha);
        sketch.strokeWeight(1);
        sketch.stroke(cr, cg, cb, alpha);
        sketch.line(x1, y1, x2, y2);
        System.out.println(x1 + ", " + y1 + ", " + x2 + ", " + y2);
    }
    }
}
