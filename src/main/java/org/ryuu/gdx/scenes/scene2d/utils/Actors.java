package org.ryuu.gdx.scenes.scene2d.utils;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

public class Actors {
    private Actors() {
    }

    public static void align(Actor self, int alignSelf, Actor reference, int alignReference) {
        self.setPosition(reference.getX(alignReference), reference.getY(alignReference), alignSelf);
    }

    public static void align(Actor self, int alignSelf, Actor reference, int alignReference, float x, float y) {
        align(self, alignSelf, reference, alignReference);
        self.moveBy(x, y);
    }

    public static void align(Actor actor, int alignSelf, int alignParent) {
        Group parent = actor.getParent();
        actor.setPosition(
                parent.getX(alignParent) - parent.getX(),
                parent.getY(alignParent) - parent.getY(),
                alignSelf
        );
    }

    public static void align(Actor actor, int alignSelf, int alignParent, float x, float y) {
        align(actor, alignSelf, alignParent);
        actor.moveBy(x, y);
    }

    public static void align(Actor actor, int align) {
        align(actor, align, align);
    }

    public static void align(Actor actor, int align, float x, float y) {
        align(actor, align, align, x, y);
    }

    public static void setSize(Actor self, Actor reference) {
        self.setSize(reference.getWidth(), reference.getHeight());
    }

    public static void setSizeIsotropic(Actor self, Actor reference, float scale) {
        float widthScale = reference.getWidth() / self.getWidth();
        float heightScale = reference.getHeight() / self.getHeight();
        float targetScale = Math.min(widthScale, heightScale) * scale;
        self.setSize(self.getWidth() * targetScale, self.getHeight() * targetScale);
    }

    public static void setSizeIsotropic(Actor self, Actor reference) {
        setSizeIsotropic(self, reference, 1);
    }

    public static void setSizeByWidth(Actor actor, int width) {
        float scale = width / actor.getWidth();
        actor.setSize(actor.getWidth() * scale, actor.getHeight() * scale);
    }

    public static void setSizeByHeight(Actor actor, int height) {
        float scale = height / actor.getHeight();
        actor.setSize(actor.getWidth() * scale, actor.getHeight() * scale);
    }
}