package org.ryuu.gdx.scenes.scene2d.utils;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

public class Actors {
    private Actors() {
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