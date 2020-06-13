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
            //wenn Maske nicht getragen wird, infizierung nach folgendem Algorithmus
            if (pers[i].getCurrentHealthStatus() == HealthStatus.INFECTED &&pers[i].isMasked()==false) {
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
            //wenn Maske getragen wird, infefizierung nach folgendem Algorithmus
            if (pers[i].getCurrentHealthStatus() == HealthStatus.INFECTED &&pers[i].isMasked()==true) {
                //---- Übertragen der Krankheit auf eine andere Person?
                for (int j = 0; j < pers.length; j++) {

                    if ((pers[i] != pers[j]) && (pers[j].getCurrentHealthStatus() == HealthStatus.HEALTHY)) {

                        if (personDistance(pers[i], pers[j]) < pers[j].getRadiusPerson()) {

                            if (pers[i].isAbleToInfect(basicReproductionRatioValue) && (int)Math.random()==1) {

                                pers[j].setCurrentHealthStatus(HealthStatus.INFECTED);

                            }
                        }
                    }
                }
            }
        }
    }
}
