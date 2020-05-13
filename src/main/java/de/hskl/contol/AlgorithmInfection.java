package de.hskl.contol;

import de.hskl.model.HealthStatus;
import de.hskl.model.Person;

import static processing.core.PApplet.dist;

public class AlgorithmInfection {

    private static float personDistance(Person per1, Person per2) {
        return dist(per1.getPosition().x, per1.getPosition().y, per2.getPosition().x, per2.getPosition().y);
    }


    // TODO Was wird hier gemacht. Beschreibung einfügen
    public static void Infected(Person[] pers, float basicReproductionRatioValue, int dayUPToHealing) {

        for (int i = 0; i < pers.length; i++) {

            if (pers[i].getCurrentHealthStatus() == HealthStatus.INFECTED) {
                //---- Übertragen der Krankheit auf eine andere Person?
                for (int j = 0; j < pers.length; j++) {

                    if ((pers[i] != pers[j]) && (pers[j].getCurrentHealthStatus() == HealthStatus.HEALTHY)) {

                        if (personDistance(pers[i], pers[j]) < pers[j].getRadiusPerson()) {

                            if (pers[i].isAbleToInfect(basicReproductionRatioValue)) {

                                pers[j].setCurrentHealthStatus(HealthStatus.INFECTED);
                                pers[j].setDaysOfInfection(dayUPToHealing);
                            }
                        }
                    }
                }
            }
        }
    }
}
