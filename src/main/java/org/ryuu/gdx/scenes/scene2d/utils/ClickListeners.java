package org.ryuu.gdx.scenes.scene2d.utils;

import com.badlogic.gdx.scenes.scene2d.Actor;
import org.ryuu.functional.Action;

import static org.ryuu.gdx.scenes.scene2d.utils.ClickListenerFactory.clickListener;

public class ClickListeners {
    private ClickListeners() {
    }

    public static <T extends Actor> T addClickListener(T actor, Action onClick) {
        actor.addListener(clickListener(onClick));
        return actor;
    }

    public static <T extends Actor> T addClickListener(T actor, float clickInterval, float delay, Action onClick) {
        actor.addListener(clickListener(actor, clickInterval, delay, onClick));
        return actor;
    }
}