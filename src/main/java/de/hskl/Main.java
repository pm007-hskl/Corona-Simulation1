package de.hskl;

import de.hskl.contol.AlgorithmInfection;
import de.hskl.util.MathGValueControl;
import de.hskl.model.Person;
import de.hskl.view.GuiSettings;
import g4p_controls.*;
import processing.core.PApplet;

import java.awt.*;

import static de.hskl.model.HealthStatus.*;


public class Main extends PApplet {

    GCustomSlider basicReproductionRatio;
    GCustomSlider numPerson;
    GCustomSlider numStartInfections;
    private float GuiBasicReproductionRatioValue = 2.0f;
    private int GuiNumPersonValue = 100;
    private int GuiNumStartInfectionsValue = 4;
    public Person[] persons = new Person[100];
    public static GCustomSlider slider;
    public static GButton btnstart;
    public static GButton btnstop;
    public static GLabel healthyState;
    public static GLabel infectedState;
    public static GLabel deadState;
    public static GLabel healedState;
    public static int healthyCounter = 0;
    public static int infectedCounter = 0;
    public static int deadCounter = 0;
    public static int healedCounter = 0;
    public static Font font = new Font("TimesRoman", Font.PLAIN, 20);
    public static int strokeWeightValue = 4; //dicke der Striche definiert
    public static int frameCounter = 0; // Test feste Framerate


    public static void main(String[] args) {
        PApplet.main(new String[]{Main.class.getName()});
    }

    public void settings() {
        size(1000, 800);
    }

    public void setup() {

        initialize();

        /*
         * Erzeugen der Buttons
         * */

        btnstart = new GButton(this, 10, 20, 140, 20, "START");
        btnstart.setLocalColorScheme(GCScheme.CYAN_SCHEME);

        btnstop = new GButton(this, 10, 50, 140, 20, "STOP");
        btnstop.setLocalColorScheme(GCScheme.RED_SCHEME);

        healthyState = new GLabel(this, 0, 500, 200, 100);
        healthyState.setFont(font);

        infectedState = new GLabel(this, 0, 570, 200, 100);
        infectedState.setFont(font);

        deadState = new GLabel(this, 0, 640, 200, 100);
        deadState.setFont(font);

        healedState = new GLabel(this, 0, 710, 200, 100);
        healedState.setFont(font);


        /*
         * Den Labelnamen der Slider mit den Slider gruppieren
         * */

        GGroup groupOfLabelReprRatio = new GGroup(this);
        GLabel labelRepRatio = new GLabel(this, 0, 70, 200, 30, "Reproduktionszahl");
        basicReproductionRatio = GuiSettings.buildSliderForBasicReproductionRatio(this, basicReproductionRatio);
        groupOfLabelReprRatio.addControls(labelRepRatio, basicReproductionRatio);


        GGroup groupOfLabelNumPerson = new GGroup(this);
        GLabel labelNumPerson = new GLabel(this, 0, 170, 200, 30, "Gesamtanzahl der Personen");
        numPerson = GuiSettings.buildSliderForNumberPerson(this, numPerson);
        groupOfLabelReprRatio.addControls(labelNumPerson, numPerson);


        GGroup groupOfLabelNumStartInfects = new GGroup(this);
        GLabel labelNumInfects = new GLabel(this, 0, 270, 200, 30, "Anzahl der Infizierte am Anfang");
        numStartInfections = GuiSettings.buildSliderForNumberStartInfects(this, numStartInfections);
        groupOfLabelReprRatio.addControls(labelNumInfects, numStartInfections);

        //frameRate(30); // Test feste Framerate
    }


    /*
     * Jede Änderung des Sliders führt zu einer Neuinitialisierung der Werte
     * dies muss mit dem Startbutton bestötigt werden
     * */

