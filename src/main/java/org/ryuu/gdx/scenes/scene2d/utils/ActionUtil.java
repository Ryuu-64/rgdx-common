package org.ryuu.gdx.scenes.scene2d.utils;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import org.ryuu.functional.util.FunctionalUtils;

public class ActionUtil {
    private ActionUtil() {
    }

    public static Action of(org.ryuu.functional.Action act) {
        return new Action() {
            @Override
            public boolean act(float delta) {
                FunctionalUtils.invokeNonNull(act);
                return true;
            }
        };
    }

    public static Action delay(float duration, org.ryuu.functional.Action act) {
        return Actions.delay(duration, of(act));
    }

    public static Action foreverDelay(float duration, org.ryuu.functional.Action act) {
        return Actions.forever(ActionUtil.delay(duration, act));
    }

    public static Action foreverDelay(org.ryuu.functional.Action act, float duration) {
        FunctionalUtils.invokeNonNull(act);
        return foreverDelay(duration, act);
    }
}