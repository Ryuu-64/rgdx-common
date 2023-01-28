package org.ryuu.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import lombok.Getter;
import org.ryuu.gdx.physics.box2d.WorldSettings;
import org.ryuu.gdx.physics.box2d.interfacecontact.InterfaceContactListener;

public class StageWorldScreen extends ScreenAdapter {
    @Getter
    private final OrthographicCamera orthographicCamera;
    @Getter
    private final ExtendViewport extendViewport;
    @Getter
    private final Stage stage;
    @Getter
    private final InputMultiplexer inputMultiplexer = new InputMultiplexer();
    @Getter
    private final World world;
    @Getter
    private final Box2DDebugRenderer box2DDebugRenderer;
    @Getter
    private final WorldSettings worldSettings;
    private float stepTime = 0;

    public StageWorldScreen(float designWorldWidth, float designWorldHeight, WorldSettings worldSettings) {
        this.worldSettings = worldSettings;
        orthographicCamera = new OrthographicCamera();
        extendViewport = new ExtendViewport(designWorldWidth, designWorldHeight, orthographicCamera);
        stage = new Stage(extendViewport);
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);
        world = new World(Vector2.Zero, false);
        world.setContactListener(new InterfaceContactListener());
        box2DDebugRenderer = new Box2DDebugRenderer();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
        float fixedTimeStep = worldSettings.getFixedTimeStep();
        stepTime += Math.min(delta, worldSettings.getMaxStepTime()); // avoid death spiral
        while (stepTime >= fixedTimeStep) {
            world.step(fixedTimeStep, worldSettings.getVelocityIterations(), worldSettings.getPositionIterations());
            stepTime -= fixedTimeStep;
        }
        float worldUnitPerMeter = worldSettings.getWorldUnitPerMeter();
        box2DDebugRenderer.render(world, orthographicCamera.combined.cpy().scale(worldUnitPerMeter, worldUnitPerMeter, worldUnitPerMeter));
    }

    @Override
    public void resize(int width, int height) {
        extendViewport.update(width, height);
    }

    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
        box2DDebugRenderer.dispose();
    }
}