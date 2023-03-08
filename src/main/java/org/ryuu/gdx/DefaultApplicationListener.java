package org.ryuu.gdx;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import lombok.Getter;
import org.ryuu.gdx.scenes.scene2d.StageCanvas;

public class DefaultApplicationListener extends MulticastApplicationListener {
    @Getter
    private StageCanvas gameplayCanvas;

    private DefaultApplicationListener() {
    }

    /**
     * Since {@link StageCanvas} will create {@link Stage}, {@link SpriteBatch} will be created when Stage is created.
     * Some fields in the {@link Gdx} class (such as {@link Gdx#gl30} and {@link Gdx#graphics}) are used when creating a {@link SpriteBatch}.
     * Therefore, the creation of {@link StageCanvas} must be at or after {@link ApplicationListener#create()}.
     */
    public static DefaultApplicationListener create(float minWorldWidth, float minWorldHeight) {
        InputProcessorManagement.setInputMultiplexer(new InputMultiplexer());
        DefaultApplicationListener applicationListener = new DefaultApplicationListener();
        applicationListener.render.add(() -> Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT));
        applicationListener.create.add(() -> {
            applicationListener.gameplayCanvas = new StageCanvas(new ExtendViewport(minWorldWidth, minWorldHeight));
            applicationListener.gameplayCanvas.attachTo(applicationListener);
        });
        return applicationListener;
    }
}