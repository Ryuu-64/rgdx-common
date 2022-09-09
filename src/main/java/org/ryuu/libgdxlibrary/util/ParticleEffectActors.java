package org.ryuu.libgdxlibrary.util;

import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.scenes.scene2d.ui.ParticleEffectActor;
import com.badlogic.gdx.utils.Array;

public class ParticleEffectActors {
    private ParticleEffectActors() {
    }

    public static void setEmittersAngleHigh(ParticleEffectActor particleActor, float min, float max) {
        Array<ParticleEmitter> emitters = particleActor.getEffect().getEmitters();
        for (int i = 0; i < emitters.size; i++) {
            emitters.get(i).getAngle().setHighMax(max);
            emitters.get(i).getAngle().setHighMin(min);
        }
    }
}