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
    public static void infect(Person[] pers, float basicReproductionRatioValue) {

        for (int i = 0; i < pers.length; i++) {

            if (pers[i].getCurrentHealthStatus() == HealthStatus.INFECTED) {
                //---- Übertragen der Krankheit auf eine andere Person?
                for (int j = 0; j < pers.length; j++) {

                    if ((pers[i] != pers[j]) && (pers[j].getCurrentHealthStatus() == HealthStatus.HEALTHY)) {

                        if (personDistance(pers[i], pers[j]) < pers[j].getRadiusPerson()) {

                            if (pers[i].isAbleToInfect(basicReproductionRatioValue)) {

                                pers[j].setCurrentHealthStatus(HealthStatus.INFECTED);

                            }
                        }
                    }
                }
            }
        }
    }
}
