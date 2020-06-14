/*
 * MIT License
 * Copyright (c) 2020 Wissam Aalamareen, Zouhir El Kharboubi

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


package de.hskl.contol;

import de.hskl.model.HealthStatus;
import de.hskl.model.Person;

import static processing.core.PApplet.dist;

public class AlgorithmInfection {

    private static float personDistance(Person per1, Person per2) {
        return dist(per1.getPosition().x, per1.getPosition().y, per2.getPosition().x, per2.getPosition().y);
    }


    //
    /*
     *  Vergleicht 2 Personen innerhalb der Infektionsradius, ob diese sich gegenseitig anstecken
     *  (eine Person muss gesund sein und die andere infiziert)
     *  der Reproduktionsfaftor gibt mit an wie viele eine Person infizieren kann
     * */
    public static void infect(Person[] persons, float basicReproductionRatioValue) {
        int count = 0;
        int count2 = 0;
        for (int i = 0; i < persons.length; i++) {
            //wenn Maske nicht getragen wird, infizierung nach folgendem Algorithmus
            //System.out.println(pers[i].isMasked()+" "+pers[i].isDistanceOK());
            if (persons[i].getCurrentHealthStatus() == HealthStatus.INFECTED && persons[i].isMasked() == false && persons[i].isDistanceOK() == false) {
                //---- Übertragen der Krankheit auf eine andere Person?
                for (int j = 0; j < persons.length; j++) {

                    if ((persons[i] != persons[j]) && (persons[j].getCurrentHealthStatus() == HealthStatus.HEALTHY)) {

                        if (personDistance(persons[i], persons[j]) < persons[j].getRadiusPerson()) {

                            if (persons[i].isAbleToInfect(basicReproductionRatioValue)) {
                                //System.out.println("normal called");
                                persons[j].setCurrentHealthStatus(HealthStatus.INFECTED);

                            }
                        }
                    }
                }
            }
            //wenn Maske getragen wird, infefizierung nach folgendem Algorithmus
            if (persons[i].getCurrentHealthStatus() == HealthStatus.INFECTED && persons[i].isMasked() == true && persons[i].isDistanceOK() == false) {
                //---- Übertragen der Krankheit auf eine andere Person?
                for (int j = 0; j < persons.length; j++) {

                    if ((persons[i] != persons[j]) && (persons[j].getCurrentHealthStatus() == HealthStatus.HEALTHY)) {

                        if (personDistance(persons[i], persons[j]) < persons[j].getRadiusPerson()) {

                            if (persons[i].isAbleToInfect(basicReproductionRatioValue) && (int) (Math.random() * 5) == 1) {

                                persons[j].setCurrentHealthStatus(HealthStatus.INFECTED);
                            }
                        }
                    }
                }

            }

            if (persons[i].getCurrentHealthStatus() == HealthStatus.INFECTED && persons[i].isMasked() == false && persons[i].isDistanceOK() == true) {
                //---- Übertragen der Krankheit auf eine andere Person?
                for (int j = 0; j < persons.length; j++) {

                    if ((persons[i] != persons[j]) && (persons[j].getCurrentHealthStatus() == HealthStatus.HEALTHY)) {

                        if (personDistance(persons[i], persons[j]) < persons[j].getRadiusPerson()) {

                            if (persons[i].isAbleToInfect(basicReproductionRatioValue) && (int) (Math.random() * 7) == 1) {

                                persons[j].setCurrentHealthStatus(HealthStatus.INFECTED);

                            }
                        }
                    }
                }
            }
            //wenn Maske getragen wird, infefizierung nach folgendem Algorithmus
            if (persons[i].getCurrentHealthStatus() == HealthStatus.INFECTED && persons[i].isMasked() == true && persons[i].isDistanceOK() == true) {
                //---- Übertragen der Krankheit auf eine andere Person?
                for (int j = 0; j < persons.length; j++) {

                    if ((persons[i] != persons[j]) && (persons[j].getCurrentHealthStatus() == HealthStatus.HEALTHY)) {

                        if (personDistance(persons[i], persons[j]) < persons[j].getRadiusPerson()) {

                            if (persons[i].isAbleToInfect(basicReproductionRatioValue) && (int) (Math.random() * 15) == 1) {
                                System.out.println((int) Math.random() * 10);
                                persons[j].setCurrentHealthStatus(HealthStatus.INFECTED);

                            }
                        }
                    }
                }
            }
        }
    }
}
