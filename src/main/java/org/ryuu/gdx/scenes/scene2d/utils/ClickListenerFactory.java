package org.ryuu.gdx.scenes.scene2d.utils;

import com.badlogic.gdx.scenes.scene2d.Actor;
import org.ryuu.functional.Action;
import org.ryuu.functional.util.FunctionalUtils;
import org.ryuu.gdx.scenes.scene2d.AdvanceClickListener;

public class ClickListenerFactory {
    private ClickListenerFactory() {
    }

    public static AdvanceClickListener clickListener(Action onClick) {
        return new AdvanceClickListener(0, (event, x, y) -> FunctionalUtils.invokeNonNull(onClick));
    }

    public static AdvanceClickListener clickListener(Actor actor, float clickInterval, float delay, Action onClick) {
        return new AdvanceClickListener(clickInterval, (event, x, y) -> actor.addAction(ActionUtil.delay(delay, onClick)));
    }
}