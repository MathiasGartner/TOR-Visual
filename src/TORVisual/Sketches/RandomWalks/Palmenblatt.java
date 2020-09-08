package TORVisual.Sketches.RandomWalks;

import TORVisual.Database.DiceResult;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;


public class Palmenblatt extends RandomWalker
{
    float dh,dw,sizemax,sd,swmax, xdistance, ydistance, distancemax, dxrotate, dyrotate;
    float sw;
    float swd;
    public Palmenblatt(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);
        name="Palmenblatt";
        nameLatin="Dypsis lutescens";

            colorStart = sketch.color(40, 64, 47);
            colorEnd = sketch.color(37, 77, 49);

            dx = this.area.w/100.0f*0.5f; //difference x
            dy = this.area.w/100.0f*0.6f; //difference y

            dxrotate = this.area.w/100.0f*0.1f;
            dyrotate = this.area.w/100.0f*0.1f;

            xdistance = this.area.w/100.0f*0.02f;
            ydistance = this.area.w/100.0f*0.02f;

            distancemax = this.area.w/100.0f*0.2f;

            x2 = x1+xdistance;
            y2= y1+ydistance;


            cr = 110; //rgb color value red
            cg = 56; //rgb color value green
            cb = 23;  //rgb color value blue
            alpha =40;
            sw= this.area.w/100.0f*0.4f; //stroke weight
            swd= this.area.w/100.0f*0.05f; //stroke weight difference
            swmax=this.area.w/100.0f*1f; //stroke weight maximum

        }

        @Override
        public void draw() {
            for (var result : this.resultsToShow) {
                int r = result.Result;

                switch (r) {
                    case 1:
                        if (x1+dx <= area.w && x2+dx <= area.w)  {
                            x1 += dx;
                            x2 += dx;
                        }
                        break;

                    case 2:
                        if ((x1-dx >= 0) && (x2-dx >= 0))
                        {
                            x1 -= dx;
                            x2 -= dx;
                        }
                        break;


                    case 3:

                        if ((y1+dy <= area.h) && (y2+dy <= area.h))
                        {
                            y1 += dy;
                            y2 += dy;
                        }
                        break;


                    case 4:
                        if ((y1-dy >= 0) && (y2-dy >= 0)) {
                            y1 -= dy;
                            y2 -= dy;
                        }
                        break;

                    case 5:
                        if (this.recentDiceResults.isEmpty() || this.recentDiceResults.get(0).Result<4)
                        {
                            if (Math.abs(x2 - x1)<distancemax - dxrotate && x1+dxrotate <= area.w)
                            {
                                if (x1 < x2) {
                                    x1 -= dxrotate;
                                }
                                else {
                                    x1 += dxrotate;
                                }
                            }
                        }
                        else {
                            if (Math.abs(y2 - y1) < distancemax - dyrotate && (y1 + dyrotate <= area.h)) {
                                if (y1 < y2) {
                                    y1 -= dyrotate;
                                }
                                else {
                                    y1 += dyrotate;
                                }
                            }
                        }

                        break;


                    case 6:
                        if (this.recentDiceResults.isEmpty() || this.recentDiceResults.get(0).Result<4)
                        {
                            if (x1 < x2) {
                                x1 += dxrotate;
                            }
                            else {
                                x1 -= dxrotate;
                            }
                        }
                        else {
                            if (y1 < y2) {
                                y1 += dyrotate;
                            }
                            else {
                                y1 -= dyrotate;
                            }
                        }

                        break;

                }


                this.canvas.strokeWeight(1);
                var c = sketch.lerpColor(colorStart, colorEnd, colorPercent);
                this.canvas.stroke(c, alpha);

                this.canvas.line(x1, y1, x2, y2);
        /*if (Math.abs(x1 - x2) > distancemax) {
            int asdf = 0;
        }*/
                //System.out.println(x1 + ", " + y1 + ", " + x2 + ", " + y2);
            }
        }
    }
