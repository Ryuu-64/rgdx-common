package org.ryuu.gdx.physics.box2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.ryuu.functional.Action;
import org.ryuu.gdx.scenes.scene2d.StageWrapper;
import org.ryuu.gdx.physics.box2d.interfacecontact.InterfaceContactListener;

public class WorldWrapper implements Disposable {
    @Getter
    private final World world;
    @Getter
    private final Box2DDebugRenderer box2DDebugRenderer;
    @Getter
    private final Settings settings;
    private final OrthographicCamera camera;
    private float stepTime = 0;

    public WorldWrapper(Settings settings, StageWrapper stageWrapper) {
        if (settings == null) {
            throw new IllegalArgumentException();
        }
        this.settings = settings;

        if (stageWrapper == null) {
            throw new IllegalArgumentException();
        }

        this.camera = stageWrapper.getOrthographicCamera();
        if (camera == null) {
            throw new IllegalArgumentException();
        }

        world = new World(settings.gravity, settings.isSleep);
        world.setContactListener(new InterfaceContactListener());
        box2DDebugRenderer = new Box2DDebugRenderer();

        Action render = this::render;
        stageWrapper.afterDraw.add(render);
        stageWrapper.dispose.add(() -> {
            dispose();
            stageWrapper.afterDraw.remove(render);
        });
    }

    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        stepTime += Math.min(deltaTime, settings.maxStepTime); // avoid death spiral
        float fixedTimeStep = settings.fixedTimeStep;
        while (stepTime >= fixedTimeStep) {
            world.step(fixedTimeStep, settings.velocityIterations, settings.positionIterations);
            stepTime -= fixedTimeStep;
        }
        box2DDebugRenderer.render(world, camera.combined);
    }

    @Override
    public void dispose() {
        world.dispose();
        box2DDebugRenderer.dispose();
    }

    @AllArgsConstructor
    @ToString
    public static class Settings {
        public final Vector2 gravity;
        public final boolean isSleep;
        public final float fixedTimeStep;
        public final float maxStepTime;
        public final int velocityIterations;
        public final int positionIterations;
    }
}