package org.ryuu.gdx.scenes.scene2d.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import org.ryuu.functional.Action;
import org.ryuu.gdx.graphics.glutils.GetMaterial;
import org.ryuu.gdx.graphics.glutils.Material;
import org.ryuu.gdx.graphics.glutils.MaterialProperty;
import org.ryuu.gdx.graphics.glutils.SetMaterial;
import org.ryuu.gdx.scenes.scene2d.AdvanceClickListener;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;
import static org.ryuu.gdx.graphics.glutils.utils.Shaders.*;

public class ClickListenerFactory {
    private ClickListenerFactory() {
    }

    public static AdvanceClickListener clickListener(Actor actor, float clickInterval, float delay, Action onClick) {
        return new AdvanceClickListener(clickInterval, (event, x, y) -> actor.addAction(ActionUtil.delay(delay, onClick)));
    }

    public static AdvanceClickListener clickListener(Action onClick) {
        return new AdvanceClickListener(0, (event, x, y) -> onClick.invoke());
    }

    public static AdvanceClickListener colorChange(Actor actor, Color color, float intensity) {
        return new AdvanceClickListener(0) {
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
                }
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (actor instanceof MaterialProperty) {
                    ((SetMaterial) actor).setMaterial(actorMaterial);
                }
            }
        };
    }

    public static AdvanceClickListener sizeChange(Actor actor, float downScale, float downScaleDuration, float upScale, float upScaleDuration) {
        return new AdvanceClickListener(0) {
            private final float touchDownScaleX = actor.getScaleX();
            private final float touchDownScaleY = actor.getScaleY();

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                actor.addAction(scaleTo(downScale * touchDownScaleX, downScale * touchDownScaleY, downScaleDuration));
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                actor.addAction(scaleTo(upScale * touchDownScaleX, upScale * touchDownScaleY, upScaleDuration));
            }
        };
    }
}