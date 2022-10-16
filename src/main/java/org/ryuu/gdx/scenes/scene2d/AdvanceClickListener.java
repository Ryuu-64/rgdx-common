package org.ryuu.gdx.scenes.scene2d;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import lombok.Getter;
import lombok.Setter;

public class AdvanceClickListener extends ClickListener {
    @Setter
    private float clickInterval;
    @Getter
    private long clickTimestamp = 0;

    public AdvanceClickListener(float clickInterval) {
        this.clickInterval = clickInterval;
    }

    public AdvanceClickListener(int button, float clickInterval) {
        super(button);
        this.clickInterval = clickInterval;
    }

    @Override
    public final void clicked(InputEvent event, float x, float y) {
        if (System.currentTimeMillis() < clickTimestamp + (long) (clickInterval * 1_000L)) {
            return;
        }
        clickedImpl(event, x, y);
        clickTimestamp = System.currentTimeMillis();
    }

    public void clickedImpl(InputEvent event, float x, float y) {
    }
}