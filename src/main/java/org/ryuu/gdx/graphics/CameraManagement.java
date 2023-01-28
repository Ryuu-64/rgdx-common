package org.ryuu.gdx.graphics;

import com.badlogic.gdx.graphics.Camera;
import lombok.Getter;
import lombok.Setter;

public class CameraManagement {
    @Getter
    @Setter
    private static Camera camera;

    private CameraManagement() {
    }
}