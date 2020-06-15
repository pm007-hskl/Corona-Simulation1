/*
 * MIT License
 * Copyright (c) 2020 Yannis Heim, Stefan Böbel

 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * */

/*
* Beschreibung
*
* Hier wird eine Technologie genutzt namens "Processing",
* die spezialisiert ist auf die Erstellung von Grafiken, Simulationen und Animationen.
* Die Technologie "Processing" ist im engeren Sinne eine Programmiersprache, die auf
* JAVA basiert. Processing nutzt zum Zeichnen der Visualierungselemte drei Klassen:
* settings(), setup() und draw(), die hier in der "Main" Klasse zu finden sind.
* Weiter Informationen hierzu findet man auf https://processing.org/.
*
* */

package de.hskl;

import de.hskl.contol.AlgorithmInfection;
import de.hskl.contol.MaskDistanceController;
import de.hskl.model.StatusPoint;
import de.hskl.util.MathUtil;
import de.hskl.model.Person;
import de.hskl.view.GuiSettings;
import g4p_controls.*;
import processing.core.PApplet;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;

import static de.hskl.model.HealthStatus.*;


public class Main extends PApplet {

    public static GCustomSlider basicReproductionRatio;
    public static GCustomSlider numPerson;
    public static GCustomSlider numStartInfections;
    public static GCustomSlider deathratio;
    public static GCustomSlider peopleAtRisk;
    public static GCheckbox Mask;
    public static GCheckbox Distance;
    private static boolean InfoOpened= false;
    private float GuiBasicReproductionRatioValue = 2.0f;
    private int GuiNumPersonValue = 100;
    private int GuiNumStartInfectionsValue = 4;
    private float GuiDeathRatio = 1.0f;
    private float GuiPeopleAtRisk = 0.0f;
    public Person[] persons = new Person[100];
    public MaskDistanceController maskdistanceController = new MaskDistanceController(false, false);
    public static GButton btnstart;
    public static GButton btnstop;
    public static GButton info;
    public static GWindow infoWindow;
    public static GLabel healthyState;
    public static GLabel infectedState;
    public static GLabel deadState;
    public static GLabel healedState;
    public static GLabel riskState;
    public static int healthyCounter = 0;
    public static int infectedCounter = 0;
    public static int deadCounter = 0;
    public static int healedCounter = 0;
    public static Font font = new Font("TimesRoman", Font.PLAIN, 16);
    public static int strokeWeightValue = 8; //dicke der Punkte definiert
    public static int frameCounter = 0; // Test feste Framerate
    public static StatusPoint infectedStatePoint;
    public static StatusPoint healthyStatePoint;
    public static StatusPoint deadStatePoint;
    public static StatusPoint healedStatePoint;
    public static StatusPoint riskStatePoint;


    public static void main(String[] args) {
        PApplet.main(new String[]{Main.class.getName()});
    }

    public void settings() {
        size(1000, 800);
    }

