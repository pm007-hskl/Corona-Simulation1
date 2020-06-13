package de.hskl.model;

import processing.core.PApplet;
import processing.core.PVector;


/*
 * Bei der Personen Klasse wird unter anderem die Position und Gesundheitszustand gespeichert
 * Die Position der Person wird zuföllig gewählt
 * Movement als fixer Parameter beim Erzeugen der Person wird definiert
 * durch OutOfBounds wird verhindert das sie den Canvas verlassen
 */

public class Person {

    private PApplet p;
    private PVector position;
    private PVector move;
    private HealthStatus currentHealthStatus;
    private float radiusPerson = 7f;
    private int daysOfInfection = 0;
    private int daysCounter = 0;
    private boolean canInfect = false;
    private int infectCounter = 0;
    private boolean isSafe = false;
    private int strokeWeightValue;

    public Person(PApplet p, int strokeWeightValue) {
        this.p = p;
        this.strokeWeightValue = strokeWeightValue;
        currentHealthStatus = HealthStatus.HEALTHY;
        move = new PVector(p.random(-0.5f, 0.5f), p.random(-0.5f, 0.5f));
    }

    public PVector getPosition() {
        return position;
    }

    public float getRadiusPerson() {
        return radiusPerson;
    }

    public HealthStatus getCurrentHealthStatus() {
        return currentHealthStatus;
    }

    public void setCurrentHealthStatus(HealthStatus currentHealthStatus) {
        this.currentHealthStatus = currentHealthStatus;
    }

    public int getDaysOfInfection() {
        return daysOfInfection;
    }

    public int getDaysCounter() {
        return daysCounter;
    }

    public boolean getIsSafe() {
        return isSafe;
    }

    public void setIsSafe(boolean value) {
        isSafe = value;
    }


    public void generateRandomPosition(int strokeWeightValue) {
        position = new PVector(p.random(200 + strokeWeightValue, p.width), p.random(0, p.height));
    }



    /*
     * Bewegt die Person gleichmäßig in eine Richtung
     * */

    public void move() {
        position.x += move.x;
        position.y += move.y;

        /*
         * Wenn die Person tot ist, soll sie sich nicht mehr bewegen
         * */

        if (currentHealthStatus == HealthStatus.DEAD) {
            move.x = 0;
            move.y = 0;
        }

        /*
         * Falls die Person aus der Bildgrenze hinaus gelangt,
         * gilt folgende Regel: Einfallswinkel = Ausfallswinkel
         * */

        outOfBounce();
    }


    /*
     * Erkennung der Grenzen des Canvas. Invertierung der x bzw y Achse, so dass alle Personen im Feld bleiben
     * Regel: Einfallswinkel = Ausfallswinkel
     */

    public void outOfBounce() {
        if (position.x > p.width || position.x <= 0) {
            move.x = -1 * move.x;
        } else if (position.y > p.height || position.y <= 0) {
            move.y = -1 * move.y;
        } else if (position.x <= 200 + strokeWeightValue) {
            move.x = -1 * move.x;
        }
    }


    /*
     * Farbcodierung der Person in RGB Farben auf dem Feld
     * */

    public void show() {
        if (currentHealthStatus == HealthStatus.HEALTHY) {
            p.stroke(0, 247, 0);
            p.point(position.x, position.y);

        } else if (currentHealthStatus == HealthStatus.INFECTED) {
            p.stroke(255, 255, 0);
            p.point(position.x, position.y);

        } else if (currentHealthStatus == HealthStatus.DEAD) {
            p.stroke(255, 0, 0);
            p.point(position.x, position.y);

        } else {
            p.stroke(0, 51, 255);
            p.point(position.x, position.y);
        }
    }


    public void riseCounter() {
        daysOfInfection++;
        daysCounter++;
    }


    public void resetCounters() {
        daysCounter = 0;
        canInfect = true;
        infectCounter = 0;
        isSafe = false;
    }


    public boolean isAbleToInfect(float reproduction) {
        if (infectCounter < reproduction * 10.0f) {
            canInfect = true;
            infectCounter += 10;
        } else {
            canInfect = false;
        }
        return canInfect;
    }
}