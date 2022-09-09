package org.ryuu.libgdxlibrary.time;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Disposable;
import org.ryuu.functional.IAction;
import org.ryuu.functional.Action;

@SuppressWarnings("UnusedReturnValue")
public class Timer implements Disposable {
    private final IAction updateCache = this::update;
    private final Action update;
    public final Action onStop = new Action();
    private float elapsedTime;
    private float timeSpan;
    private boolean isStop;

    public float getElapsedTime() {
        return elapsedTime;
    }

    public float getTimeSpan() {
        return timeSpan;
    }

    public boolean isStop() {
        return isStop;
    }

    public boolean isPause() {
        return elapsedTime != 0 && elapsedTime < timeSpan && isStop;
    }

    public Timer(Action update) {
        if (update == null) {
            throw new NullPointerException();
        }
        this.update = update;
        update.add(updateCache);
    }

    public Timer start() {
        isStop = false;
        return this;
    }

    public Timer set(float timeSpan) {
        this.timeSpan = timeSpan;
        return this;
    }

    public Timer reset() {
        elapsedTime = 0;
        return this;
    }

    private void update() {
        if (isStop) {
            return;
        }

        elapsedTime += Gdx.graphics.getDeltaTime();
        if (elapsedTime < timeSpan) {
            return;
        }

        onStop.invoke();
        stop();
    }

    public Timer stop() {
        isStop = true;
        return this;
    }

    @Override
    public void dispose() {
        update.remove(updateCache);
    }

    public static Timer delay(Action update, float time, IAction action) {
        Timer timer = new Timer(update);
        timer.set(time);
        timer.onStop.add(action);
        timer.onStop.add(timer::dispose);
        timer.start();
        return timer;
    }
}