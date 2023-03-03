package org.ryuu.gdx;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import lombok.Getter;
import org.ryuu.functional.Actions;

public class DefaultStageWrapper implements Disposable {
    @Getter
    protected final OrthographicCamera orthographicCamera;
    @Getter
    protected final Viewport viewport;
    @Getter
    protected final Stage stage;
    public final Actions afterDraw = new Actions();
    public final Actions dispose = new Actions();

    public DefaultStageWrapper(float designWorldWidth, float designWorldHeight) {
        orthographicCamera = new OrthographicCamera();
        viewport = new ExtendViewport(designWorldWidth, designWorldHeight, orthographicCamera);
        stage = new Stage(viewport);
        InputProcessorManagement.getInputMultiplexer().addProcessor(stage);
    }

    public void render() {
        stage.act();
        stage.draw();
        afterDraw.invoke();
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        stage.dispose();
        dispose.invoke();
    }
}