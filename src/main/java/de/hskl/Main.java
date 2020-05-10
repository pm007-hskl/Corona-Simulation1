package de.hskl;

import de.hskl.model.Person;
import processing.core.PApplet;

public class Main extends PApplet {
    Person[] pers=new Person[1000];

    public static void main(String[] args) {
        PApplet.main(new String[]{Main.class.getName()});
    }

    public void settings() {
        size(800, 800);
    }

    public void setup() {
        //frameRate(30);
        for(int i=0;i<pers.length;i++) {
            pers[i] = new Person(this);
            pers[i].generatePosition();
        }
        pers[54].setcondition("DEAD");

    }

    public void draw() {
        background(0);
        strokeWeight(3);
        for(int i=0;i<pers.length;i++){
            pers[i].movement();
            pers[i].show();
        }
    }

}