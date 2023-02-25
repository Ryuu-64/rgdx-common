package org.ryuu.gdx;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import lombok.Getter;
import org.ryuu.gdx.physics.box2d.WorldSettings;
import org.ryuu.gdx.physics.box2d.interfacecontact.InterfaceContactListener;

public class WorldStageWrapper extends DefaultStageWrapper {
    @Getter
    private final World world;
    @Getter
    private final Box2DDebugRenderer box2DDebugRenderer;
    @Getter
    private final WorldSettings worldSettings;
    private float stepTime = 0;

    public WorldStageWrapper(float designWorldWidth, float designWorldHeight, WorldSettings worldSettings) {
        super(designWorldWidth, designWorldHeight);
        if (worldSettings == null) {
            throw new IllegalArgumentException();
        }
        this.worldSettings = worldSettings;
        world = new World(Vector2.Zero, false);
        world.setContactListener(new InterfaceContactListener());
        box2DDebugRenderer = new Box2DDebugRenderer();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        stepTime += Math.min(delta, worldSettings.maxStepTime); // avoid death spiral
        float fixedTimeStep = worldSettings.fixedTimeStep;
        while (stepTime >= fixedTimeStep) {
            world.step(fixedTimeStep, worldSettings.velocityIterations, worldSettings.positionIterations);
            stepTime -= fixedTimeStep;
        }
        box2DDebugRenderer.render(world, orthographicCamera.combined);
    }

    @Override
    public void dispose() {
        super.dispose();
        world.dispose();
        box2DDebugRenderer.dispose();
    }
}