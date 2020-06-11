package de.hskl.view;

import g4p_controls.G4P;
import g4p_controls.GCustomSlider;
import processing.core.PApplet;

public class GuiSettings {

    /*
     * Slider Settings
     * */

    public static GCustomSlider buildSliderForBasicReproductionRatio(PApplet p, GCustomSlider basicReproductionRatio) {
        basicReproductionRatio = new GCustomSlider(p, 5, 70, 200, 100, "grey_blue");
        basicReproductionRatio.setShowValue(true);
        basicReproductionRatio.setShowLimits(true);
        basicReproductionRatio.setLimits(2, 0, 10);
        basicReproductionRatio.setNbrTicks(11);
        basicReproductionRatio.setShowTicks(true);
        basicReproductionRatio.setEasing(1.0f);
        basicReproductionRatio.setNumberFormat(G4P.DECIMAL, 0);
        basicReproductionRatio.setOpaque(false);
        basicReproductionRatio.setPrecision(1);
        return basicReproductionRatio;
    }


    public static GCustomSlider buildSliderForNumberPerson(PApplet p, GCustomSlider numPerson) {
        numPerson = new GCustomSlider(p, 5, 170, 200, 100, "grey_blue");
        numPerson.setShowValue(true);
        numPerson.setShowLimits(true);
        numPerson.setLimits(100, 100, 1000);
        numPerson.setNbrTicks(11);
        numPerson.setShowTicks(true);
        numPerson.setEasing(1.0f);
        numPerson.setNumberFormat(G4P.INTEGER, 0);
        numPerson.setOpaque(false);
        return numPerson;
    }


    public static GCustomSlider buildSliderForNumberStartInfects( PApplet p, GCustomSlider numStartInfections) {
        numStartInfections = new GCustomSlider(p, 5, 270, 200, 100, "grey_blue");
        numStartInfections.setShowValue(true);
        numStartInfections.setShowLimits(true);
        numStartInfections.setLimits(4, 0, 100);
        numStartInfections.setNbrTicks(11);
        numStartInfections.setShowTicks(true);
        numStartInfections.setEasing(1.0f);
        numStartInfections.setNumberFormat(G4P.INTEGER, 0);
        numStartInfections.setOpaque(false);
        return numStartInfections;
    }


    public static GCustomSlider buildSliderForDeathratio( PApplet p, GCustomSlider deathratio) {
        deathratio = new GCustomSlider(p, 5, 370, 200, 100, "grey_blue");
        deathratio.setShowValue(true);
        deathratio.setShowLimits(true);
        deathratio.setLimits(1,0,100);
        deathratio.setNbrTicks(11);
        deathratio.setShowTicks(true);
        deathratio.setEasing(1.0f);
        deathratio.setNumberFormat(G4P.DECIMAL,0);
        deathratio.setOpaque(false);
        return deathratio;
    }
}
