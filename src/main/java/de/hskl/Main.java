package de.hskl;

<<<<<<< HEAD
=======
import de.hskl.model.Person;
>>>>>>> modell
import g4p_controls.*;
import processing.core.PApplet;

import java.awt.*;

public class Main extends PApplet {
<<<<<<< HEAD
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
        /*
        * Den Labelnamen der Slider mit den Slider gruppieren
        * */

        GGroup groupOfLabelReprRatio = new GGroup(this);
        GLabel labelRepRatio = new GLabel(this, 50, 50, 200, 30, "Reproduktionszahl");
        buildSliderForBasicReproductionRatio();
        groupOfLabelReprRatio.addControls(labelRepRatio, basicReproductionRatio);

        GGroup groupOfLabelNumStartInfects = new GGroup(this);
        GLabel labelNumInfects = new GLabel(this, 50, 150, 200, 30, "Anzahl der Infizierte am Anfang");
        buildSliderForNumberStartInfects();
        groupOfLabelReprRatio.addControls(labelNumInfects, numPerson);

        GGroup groupOfLabelNumPerson= new GGroup(this);
        GLabel labelNumPerson = new GLabel(this, 50, 250, 200, 30, "Gesamtanzahl der Personen");
        buildSliderForNumberPerson();
        groupOfLabelReprRatio.addControls(labelNumPerson, numStartInfections);

    }


    public void draw() {
        background(255);

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
            System.out.println("Anzahl Personen: " + numPersonValue);
        }
        if (slider ==  numStartInfections) {
            numStartInfectionsValue =  slider.getValueI();
            System.out.println("Anzahl Start Infizierte" + numStartInfectionsValue);
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
        basicReproductionRatio.setOpaque(false);
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
        numPerson.setOpaque(false);
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
        numStartInfections.setOpaque(false);
    }

=======
    Person[] pers = new Person[1000];
    public static GCustomSlider slider;
    public static GButton btnstart;
    public static GButton btnstop;
    public static GLabel healthyState;
    public static GLabel InfectedState;
    public static GLabel DeadState;
    public static GLabel HealedState;
    public static int healthycc = 0;
    public static int Infectedcc = 0;
    public static int Deadcc = 0;
    public static int Healedcc=0;
    public static Font font = new Font("TimesRoman", Font.PLAIN, 20);

    public static void main(String[] args) {
        PApplet.main(new String[]{Main.class.getName()});
    }

    public void settings() {
        size(1000, 800);
    }

    public void setup() {
        //frameRate(30);
        for (int i = 0; i < pers.length; i++) {
            pers[i] = new Person(this);
            pers[i].generatePosition();
        }

        btnstart = new GButton(this, 10, 20, 140, 20, "START");
        btnstop = new GButton(this, 10, 50, 140, 20, "STOP");
        btnstart.setLocalColorScheme(GCScheme.CYAN_SCHEME);
        btnstop.setLocalColorScheme(GCScheme.RED_SCHEME);

        healthyState = new GLabel(this, 0, 500, 200, 100);
        InfectedState = new GLabel(this, 0, 570, 200, 100);
        DeadState = new GLabel(this, 0, 640, 200, 100);
        HealedState=new GLabel(this,0,710,200,100);
        healthyState.setFont(font);
        InfectedState.setFont(font);
        DeadState.setFont(font);
        HealedState.setFont(font);
    }

    public void draw() {

        background(0);
        stroke(255);
        rect(0, 0, 200, height);
        strokeWeight(3);
        for (int i = 0; i < pers.length; i++) {
            pers[i].movement();
            pers[i].show();
        }
        for (int i = 0; i < pers.length; i++) {
            switch (pers[i].getCondition()) {
                case "HEALTHY":
                    healthycc++;
                    break;
                case "INFECTED":
                    Infectedcc++;
                    break;
                case "DEAD":
                    Deadcc++;
                    break;
                case"HEALED":
                    Healedcc++;
                    break;
            }
        }
        pers[44].setcondition("HEALED");
        healthyState.setText("Anzahl gesunder Menschen: " + healthycc);
        healthycc = 0;

        InfectedState.setText("Anzahl infizierter Menschen: " + Infectedcc);
        Infectedcc = 0;

        DeadState.setText("Anzahl Tote: " + Deadcc);
        Deadcc = 0;
        HealedState.setText("Anzahl geheilter Personen: "+Healedcc);
        Healedcc=0;

    }

    public void handleButtonEvents(GButton button, GEvent event) {
        if (button == btnstart && event == GEvent.CLICKED) {
            loop();
        }
        if (button == btnstop && event == GEvent.CLICKED) {
            noLoop();
        }
    }

    public void infect() {
        for (int i = 0; i < pers.length; i++) {
            for (int j = 0; j < pers.length; j++) {
                if (i != j) {
                    //vergleich personDistance mit radius von Person i
                    if (personDistance(pers[i], pers[j]) <= pers[i].getRadius()) {
                        //TODO infeketions algorithmus
                    }
                }
            }
        }
    }

    private float personDistance(Person per, Person per1) {
        return dist(per.getPosition().x, per.getPosition().y, per1.getPosition().x, per1.getPosition().y);
    }

>>>>>>> modell
}