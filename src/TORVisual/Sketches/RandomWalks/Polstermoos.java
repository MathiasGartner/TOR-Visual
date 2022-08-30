package TORVisual.Sketches.RandomWalks;

import TORVisual.Database.DiceResult;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;

public class Polstermoos extends RandomWalker {

    float dh, dw, sizemax, sizemin, sd, swmax, x4, y4, winkel, multiply, start;
    float sw;
    float swd;

    public Polstermoos(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);
        name = "Polstermoos";
        nameLatin = "Leucobryum glaucum";

        x = startX;
        y = startY;
        w = this.area.w / 100.0f * 1.0f;
        h = this.area.w / 100.0f * 0.6f;
        sd = this.area.h / 100.0f * 0.1f; //size difference
        sizemax = this.area.w / 100.0f * 1.5f;
        sizemin = this.area.w / 100.0f * 0.15f;
        winkel = (float) 1.5f;
        multiply = 0f;
        start = 0;

        cr = 15; //rgb color value red
        cg = 80; //rgb color value green
        cb = 48;  //rgb color value blue
        dx = this.area.w / 100.0f * 0.6f; //difference x
        dy = this.area.w / 100.0f * 0.6f; //difference y
        // dh = this.area.w / 300;
        // dw = this.area.w / 300;
        //w=this.area.w/200;
        //wmax=this.area.w/100;
        //h=this.area.h/200;
        sw = this.area.w / 100.0f * 0.15f; //stroke weight
        swd = this.area.w / 100.0f * 0.01f; //stroke weight difference
        swmax = this.area.w / 100.0f * 0.4f; //stroke weight maximum


    }

    @Override
    public void draw() {

        for (var result : this.resultsToShow) {
            int r = result.Result;

            switch (r) {
                case 1:
                    this.moveY(dy);
                    if (sw > swd & sw < swmax)
                        sw -= swd;  //stroke weight - stroke weight distance

                    if (cg > 60)
                        cg -= 1;  //green -1

                    //x=x+0.1f;
                    break;

                case 2:
                    this.moveX(dx);
                    if (sw + swd < swmax)
                        sw += swd; //stroke weight + stroke weight distance

                    if (cg < 144)
                        cg += 1;   //green + 1
                    break;


                case 3:
                    this.moveX(-dx);

                    if (w + sd < sizemax)
                        w += sd; //+w difference

                    if (cb < 55)
                        cb += 1;   //blue + 1
                    start = (float) -(Math.PI);
                    break;

                case 4:
                    this.moveY(-dy);

                    if (cb > 0)
                        cb -= 1;   //blue - 1
                    //multiply=2.5f;

                    if (h + sd < sizemax)
                        h += sd; //+w difference

                    //start= (float) Math.PI;
                    break;

                case 5:

                    if (alpha <= 100 & alpha >= 20)
                        alpha -= 1;  //alpha -1

                    //winkel= (float) 2f;

                    if (h > sizemin & h < sizemax)
                        h -= sd;

                    start = 0;
                    break;


                case 6:
                    //if (size<(this.area.h/100.0*5))
                    //    size += ds;
                    //if (h > ds)
                    //    h -= ds;

                    if (alpha >= 20 & alpha <= 100)
                        alpha += 1;

                    if (w > sizemin & w < sizemax)
                        w -= sd;  //halfmoon size - difference size
                    //winkel= (float) 0f;
                    //multiply=1.5f;
                    break;
            }

            //sketch.fill(cr, cg, cb, alpha);
            //     sketch.strokeWeight((float) sw);
            this.canvas.stroke(cr, cg, cb, alpha);
            this.canvas.noFill();
           /*sketch.beginShape();
           sketch.vertex(x, y);
           sketch.bezierVertex(x2, y2, x3, y3, x4, y4);
           sketch.endShape();
*/
            this.canvas.arc(x, y, w, h, start, (float) ((float) multiply * Math.PI / winkel));

        }
    }
}
