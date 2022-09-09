package org.ryuu.libgdxlibrary.shader;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public interface IShaderProgram {
    ShaderProgram getShader();

    void setShader(ShaderProgram shader);
}
