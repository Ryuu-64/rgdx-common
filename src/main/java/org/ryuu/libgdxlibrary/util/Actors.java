package org.ryuu.libgdxlibrary.util;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

import static com.badlogic.gdx.graphics.Color.CLEAR;
import static com.badlogic.gdx.graphics.Color.WHITE;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static org.ryuu.libgdxlibrary.util.Aligns.*;

public class Actors {
    private Actors() {
    }

    public static Vector2 getIsotropicScaleSize(Actor actor, float targetSize, boolean isWidth) {
        return Util.getIsotropicScaleSize(actor.getWidth(), actor.getHeight(), targetSize, isWidth);
    }

    public static <T extends Actor> T setIsotropicScaleSize(T actor, float targetSize, boolean isWidth) {
        if (targetSize == 0) {
            throw new IllegalArgumentException();
        }

        float width = actor.getWidth();
        float height = actor.getHeight();
        float originSize = isWidth ? width : height;
        float ratio = targetSize / originSize;
        actor.setSize(width * ratio, height * ratio);
        return actor;
    }

    public static void showHideActor(Actor actor) {
        if (actor.isVisible()) {
            return;
        }
        actor.setColor(CLEAR);
        actor.addAction(sequence(
                new Action() {
                    @Override
                    public boolean act(float delta) {
                        actor.setVisible(true);
                        return true;
                    }
                },
                color(WHITE, 1 / 60F * 10),
                delay(.75F),
                color(CLEAR, 1 / 60F * 10),
                new Action() {
                    @Override
                    public boolean act(float delta) {
                        actor.setVisible(false);
                        return true;
                    }
                }
        ));
    }

    public static Vector2 getStagePosition(Actor actor, int pivot) {
        Vector2 pivotVector = getPivot(pivot);
        return actor.localToStageCoordinates(new Vector2(
                actor.getWidth() * pivotVector.x,
                actor.getHeight() * pivotVector.y
        ));
    }

    public static void align(Actor self, int alignSelf, Actor reference, int alignReference, float x, float y) {
        align(self, alignSelf, reference, alignReference);
        self.moveBy(x, y);
    }

    public static void align(Actor self, int alignSelf, Actor reference, int alignReference) {
        self.setPosition(reference.getX(alignReference), reference.getY(alignReference), alignSelf);
    }

    public static void align(Actor self, int align, float x, float y) {
        align(self, align);
        self.moveBy(x, y);
    }

    public static void align(Actor self, int align) {
        Group parent = self.getParent();
        self.setPosition(
                parent.getX(align) - parent.getX(),
                parent.getY(align) - parent.getY(),
                align
        );
    }
}