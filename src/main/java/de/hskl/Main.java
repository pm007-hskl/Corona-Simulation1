package de.hskl;


import de.hskl.model.Person;
import g4p_controls.*;
import processing.core.PApplet;

import java.awt.*;

public class Main extends PApplet {

    GCustomSlider basicReproductionRatio;
    GCustomSlider numPerson;
    GCustomSlider numStartInfections;
    private float basicReproductionRatioValue=2.0f;
    private int numPersonValue=100;
    private int numStartInfectionsValue=4;
    public Person[] pers = new Person[100];
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
    public static int Healedcc = 0;
    public static Font font = new Font("TimesRoman", Font.PLAIN, 20);
    public static int framecounter=0;

    public static int DayUPToHealing;


    public static void main(String[] args) {
        PApplet.main(new String[]{Main.class.getName()});
    }

    public void settings() {
        size(1000, 800);
    }

    public void setup() {

        /*
         * Den Labelnamen der Slider mit den Slider gruppieren
         * */
        initialize();

        btnstart = new GButton(this, 10, 20, 140, 20, "START");
        btnstop = new GButton(this, 10, 50, 140, 20, "STOP");
        btnstart.setLocalColorScheme(GCScheme.CYAN_SCHEME);
        btnstop.setLocalColorScheme(GCScheme.RED_SCHEME);

        healthyState = new GLabel(this, 0, 500, 200, 100);
        InfectedState = new GLabel(this, 0, 570, 200, 100);
        DeadState = new GLabel(this, 0, 640, 200, 100);
        HealedState = new GLabel(this, 0, 710, 200, 100);
        healthyState.setFont(font);
        InfectedState.setFont(font);
        DeadState.setFont(font);
        HealedState.setFont(font);
        GGroup groupOfLabelReprRatio = new GGroup(this);
        GLabel labelRepRatio = new GLabel(this, 0, 70, 200, 30, "Reproduktionszahl");
        buildSliderForBasicReproductionRatio();
        groupOfLabelReprRatio.addControls(labelRepRatio, basicReproductionRatio);



        GGroup groupOfLabelNumPerson = new GGroup(this);
        GLabel labelNumPerson = new GLabel(this, 0, 170, 200, 30, "Gesamtanzahl der Personen");
        buildSliderForNumberPerson();
        groupOfLabelReprRatio.addControls(labelNumPerson, numPerson);

        GGroup groupOfLabelNumStartInfects = new GGroup(this);
        GLabel labelNumInfects = new GLabel(this, 0, 270, 200, 30, "Anzahl der Infizierte am Anfang");
        buildSliderForNumberStartInfects();
        groupOfLabelReprRatio.addControls(labelNumInfects, numStartInfections);
        //frameRate(30);
    }

