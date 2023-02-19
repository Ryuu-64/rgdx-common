package org.ryuu.gdx;

import com.badlogic.gdx.ScreenAdapter;

import java.util.ArrayList;
import java.util.List;

public class StageWrappersScreen extends ScreenAdapter {
    private final List<StageWrapper> stageWrappers = new ArrayList<>();

    @Override
    public void render(float delta) {
        for (StageWrapper stageWrapper : stageWrappers) {
            stageWrapper.render(delta);
        }
    }

    @Override
    public void resize(int width, int height) {
        for (StageWrapper stageWrapper : stageWrappers) {
            stageWrapper.resize(width, height);
        }
    }

    @Override
    public void dispose() {
        for (StageWrapper stageWrapper : stageWrappers) {
            stageWrapper.dispose();
        }
    }

    public void addStageWrapper(StageWrapper stageWrapper) {
        stageWrappers.add(stageWrapper);
    }
}