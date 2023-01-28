package org.ryuu.gdx.scenes.scene2d;

import com.badlogic.gdx.scenes.scene2d.Stage;
import lombok.Getter;
import lombok.Setter;

public class StageManagement {
    @Getter
    @Setter
    private static Stage stage;

    private StageManagement() {
    }
}