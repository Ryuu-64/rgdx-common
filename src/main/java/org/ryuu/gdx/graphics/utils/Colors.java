package org.ryuu.gdx.graphics.utils;

import com.badlogic.gdx.graphics.Color;

public class Colors {
    private Colors() {
    }

    public static Color color32Bit(int r, int g, int b, int a) {
        return new Color(r / 255f, g / 255f, b / 255f, a / 255f);
    }
}