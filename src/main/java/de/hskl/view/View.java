package de.hskl.view;

import g4p_controls.G4P;
import g4p_controls.GCustomSlider;
import processing.core.PApplet;

public class View extends PApplet{
    GCustomSlider slider;

    public void settings() {
        size(500, 200);
    }

    public void setup() {
        slider = new GCustomSlider(this, 50, 50, 400, 100, "metallic");
        slider.setShowValue(true);
        slider.setShowLimits(true);
        slider.setLimits(38, 0, 100);
        slider.setNbrTicks(11);
        slider.setShowTicks(true);
        slider.setEasing(6.0f);
        slider.setNumberFormat(G4P.INTEGER, 0);
        slider.setOpaque(true);
    }

    public void draw() {
        background(200, 200, 220);
    }




    public static void main(String[] args) {
        PApplet.main(new String[]{View.class.getName()});
    }
}
