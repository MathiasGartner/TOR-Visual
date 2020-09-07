package TORVisual.Sketches.RandomWalks;

import TORVisual.Database.DiceResult;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;

public class Pepper extends RandomWalker{

    float dh,dw,sizemax,sd,swmax;
    float sw;
    float swd;
    public Pepper(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);
        name="Pfeffer";
        nameLatin="Schinus";
        colorStart = canvas.color(164, 55, 60);
        /*colorEnd = canvas.color(172,76,78);

        dColor = 0.05f;*/
        alpha =10;


        dx = this.canvas.width/100.0f*0.5f; //difference x
        dy = this.canvas.width/100.0f*0.5f; //difference y
        sw= this.canvas.width/100.0f*0.1f; //stroke weight
        swd= this.canvas.width/100.0f*0.02f; //stroke weight difference
        swmax=this.canvas.width/100.0f*0.5f; //stroke weight maximum
        size =this.canvas.height/100.0f*0.3f; //circle size
        sizemax=this.canvas.height/100.0f*1.5f; //circle size maximum
        sd=this.canvas.height/100.0f*0.05f; //size difference
    }

    @Override
    public void draw() {

       for (var result : this.resultsToShow) {
           int r = result.Result;

           switch (r) {
            case 1:
               moveX(dx);

                if (sw > swd && sw<swmax)
                {
                    sw -= swd;  //stroke weight - stroke weight distance
                }
                if (colorPercent > 0) {
                    colorPercent -= dColor;
                }

                break;

            case 2:
                moveX(-dx);
                if (alpha >= 1 && alpha <= 30) {
                    alpha += 0.2;
                }
                if (size > sd && size<sizemax) {
                    size -= sd;  //circle size - difference size
                }
                if (colorPercent < 1) {
                    colorPercent += dColor;
                }
                break;


            case 3:
                if (alpha <= 30 && alpha > 1.2) {
                    alpha -= 0.2;  //alpha - 0.2
                }
                break;

            case 4:
                moveY(-dy);

            case 5:


                if (size + sd < sizemax) {
                    size += sd; //+size difference
                }
                break;


            case 6: {
                moveY(dy);

                if (sw + swd < swmax) {
                    sw += swd; //stroke weight + stroke weight distance
                }

                break;
            }
        }

        //var c = canvas.lerpColor(colorStart, colorEnd, colorPercent);

        this.canvas.fill(200, 95, 100, 10);
        this.canvas.noStroke();
        //this.canvas.strokeWeight((float) sw+2);
        //this.canvas.stroke(colorStart,0);
        this.canvas.circle(x, y, (float) size);
        //System.out.println(canvas.red(c) + " " + canvas.green(c) + " " + canvas.blue(c)+ " " + alpha);
    }
    }
}
