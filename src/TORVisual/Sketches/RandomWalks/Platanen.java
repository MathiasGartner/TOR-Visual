package TORVisual.Sketches.RandomWalks;

import TORVisual.Data.DiceResult;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;

public class Platanen extends RandomWalker
{
 float angle_rad, sw, swd, swmax, sizemax, sizemin, sd, dangle;
 public Platanen(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);


        colorStart = sketch.color(54, 87, 64);
        colorEnd = sketch.color(75, 105, 84);

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

        break;


        case 3:
        if (alpha > 0)
        alpha -= 1;  //alpha -1

        angle_rad = angle_rad + dangle;
        break;

        case 4:
        //moveY(-(float) Math.sin(angle_rad)*dy);
        //moveX(-(float) Math.cos(angle_rad)*dx);
        break;

        case 5:
        moveY(-(float) Math.sin(angle_rad)*dy);
        moveX(+(float) Math.cos(angle_rad)*dx);
        if (size + sd < sizemax)
        size += sd; //+size difference
        break;


        case 6:
        moveY(+(float) Math.sin(angle_rad)*dy);
        moveX(-(float) Math.cos(angle_rad)*dx);
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


/*
public class Platanen extends RandomWalker
{
    float sw;
    float swd;
    float y, angle,halfAngle,npointsmax;
    float radius1;
    float radius2, radius2max,rd;
    int npoints;
    public Platanen(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);
        colorStart = sketch.color(85, 194, 112);
        colorEnd = sketch.color(84, 170, 104);

        x = startX;
        y = startY;
        radius1=0.1f;
        radius2=0.5f;
        radius2max=1.2f;
        rd=0.04f;
        npoints=4;
        npointsmax=10;
        float angle = (float) ((2*Math.PI) / npoints);
        float halfAngle = (float) (angle/2.0);
        alpha =10;
        dx = this.area.w/100.0f*0.6f; //difference x
        dy = this.area.w/100.0f*0.6f; //difference y

    }

    @Override
    public void draw() {
        for (var result : this.resultsToShow) {
            int r = result.Result;
            switch (r) {
                    case 1:
                        if (x > dx & x < area.w)
                            x -= dx;

                    if (radius2>rd && radius2<(radius2max-rd))
                    {
                        radius2=radius2-rd;
                    }

                        break;

                    case 2:
                        if (colorPercent > 0) {
                            colorPercent -= dColor;
                        }
                        break;


                    case 3:
                        if (x+dx >= area.w)
                            x -= dx;

                        if (x < area.w)
                            x += dx;

                        break;

                    case 4:
                        if (y > dy & y < area.h)
                            y -= dy;

                        if (colorPercent < 1) {
                            colorPercent += dColor;
                        }
                        break;

                    case 5:
                        if (y+dy >= area.h)
                            y -= dy;

                        if (y+dy < area.h)
                            y += dy;
                        if (npoints<npointsmax)
                            npoints=npoints++;

                        break;
                    case 6:
                        if (npoints>3)
                            npoints=npoints--;

                        if (radius2>rd && radius2<radius2max-rd)
                        {
                            radius2=radius2+rd;
                        }
                        break;

            }

            var c = sketch.lerpColor(colorStart, colorEnd, colorPercent);
            this.canvas.fill(c, 20);
            this.canvas.stroke(c,30);
            star(x,y,radius1,radius2,npoints);


        }
    }
    void star(float x, float y, float radius1, float radius2, int npoints)
    {
        float angle = (float) ((2*Math.PI) / npoints);
        float halfAngle = (float) (angle/2.0);

        this.canvas.beginShape();
        for (float a = 0; a < (2*(Math.PI)); a += angle) {
            float sx = (float) (x + Math.cos(a) * radius2);
            float sy = (float) (y + Math.sin(a) * radius2);
            this.canvas.vertex(sx, sy);
            sx = (float) (x + Math.cos(a+halfAngle) * radius1);
            sy = (float) (y + Math.sin(a+halfAngle) * radius1);
            this.canvas.vertex(sx, sy);
        }
        this.canvas.endShape();
    }
}
*/