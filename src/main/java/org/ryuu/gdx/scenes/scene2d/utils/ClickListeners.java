package org.ryuu.gdx.scenes.scene2d.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import org.ryuu.functional.IAction;
import org.ryuu.functional.IFunc5Arg;
import org.ryuu.gdx.graphics.opengl.IGetShaderProgram;
import org.ryuu.gdx.graphics.opengl.ISetShaderProgram;
import org.ryuu.gdx.graphics.opengl.IShaderProgram;
import org.ryuu.gdx.graphics.opengl.utils.Shaders;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static com.badlogic.gdx.utils.Align.center;
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

    public static ClickListener newDownUpColorChange(Actor actor) {
        return new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                setColorLightGray(actor);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                unsetColorLightGray(actor);
            }
        };
    }

    public static ClickListener newDownUpColorChange(Actor actor, IFunc5Arg<InputEvent, Float, Float, Integer, Integer, Boolean> conditionValid) {
        return new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (conditionValid.invoke(event, x, y, pointer, button)) {
                    setColorLightGray(actor);
                }
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (conditionValid.invoke(event, x, y, pointer, button)) {
                    unsetColorLightGray(actor);
                }
            }
        };
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
                actor.addAction(sequence(scaleTo(1.05F * scaleX, 1.05F * scaleY, 1 / 60F * 5), scaleTo(scaleX, scaleY, 1 / 60F * 5), of(() -> isSizeChange = false)));
                return true;
            }
        };
    }

    private static void setColorLightGray(Actor actor) {
        if (actor instanceof IShaderProgram) {
            ((ISetShaderProgram) actor).setShaderProgram(Shaders.HDR_COLOR);
            ((IGetShaderProgram) actor).getShaderProgram().setAttributef("a_intensity", 1.35f, 0, 0, 0);
            ((IGetShaderProgram) actor).getShaderProgram().setAttributef("a_multipleColor", 1f, 1f, 1f, 1f);
        } else {
            actor.setColor(Color.LIGHT_GRAY);
        }
    }

    private static void unsetColorLightGray(Actor actor) {
        if (actor instanceof ISetShaderProgram) {
            ((ISetShaderProgram) actor).setShaderProgram(null);
        } else {
            actor.setColor(Color.WHITE);
        }
    }
}