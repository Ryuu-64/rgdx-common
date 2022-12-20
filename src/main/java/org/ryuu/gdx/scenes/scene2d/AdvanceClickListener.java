package org.ryuu.gdx.scenes.scene2d;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import lombok.Getter;
import lombok.Setter;
import org.ryuu.functional.IAction3Arg;

public class AdvanceClickListener extends ClickListener {
    @Setter
    private float clickInterval;
    @Setter
    private IAction3Arg<InputEvent, Float, Float> onClick;
    @Getter
    private long clickTimestamp = 0;

    public AdvanceClickListener(float clickInterval) {
        this.clickInterval = clickInterval;
    }

    public AdvanceClickListener(int button, float clickInterval) {
        super(button);
        this.clickInterval = clickInterval;
    }

    public AdvanceClickListener(float clickInterval, IAction3Arg<InputEvent, Float, Float> onClick) {
        this.clickInterval = clickInterval;
        this.onClick = onClick;
    }

    public AdvanceClickListener(int button, float clickInterval, IAction3Arg<InputEvent, Float, Float> onClick) {
        super(button);
        this.clickInterval = clickInterval;
        this.onClick = onClick;
    }

    @Override
    public final void clicked(InputEvent event, float x, float y) {
        if (System.currentTimeMillis() < clickTimestamp + (long) (clickInterval * 1_000L)) {
            return;
        }
        if (onClick != null) {
            onClick.invoke(event, x, y);
        }
        clickTimestamp = System.currentTimeMillis();
    }
}