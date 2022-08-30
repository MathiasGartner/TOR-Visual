package TORVisual.Sketches.RandomWalks;

import TORVisual.Database.DiceResult;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;


public class Palmringe extends RandomWalker {
    float angle_rad, sw, swd, swmax, sizemax, sizemin, sd, dangle;

    public Palmringe(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);
        name = "Palmringe";
        nameLatin = "Borassus flabellifer";

        colorStart = sketch.color(56, 37, 2);
        colorEnd = sketch.color(46, 34, 15);

        alpha = 20;
        dx = this.area.w / 100.0f * 0.4f; //difference x
        dy = this.area.w / 100.0f * 0.5f; //difference y
        angle_rad = 20;
        dangle = 0.4f;
        sw = this.area.w / 100.0f * 0.4f; //stroke weight
        swd = this.area.w / 100.0f * 0.04f; //stroke weight difference
        swmax = this.area.w / 100.0f * 0.8f; //stroke weight maximum
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
                    if (sw + swd < swmax)
                        sw += swd; //stroke weight + stroke weight distance
                    //  moveY(-(float) Math.sin(angle_rad)*dy);
                    //  moveX(-(float) Math.cos(angle_rad)*dx);


                    break;

                case 2:
                    if (sw > swd & sw < swmax)
                        sw -= swd;  //stroke weight - stroke weight distance
                    if (alpha >= 10 & alpha <= 40)
                        alpha += 1;
                    break;


                case 3:
                    angle_rad = angle_rad - dangle;

                    break;

                case 4:
                    if (alpha <= 50 & alpha >= 20)
                        alpha -= 1;  //alpha -1
                    //angle_rad = angle_rad + dangle;
                    break;

                case 5:
                    moveY((float) Math.sin(angle_rad) * dy);
                    moveX((float) Math.cos(angle_rad) * dx);

                    if (size + sd < sizemax)
                        size += sd; //+size difference
                    break;


                case 6:

                    break;
            }
            var c = sketch.lerpColor(colorStart, colorEnd, colorPercent);

            this.canvas.fill(c, alpha);
            this.canvas.strokeWeight((float) sw);
            this.canvas.stroke(c, alpha);
            //this.canvas.circle(x, y, (float) size);
            this.canvas.point(x, y);
        }
    }
}
