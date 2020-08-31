package TORVisual.Sketches.RandomWalks;

import TORVisual.Data.DiceResult;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;


public class AcaciaBohnen extends RandomWalker
{
        float angle_rad, sw, swd, swmax, sizemax, sizemin, sd;
    public AcaciaBohnen(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
            super(sketch, area, resultsToShow);
            cr = 190; //rgb color value red
            cg = 125; //rgb color value green
            cb = 255;  //rgb color value blue
            alpha =10;
            sw= this.area.w/100.0f*0.2f; //stroke weight
            swd= this.area.w/100.0f*0.05f; //stroke weight difference
            swmax=this.area.w/100.0f*1f; //stroke weight maximum
            dx = this.area.w/100.0f*0.6f; //difference x
            dy = this.area.w/100.0f*0.6f; //difference y
            angle_rad=5;
            sw= this.area.w/100.0f*0.2f; //stroke weight
            swd= this.area.w/100.0f*0.05f; //stroke weight difference
            size =this.area.h/100.0f*1f; //circle size
            sd=this.area.h/100.0f*0.1f; //size difference
        name="Acacia Bohnen";
        nameLatin="Delonix regia";
        }

        @Override
        public void draw() {
            for (var result : this.resultsToShow) {
                int r = result.Result;

                switch (r) {
                    case 1:
                        moveY((float) Math.cos(angle_rad));
                        if (sw > swd & sw < swmax)
                            sw -= swd;  //stroke weight - stroke weight distance

                        break;

                    case 2:
                        if (alpha >= 5 & alpha <= 50)
                            alpha += 1;

                        if (size > sizemin & size < sizemax)
                            size -= sd;  //circle size - difference size

                        if (angle_rad>1)
                        {
                            angle_rad = angle_rad - 1;
                        }

                        break;


                    case 3:
                        if (alpha <= 50 & alpha >= 5)
                            alpha -= 1;  //alpha -1
                        if (angle_rad<360)
                        {
                            angle_rad = angle_rad + 1;
                        }
                        break;

                    case 4:
                        moveY(-(float) Math.cos(angle_rad));
                        break;

                    case 5:
                        moveX((float)(Math.sin(angle_rad)));

                        if (size + sd < sizemax)
                            size += sd; //+size difference
                        break;


                    case 6:

                        moveX(-(float)(Math.sin(angle_rad)));

                        if (sw + swd < swmax)
                            sw += swd; //stroke weight + stroke weight distance

                        break;
                }
                this.canvas.fill(cr, cg, cb, 0);
                this.canvas.strokeWeight((float) sw);
                this.canvas.stroke(cr, cg, cb, alpha);
                this.canvas.circle(x, y, (float) size);
            }
        }
    }