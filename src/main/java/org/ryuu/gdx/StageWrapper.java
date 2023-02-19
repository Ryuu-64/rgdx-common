package org.ryuu.gdx;

import com.badlogic.gdx.utils.Disposable;
import org.ryuu.gdx.scenes.scene2d.GetStage;
import org.ryuu.gdx.utils.viewport.GetViewport;

public interface StageWrapper extends GetStage, GetViewport, Disposable {
    void render(float delta);

    void resize(int width, int height);

    void dispose();
}