package TORVisual.Sketches.RandomWalks;

import TORVisual.Database.DiceResult;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;


public class Baumschwamm extends RandomWalker
{
    float angle_rad, sw, swd, swmax, sizemax, sizemin, sd, dangle;
    public Baumschwamm(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);

        name="Baumschwamm";
        nameLatin="Fomitopsis pinicola";

            colorStart = sketch.color(94, 84, 42);
            colorEnd = sketch.color(227, 190, 120);

            alpha =10;
            sw= this.area.w/100.0f*0.1f; //stroke weight
            swd= this.area.w/100.0f*0.02f; //stroke weight difference
            swmax=this.area.w/100.0f*1.0f; //stroke weight maximum
            dx = this.area.w/100.0f*0.4f; //difference x
            dy = this.area.w/100.0f*0.5f; //difference y
            angle_rad=0.3f;
            //dangle=0.1f;
            dangle=1;
            sw= this.area.w/100.0f*0.1f; //stroke weight
            swd= this.area.w/100.0f*0.02f; //stroke weight difference
            size =this.area.h/100.0f*0.6f; //circle size
            sd=this.area.h/100.0f*0.05f; //size difference

        }

        @Override
        public void draw() {
            for (var result : this.resultsToShow) {
                int r = result.Result;

                switch (r) {
                    case 1:

                        if (sw > swd & sw < swmax)
                            sw -= swd;  //stroke weight - stroke weight distance

                        if (alpha < 30)
                            alpha += 1;
                        angle_rad = angle_rad - dangle;
                        break;

                    case 2:
                        if (alpha > 0)
                            alpha -= 1;  //alpha -1

                        angle_rad = angle_rad + dangle;
                        moveY(-(float) Math.sin(angle_rad)*dy);
                        moveX(-(float) Math.cos(angle_rad)*dx);
                        break;


                    case 3:
                        if (size > sizemin & size < sizemax)
                            size -= sd;  //circle size - difference size
                        //moveY((float) Math.sin(angle_rad)*dy);
                        //moveX((float) Math.cos(angle_rad)*dx);
                        break;

                    case 4:
                        //moveY(+(float) Math.cos(angle_rad)*dy);
                        //moveX(-(float) Math.sin(angle_rad)*dx);
                        break;

                    case 5:

                        if (size + sd < sizemax)
                            size += sd; //+size difference
                        break;


                    case 6:
                        moveY(-(float) Math.cos(angle_rad)*dy);
                        moveX(+(float) Math.sin(angle_rad)*dx);
                        if (sw + swd < swmax)
                            sw += swd; //stroke weight + stroke weight distance

                        break;
                }
                var c = sketch.lerpColor(colorStart, colorEnd, colorPercent);
                this.canvas.fill(c, alpha/2);
                this.canvas.strokeWeight((float) sw);
                this.canvas.stroke(c, alpha);
                //this.canvas.circle(x, y, (float) size);
                this.canvas.point(x,y);
            }
        }
    }
