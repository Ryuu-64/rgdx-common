package org.ryuu.gdx.scenes.scene2d.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import org.ryuu.functional.IAction;
import org.ryuu.gdx.graphics.glutils.GetShaderProgram;
import org.ryuu.gdx.graphics.glutils.SetShaderProgram;
import org.ryuu.gdx.graphics.glutils.ShaderProgramProperty;

import static com.badlogic.gdx.graphics.Color.WHITE;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static com.badlogic.gdx.utils.Align.center;
import static org.ryuu.gdx.graphics.glutils.utils.Shaders.*;
import static org.ryuu.gdx.scenes.scene2d.utils.ActionUtil.of;

public class ClickListenerFactory {
    private ClickListenerFactory() {
    }

    public static ClickListener delayClickListener(Actor actor, IAction onClick, float delay) {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                actor.addAction(delay(delay, of(onClick)));
            }
        };
    }

    public static ClickListener clickListener(IAction onClick) {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                onClick.invoke();
            }
        };
    }

    public static ClickListener colorChange(Actor actor, Color color, float intensity) {
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

    public static ClickListener sizeChange(Actor actor) {
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
                actor.addAction(sequence(
                        scaleTo(1.05f * scaleX, 1.05f * scaleY, 1 / 60f * 5),
                        scaleTo(scaleX, scaleY, 1 / 60f * 5),
                        of(() -> isSizeChange = false)
                ));
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