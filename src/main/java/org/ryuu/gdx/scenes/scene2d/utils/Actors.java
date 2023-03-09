package org.ryuu.gdx.scenes.scene2d.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;

import static com.badlogic.gdx.utils.Align.*;

public class Actors {
    private Actors() {
    }

    public static void align(Actor actor, int alignSelf, Actor reference, int alignReference) {
        if (actor == reference) {
            return;
        }

        Group actorParent = actor.getParent();
        Group referenceParent = reference.getParent();
        if (reference == actorParent || actorParent == referenceParent) {
            actor.setPosition(reference.getX(alignReference), reference.getY(alignReference), alignSelf);
            return;
        }

        Stage actorStage = actor.getStage();
        Group actorRoot = actorStage.getRoot();
        if (reference == actorRoot) {
            actor.setPosition(
                    actorStage.getWidth() * alignX(alignReference),
                    actorStage.getHeight() * alignY(alignReference),
                    alignSelf
            );
            return;
        }

        Stage referenceStage = reference.getStage();
        if (actorStage != referenceStage) {
            Vector3 vector3 = new Vector3(reference.getX(alignReference), reference.getY(alignReference), 0);
            referenceStage.getCamera().project(vector3);
            vector3.y = Gdx.graphics.getHeight() - vector3.y;
            actorStage.getCamera().unproject(vector3);
            actor.setPosition(vector3.x, vector3.y, alignSelf);
            return;
        }

        Vector2 localToActorPosition = getLocalToActorPosition(reference, alignReference, actor.getParent());
        actor.setPosition(localToActorPosition.x, localToActorPosition.y, alignSelf);
    }

    public static void align(Actor actor, int alignSelf, Actor reference, int alignReference, float x, float y) {
        align(actor, alignSelf, reference, alignReference);
        actor.moveBy(x, y);
    }

    public static void align(Actor actor, Actor reference, int align) {
        align(actor, align, reference, align);
    }

    public static void align(Actor actor, Actor reference, int align, float x, float y) {
        align(actor, reference, align);
        actor.moveBy(x, y);
    }

    public static void align(Actor actor, int alignSelf, int alignParent) {
        align(actor, alignSelf, actor.getParent(), alignParent);
    }

    public static void align(Actor actor, int align) {
        align(actor, align, align);
    }

    public static void align(Actor actor, int alignSelf, int alignParent, float x, float y) {
        align(actor, alignSelf, alignParent);
        actor.moveBy(x, y);
    }

    public static void align(Actor actor, int align, float x, float y) {
        align(actor, align, align, x, y);
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
        return actor.localToStageCoordinates(new Vector2(actor.getWidth() * alignX(pivot), actor.getHeight() * alignY(pivot)));
    }

    public static Vector2 getLocalToStagePosition(Actor actor, int pivot, float x, float y) {
        return getLocalToStagePosition(actor, pivot).add(x, y);
    }

    public static Vector2 getLocalToActorPosition(Actor actor, int pivot, Actor reference) {
        return actor.localToActorCoordinates(reference, new Vector2(actor.getWidth() * alignX(pivot), actor.getHeight() * alignY(pivot)));
    }

    public static Vector2 getLocalToActorPosition(Actor actor, int pivot, Actor reference, float x, float y) {
        return getLocalToActorPosition(actor, pivot, reference).add(x, y);
    }

    private static float alignX(int align) {
        if ((align & left) != 0) {
            return 0;
        } else if ((align & right) != 0) {
            return 1;
        } else {
            return .5f;
        }
    }

    private static float alignY(int align) {
        if ((align & bottom) != 0) {
            return 0;
        } else if ((align & top) != 0) {
            return 1;
        } else {
            return .5f;
        }
    }
}