package org.ryuu.gdx.scenes.scene2d.utils;

import com.badlogic.gdx.scenes.scene2d.Actor;
import org.junit.jupiter.api.Test;

public class ActionUtilTest {
    @Test
    void ofTest() {
        final boolean[] isExecute = {false};
        Actor actor = new Actor();
        actor.addAction(ActionUtil.of(() -> isExecute[0] = true));
        actor.act(0);
        assert (isExecute[0]);
    }

    @Test
    void delayTest() {
        final boolean[] isExecute = {false};
        Actor actor = new Actor();
        actor.addAction(ActionUtil.delay(1, () -> isExecute[0] = true));
        actor.act(0);
        assert !isExecute[0];
        actor.act(1);
        assert (isExecute[0]);
    }

    @Test
    void foreverDelayDurationActTest() {
        final int[] actCount = {0};
        Actor actor = new Actor();
        actor.addAction(ActionUtil.foreverDelay(1, () -> actCount[0]++));
        float duration = 0;
        float timeStep = .02f;
        while (duration < 5 + timeStep * 4) {
            actor.act(timeStep);
            duration += timeStep;
        }
        assert (actCount[0] == 5);
    }

    @Test
    void foreverDelayActDurationTest() {
        final int[] actCount = {0};
        Actor actor = new Actor();
        actor.addAction(ActionUtil.foreverDelay((() -> actCount[0]++), 1));
        float duration = 0;
        float timeStep = .02f;
        while (duration < 5 + timeStep * 4) {
            actor.act(timeStep);
            duration += timeStep;
        }
        assert (actCount[0] == 6);
    }
}