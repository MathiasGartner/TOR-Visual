package TORVisual.Sketches.RandomWalks;

import TORVisual.Data.DiceResult;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;


public class Kork extends RandomWalker
{
    float angle_rad, sw, swd, swmax, sizemax, sizemin, sd, dangle;
    public Kork(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);
        name="Kork";
        nameLatin="Quercus suber";

        colorStart = sketch.color(199, 156, 127);
        colorEnd = sketch.color(120, 80, 53);

        alpha =10;
        dx = this.area.w/100.0f*0.7f; //difference x
        dy = this.area.w/100.0f*0.8f; //difference y
        angle_rad=20;
        dangle=0.4f;
        sw= this.area.w/100.0f*0.3f; //stroke weight
        swd= this.area.w/100.0f*0.04f; //stroke weight difference
        swmax=this.area.w/100.0f*1f; //stroke weight maximum
        //size =this.area.h/100.0f*1f; //circle size
        //sizemax=this.area.h/100.0f*1.6f; //circle size maximum
        //sizemin=this.area.h/100.0f*0.4f;
        //sd=this.area.h/100.0f*0.1f; //size difference

    }

    @Override
    public void draw() {
        for (var result : this.resultsToShow) {
            int r = result.Result;

            switch (r) {
                case 1:
                    moveY((float) Math.sin(angle_rad)*dy);
                    moveX((float) Math.cos(angle_rad)*dx);
                    if (sw > swd & sw < swmax)
                        sw -= swd;  //stroke weight - stroke weight distance

                    break;

                case 2:
                    if (alpha >= 5 & alpha <= 50)
                        alpha += 1;

                   // if (size > sizemin & size < sizemax)
                   //     size -= sd;  //circle size - difference size

                    angle_rad = angle_rad - dangle;

                    break;


                case 3:
                    if (alpha <= 50 & alpha >= 5)
                        alpha -= 1;  //alpha -1

                    break;

                case 4:

                    angle_rad = angle_rad + dangle;
                    break;

                case 5:


                    if (size + sd < sizemax)
                        size += sd; //+size difference
                    break;


                case 6:
                    moveY(-(float) Math.sin(angle_rad)*dy);
                    moveX(-(float) Math.cos(angle_rad)*dx);

                    if (sw + swd < swmax)
                        sw += swd; //stroke weight + stroke weight distance

                    break;
            }
            var c = sketch.lerpColor(colorStart, colorEnd, colorPercent);

            this.canvas.fill(c, alpha);
            this.canvas.strokeWeight((float) sw);
            this.canvas.stroke(c, alpha);
            //this.canvas.circle(x, y, (float) size);
            this.canvas.point(x,y);
        }
    }
}
