package TORVisual.Sketches.RandomWalks;

import TORVisual.Database.DiceResult;
import TORVisual.SketchArea;
import TORVisual.Utils.Utils;
import processing.core.PApplet;

import java.util.ArrayList;

public class Iceland_Moss extends RandomWalker {

    float dh, dw, sizemax, sd, swmax;
    float sw;
    float swd;

    public Iceland_Moss(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);

        name = "Islandmoos";
        nameLatin = "... Islandica";

        x = startX;
        y = startY;
        cr = 126; //rgb color value red
        cg = 133; //rgb color value green
        cb = 83;  //rgb color value blue
        dx = this.area.w / 100.0f * 0.9f; //difference x
        dy = this.area.w / 100.0f * 0.9f; //difference y
        // dh = this.area.w / 300;
        // dw = this.area.w / 300;
        //w=this.area.w/200;
        //wmax=this.area.w/100;
        //h=this.area.h/200;
        sw = this.area.w / 100.0f * 0.15f; //stroke weight
        swd = this.area.w / 100.0f * 0.01f; //stroke weight difference
        swmax = this.area.w / 100.0f * 0.4f; //stroke weight maximum
        size = this.area.h / 100.0f * 1.3f; //circle size
        sizemax = this.area.h / 100.0f * 4f; //circle size maximum
        sd = this.area.h / 100.0f * 0.4f; //size difference
    }

    @Override
    public void draw() {

        //for (var result : this.resultsToShow) {
        int r = Utils.randDiceResult();
        //int r = result.Result;

        switch (r) {
            case 1:
                if (y + dy >= area.h)
                    y -= dy;

                if (y + dy < area.h)
                    y += dy;   //+ difference y

                if (sw > swd & sw < swmax)
                    sw -= swd;  //stroke weight - stroke weight distance

                if (cg > 134)
                    cg -= 1;  //green -1
                break;

            case 2:
                if (x + dx >= area.w)
                    x -= dx;

                if (x < area.w)
                    x += dx;   //+ difference x

                if (sw + swd < swmax)
                    sw += swd; //stroke weight + stroke weight distance

                if (cg < 255)
                    cg += 1;   //green + 1
                break;


            case 3:
                if (x > dx & x < area.w)
                    x -= dx; //- difference x

                if (size + sd < sizemax)
                    size += sd; //+size difference
                break;

            case 4:
                if (y > dy & y < area.h)
                    y -= dy; //- difference y

            case 5:

                if (alpha <= 100 & alpha >= 20)
                    alpha -= 1;  //alpha -1
                break;

            case 6:
                //if (size<(this.area.h/100.0*5))
                //    size += ds;
                //if (h > ds)
                //    h -= ds;

                if (alpha >= 20 & alpha <= 100)
                    alpha += 1;

                if (size > sd & size < sizemax)
                    size -= sd;  //circle size - difference size
                break;
        }
        //sketch.fill(cr, cg, cb, alpha);
        sketch.fill(cr, cg, cb, 0);
        sketch.strokeWeight((float) sw);
        sketch.stroke(cr, cg, cb, alpha);
        sketch.circle(x, y, (float) size);
        // }
    }
}
