package TORVisual.Sketches.RandomWalks;

import TORVisual.Database.DiceResult;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;


public class Essigbaum extends RandomWalker {

    float dh, dw, sizemax, sd, swmax;
    float sw;
    float swd;

    ArrayList<Integer> colorPalette;

    public Essigbaum(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);

        name = "Essigbaum";
        nameLatin = "Rhus typhina";

        // GEÄNDERT: Erweiterte Farbpalette mit 7 Farben
        colorPalette = new ArrayList<Integer>();
        colorPalette.add(sketch.color(229, 220, 215)); // #E5DCD7 (Hellgrau/Weiß)
        colorPalette.add(sketch.color(137, 94, 65));   // #895E41 (Dunkelbraun)
        colorPalette.add(sketch.color(194, 139, 85));  // #C28B55 (Kupferbraun)
        colorPalette.add(sketch.color(100, 87, 81));   // #645751 (Dunkles Grau-Braun)
        colorPalette.add(sketch.color(198, 171, 124)); // #C6AB7C (Gold/Sand)
        colorPalette.add(sketch.color(5, 2, 0));       // #050200 (Fast Schwarz)
        colorPalette.add(sketch.color(178, 214, 100)); // #B2D664 (Moosgrün-Akzent)

        x = startX;
        y = startY;
        alpha = 20;
        dx = this.area.w / 100.0f * 0.6f;
        dy = this.area.w / 100.0f * 0.6f;
        sw = this.area.w / 100.0f * 0.05f;
        swd = this.area.w / 100.0f * 0.02f;
        swmax = this.area.w / 100.0f * 0.8f;
        size = this.area.h / 100.0f * 0.4f;
        sizemax = this.area.h / 100.0f * 1.1f;
        sd = this.area.h / 100.0f * 0.03f;
    }

    @Override
    public void draw() {

        for (var result : this.resultsToShow) {
            int r = result.Result;

            switch (r) {
                case 1:
                    moveY(dy);
                    if (sw > swd & sw < swmax)
                        sw -= swd;
                    break;

                case 2:
                    if (alpha >= 20 & alpha <= 40)
                        alpha += 1;
                    if (size > sd & size < sizemax)
                        size -= sd;
                    break;

                case 3:
                    if (alpha <= 40 & alpha >= 20)
                        alpha -= 1;
                    break;

                case 4:
                    moveY(-dy);
                    break;

                case 5:
                    moveX(-dx);
                    if (size + sd < sizemax)
                        size += sd;
                    break;

                case 6:
                    moveX(dx);
                    if (sw + swd < swmax)
                        sw += swd;
                    break;
            }

            int c = colorPalette.get((int)sketch.random(colorPalette.size()));

            this.canvas.fill(c, 0);
            this.canvas.strokeWeight((float) sw);
            this.canvas.stroke(c, alpha);
            this.canvas.circle(x, y, (float) size);
        }
    }
}

