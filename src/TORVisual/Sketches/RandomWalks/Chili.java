package TORVisual.Sketches.RandomWalks;

import TORVisual.Data.DiceResult;
import TORVisual.SketchArea;
import TORVisual.Utils.Utils;
import processing.core.PApplet;

import java.util.ArrayList;

public class Chili extends RandomWalker{

    float dh,dw,sizemax,sizemin, sd,swmax, x4, y4, winkel, multiply,start;
    float sw;
    float swd;
    public Chili(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);


        x = startX;
        y = startY;
        x2= (float) (x+(this.area.w/100.0f*2.5));
        y2=y;
        x3=(float) (x+(this.area.w/100.0f*1.5));
        y3=(float) (y+(this.area.w/100.0f*1.5));
        x4=(float) (x-(this.area.w/100.0f*1.5));
        y4=y*1.5f;
        w=this.area.w/100.0f*4;
        h=this.area.w/100.0f*3;
        sd=this.area.h/100.0f*0.2f; //size difference
        sizemax=this.area.w/100.0f*4.5f;
        sizemin=this.area.w/100.0f*1.5f;
        winkel= (float) 1.5f;
        multiply=0f;
        start=0;

        cr = 146; //rgb color value red
        cg = 0; //rgb color value green
        cb = 0;  //rgb color value blue
        alpha=60;
        dx = this.area.w/100.0f*1.5f; //difference x
        dy = this.area.w/100.0f*1.5f; //difference y
       // dh = this.area.w / 300;
       // dw = this.area.w / 300;
        //w=this.area.w/200;
        //wmax=this.area.w/100;
        //h=this.area.h/200;
        sw= this.area.w/100.0f*0.15f; //stroke weight
        swd= this.area.w/100.0f*0.01f; //stroke weight difference
        swmax=this.area.w/100.0f*0.4f; //stroke weight maximum


    }

    @Override
    public void draw() {

       //for (var result : this.resultsToShow) {
           int r = Utils.randDiceResult();
        //  int r = result.Result;

           switch (r) {
            case 1:
                if (x+dx >= area.w)
                    x -= dx;

                if (x < area.w)
                    x += dx;   //+ difference x

                if (sw+swd < swmax)
                    sw += swd; //stroke weight + stroke weight distance

                if (cr < 220)
                    cr += 1;   //red + 1
                break;


            case 2:
                if (y+dy >= area.h)
                    y -= dy;

                if (y+dy < area.h)
                    y += dy;   //+ difference y

                if (sw > swd & sw<swmax)
                    sw -= swd;  //stroke weight - stroke weight distance

                if (cr > 120)
                    cr -= 1;  //red -1

                //x=x+0.1f;
                break;


            case 3:
                if (y > dy & y < area.h)
                    y -= dy; //- difference y

                if (cg > 0)
                    cg -= 1;   //green - 1
                multiply=2.5f;

                if (h+sd < sizemax)
                    h += sd; //+w difference

                start= (float) Math.PI;
                break;

            case 4:
                if (x > dx & x < area.w)
                    x -= dx; //- difference x

                if (w+sd < sizemax)
                    w += sd; //+w difference

                if (cg < 40)
                    cg += 1;   //green + 1
                start= (float) -(Math.PI);
                break;


            case 5:

                if (alpha >= 10 & alpha <= 100)
                    alpha += 1;

                if (w > sizemin & w<sizemax)
                    w -= sd;  //halfmoon size - difference size
                winkel= (float) 0f;
                multiply=1.5f;
                break;


            case 6:
                if (alpha <= 100 & alpha >= 10)
                    alpha -= 1;  //alpha -1

                winkel= (float) 1.5f;

                if (h > sizemin & h<sizemax)
                    h -= sd;

                break;


        }

         //sketch.fill(cr, cg, cb, alpha);
    //     sketch.strokeWeight((float) sw);
           sketch.stroke(cr, cg, cb, alpha);
           sketch.noFill();
           /*sketch.beginShape();
           sketch.vertex(x, y);
           sketch.bezierVertex(x2, y2, x3, y3, x4, y4);
           sketch.endShape();
*/
           //sketch.arc(x, y, w, h, start, (float) ((float) multiply*Math.PI / winkel));
           sketch.arc(x, y, w, h, 0, (float) ((float) 3*Math.PI / (this.area.w/100*2f)));
      // }
    }
}
