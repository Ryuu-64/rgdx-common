package org.ryuu.gdx;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import org.ryuu.functional.Actions;
import org.ryuu.functional.Actions2Args;
import org.ryuu.gdx.scenes.scene2d.CanvasManagement;
import org.ryuu.gdx.scenes.scene2d.StageCanvas;

public class MulticastApplicationListener implements ApplicationListener, Disposable {
    public final Actions create = new Actions();
    public final Actions2Args<Integer, Integer> resize = new Actions2Args<>();
    public final Actions render = new Actions();
    public final Actions pause = new Actions();
    public final Actions resume = new Actions();
    public final Actions dispose = new Actions();

    @Override
    public void create() {
        create.invoke();
    }

    @Override
    public void resize(int width, int height) {
        resize.invoke(width, height);
    }

    @Override
    public void render() {
        render.invoke();
    }

    @Override
    public void pause() {
        pause.invoke();
    }

    @Override
    public void resume() {
        resume.invoke();
    }

    @Override
    public void dispose() {
        dispose.invoke();
    }

    /**
     * Since {@link StageCanvas} will create {@link Stage}, {@link SpriteBatch} will be created when Stage is created.
     * Some fields in the {@link Gdx} class (such as {@link Gdx#gl30} and {@link Gdx#graphics}) are used when creating a {@link SpriteBatch}.
     * Therefore, the creation of {@link StageCanvas} must be at or after {@link ApplicationListener#create()}.
     */
    public static void create(float minWorldWidth, float minWorldHeight) {
        InputProcessorManagement.setInputMultiplexer(new InputMultiplexer());
        MulticastApplicationListener applicationListener = new MulticastApplicationListener();
        applicationListener.render.add(() -> Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT));
        applicationListener.create.add(() -> {
            StageCanvas gameplayCanvas = new StageCanvas(new ExtendViewport(minWorldWidth, minWorldHeight));
            gameplayCanvas.attachTo(applicationListener);
            CanvasManagement.setGameplayCanvas(gameplayCanvas);
        });
        ApplicationListenerManagement.setApplicationListener(applicationListener);
    }
}