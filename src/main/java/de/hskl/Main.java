package de.hskl;

import g4p_controls.G4P;
import g4p_controls.GCustomSlider;
import g4p_controls.GEvent;
import g4p_controls.GValueControl;
import processing.core.PApplet;

public class Main extends PApplet {
    GCustomSlider basicReproductionRatio;
    GCustomSlider numPerson;
    GCustomSlider numStartInfections;

    private float basicReproductionRatioValue;
    private int numPersonValue;
    private int numStartInfectionsValue;


    public void settings() {
        size(500, 500);

    }

    public void setup() {
        buildSliderForBasicReproductionRatio();
        buildSliderForNumberStartInfects();
        buildSliderForNumberPerson();

    }


    public void draw() {

    }


    public static void main(String[] args) {
        PApplet.main(new String[]{Main.class.getName()});
    }

    public void handleSliderEvents(GValueControl slider, GEvent event) {
        if(slider == basicReproductionRatio){
            basicReproductionRatioValue = slider.getValueF();
            System.out.println("Reproduktionszahl: " + basicReproductionRatioValue);
        }
        if(slider == numPerson){
            numPersonValue = slider.getValueI();
            System.out.println(numPersonValue);
        }
        if (slider ==  numStartInfections) {
            numStartInfectionsValue =  slider.getValueI();
            System.out.println(numStartInfectionsValue);
        }
    }

    // Slider Settings für Reproduktionsfaktor
    public void buildSliderForBasicReproductionRatio() {
        basicReproductionRatio = new GCustomSlider(this, 50, 50, 400, 100, "metallic");
        basicReproductionRatio.setShowValue(true);
        basicReproductionRatio.setShowLimits(true);
        basicReproductionRatio.setLimits(2, 0, 10);
        basicReproductionRatio.setNbrTicks(11);
        basicReproductionRatio.setShowTicks(true);
        basicReproductionRatio.setEasing(20f);
        basicReproductionRatio.setNumberFormat(G4P.DECIMAL, 0);
        basicReproductionRatio.setOpaque(true);
        basicReproductionRatio.setPrecision(1);
    }

    public void buildSliderForNumberPerson() {
        numPerson = new GCustomSlider(this, 50, 150, 400, 100, "metallic");
        numPerson.setShowValue(true);
        numPerson.setShowLimits(true);
        numPerson.setLimits(50, 0, 100);
        numPerson.setNbrTicks(11);
        numPerson.setShowTicks(true);
        numPerson.setEasing(6.0f);
        numPerson.setNumberFormat(G4P.INTEGER, 0);
        numPerson.setOpaque(true);
    }

    public void buildSliderForNumberStartInfects() {
        numStartInfections = new GCustomSlider(this, 50, 250, 400, 100, "metallic");
        numStartInfections.setShowValue(true);
        numStartInfections.setShowLimits(true);
        numStartInfections.setLimits(4, 0, 100);
        numStartInfections.setNbrTicks(11);
        numStartInfections.setShowTicks(true);
        numStartInfections.setEasing(6.0f);
        numStartInfections.setNumberFormat(G4P.INTEGER, 0);
        numStartInfections.setOpaque(true);
    }

}