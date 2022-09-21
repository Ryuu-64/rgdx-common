package org.ryuu.gdx.scenes.scene2d;

import com.badlogic.gdx.scenes.scene2d.Actor;
import org.ryuu.functional.Action2Arg;

@FunctionalInterface
public interface IAfterVisibleChange {
    Action2Arg<Actor, Boolean> getAfterVisibleChange();
}