    //alles was durch Slider geändert wird muss hier neu geladen werden
    private void initialize() {
        if(numPersonValue>=numStartInfectionsValue){
            for (int i = 0; i < numPersonValue; i++) {
                pers[i] = new Person(this);
                pers[i].generatePosition();
            }

            for(int i=0;i<numStartInfectionsValue;i++){
                pers[i].setcondition("INFECTED");
            }
        }else{
            for (int i = 0; i < numPersonValue; i++) {
                pers[i] = new Person(this);
                pers[i].generatePosition();
            }
            for(int i=0;i<numPersonValue;i++){
                pers[i].setcondition("INFECTED");
            }

        }




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
                case "HEALED":
                    Healedcc++;
                    break;
            }
        }
        Infected();


        healthyState.setText("Anzahl gesunder Menschen: " + healthycc);
        healthycc = 0;

        InfectedState.setText("Anzahl infizierter Menschen: " + Infectedcc);
        Infectedcc = 0;

        DeadState.setText("Anzahl Tote: " + Deadcc);
        Deadcc = 0;

        HealedState.setText("Anzahl geheilter Personen: " + Healedcc);
        Healedcc = 0;
        for(int i=0;i<pers.length;i++){
            if(pers[i].getCondition().equals("INFECTED")) {
                if (pers[i].getCounter() >= 300) {
                    pers[i].resetCounter();
                } else {
                    pers[i].riseCounter();
                }
                if((int)(Math.random()*1000)+1<=8&&pers[i].getIsSafe()==false){
                    pers[i].setcondition("DEAD");
                }else{
                    pers[i].setIsSafe(true);
                }
                if(pers[i].getDaysOfInfection()>=700){
                    pers[i].setcondition("HEALED");
                }
            }
        }
    }


    public void handleSliderEvents(GValueControl slider, GEvent event) {
        if (slider == basicReproductionRatio) {
            basicReproductionRatioValue= round1diggit(slider);
            //System.out.println("Reproduktionszahl: " + basicReproductionRatioValue);
        }
        if (slider == numPerson) {
            numPersonValue = slider.getValueI();
            //System.out.println("Anzahl Personen: " + numPersonValue);
        }
        if (slider == numStartInfections) {
            numStartInfectionsValue = slider.getValueI();
            //System.out.println("Anzahl Start Infizierte" + numStartInfectionsValue);
        }
    }



    // Slider Settings für Reproduktionsfaktor
    public void buildSliderForBasicReproductionRatio() {
        basicReproductionRatio = new GCustomSlider(this, 5, 70, 200, 100, "grey_blue");
        basicReproductionRatio.setShowValue(true);
        basicReproductionRatio.setShowLimits(true);
        basicReproductionRatio.setLimits(2, 0, 10);
        basicReproductionRatio.setNbrTicks(11);
        basicReproductionRatio.setShowTicks(true);
        basicReproductionRatio.setEasing(1.0f);
        basicReproductionRatio.setNumberFormat(G4P.DECIMAL, 0);
        basicReproductionRatio.setOpaque(false);
        basicReproductionRatio.setPrecision(1);
    }


    public void buildSliderForNumberPerson() {
        numPerson = new GCustomSlider(this, 5, 170, 200, 100, "grey_blue");
        numPerson.setShowValue(true);
        numPerson.setShowLimits(true);
        numPerson.setLimits(100, 100, 1000);
        numPerson.setNbrTicks(11);
        numPerson.setShowTicks(true);
        numPerson.setEasing(1.0f);
        numPerson.setNumberFormat(G4P.INTEGER, 0);
        numPerson.setOpaque(false);
    }


    public void buildSliderForNumberStartInfects() {
        numStartInfections = new GCustomSlider(this, 5, 270, 200, 100, "grey_blue");
        numStartInfections.setShowValue(true);
        numStartInfections.setShowLimits(true);
        numStartInfections.setLimits(4, 0, 100);
        numStartInfections.setNbrTicks(11);
        numStartInfections.setShowTicks(true);
        numStartInfections.setEasing(1.0f);
        numStartInfections.setNumberFormat(G4P.INTEGER, 0);
        numStartInfections.setOpaque(false);
    }


    public void handleButtonEvents(GButton button, GEvent event) {
        if (button == btnstart && event == GEvent.CLICKED) {
            pers = new Person[numPersonValue];
            initialize();
            loop();
        }
        if (button == btnstop && event == GEvent.CLICKED) {
            noLoop();
        }
    }

    private float personDistance(Person per, Person per1) {
        return dist(per.getPosition().x, per.getPosition().y, per1.getPosition().x, per1.getPosition().y);
    }

    private float round1diggit(GValueControl slider) {
        return (float)(((int)(slider.getValueF()*10))/10.0);
    }

    public void Infected() {

        for (int i=0; i<pers.length; i++) {
            if (pers[i].getCondition().equals("INFECTED")) {
                //---- Übertragen der Krankheit auf eine andere Person?
                for (int j=0; j<pers.length; j++) {
                    if(pers[i]!=pers[j]&&pers[j].getCondition().equals("HEALTHY")){
                        if (personDistance(pers[i], pers[j]) < pers[j].getRadius()) {
                            if(pers[i].isAbleToInfect(basicReproductionRatioValue)){
                                pers[j].setcondition("INFECTED");
                                pers[j].daysOfInfection = DayUPToHealing;
                            }


                        }
                    }
                }
            }
        }
    }



}