    public void setup() {
        surface.setTitle("Corona-Simulation");
        initialize();

        /*
         * Erzeugen der Buttons
         * */

        btnstart = new GButton(this, 32, 20, 140, 20, "START");
        btnstart.setLocalColorScheme(GCScheme.CYAN_SCHEME);

        btnstop = new GButton(this, 32, 50, 140, 20, "STOP");
        btnstop.setLocalColorScheme(GCScheme.RED_SCHEME);





        /*
         * Den Labelnamen der Slider mit den Slider gruppieren
         * */

        GGroup groupOfLabelReprRatio = new GGroup(this);
        GLabel labelRepRatio = new GLabel(this, 50, 65, 200, 46, "Reproduktionszahl");
        basicReproductionRatio = GuiSettings.buildSliderForBasicReproductionRatio(this, basicReproductionRatio);
        groupOfLabelReprRatio.addControls(labelRepRatio, basicReproductionRatio);


        GGroup groupOfLabelNumPerson = new GGroup(this);
        GLabel labelNumPerson = new GLabel(this, 25, 135, 200, 45, "Gesamtanzahl der Personen");
        numPerson = GuiSettings.buildSliderForNumberPerson(this, numPerson);
        groupOfLabelReprRatio.addControls(labelNumPerson, numPerson);


        GGroup groupOfLabelNumStartInfects = new GGroup(this);
        GLabel labelNumInfects = new GLabel(this, 20, 205, 200, 45, "Anzahl der Infizierte am Anfang");
        numStartInfections = GuiSettings.buildSliderForNumberStartInfects(this, numStartInfections);
        groupOfLabelReprRatio.addControls(labelNumInfects, numStartInfections);

        GGroup groupOfLabelDeathRatio = new GGroup(this);
        GLabel labelDeathRation = new GLabel(this, 45, 275, 200, 45, "Sterblichkeitsrate in %");
        deathratio = GuiSettings.buildSliderForDeathratio(this, deathratio);
        groupOfLabelDeathRatio.addControls(labelDeathRation, deathratio);

        GGroup groupOfLabelPeopleAtRisk = new GGroup(this);
        GLabel labelPeopleAtRisk = new GLabel(this, 37, 345, 200, 45, "Risikogruppenanteil in %");
        peopleAtRisk = GuiSettings.buildSliderForPeopleAtRisk(this, peopleAtRisk);
        groupOfLabelPeopleAtRisk.addControls(labelPeopleAtRisk, peopleAtRisk);
        strokeWeight(1);

        Mask = GuiSettings.buildCheckboxForMask(this, Mask);

        Distance = GuiSettings.buildCheckboxForDistance(this, Distance);
        strokeWeight(strokeWeightValue);

        /*
         * Erstellen der Legende
         * */

        healthyState = new GLabel(this, 25, 500, 200, 100);
        healthyState.setFont(font);
        healthyStatePoint = new StatusPoint(this, 0,247,0).setxPos(10).setyPos(550).setStroke(10);

        infectedState = new GLabel(this, 25, 530, 200, 100);
        infectedState.setFont(font);
        infectedStatePoint = new StatusPoint(this, 255,255,0).setxPos(10).setyPos(580).setStroke(10);

        deadState = new GLabel(this, 25, 560, 200, 100);
        deadState.setFont(font);
        deadStatePoint = new StatusPoint(this, 255,0,0).setxPos(10).setyPos(610).setStroke(10);

        healedState = new GLabel(this, 25, 590, 200, 100);
        healedState.setFont(font);
        healedStatePoint = new StatusPoint(this, 0,51,255).setxPos(10).setyPos(640).setStroke(10);

        riskState = new GLabel(this, 25, 620, 200, 100);
        riskState.setFont(font);
        riskStatePoint = new StatusPoint(this, 0,51,255).setxPos(10).setyPos(670).setStroke(10);

        info=new GButton(this,32,720,140,20,"INFO");

        infoWindow = GWindow.getWindow(this, "Info zu Covid-19", 0, 0, 300, 300, JAVA2D);
        infoWindow.addDrawHandler(this, "InfoHandler");
        infoWindow.setVisible(false);
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



        float AbsPeopleAtRisk = (GuiPeopleAtRisk / 100.0f) * (float) GuiNumPersonValue;
        int AbsPeopleAtRiskAsInt = (int) AbsPeopleAtRisk;
        for (int i = 0; i < AbsPeopleAtRiskAsInt; i++) {
            persons[i].setisAtRisk();
        }

        for (int i = 0; i < persons.length; i++) {
            if (persons[i].getCurrentHealthStatus() == INFECTED) {
                if (maskdistanceController.isMasked() && !maskdistanceController.isDistance()) {
                    persons[i].setMasked(true);
                    persons[i].setDistanceOK(false);
                } else if (maskdistanceController.isMasked() && maskdistanceController.isDistance()) {
                    persons[i].setMasked(true);
                    persons[i].setDistanceOK(true);
                } else if (!maskdistanceController.isMasked() && maskdistanceController.isDistance()) {
                    persons[i].setMasked(false);
                    persons[i].setDistanceOK(true);
                }
            }
        }


    }


