package TORVisual.Sketches.RandomWalks;

import TORVisual.Database.DiceResult;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;

public class Sternanis extends RandomWalker{

    float y, angle,halfAngle,npointsmax;
    float radius1;
    float radius2;
    int npoints;
    public Sternanis(PApplet sketch, SketchArea area,  ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);
        name="Sternanis";
        nameLatin="Illicium verum";
        cr = 99;
        cg = 40;
        cb = 22;
        dx = (float) this.area.w/100*0.4f;
        dy = (float) this.area.w/100*0.4f;
        ds = (float) this.area.w/100*1f;
        alpha=10;
        x = startX;
        y = startY;
        radius1=0.2f;
        radius2=1.2f;
        npoints=4;
        npointsmax=10;
        float angle = (float) ((2*Math.PI) / npoints);
        float halfAngle = (float) (angle/2.0);
    }

    @Override
    public void draw() {
        for (var result : this.resultsToShow) {
            //int r = randInt();
            int r = result.Result;

            switch (r) {
                case 1:
                    if (x+dx >= area.w)
                        x -= dx;

                    if (x < area.w)
                        x += dx;
                    break;

                case 2:
                    if (x > dx & x < area.w)
                        x -= dx;
                    break;


                case 3:
                    if (y > dy & y < area.h)
                        y -= dy;
                    break;

                case 4:
                    if (y+dy >= area.h)
                        y -= dy;

                    if (y+dy < area.h)
                        y += dy;
                    break;

                case 5:

                 if (npoints<npointsmax)
                     npoints=npoints++;

                        break;
                case 6:
                    if (npoints>3)
                        npoints=npoints--;

                        break;

            }
            this.canvas.fill(cr, cg, cb, alpha);
            this.canvas.stroke(cr, cg, cb, alpha);
            star(x,y,radius1,radius2,npoints);
            //sketch.triangle(x1, y1, x2, y2, x3, y3);
            /*sketch.beginShape();
            for (float a = 0; a < (2*(Math.PI)); a += angle) {
                float sx = (float) (x + Math.cos(a) * radius2);
                float sy = (float) (y + Math.sin(a) * radius2);
                sketch.vertex(sx, sy);
                sx = (float) (x + Math.cos(a+halfAngle) * radius1);
                sy = (float) (y + Math.sin(a+halfAngle) * radius1);
                sketch.vertex(sx, sy);
            }
            sketch.endShape();

             */
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



