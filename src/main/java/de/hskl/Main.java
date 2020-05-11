package de.hskl;

import de.hskl.model.Person;
import g4p_controls.GButton;
import g4p_controls.GCustomSlider;
import g4p_controls.GEvent;
import processing.core.PApplet;

public class Main extends PApplet {
    Person[] pers = new Person[1000];
    public static GCustomSlider slider;
    public static GButton btnstart;
    public static GButton btnstop;

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

        pers[54].setcondition("DEAD");
        btnstart = new GButton(this, 10, 20, 140, 20, "START");
        btnstop = new GButton(this, 10, 50, 140, 20, "STOP");

    }

    public void draw() {

        background(0);
        stroke(255);
        rect(0,0,200,height);
        strokeWeight(3);
        for (int i = 0; i < pers.length; i++) {
            pers[i].movement();
            pers[i].show();
        }
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

}