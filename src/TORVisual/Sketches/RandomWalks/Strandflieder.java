package TORVisual.Sketches.RandomWalks;

import TORVisual.Database.DiceResult;
import TORVisual.SketchArea;
import processing.core.PApplet;

import java.util.ArrayList;


public class Strandflieder extends RandomWalker {
    float angle_rad, sw, swd, swmax, sizemax, sizemin, sd, dangle;

    // Farbvariablen
    int colorPurpleDark;
    int colorPurpleLight;
    int colorPurpleAccent;
    int colorPurpleVibrant; // NEU: Zusätzliche Akzentfarbe
    int colorStemDark;
    int colorStemLight;

    public Strandflieder(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);

        name = "Strandflieder";
        nameLatin = "Limonium sinuatum";

        // Definition der Farbpaletten
        // Palette 1: Blütenfarben
        colorPurpleDark = sketch.color(36, 27, 75);      // #241B4B
        colorPurpleLight = sketch.color(101, 96, 174);     // #6560AE
        colorPurpleAccent = sketch.color(127, 99, 183);    // #7F63B7
        colorPurpleVibrant = sketch.color(122, 47, 175);   // NEU: #7A2FAF

        // Palette 2: Stammfarben
        colorStemDark = sketch.color(102, 99, 86);      // #666356
        colorStemLight = sketch.color(196, 185, 163);    // #C4B9A3

        alpha = 10;
        sw = this.area.w / 100.0f * 0.2f;
        swd = this.area.w / 100.0f * 0.05f;
        swmax = this.area.w / 100.0f * 1f;
        dx = this.area.w / 100.0f * 0.6f;
        dy = this.area.w / 100.0f * 0.6f;
        angle_rad = 5;
        dangle = 1;

        size = this.area.h / 100.0f * 1f;
        sd = this.area.h / 100.0f * 0.1f;

        sizemin = this.area.h / 100.0f * 0.5f;
        sizemax = this.area.h / 100.0f * 2.0f;
    }

    @Override
    public void draw() {
        for (var result : this.resultsToShow) {
            int r = result.Result;

            switch (r) {
                case 1:
                    moveY((float) Math.sin(angle_rad) * dy);
                    moveX((float) Math.cos(angle_rad) * dx);
                    if (sw > swd && sw < swmax)
                        sw -= swd;
                    break;

                case 2:
                    if (alpha < 128)
                        alpha += sketch.random(1, 4);

                    if (size > sizemin && size < sizemax)
                        size -= sd;

                    angle_rad = angle_rad - dangle;
                    break;

                case 3:
                    if (alpha > 0)
                        alpha -= sketch.random(1, 4);

                    angle_rad = angle_rad + dangle;
                    break;

                case 4:
                    moveY(-(float) Math.sin(angle_rad) * dy);
                    moveX(-(float) Math.cos(angle_rad) * dx);
                    break;

                case 5:
                    moveY(-(float) Math.sin(angle_rad) * dy);
                    moveX(+(float) Math.cos(angle_rad) * dx);
                    if (size + sd < sizemax)
                        size += sd;
                    break;

                case 6:
                    moveY(+(float) Math.sin(angle_rad) * dy);
                    moveX(-(float) Math.cos(angle_rad) * dx);
                    if (sw + swd < swmax)
                        sw += swd;
                    break;
            }

            // Zufällige Auswahl der Farbe aus den Paletten
            int c;
            float chance = sketch.random(1);

            if (chance < 0.9) { // 90% Wahrscheinlichkeit für eine Blütenfarbe
                // GEÄNDERT: Wähle zufällig zwischen dem Verlauf und den ZWEI Akzentfarben
                float flowerChoice = sketch.random(1);
                if (flowerChoice < 0.70) { // 70% Chance für den Hauptverlauf
                    c = sketch.lerpColor(colorPurpleDark, colorPurpleLight, colorPercent);
                } else if (flowerChoice < 0.85) { // 15% Chance für den ersten Akzent
                    c = colorPurpleAccent;
                } else { // 15% Chance für den neuen, leuchtenden Akzent
                    c = colorPurpleVibrant;
                }
            } else { // 10% Wahrscheinlichkeit für eine Stammfarbe
                c = sketch.lerpColor(colorStemDark, colorStemLight, colorPercent);
            }

            this.canvas.fill(c, alpha / 3);
            this.canvas.strokeWeight((float) sw);
            this.canvas.stroke(c, alpha / 2);
            this.canvas.circle(x, y, (float) size);
        }
    }
}




/*
public class Strandflieder extends RandomWalker {
    float angle_rad, sw, swd, swmax, sizemax, sizemin, sd, dangle;

    public Strandflieder(PApplet sketch, SketchArea area, ArrayList<DiceResult> resultsToShow) {
        super(sketch, area, resultsToShow);

        name = "Strandflieder";
        nameLatin = "Limonium sinuatum";

        colorStart = sketch.color(99, 80, 117);
        colorEnd = sketch.color(87, 77, 97);

        alpha = 10;
        sw = this.area.w / 100.0f * 0.2f; //stroke weight
        swd = this.area.w / 100.0f * 0.05f; //stroke weight difference
        swmax = this.area.w / 100.0f * 1f; //stroke weight maximum
        dx = this.area.w / 100.0f * 0.6f; //difference x
        dy = this.area.w / 100.0f * 0.6f; //difference y
        angle_rad = 5;
        //dangle=0.1f;
        dangle = 1;
        sw = this.area.w / 100.0f * 0.2f; //stroke weight
        swd = this.area.w / 100.0f * 0.05f; //stroke weight difference
        size = this.area.h / 100.0f * 1f; //circle size
        sd = this.area.h / 100.0f * 0.1f; //size difference

    }

    @Override
    public void draw() {
        for (var result : this.resultsToShow) {
            int r = result.Result;

            switch (r) {
                case 1:
                    moveY((float) Math.sin(angle_rad) * dy);
                    moveX((float) Math.cos(angle_rad) * dx);
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
                    moveY(-(float) Math.sin(angle_rad) * dy);
                    moveX(-(float) Math.cos(angle_rad) * dx);
                    break;

                case 5:
                    moveY(-(float) Math.sin(angle_rad) * dy);
                    moveX(+(float) Math.cos(angle_rad) * dx);
                    if (size + sd < sizemax)
                        size += sd; //+size difference
                    break;


                case 6:
                    moveY(+(float) Math.sin(angle_rad) * dy);
                    moveX(-(float) Math.cos(angle_rad) * dx);
                    if (sw + swd < swmax)
                        sw += swd; //stroke weight + stroke weight distance

                    break;
            }
            var c = sketch.lerpColor(colorStart, colorEnd, colorPercent);
            this.canvas.fill(c, alpha / 3);
            this.canvas.strokeWeight((float) sw);
            this.canvas.stroke(c, alpha / 2);
            this.canvas.circle(x, y, (float) size);
        }
    }
}

 */