package org.ryuu.gdx.scenes.scene2d.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import org.ryuu.functional.IAction;
import org.ryuu.gdx.graphics.glutils.GetShaderProgram;
import org.ryuu.gdx.graphics.glutils.SetShaderProgram;
import org.ryuu.gdx.graphics.glutils.ShaderProgramProperty;
import org.ryuu.gdx.graphics.glutils.utils.Shaders;

import static com.badlogic.gdx.graphics.Color.*;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static com.badlogic.gdx.utils.Align.center;
import static org.ryuu.gdx.graphics.glutils.utils.Shaders.*;
import static org.ryuu.gdx.scenes.scene2d.utils.ActionUtil.of;

public class ClickListeners {
    private ClickListeners() {
    }

    public static ClickListener newDelayClickListener(Actor actor, IAction onClick) {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                actor.addAction(delay(1 / 60f * 5, of(onClick)));
            }
        };
    }

    public static void addDelayClickListener(Actor actor, IAction onClick) {
        actor.addListener(newDelayClickListener(actor, onClick));
    }

    public static void addClickListener(Actor actor, IAction onClick) {
        actor.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                actor.addAction(of(onClick));
            }
        });
    }

    public static <T extends Actor> T addColorChange(T actor, Color color, float intensity) {
        actor.addListener(newColorChange(actor, color, intensity));
        return actor;
    }

    public static ClickListener newColorChange(Actor actor, Color color, float intensity) {
        return new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                setColor(actor, color, intensity);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                unsetColor(actor);
            }
        };
    }

    public static <T extends Actor> T addSizeChange(T actor) {
        actor.addListener(newSizeChange(actor));
        return actor;
    }

    public static ClickListener newSizeChange(Actor actor) {
        return new ClickListener() {
            private boolean isSizeChange;

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (isSizeChange) {
                    return true;
                }
                actor.setOrigin(center);
                float scaleX = actor.getScaleX();
                float scaleY = actor.getScaleY();
                isSizeChange = true;
                actor.addAction(sequence(scaleTo(1.05f * scaleX, 1.05f * scaleY, 1 / 60f * 5), scaleTo(scaleX, scaleY, 1 / 60f * 5), of(() -> isSizeChange = false)));
                return true;
            }
        };
    }

    private static void setColor(Actor actor, Color color, float intensity) {
        if (actor instanceof ShaderProgramProperty) {
            ((SetShaderProgram) actor).setShaderProgram(HDR);
            ((GetShaderProgram) actor).getShaderProgram().setAttributef(HDR_COLOR_ATTRIBUTE, color.r, color.g, color.b, color.a);
            ((GetShaderProgram) actor).getShaderProgram().setAttributef(INTENSITY_ATTRIBUTE, intensity, 0, 0, 0);
        } else {
            actor.setColor(color);
        }
    }

    private static void unsetColor(Actor actor) {
        if (actor instanceof SetShaderProgram) {
            ((SetShaderProgram) actor).setShaderProgram(null);
        } else {
            actor.setColor(WHITE);
        }
    }
}