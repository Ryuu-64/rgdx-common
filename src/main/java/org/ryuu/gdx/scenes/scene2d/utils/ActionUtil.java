package org.ryuu.gdx.scenes.scene2d.utils;

import com.badlogic.gdx.scenes.scene2d.Action;
import org.ryuu.functional.IAction;

public class ActionUtil {
    private ActionUtil() {
    }

    public static Action of(IAction act) {
        return new Action() {
            @Override
            public boolean act(float delta) {
                act.invoke();
                return true;
            }
        };
    }
}