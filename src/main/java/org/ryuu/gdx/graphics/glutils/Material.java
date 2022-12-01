package org.ryuu.gdx.graphics.glutils;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class Material {
    @Getter
    @Setter
    private ShaderProgram shaderProgram;
    private final HashMap<String, Attributef> attributefMap = new HashMap<>();

    public void applyShaderParameters() {
        if (shaderProgram == null) {
            return;
        }

        for (Map.Entry<String, Attributef> entry : attributefMap.entrySet()) {
            String name = entry.getKey();
            Attributef attributef = entry.getValue();
            shaderProgram.setAttributef(name, attributef.value1, attributef.value2, attributef.value3, attributef.value4);
        }
    }

    public void setAttributef(String name, float value1, float value2, float value3, float value4) {
        attributefMap.put(name, new Attributef(value1, value2, value3, value4));
    }

    @AllArgsConstructor
    private static class Attributef {
        private final float value1;
        private final float value2;
        private final float value3;
        private final float value4;
    }
}