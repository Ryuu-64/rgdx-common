package org.ryuu.gdx.graphics.opengl;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;

@FunctionalInterface
public interface ISetShaderProgram {
    void setShaderProgram(ShaderProgram shaderProgram);
}