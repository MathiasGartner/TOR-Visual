package TORVisual.Sketches.RandomWalks;

import TORVisual.Data.DiceResult;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;

public class Pepper extends RandomWalker{

    float dh,dw,sizemax,sd,swmax;
    float sw;
    float swd;
    public Pepper(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);
        x = startX;
        y = startY;
        cr = 185; //rgb color value red
        cg = 38; //rgb color value green
        cb = 78;  //rgb color value blue
        alpha =34;
        dx = this.area.w/100.0f*0.7f; //difference x
        dy = this.area.w/100.0f*0.7f; //difference y
        sw= this.area.w/100.0f*0.6f; //stroke weight
        swd= this.area.w/100.0f*0.05f; //stroke weight difference
        swmax=this.area.w/100.0f*1f; //stroke weight maximum
        size =this.area.h/100.0f*1f; //circle size
        sizemax=this.area.h/100.0f*3f; //circle size maximum
        sd=this.area.h/100.0f*0.2f; //size difference
    }

    @Override
    public void draw() {

       for (var result : this.resultsToShow) {
           //int r = randInt();
           int r = result.Result;

           switch (r) {
            case 1:
                if (y+dy < area.h)
                    y += dy;   //+ difference y

                if (sw > swd && sw<swmax)
                    sw -= swd;  //stroke weight - stroke weight distance

                if (cr > 50)
                    cr -= 1;  //red -1
                break;

            case 2:
                if (alpha >= 15 && alpha <= 60)
                    alpha += 1;

                if (size > sd && size<sizemax)
                    size -= sd;  //circle size - difference size

                if (cr > 75)
                    cr += 1;   //red + 1

                break;


            case 3:
                if (alpha <= 60 && alpha >= 15)
                    alpha -= 1;  //alpha -1
                break;

            case 4:
                if (y+dy > 0 && y < area.h)
                    y -= dy; //- difference y

            case 5:

                if (x > dx && x < area.w)
                    x -= dx; //- difference x

                if (size+sd < sizemax)
                    size += sd; //+size difference
                break;


            case 6:

                if (x < area.w-dx)
                    x += dx;   //+ difference x

                if (sw+swd < swmax)
                    sw += swd; //stroke weight + stroke weight distance

                if (cr < 180)
                    cr += 1;   //red + 1
                break;

        }
        sketch.fill(cr, cg, cb, alpha);
      //  sketch.fill(cr, cg, cb, 0);
      //     sketch.stroke(32,79,17,31);
        sketch.strokeWeight((float) sw+2);
        sketch.stroke(65, 0, 3, 10);
        sketch.circle(x, y, (float) size);
    }
    }
}
