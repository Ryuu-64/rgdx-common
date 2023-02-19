package org.ryuu.gdx.physics.box2d;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class WorldSettings {
    @Getter
    @Setter
    private float fixedTimeStep;
    @Getter
    @Setter
    private float maxStepTime;
    @Getter
    @Setter
    private int velocityIterations;
    @Getter
    @Setter
    private int positionIterations;
}
