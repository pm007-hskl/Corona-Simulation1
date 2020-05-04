package de.hskl;

import processing.core.PApplet;

public class App extends PApplet {

    public void settings() {
        size(500, 500);
    }

    public void draw() {
        ellipse(mouseX, mouseY, 50, 50);
    }

    public void mousePressed() {
        background(64);
    }

    public static void main(String[] args) {
        PApplet.main(new String[]{de.hskl.App.class.getName()});
    }
}