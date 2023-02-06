package org.ryuu.gdx.scenes.scene2d.utils;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class ActionUtil {
    private ActionUtil() {
    }

    public static Action of(org.ryuu.functional.Action act) {
        return new Action() {
            @Override
            public boolean act(float delta) {
                act.invoke();
                return true;
            }
        };
    }

    public static Action of(org.ryuu.functional.Func1Arg<Float, Boolean> act) {
        return new Action() {
            @Override
            public boolean act(float delta) {
                return act.invoke(delta);
            }
        };
    }

    public static Action delay(float duration, org.ryuu.functional.Action act) {
        return Actions.delay(duration, of(act));
    }

    public static Action foreverDelay(float duration, org.ryuu.functional.Action act) {
        return Actions.forever(Actions.sequence(
                ActionUtil.delay(duration, act)
        ));
    }

    public static Action foreverDelay(org.ryuu.functional.Action act, float duration) {
        act.invoke();
        return foreverDelay(duration, act);
    }
}