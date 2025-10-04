package TORVisual.Sketches.RandomWalks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import TORVisual.Database.DiceResult;
import TORVisual.SketchArea;
import TORVisual.Utils.TriFunction;
import processing.core.PApplet;

public class WalkerFactory {
    private static final Map<String, TriFunction<PApplet, SketchArea, ArrayList<DiceResult>, RandomWalker>> mapping = new HashMap<>();

    static {
        mapping.put("Acacia Bohnen", AcaciaBohnen::new);
        mapping.put("Air-Fern", AirFern::new);
        mapping.put("Linsen", Linsen::new);
        mapping.put("Elefantenohren", Elefantenohren::new);
        mapping.put("Blumenknospen", Blumenknospen::new);
        mapping.put("Zimtstangen", Zimtstangen::new);
        mapping.put("Gerstenähren", Gerstenähren::new);
        mapping.put("Akazienzapfen", Akazienzapfen::new);
        mapping.put("Bananenstiel", Bananenstiel::new);
        mapping.put("Japanischer Schlitzahorn", JapanischerSchlitzahorn::new);
        mapping.put("Strandflieder", Strandflieder::new);
        mapping.put("Amberbaum", Amberbaum::new);
        mapping.put("Chilis", Chilis::new);
        mapping.put("Luffa", Luffa::new);
        mapping.put("Platanen", Platanen::new);
        mapping.put("Orangenscheiben", Orangenscheiben::new);
        mapping.put("Sternanis", Sternanis::new);
        mapping.put("Islandmoos", Islandmoos::new);
        mapping.put("Limettenscheiben", Limettenscheiben::new);
        mapping.put("Pampasgras", Pampasgras::new);
        mapping.put("Kork", Kork::new);
        mapping.put("Palmringe", Palmringe::new);
        mapping.put("Lavendel", Lavendel::new);
        mapping.put("Baumschwamm", Baumschwamm::new);
        mapping.put("Lianenscheiben", Lianenscheiben::new);
        mapping.put("Essigbaum", Essigbaum::new);
        mapping.put("Baumwolle", Baumwolle::new);
        mapping.put("Palmenblatt", Palmenblatt::new);
        mapping.put("Samtgras", Samtgras::new);
        mapping.put("Acacia Bohnen", AcaciaBohnen::new);
        mapping.put("Strohblume", Strohblume::new);
    }

    public static RandomWalker create(String name, PApplet sketch, SketchArea area, ArrayList<DiceResult> results) {
        TriFunction<PApplet, SketchArea, ArrayList<DiceResult>, RandomWalker> fn = mapping.get(name);
        if (fn == null) {
            return new Orangenscheiben(sketch, area, results);
        }
        return fn.apply(sketch, area, results);
    }
}
