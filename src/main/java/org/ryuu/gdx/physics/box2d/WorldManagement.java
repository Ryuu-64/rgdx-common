package org.ryuu.gdx.physics.box2d;

import com.badlogic.gdx.physics.box2d.World;
import lombok.Getter;
import lombok.Setter;

public class WorldManagement {
    @Getter
    @Setter
    private static World world;
    @Getter
    @Setter
    private static WorldSettings worldSettings;

    private WorldManagement() {
    }
}