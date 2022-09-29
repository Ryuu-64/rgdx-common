package org.ryuu.gdx.scenes.scene2d.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import org.ryuu.functional.IAction;
import org.ryuu.gdx.graphics.glutils.GetMaterial;
import org.ryuu.gdx.graphics.glutils.Material;
import org.ryuu.gdx.graphics.glutils.MaterialProperty;
import org.ryuu.gdx.graphics.glutils.SetMaterial;

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
                actor.addAction(ActionUtil.delay(delay, onClick));
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
            private Material actorMaterial;

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (actor instanceof MaterialProperty) {
                    actorMaterial = ((GetMaterial) actor).getMaterial();
                    Material material = new Material();
                    material.setShaderProgram(HDR);
                    material.setAttributef(HDR_COLOR_ATTRIBUTE, color.r, color.g, color.b, color.a);
                    material.setAttributef(INTENSITY_ATTRIBUTE, intensity, 0, 0, 0);
                    ((SetMaterial) actor).setMaterial(material);
                } else {
                    actor.setColor(color);
                }
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (actor instanceof MaterialProperty) {
                    ((SetMaterial) actor).setMaterial(actorMaterial);
                } else {
                    actor.setColor(WHITE);
                }
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
}