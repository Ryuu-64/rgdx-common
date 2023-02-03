package org.ryuu.gdx.scenes.scene2d.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import org.ryuu.functional.Action;

import static org.ryuu.gdx.scenes.scene2d.utils.ClickListenerFactory.*;

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

    public static <T extends Actor> T addColorChange(T actor, Color color) {
        actor.addListener(colorChange(actor, color, 1));
        return actor;
    }

    public static <T extends Actor> T addColorChange(T actor, Color color, float intensity) {
        actor.addListener(colorChange(actor, color, intensity));
        return actor;
    }

    public static <T extends Actor> T addSizeChange(T actor) {
        actor.addListener(sizeChange(actor, 1.05f, 1 / 60f * 5, 1, 1 / 60f * 5));
        return actor;
    }

    public static <T extends Actor> T addSizeChange(T actor, float downScale, float downScaleDuration, float upScale, float upScaleDuration) {
        actor.addListener(sizeChange(actor, downScale, downScaleDuration, upScale, upScaleDuration));
        return actor;
    }
}