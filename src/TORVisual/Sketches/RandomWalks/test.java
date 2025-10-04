package TORVisual.Sketches.RandomWalks;

import TORVisual.Database.DiceResult;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;


public class test extends RandomWalker {


    public test(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);

        x = startX;
        y = startY;
        dx = this.area.w / 100.0f * 0.9f; //difference x
        dy = this.area.w / 100.0f * 0.9f; //difference y

        size = this.area.h / 100.0f * 4f; //circle size
    }

    @Override
    public void draw() {

        for (var result : this.resultsToShow) {
            //int r = randInt();
            int r = result.Result;

            switch (r) {
                case 1:
                    y += dy;   //+ difference y
                    break;

                case 2:

                    x += dx;   //+ difference x
                    break;


                case 3:
                    y -= dy;
                    break;

                case 4:
                    x -= dx;
                    break;

                case 5:
                    break;

                case 6:
                    break;
            }
            //sketch.fill(cr, cg, cb, alpha);
            sketch.fill(100, 50, 50, 100);
            sketch.strokeWeight((float) 1);
            sketch.stroke(cr, cg, cb, 100);
            sketch.circle(x, y, (float) size);
        }
    }
}