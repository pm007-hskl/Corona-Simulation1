package de.hskl.model;

import processing.core.PApplet;
import processing.core.PVector;


enum condi {INFECTED, HEALTHY, DEAD,HEALED}

/*
Position der Person wahllos definiert
Movement als fixer Parameter beim erzeugen der Person wird definiert
durch OutOfBounds wird verhindert das sie den Canvas verlassen

 */
public class Person {

    PApplet p;
    PVector position;
    PVector move;
    // condi -> 1:healthy 2:inf 3:dead
    String accualCondition;
    float radius = 7f;
    public int daysOfInfection=0;
    public int dayCounter=0;
    public boolean canInfect=false;
    public int infectCounter=0;
    public boolean isSafe=false;

    public Person(PApplet p) {
        this.p = p;
        //inialer Zustand is healty
        accualCondition = String.valueOf(condi.HEALTHY);

        move = new PVector(p.random(-0.5f, 0.5f), p.random(-0.5f, 0.5f));
    }

    public PVector getPosition() {
        return position;
    }

    public float getRadius() {
        return radius;
    }

    public String getCondition() {
        return accualCondition;
    }

    public void setcondition(String condition) {
        accualCondition = condition;
    }

    public void generatePosition() {
        position = new PVector(p.random(200, p.width), p.random(0, p.height));
    }

    //definiert die gleichmäßige bewegung in eine Richtung
    public void movement() {
        position.x += move.x;
        position.y += move.y;
        if (accualCondition.equals("DEAD")) {
            move.x = 0;
            move.y = 0;
        }
        outOfBounce();

    }

    //Erkennung der Grenzen des Canvas und Invertierung der x bzw y Achse sodass alle in-bounce bleiben
    public void outOfBounce() {
        if (position.x >= p.width || position.x <= 0) {
            move.x = move.x * -1;
        } else if (position.y >= p.height || position.y <= 0) {
            move.y = move.y * -1;
        }else if(position.x<=200){
            move.x=move.x*-1;
        }
    }

    public int getDaysOfInfection() {
        return daysOfInfection;
    }

    //displayed die Person in der Canvas inklusive Farbcodierung
    public void show() {
        if (accualCondition.equals("HEALTHY")) {
            p.stroke(0, 247, 0);
            p.point(position.x, position.y);
        } else if (accualCondition.equals("INFECTED")) {
            p.stroke(255, 255, 0);
            p.point(position.x, position.y);
        } else if(accualCondition.equals("DEAD")){
            p.stroke(255, 0, 0);
            p.point(position.x, position.y);
        }else{
            p.stroke(0,51,255);
            p.point(position.x,position.y);
        }

    }
    public int getCounter(){
        return dayCounter;
    }
    public void riseCounter(){
        daysOfInfection++;
        dayCounter++;
    }
    public void resetCounter(){
        System.out.println("called reset");
        dayCounter=0;
        canInfect=true;
        infectCounter=0;
        isSafe=false;
    }
    public boolean getIsSafe(){
        return isSafe;
    }
    public boolean isAbleToInfect(float reproduction){
        if(infectCounter<reproduction*10.0f) {
            canInfect=true;
            infectCounter+=10;
        }else{
            canInfect=false;
        }

        return canInfect;
    }
    public void setIsSafe(boolean value){
        isSafe=value;
    }
}