    public void draw() {

        background(20);
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
        healthyStatePoint.show();
        healthyState.setText(healthyCounter + " Gesunde");
        healthyCounter = 0;


        infectedStatePoint.show();
        infectedState.setText(infectedCounter + " Infizierte");
        infectedCounter = 0;

        deadStatePoint.show();
        deadState.setText(deadCounter + " Tote");
        deadCounter = 0;

        healedStatePoint.show();
        healedState.setText(healedCounter + " Geheilte");
        healedCounter = 0;


        /*
         * DayTime-Simulator: zählt die Frames, um den Reproduktionsfaktor zu visualisieren
         * Setzt die Wahrscheinlichkeit, dass ein Infizierter stirbt
         * */

        for (int i = 0; i < persons.length; i++) {

            if (persons[i].isStoped() == false && persons[i].getCurrentHealthStatus() == INFECTED) {

                //300 Frames entsprechen einem Tag
                if (persons[i].getDaysCounter() >= 300) {
                    persons[i].resetCounters();
                } else {
                    persons[i].riseCounter();
                }


                /*
                Todeswahrscheinlichkeit = GuiDeathRatio, es wird ein Wert zwischen 1 und 1000 gewürfelt,
                wenn dieser kleiner oder gleich DeathRatio ist stirbt die Person.
                Falls eine Person teil der Risikogruppe ist erhöht sich die Sterbewahrscheinlichkeit auf das 3-fache
                 */


                if (((int) (Math.random() * 1000) + 1 <= GuiDeathRatio * 10.0f) && (persons[i].getIsSafe() == false) && persons[i].getisAtRisk() == false) {
                    persons[i].setCurrentHealthStatus(DEAD);
                } else if (persons[i].getisAtRisk() == true && persons[i].getIsSafe() == false && (int) (Math.random() * 1000) + 1 <= GuiDeathRatio * 10.0f * 3.0f) {
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
            GuiBasicReproductionRatioValue = MathUtil.roundOneDigit(slider.getValueF());
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
        if (slider == deathratio) {
            GuiDeathRatio = MathUtil.roundOneDigit(slider.getValueF());
        }
        if (slider == peopleAtRisk) {
            GuiPeopleAtRisk = slider.getValueI();
        }
    }


    /*
     * xxEvents stellt die Werte der Eingabe von der GUI bereit
     * */
    
    public void handleButtonEvents(GButton button, GEvent event) {
        if (button == btnstart && event == GEvent.CLICKED) {
            persons = new Person[GuiNumPersonValue];
            initialize();
        }
        if (button == btnstop && event == GEvent.CLICKED) {
            for (int i = 0; i < persons.length; i++) {
                persons[i].stopMotion();
            }
        }
        if(button==info && event == GEvent.CLICKED){
            if(InfoOpened==false) {
                infoWindow.setVisible(true);
                InfoOpened=true;
            }else{
                infoWindow.setVisible(false);
                InfoOpened=false;
            }

        }
    }

    public void handleToggleControlEvents(GToggleControl box, GEvent event) {
        if (Mask.isSelected() == true && Distance.isSelected() == false) {
            maskdistanceController.setMasked(true);
            maskdistanceController.setDistance(false);
        }
        if (Mask.isSelected() == true && Distance.isSelected() == true) {
            maskdistanceController.setMasked(true);
            maskdistanceController.setDistance(true);
        }
        if (Mask.isSelected() == false && Distance.isSelected() == true) {
            maskdistanceController.setMasked(false);
            maskdistanceController.setDistance(true);
        }
    }

    public void InfoHandler(PApplet p,GWinData data){
        BufferedReader reader = createReader("CoV-19Info.txt");
        String line = null;
        /*try {
            while ((line = reader.readLine()) != null) {
                println(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

}