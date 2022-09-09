package org.ryuu.libgdxlibrary.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import org.ryuu.functional.IAction;
import org.ryuu.libgdxlibrary.action.ActionToStringOverride;

public class ActionUtil {
    private ActionUtil() {
    }

    public static Action of(IAction action) {
        return new ActionToStringOverride() {
            @Override
            public boolean act(float delta) {
                action.invoke();
                return true;
            }
        };
    }
}