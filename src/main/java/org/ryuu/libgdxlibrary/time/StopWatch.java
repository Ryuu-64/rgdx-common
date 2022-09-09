package org.ryuu.libgdxlibrary.time;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Disposable;
import org.ryuu.functional.Action;
import org.ryuu.functional.Action1Arg;
import org.ryuu.functional.IAction;

public class StopWatch implements Disposable {
    private final IAction updateCache = this::update;
    private final Action update;
    public final Action1Arg<Float> onUpdate = new Action1Arg<>();
    private float timeElapsed;
    private boolean isStop;

    public float getTimeElapsed() {
        return timeElapsed;
    }

    public boolean isStop() {
        return isStop;
    }

    public StopWatch(Action update) {
        if (update == null) {
            throw new NullPointerException();
        }
        this.update = update;
        update.add(updateCache);
    }

    public void start() {
        isStop = false;
    }

    public void stop() {
        isStop = true;
    }

    public void reset() {
        timeElapsed = 0;
    }

    private void update() {
        if (isStop) {
            return;
        }
        timeElapsed += Gdx.graphics.getDeltaTime();
        onUpdate.invoke(timeElapsed);
    }

    @Override
    public void dispose() {
        update.remove(updateCache);
    }
}