package de.hskl;

import de.hskl.model.Person;
import g4p_controls.GCustomSlider;
import processing.core.PApplet;

public class Main extends PApplet {
    Person[] pers = new Person[1000];
    public static GCustomSlider slider;
    public static void main(String[] args) {
        PApplet.main(new String[]{Main.class.getName()});
    }

    public void settings() {
        size(800, 800);
    }

    public void setup() {
        //frameRate(30);
        for (int i = 0; i < pers.length; i++) {
            pers[i] = new Person(this);
            pers[i].generatePosition();
        }
        pers[54].setcondition("DEAD");


    }

    public void draw() {
        background(0);
        strokeWeight(3);
        for (int i = 0; i < pers.length; i++) {
            pers[i].movement();
            pers[i].show();
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