    private void initialize() {
        if (GuiNumPersonValue >= GuiNumStartInfectionsValue) {
            for (int i = 0; i < GuiNumPersonValue; i++) {
                persons[i] = new Person(this, strokeWeightValue);
                persons[i].generateRandomPosition(strokeWeightValue);
            }

            for (int i = 0; i < GuiNumStartInfectionsValue; i++) {
                persons[i].setCurrentHealthStatus(INFECTED);
            }

        } else {
            for (int i = 0; i < GuiNumPersonValue; i++) {
                persons[i] = new Person(this, strokeWeightValue);
                persons[i].generateRandomPosition(strokeWeightValue);
                persons[i].setCurrentHealthStatus(INFECTED);
            }
        }
    }


    public void draw() {

        background(0);
        stroke(255);
        strokeWeight(strokeWeightValue);
        rect(0, 0, 200, height);

        // Person anzeigen und bewegung berechnen
        for (int i = 0; i < persons.length; i++) {
            persons[i].move();
            persons[i].show();
        }
        //Zählt bei jedem durchlauf die Anzahl toter,gesunder,geheilter und infizierter Menschen
        for (int i = 0; i < persons.length; i++) {
            switch (persons[i].getCurrentHealthStatus()) {
                case HEALTHY:
                    healthyCounter++;
                    break;
                case INFECTED:
                    infectedCounter++;
                    break;
                case DEAD:
                    deadCounter++;
                    break;
                case HEALED:
                    healedCounter++;
                    break;
            }
        }

        AlgorithmInfection.infect(persons, GuiBasicReproductionRatioValue);

        /*
         * Gui Werte aktualiseren
         * */

        healthyState.setText("Anzahl gesunder Menschen: " + healthyCounter);
        healthyCounter = 0;

        infectedState.setText("Anzahl infizierter Menschen: " + infectedCounter);
        infectedCounter = 0;

        deadState.setText("Anzahl Tote: " + deadCounter);
        deadCounter = 0;

        healedState.setText("Anzahl geheilter Personen: " + healedCounter);
        healedCounter = 0;

        /*
         * DayTime-Simulator: zählt Frames, um Reproduktionsfaktor zu visualisieren
         * Setzt die Wahrscheinlichkeit, dass ein Infizierter stirbt
         * */
        for (int i = 0; i < persons.length; i++) {

            if (persons[i].getCurrentHealthStatus() == INFECTED) {

                //300 Frames entsprechen einem Tag
                if (persons[i].getDaysCounter() >= 300) {
                    persons[i].resetCounters();
                } else {
                    persons[i].riseCounter();
                }

                //Todeswahrscheinlichkeit 0,8%, es wird ein Wert zwischen 1 und 1000 gewürfelt, wenn dieser kleiner oder gleich 8 ist stirbt die Person
                if (((int) (Math.random() * 1000) + 1 <= 8) && (persons[i].getIsSafe() == false)) {
                    persons[i].setCurrentHealthStatus(DEAD);
                } else {
                    persons[i].setIsSafe(true);
                }

                //nach 700 Frames sind sie wieder geheilt, falls sie nicht bereits tot sind
                if (persons[i].getDaysOfInfection() >= 700) {
                    persons[i].setCurrentHealthStatus(HEALED);
                }
            }
        }
    }


    /*
     * handleSliderEvents wird nur bei Änderung der Werte ausgeführt,
     * dies übernimmt Processing von selbst
     * */

    public void handleSliderEvents(GValueControl slider, GEvent event) {
        if (slider == basicReproductionRatio) {
            GuiBasicReproductionRatioValue = MathGValueControl.roundOneDigit(slider);
            //System.out.println("Reproduktionszahl: " + basicReproductionRatioValue);
        }
        if (slider == numPerson) {
            GuiNumPersonValue = slider.getValueI();
            //System.out.println("Anzahl Personen: " + numPersonValue);
        }
        if (slider == numStartInfections) {
            GuiNumStartInfectionsValue = slider.getValueI();
            //System.out.println("Anzahl Start Infizierte" + numStartInfectionsValue);
        }
    }


    /*
     * xxEvents stellt die Werte der Eingabe von der GUI bereit
     * */
    public void handleButtonEvents(GButton button, GEvent event) {
        if (button == btnstart && event == GEvent.CLICKED) {
            persons = new Person[GuiNumPersonValue];
            initialize();
            loop();
        }
        if (button == btnstop && event == GEvent.CLICKED) {
            noLoop();
        }
    }

}