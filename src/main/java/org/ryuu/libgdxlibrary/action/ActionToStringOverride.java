package org.ryuu.libgdxlibrary.action;

import org.ryuu.functional.IAction;

public abstract class ActionToStringOverride extends com.badlogic.gdx.scenes.scene2d.Action {
    @Override
    public String toString() {
        String toString = super.toString();
        if (getTarget() != null) {
            toString += ",target class name=" + getTarget().getClass().getName();
        } else {
            toString += ",target=null";
        }
        return toString;
    }

    public static ActionToStringOverride of(IAction action) {
        return new ActionToStringOverride() {
            @Override
            public boolean act(float delta) {
                action.invoke();
                return true;
            }
        };
    }
}