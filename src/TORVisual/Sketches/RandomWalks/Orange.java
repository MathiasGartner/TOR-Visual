package TORVisual.Sketches.RandomWalks;

import TORVisual.Data.DiceResult;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;


public class Orange extends RandomWalker
{
    float dh,dw,sizemax, sizemin, sd,swmax;
    float sw;
    float swd;
    public Orange(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);

        colorStart = sketch.color(255, 151, 33);
        colorEnd = sketch.color(240, 77, 40);

        alpha =10;
        dx = this.area.w/100.0f*0.6f; //difference x
        dy = this.area.w/100.0f*0.6f; //difference y

        sw= this.area.w/100.0f*0.05f; //stroke weight
        swd= this.area.w/100.0f*0.05f; //stroke weight difference
        swmax=this.area.w/100.0f*0.5f; //stroke weight maximum
        size =this.area.h/100.0f*0.7f; //circle size
        sizemax=this.area.h/100.0f*1.8f; //circle size maximum
        sizemin=this.area.h/100.0f*0.2f;
        sd=this.area.h/100.0f*0.05f; //size difference

    }

    @Override
    public void draw() {
        for (var result : this.resultsToShow) {
            int r = result.Result;

            switch (r) {
                case 1:

                    if (colorPercent > 0) {
                        colorPercent -= dColor;
                    }
                    if (sw > swd & sw < swmax) {
                        sw -= swd;  //stroke weight - stroke weight distance
                    }
                    break;

                case 2:
                    moveX(-dx);
                    if (size > sizemin & size < sizemax) {
                        size -= sd;  //circle size - difference size
                    }
                    break;


                case 3:
                    moveX(dx);

                case 4:
                    moveY(-dy);
                    if (colorPercent < 1) {
                        colorPercent += dColor;
                    }
                    break;

                case 5:


                    if (size + sd < sizemax) {
                        size += sd; //+size difference
                    }
                    break;


                case 6:
                    moveY(dy);


                    if (sw + swd < swmax) {
                        sw += swd; //stroke weight + stroke weight distance
                    }
                    break;
            }
            var c = sketch.lerpColor(colorStart, colorEnd, colorPercent);
            this.canvas.fill(c, 5);
            this.canvas.strokeWeight((float) sw);
            this.canvas.stroke(c, 30);
            this.canvas.circle(x, y, (float) size);
        }
    }
}
