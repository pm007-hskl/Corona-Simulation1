package de.hskl.util;

import g4p_controls.GValueControl;

public class MathUtil {

    public static float roundOneDigit(float value) {
        return (float) (((int) (value * 10)) / 10.0);
    }
}
