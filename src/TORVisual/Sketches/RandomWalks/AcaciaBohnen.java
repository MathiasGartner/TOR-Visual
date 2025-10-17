package TORVisual.Sketches.RandomWalks;

import TORVisual.Database.DiceResult;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;

public class AcaciaBohnen extends RandomWalker {
    float angle_rad, sw, swd, swmax, sizemax, sizemin, sd;
    ArrayList<Integer> colorPalette;
    float alphaMaxPod = 140; // 55% von 255

    // GEÄNDERT: Eine feste Farbe für die Kontur, um Präzision zu schaffen
    int strokeColor;

    public AcaciaBohnen(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);

        name = "Acacia Bohnen";
        nameLatin = "Delonix regia";

        colorPalette = new ArrayList<Integer>();
        colorPalette.add(sketch.color(222, 183, 144)); // #DEB790
        colorPalette.add(sketch.color(169, 131, 87));  // #A98357
        colorPalette.add(sketch.color(118, 90, 66));   // #765A42
        colorPalette.add(sketch.color(84, 58, 41));    // #543A29

        // GEÄNDERT: Die dunkelste Farbe der Palette wird als Konturfarbe definiert
        strokeColor = sketch.color(84, 58, 41);

        alpha = 30;

        // GEÄNDERT: Größen-Parameter für noch kleinere Shapes
        size = this.area.h / 100.0f * 0.7f;    // Kleinere Startgröße
        sd = this.area.h / 100.0f * 0.05f;
        sizemin = this.area.h / 100.0f * 0.3f;  // Kleinere Minimalgröße
        sizemax = this.area.h / 100.0f * 1.0f;  // Kleinere Maximalgröße

        // Bewegungsparameter
        dx = this.canvas.width / 100 * 0.7f;
        dy = this.canvas.width / 100 * 0.4f;
    }

    void drawAcaciaPodSegment(float segmentX, float segmentY, float segmentSize, int chosenColor, float currentAlpha) {
        float segmentHeight = segmentSize;
        float segmentWidth = segmentSize * 2.5f;

        this.canvas.pushMatrix();
        this.canvas.translate(segmentX, segmentY);
        this.canvas.rotate(sketch.random(-PApplet.PI/6, PApplet.PI/6));

        // GEÄNDERT: Kontur hinzugefügt für präzisere Shapes
        this.canvas.strokeWeight(0.75f); // Dünne Kontur
        this.canvas.stroke(strokeColor, currentAlpha * 1.2f); // Kontur ist etwas deckender als die Füllung
        this.canvas.fill(chosenColor, currentAlpha);

        // Zeichne die wellenförmige Grundform... (logik bleibt gleich)
        this.canvas.beginShape();
        this.canvas.curveVertex(-segmentWidth / 2, -segmentHeight / 2);
        this.canvas.curveVertex(-segmentWidth / 2, -segmentHeight / 2);
        this.canvas.curveVertex(-segmentWidth / 4, 0);
        this.canvas.curveVertex(0, -segmentHeight / 2);
        this.canvas.curveVertex(segmentWidth / 4, 0);
        this.canvas.curveVertex(segmentWidth / 2, -segmentHeight / 2);
        this.canvas.curveVertex(segmentWidth / 2, -segmentHeight / 2);
        this.canvas.endShape();

        this.canvas.beginShape();
        this.canvas.curveVertex(-segmentWidth / 2, segmentHeight / 2);
        this.canvas.curveVertex(-segmentWidth / 2, segmentHeight / 2);
        this.canvas.curveVertex(-segmentWidth / 4, 0);
        this.canvas.curveVertex(0, segmentHeight / 2);
        this.canvas.curveVertex(segmentWidth / 4, 0);
        this.canvas.curveVertex(segmentWidth / 2, segmentHeight / 2);
        this.canvas.curveVertex(segmentWidth / 2, segmentHeight / 2);
        this.canvas.endShape();

        this.canvas.popMatrix();
    }


    @Override
    public void draw() {
        for (var result : this.resultsToShow) {
            int r = result.Result;

            switch (r) {
                case 1:
                    moveY(dy);
                    break;
                case 2:
                    if (alpha < alphaMaxPod)
                        alpha += sketch.random(1, 3);
                    if (size > sizemin)
                        size -= sd;
                    break;
                case 3:
                    if (alpha > 5)
                        alpha -= sketch.random(1, 3);
                    break;
                case 4:
                    moveY(-dy);
                    if (size < sizemax)
                        size += sd;
                    break;
                case 5:
                    moveX(dx);
                    break;
                case 6:
                    moveX(-dx);
                    break;
            }

            int chosenColor = colorPalette.get((int)sketch.random(colorPalette.size()));
            drawAcaciaPodSegment(x, y, (float) size, chosenColor, alpha);
        }
    }
}




/*
public class AcaciaBohnen extends RandomWalker {
    float angle_rad, sw, swd, swmax, sizemax, sizemin, sd, hmax, wmax;

    public AcaciaBohnen(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);

        name = "Acacia Bohnen";
        nameLatin = "Delonix regia";

        colorStart = sketch.color(74, 48, 22);
        //colorEnd = sketch.color(74, 38, 5);

        alpha = 30;

        sw = this.area.w / 100.0f * 0.2f; //stroke weight


        angle_rad = 5;

        size = this.area.h / 100.0f * 1f; //circle size
        sd = this.area.h / 100.0f * 0.02f; //size difference


        dx = this.canvas.width / 100 * 0.7f;
        dy = this.canvas.width / 100 * 0.4f;
        ds = this.canvas.width / 100 * 0.05f;
        w = this.canvas.width / 100 * 0.2f;
        h = this.canvas.height / 100 * 1.2f;
        hmax = 2.5f;
        wmax = 0.6f;

    }

    @Override
    public void draw() {
        for (var result : this.resultsToShow) {
            int r = result.Result;

            switch (r) {
                case 1:
                    moveY((float) Math.cos(angle_rad));
                    break;

                case 2:
                    if (alpha <= 70)
                        alpha += 1;

                    if (w > sd)
                        w -= sd;

                    if (angle_rad > 1) {
                        angle_rad = angle_rad - 1;
                    }
                    if (h < hmax)
                        h += ds;
                    break;


                case 3:
                    if (alpha >= 5)
                        alpha -= 1;  //alpha -1
                    if (angle_rad < 360) {
                        angle_rad = angle_rad + 1;
                    }
                    break;

                case 4:
                    moveY(-(float) Math.cos(angle_rad));

                    if (h > ds)
                        h -= ds;

                    if (colorPercent > 0) {
                        colorPercent -= dColor;
                    }
                    break;

                case 5:
                    moveX((float) (Math.sin(angle_rad)));

                    if (w > ds)
                        w -= ds;

                    break;


                case 6:

                    moveX(-(float) (Math.sin(angle_rad)));
                    if (w < wmax)
                        w += ds;

                    if (colorPercent < 1) {
                        colorPercent += dColor;
                    }
                    break;
            }

            //var c = sketch.lerpColor(colorStart, colorEnd, colorPercent);
            this.canvas.fill(colorStart, alpha);
            this.canvas.noStroke();
            //this.canvas.strokeWeight((float) sw);
            //this.canvas.stroke(colorStart, alpha/2);
            //this.canvas.circle(x, y, (float) size);
            this.canvas.ellipse(x, y, w, h);
        }
    }
}*/