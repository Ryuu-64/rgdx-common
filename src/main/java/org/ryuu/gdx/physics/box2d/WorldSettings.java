package org.ryuu.gdx.physics.box2d;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class WorldSettings {
    public final float fixedTimeStep;
    public final float maxStepTime;
    public final int velocityIterations;
    public final int positionIterations;
}