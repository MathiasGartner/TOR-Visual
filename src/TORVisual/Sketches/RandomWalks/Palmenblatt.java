package TORVisual.Sketches.RandomWalks;

import TORVisual.Data.DiceResult;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;


public class Palmenblatt extends RandomWalker
{
    float angle_rad, sw, swd, swmax, sizemax, sizemin, sd, dangle,  xdistance, ydistance, distancemax;
    public Palmenblatt(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);
        name="Palmenblatt";
        nameLatin="Dypsis lutescens";

            colorStart = sketch.color(40, 64, 47);
            colorEnd = sketch.color(37, 77, 49);

        x1 = startX;
        y1 = startY;
        x2 = x1+xdistance;
        y2= y1+ydistance;

        dx = this.area.w/100.0f*0.6f; //difference x
        dy = this.area.w/100.0f*0.8f; //difference y

        xdistance = this.area.w/100.0f*0.8f;
        ydistance = this.area.w/100.0f*0.4f;

        distancemax = this.area.w/100.0f*2f;



            alpha =10;
            sw= this.area.w/100.0f*0.2f; //stroke weight
            swd= this.area.w/100.0f*0.05f; //stroke weight difference
            swmax=this.area.w/100.0f*1f; //stroke weight maximum
            dx = this.area.w/100.0f*0.6f; //difference x
            dy = this.area.w/100.0f*0.6f; //difference y
            angle_rad=5;
            //dangle=0.1f;
            dangle=1;
            sw= this.area.w/100.0f*0.2f; //stroke weight
            swd= this.area.w/100.0f*0.05f; //stroke weight difference
            size =this.area.h/100.0f*1f; //circle size
            sd=this.area.h/100.0f*0.1f; //size difference

        }

        @Override
        public void draw() {
            for (var result : this.resultsToShow) {
                int r = result.Result;

                switch (r) {
                    case 1:
                        //moveY((float) Math.sin(angle_rad)*dy);
                        //moveX((float) Math.cos(angle_rad)*dx);
                        if (sw > swd & sw < swmax)
                            sw -= swd;  //stroke weight - stroke weight distance

                        break;

                    case 2:
                        if (alpha < 30)
                            alpha += 1;

                        if (size > sizemin & size < sizemax)
                            size -= sd;  //circle size - difference size

                        angle_rad = angle_rad - dangle;

                        if (x1+dx <= area.w && x2+dx <= area.w && (Math.abs((x2+dx)-x1)<distancemax)) {
                            x1 +=  ((float) Math.sin(angle_rad)*dx);
                            y1 +=((float) Math.cos(angle_rad)*dy);

                            x2 += ((float) Math.sin(angle_rad)*dx);
                            y2 += ((float) Math.cos(angle_rad)*dx);
                            //x1 += dx;
                            //x2 += dx;
                        }


                        break;


                    case 3:
                        if ((x1-dx >= 0) && (x2-dx >= 0))
                        {
                            x1 -=  ((float) Math.sin(angle_rad)*dx);
                            y1 -=((float) Math.cos(angle_rad)*dy);

                            x2 -= ((float) Math.sin(angle_rad)*dx);
                            y2 -= ((float) Math.cos(angle_rad)*dx);
                        }
                        if (alpha > 0)
                            alpha -= 1;  //alpha -1

                        angle_rad = angle_rad + dangle;
                        break;

                    case 4:
                    //    if ((y1+dy <= area.h) && (y2+dy <= area.h) && Math.abs((y2+dy)-y1)<distancemax)
                        //{
                            //y1 -= ((float) Math.sin(angle_rad)*dy);

                            //y2 -= ((float) Math.sin(angle_rad)*dy);
                      //  }
                        break;

                    case 5:

                   //     if ((y1+dy <= area.h) && (y2+dy <= area.h) && Math.abs((y2+dy)-y1)<distancemax)
                       // {
                            //y1 += ((float) Math.sin(angle_rad)*dy);
                            //y2 += ((float) Math.sin(angle_rad)*dy);
                     //   }
                        if (size + sd < sizemax)
                            size += sd; //+size difference
                        break;


                    case 6:
                        if ((y1+dy <= area.h) && (y2+dy <= area.h) && Math.abs((y2+dy)-y1)<distancemax) {
                            x1 += ((float) Math.sin(angle_rad) * dx);
                            y1 += ((float) Math.cos(angle_rad) * dy);

                            x2 += ((float) Math.sin(angle_rad) * dx);
                            y2 += ((float) Math.cos(angle_rad) * dx);
                        }
                        if (sw + swd < swmax)
                            sw += swd; //stroke weight + stroke weight distance

                        break;
                }
                var c = sketch.lerpColor(colorStart, colorEnd, colorPercent);
                this.canvas.fill(c, alpha);
                this.canvas.strokeWeight((float) sw);
                this.canvas.stroke(c, alpha);
                //this.canvas.circle(x, y, (float) size);
                this.canvas.line(x1, y1, x2, y2);
            }
        }
    }