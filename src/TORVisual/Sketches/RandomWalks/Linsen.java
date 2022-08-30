package TORVisual.Sketches.RandomWalks;

import TORVisual.Database.DiceResult;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;


public class Linsen extends RandomWalker {
    float angle_rad, sw, swd, swmax, sizemax, sizemin, sd, dangle;

    public Linsen(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);
        name = "Linsen";
        nameLatin = "Lens culinaris";

        colorStart = sketch.color(250, 240, 150);
        colorEnd = sketch.color(255, 224, 97);

        alpha = 10;
        dx = this.area.w / 100.0f * 0.5f; //difference x
        dy = this.area.w / 100.0f * 0.4f; //difference y
        angle_rad = 20;
        dangle = 0.4f;
        sw = this.area.w / 100.0f * 0.3f; //stroke weight
        swd = this.area.w / 100.0f * 0.04f; //stroke weight difference
        swmax = this.area.w / 100.0f * 0.5f; //stroke weight maximum
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
                    if (size + sd < sizemax)
                        size += sd; //+size difference
                    moveY(-(float) Math.sin(angle_rad) * dy);
                    moveX(-(float) Math.cos(angle_rad) * dx);
                    break;

                case 2:
                    if (sw > swd & sw < swmax)
                        sw -= swd;  //stroke weight - stroke weight distance
                    break;


                case 3:
                    if (alpha <= 30)
                        alpha += 1;
                    //stroke weight + stroke weight distance


                    break;

                case 4:
                    if (sw + swd < swmax)
                        sw += swd;
                    break;

                case 5:
                    if (alpha >= 5)
                        alpha -= 1;  //alpha -1


                    break;


                case 6:
                    moveY((float) Math.sin(angle_rad) * dy);
                    moveX((float) Math.cos(angle_rad) * dx);
                    angle_rad = angle_rad - dangle;
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