/*
public class Essigbaum extends RandomWalker {

    float dh, dw, sizemax, sd, swmax;
    float sw;
    float swd;

    // NEU: Eine ArrayList für die neue Farbpalette
    ArrayList<Integer> colorPalette;

    public Essigbaum(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);

        name = "Essigbaum";
        nameLatin = "Rhus typhina";

        // NEU: Die Farbpalette wird mit den vier gewünschten Farbtönen gefüllt
        colorPalette = new ArrayList<Integer>();
        colorPalette.add(sketch.color(245, 240, 220)); // Helles Weiß (Splintholz)
        colorPalette.add(sketch.color(210, 215, 180)); // Hellgrün
        colorPalette.add(sketch.color(190, 170, 130)); // Hellbraun (Kernholz)
        colorPalette.add(sketch.color(140, 120, 90));  // Dunkleres Braun

        x = startX;
        y = startY;
        alpha = 20;
        dx = this.area.w / 100.0f * 0.6f;
        dy = this.area.w / 100.0f * 0.6f;
        sw = this.area.w / 100.0f * 0.05f;
        swd = this.area.w / 100.0f * 0.02f;
        swmax = this.area.w / 100.0f * 0.8f;
        size = this.area.h / 100.0f * 0.4f;
        sizemax = this.area.h / 100.0f * 1.1f;
        sd = this.area.h / 100.0f * 0.03f;
    }

    @Override
    public void draw() {

        for (var result : this.resultsToShow) {
            int r = result.Result;

            switch (r) {
                case 1:
                    moveY(dy);
                    if (sw > swd & sw < swmax)
                        sw -= swd;
                    break;

                case 2:
                    if (alpha >= 20 & alpha <= 40)
                        alpha += 1;
                    if (size > sd & size < sizemax)
                        size -= sd;
                    break;

                case 3:
                    if (alpha <= 40 & alpha >= 20)
                        alpha -= 1;
                    break;

                case 4:
                    moveY(-dy);
                    break;

                case 5:
                    moveX(-dx);
                    if (size + sd < sizemax)
                        size += sd;
                    break;

                case 6:
                    moveX(dx);
                    if (sw + swd < swmax)
                        sw += swd;
                    break;
            }

            // GEÄNDERT: Wählt eine zufällige Farbe aus der neuen Palette
            int c = colorPalette.get((int)sketch.random(colorPalette.size()));

            // Die Füllung bleibt transparent, um den Effekt von Jahresringen zu erzeugen
            this.canvas.fill(c, 0);
            this.canvas.strokeWeight((float) sw);
            this.canvas.stroke(c, alpha);
            this.canvas.circle(x, y, (float) size);
        }
    }
}
*/

/*
public class Essigbaum extends RandomWalker {

    float dh, dw, sizemax, sd, swmax;
    float sw;
    float swd;

    public Essigbaum(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);

        name = "Essigbaum";
        nameLatin = "Rhus typhina";

        colorStart = sketch.color(98, 102, 26);
        colorEnd = sketch.color(196, 201, 101);

        x = startX;
        y = startY;
        cr = 98; //rgb color value red
        cg = 102; //rgb color value green
        cb = 26;  //rgb color value blue
        alpha = 20;
        dx = this.area.w / 100.0f * 0.6f; //difference x
        dy = this.area.w / 100.0f * 0.6f; //difference y
        sw = this.area.w / 100.0f * 0.05f; //stroke weight
        swd = this.area.w / 100.0f * 0.02f; //stroke weight difference
        swmax = this.area.w / 100.0f * 0.8f; //stroke weight maximum
        size = this.area.h / 100.0f * 0.4f; //circle size
        sizemax = this.area.h / 100.0f * 1.1f; //circle size maximum
        sd = this.area.h / 100.0f * 0.03f; //size difference
    }

    @Override
    public void draw() {

        for (var result : this.resultsToShow) {
            //int r = randInt();
            int r = result.Result;

            switch (r) {
                case 1:
                    moveY(dy);

                    if (sw > swd & sw < swmax)
                        sw -= swd;  //stroke weight - stroke weight distance

                    // if (cg > 134)
                    //     cg -= 1;  //green -1
                    break;

                case 2:
                    if (alpha >= 20 & alpha <= 40)
                        alpha += 1;

                    if (size > sd & size < sizemax)
                        size -= sd;  //circle size - difference size
                    break;


                case 3:
                    if (alpha <= 40 & alpha >= 20)
                        alpha -= 1;  //alpha -1
                    break;

                case 4:
                    moveY(-dy);
                    break;

                case 5:
                    moveX(-dx);

                    if (size + sd < sizemax)
                        size += sd; //+size difference
                    break;


                case 6:

                    moveX(dx);

                    if (sw + swd < swmax)
                        sw += swd; //stroke weight + stroke weight distance

                    // if (cg < 255)
                    //     cg += 1;   //green + 1
                    break;

            }
            var c = sketch.lerpColor(colorStart, colorEnd, colorPercent);
            //sketch.fill(cr, cg, cb, alpha);
            this.canvas.fill(c, 0);
            //     sketch.stroke(32,79,17,31);
            this.canvas.strokeWeight((float) sw);
            this.canvas.stroke(c, alpha);
            this.canvas.circle(x, y, (float) size);
        }
    }
}
*/