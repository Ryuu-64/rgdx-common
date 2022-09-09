package org.ryuu.libgdxlibrary.clicklistener;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import org.ryuu.functional.IAction;
import org.ryuu.functional.IFunc5Arg;
import org.ryuu.libgdxlibrary.shader.IShaderProgram;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static com.badlogic.gdx.utils.Align.center;
import static org.ryuu.libgdxlibrary.shader.Shaders.LIGHT_GRAY;
import static org.ryuu.libgdxlibrary.util.ActionUtil.of;

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

    public static void addDelayClickListener(Actor actor, IAction action) {
        actor.addListener(newDelayClickListener(actor, action));
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
                if (actor instanceof IShaderProgram) {
                    ((IShaderProgram) actor).setShader(LIGHT_GRAY);
                } else {
                    actor.setColor(Color.LIGHT_GRAY);
                }
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (actor instanceof IShaderProgram) {
                    ((IShaderProgram) actor).setShader(null);
                } else {
                    actor.setColor(Color.WHITE);
                }
            }
        };
    }

    public static ClickListener newDownUpColorChange(Actor actor, IFunc5Arg<InputEvent, Float, Float, Integer, Integer, Boolean> isTouchValid) {
        return new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (isTouchValid.invoke(event, x, y, pointer, button)) {
                    actor.setColor(Color.LIGHT_GRAY);
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                actor.setColor(Color.WHITE);
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
                actor.addAction(sequence(
                        scaleTo(1.05F * scaleX, 1.05F * scaleY, 1 / 60F * 5),
                        scaleTo(scaleX, scaleY, 1 / 60F * 5),
                        of(() -> isSizeChange = false)
                ));
                return true;
            }
        };
    }
}