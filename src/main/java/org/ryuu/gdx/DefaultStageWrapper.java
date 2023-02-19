package org.ryuu.gdx;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import lombok.Getter;

public class DefaultStageWrapper implements StageWrapper {
    @Getter
    protected final OrthographicCamera orthographicCamera;
    @Getter
    protected final Viewport viewport;
    @Getter
    protected final Stage stage;

    public DefaultStageWrapper(float designWorldWidth, float designWorldHeight) {
        orthographicCamera = new OrthographicCamera();
        viewport = new ExtendViewport(designWorldWidth, designWorldHeight, orthographicCamera);
        stage = new Stage(viewport);
        InputProcessorManagement.getInputMultiplexer().addProcessor(stage);
    }

    @Override
    public void render(float delta) {
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}