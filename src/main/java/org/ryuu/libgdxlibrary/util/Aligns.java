package org.ryuu.libgdxlibrary.util;

import com.badlogic.gdx.math.Vector2;

import static com.badlogic.gdx.utils.Align.*;

public class Aligns {
    private Aligns() {
    }

    public static Vector2 getAnchor(int align) {
        return getVector2(align);
    }

    public static Vector2 getPivot(int align) {
        return getVector2(align);
    }

    private static Vector2 getVector2(int align) {
        float x;
        float y;

        if ((align & top) != 0) {
            y = 1;
        } else if ((align & bottom) != 0)
            y = 0;
        else /* if ((align & top) == 0 && (align & bottom) == 0) */ {
            y = .5F;
        }

        if ((align & left) != 0) {
            x = 0;
        } else if ((align & right) != 0)
            x = 1;
        else /* if ((align & left) == 0 && (align & bottom) == 0) */ {
            x = .5F;
        }

        return new Vector2(x, y);
    }
}