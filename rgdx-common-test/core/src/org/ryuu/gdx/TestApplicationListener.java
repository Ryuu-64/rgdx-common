package org.ryuu.gdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import org.ryuu.gdx.scenes.scene2d.utils.ActionUtil;

public class TestApplicationListener extends ApplicationAdapter {
    private Stage stage;

    @Override
    public void create() {
        ExtendViewport extendViewport = new ExtendViewport(1920, 1080);
        stage = new Stage(extendViewport);
        stage.addAction(ActionUtil.foreverDelay(1, () -> {
            System.out.println(1);
            System.out.println(System.currentTimeMillis());
        }));
    }

    @Override
    public void render() {
        stage.act();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
