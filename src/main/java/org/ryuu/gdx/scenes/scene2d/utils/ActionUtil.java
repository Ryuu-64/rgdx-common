package org.ryuu.gdx.scenes.scene2d.utils;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
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

    public static Action delay(float duration, IAction act) {
        return Actions.delay(duration, of(act));
    }

    public static Action foreverDelay(float duration, IAction act) {
        return Actions.forever(Actions.sequence(
                Actions.delay(duration, of(act))
        ));
    }

    public static Action foreverDelay(IAction act, float duration) {
        act.invoke();
        return foreverDelay(duration, act);
    }
}