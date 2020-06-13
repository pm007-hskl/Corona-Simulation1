package de.hskl.util;

import g4p_controls.GValueControl;

public class MathGValueControl {

    public static float roundOneDigit(GValueControl slider) {
        return (float) (((int) (slider.getValueF() * 10)) / 10.0);
    }
}
