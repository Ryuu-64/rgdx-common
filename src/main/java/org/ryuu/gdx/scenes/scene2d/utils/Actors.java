package org.ryuu.gdx.scenes.scene2d.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

import static com.badlogic.gdx.utils.Align.*;

public class Actors {
    private Actors() {
    }

    public static void align(Actor actor, int align) {
        align(actor, align, align);
    }

    public static void align(Actor actor, int align, float x, float y) {
        align(actor, align, align, x, y);
    }

    public static void align(Actor actor, int alignSelf, int alignParent) {
        Group parent = actor.getParent();
        actor.setPosition(parent.getX(alignParent) - parent.getX(), parent.getY(alignParent) - parent.getY(), alignSelf);
    }

    public static void align(Actor actor, int alignSelf, int alignParent, float x, float y) {
        align(actor, alignSelf, alignParent);
        actor.moveBy(x, y);
    }

    public static void align(Actor actor, Actor reference, int align) {
        actor.setPosition(reference.getX(align), reference.getY(align), align);
    }

    public static void align(Actor actor, Actor reference, int align, float x, float y) {
        actor.setPosition(reference.getX(align), reference.getY(align), align);
        actor.moveBy(x, y);
    }

    public static void align(Actor actor, int alignSelf, Actor reference, int alignReference) {
        actor.setPosition(reference.getX(alignReference), reference.getY(alignReference), alignSelf);
    }

    public static void align(Actor actor, int alignSelf, Actor reference, int alignReference, float x, float y) {
        align(actor, alignSelf, reference, alignReference);
        actor.moveBy(x, y);
    }

    public static void setSize(Actor actor, Actor reference) {
        actor.setSize(reference.getWidth(), reference.getHeight());
    }

    public static void setSizeIsotropic(Actor actor, Actor reference, float scale) {
        float widthScale = reference.getWidth() / actor.getWidth();
        float heightScale = reference.getHeight() / actor.getHeight();
        float targetScale = Math.min(widthScale, heightScale) * scale;
        actor.setSize(actor.getWidth() * targetScale, actor.getHeight() * targetScale);
    }

    public static void setSizeIsotropic(Actor actor, Actor reference) {
        setSizeIsotropic(actor, reference, 1);
    }

    public static void setSizeByWidth(Actor actor, float width) {
        float scale = width / actor.getWidth();
        actor.setSize(actor.getWidth() * scale, actor.getHeight() * scale);
    }

    public static void setSizeByHeight(Actor actor, float height) {
        float scale = height / actor.getHeight();
        actor.setSize(actor.getWidth() * scale, actor.getHeight() * scale);
    }

    public static Vector2 getLocalToStagePosition(Actor actor, int pivot) {
        return getLocalToStagePosition(actor, pivot, 0, 0);
    }

    public static Vector2 getLocalToStagePosition(Actor actor, int pivot, float x, float y) {
        return actor.localToStageCoordinates(new Vector2(actor.getWidth() * pivotX(pivot), actor.getHeight() * pivotY(pivot))).add(x, y);
    }

    public static Vector2 getLocalToActorPosition(Actor actor, int pivot, Actor reference) {
        return getLocalToActorPosition(actor, pivot, reference, 0, 0);
    }

    public static Vector2 getLocalToActorPosition(Actor actor, int pivot, Actor reference, float x, float y) {
        return actor.localToActorCoordinates(reference, new Vector2(actor.getWidth() * pivotX(pivot), actor.getHeight() * pivotY(pivot))).add(x, y);
    }

    private static float pivotX(int pivot) {
        if ((pivot & left) != 0) {
            return 0;
        } else if ((pivot & right) != 0) {
            return 1;
        } else {
            return .5f;
        }
    }

    private static float pivotY(int pivot) {
        if ((pivot & bottom) != 0) {
            return 0;
        } else if ((pivot & top) != 0) {
            return 1;
        } else {
            return .5f;
        }
    }
}