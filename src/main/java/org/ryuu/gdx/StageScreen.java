package org.ryuu.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import lombok.Getter;

public class StageScreen extends ScreenAdapter {
    @Getter
    private final OrthographicCamera orthographicCamera;
    @Getter
    private final ExtendViewport extendViewport;
    @Getter
    private final Stage stage;
    @Getter
    private final InputMultiplexer inputMultiplexer = new InputMultiplexer();

    public StageScreen(float designWorldWidth, float designWorldHeight) {
        orthographicCamera = new OrthographicCamera();
        extendViewport = new ExtendViewport(designWorldWidth, designWorldHeight, orthographicCamera);
        stage = new Stage(extendViewport);
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        extendViewport.update(width, height);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}