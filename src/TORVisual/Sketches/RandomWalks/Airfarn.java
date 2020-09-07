package TORVisual.Sketches.RandomWalks;

import TORVisual.Database.DiceResult;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;


public class Airfarn extends RandomWalker
{
    float angle_rad, sw, swd, swmax, sizemax, sizemin, sd, dangle,hmax,hmin;
    public Airfarn(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);

            colorStart = sketch.color(52, 94, 92);
            colorEnd = sketch.color(72, 112, 93);

        x = startX;
        y = startY;
            alpha =10;
            sw= this.area.w/100.0f*0.2f; //stroke weight
            swd= this.area.w/100.0f*0.05f; //stroke weight difference
            swmax=this.area.w/100.0f*1f; //stroke weight maximum
            dx = this.area.w/100.0f*0.6f; //difference x
            dy = this.area.w/100.0f*0.6f; //difference y
            w=this.canvas.width/100*0.4f;
            h=this.canvas.height/100*0.8f;
            hmax=this.canvas.height/100*1.5f;
            hmin=this.canvas.height/100*0.4f;
            angle_rad=5;
            //dangle=0.2f;
            dangle=1;
            sw= this.area.w/100.0f*0.2f; //stroke weight
            swd= this.area.w/100.0f*0.05f; //stroke weight difference
            sd=this.area.h/100.0f*0.1f; //size difference

        name="Air-Fern";
        nameLatin="Sertularia argentea";

        }

        @Override
        public void draw() {
            for (var result : this.resultsToShow) {
                int r = result.Result;

                switch (r) {
                    case 1:

                        angle_rad = angle_rad - dangle;

                        break;

                    case 2:
                        if (alpha < 30)
                            alpha += 1;



                        moveY((float) Math.sin(angle_rad)*dy);
                        moveX((float) Math.cos(angle_rad)*dx);
                        if (sw > swd & sw < swmax)
                            sw -= swd;  //stroke weight - stroke weight distance

                        angle_rad = angle_rad + dangle;
                        break;


                    case 3:
                        if (h < hmax)
                            h += sd;

                        if (alpha > 0)
                            alpha -= 1;  //alpha -1

                        if (size > sizemin & size < sizemax)
                            size -= sd;  //circle size - difference size


                        break;

                    case 4:
                        if (w < hmax)
                            w += sd;
                        //moveY(-(float) Math.sin(angle_rad)*dy);
                        //moveX(-(float) Math.cos(angle_rad)*dx);
                        break;

                    case 5:
                        if (w > hmin)
                            w -= sd;
                        moveY(-(float) Math.sin(angle_rad)*dy);
                        moveX(+(float) Math.cos(angle_rad)*dx);
                        if (size + sd < sizemax)
                            size += sd; //+size difference
                        break;


                    case 6:
                        if (h>hmin)
                            h -= sd;
                        //moveY(+(float) Math.sin(angle_rad)*dy);
                        //moveX(-(float) Math.cos(angle_rad)*dx);
                        if (sw + swd < swmax)
                            sw += swd; //stroke weight + stroke weight distance

                        break;
                }
                var c = sketch.lerpColor(colorStart, colorEnd, colorPercent);

                this.canvas.fill(c, alpha);
                this.canvas.strokeWeight((float) sw);
                this.canvas.stroke(c, alpha);
                //sketch.arc(x, y, w, h, 0, (float) ((float) 3*Math.PI / (this.area.w/100*2f)));
                this.canvas.point(x,y);
                //this.canvas.ellipse(x, y, w, h);
            }
        }
    }
