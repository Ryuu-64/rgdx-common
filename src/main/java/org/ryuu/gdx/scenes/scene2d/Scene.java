package org.ryuu.gdx.scenes.scene2d;

import org.ryuu.gdx.ApplicationListenerManagement;

public class Scene {
    public Scene() {
    }

    public void add(Canvas canvas) {
        if (canvas instanceof GroupCanvas) {
            CanvasManagement.getGameplayCanvas().getStage().addActor(((GroupCanvas) canvas));
        } else if (canvas instanceof StageCanvas) {
            ((StageCanvas) canvas).attachTo(ApplicationListenerManagement.getApplicationListener());
        }
    }
}