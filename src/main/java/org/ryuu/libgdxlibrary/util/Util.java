package org.ryuu.libgdxlibrary.util;

import com.badlogic.gdx.math.Vector2;

public class Util {
    private Util() {
    }

    public static Vector2 getIsotropicScaleSize(float width, float height, float targetSize, boolean isWidth) {
        Vector2 size = new Vector2(width, height);
        if (targetSize == 0) {
            throw new IllegalArgumentException();
        }

        float originSize = isWidth ? size.x : size.y;

        return size.scl(targetSize / originSize);
    }
}
