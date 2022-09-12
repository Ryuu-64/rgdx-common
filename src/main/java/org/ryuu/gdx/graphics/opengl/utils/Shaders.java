package org.ryuu.gdx.graphics.opengl.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class Shaders {
    private Shaders() {
    }

    public static final ShaderProgram GRAY_SCALE;

    static {
        GRAY_SCALE = new ShaderProgram(
                "attribute vec4 a_position;\n" +
                        "attribute vec4 a_color;\n" +
                        "attribute vec2 a_texCoord0;\n" +
                        "\n" +
                        "uniform mat4 u_projTrans;\n" +
                        "\n" +
                        "varying vec4 v_color;\n" +
                        "varying vec2 v_texCoords;\n" +
                        "\n" +
                        "void main() {\n" +
                        "    v_color = a_color;\n" +
                        "    v_texCoords = a_texCoord0;\n" +
                        "    gl_Position = u_projTrans * a_position;\n" +
                        "}",
                "#ifdef GL_ES\n" +
                        "    precision mediump float;\n" +
                        "#endif\n" +
                        "\n" +
                        "varying vec4 v_color;\n" +
                        "varying vec2 v_texCoords;\n" +
                        "uniform sampler2D u_texture;\n" +
                        "\n" +
                        "void main() {\n" +
                        "  vec4 c = v_color * texture2D(u_texture, v_texCoords);\n" +
                        "  float grey = (c.r + c.g + c.b) / 3.0;\n" +
                        "  gl_FragColor = vec4(grey, grey, grey, c.a);\n" +
                        "}"
        );

        if (!GRAY_SCALE.isCompiled()) {
            Gdx.app.log("Shader", "Grayscale shader compile error!");
            Gdx.app.log("Shader", GRAY_SCALE.getLog());
        }
    }

    public static final ShaderProgram HDR_COLOR;

    static {
        HDR_COLOR = new ShaderProgram(
                "attribute vec4 a_position;\n" +
                        "attribute vec4 a_color;\n" +
                        "attribute vec4 a_multipleColor;\n" +
                        "attribute vec2 a_texCoord0;\n" +
                        "attribute float a_intensity;\n" +
                        "varying vec4 v_color;\n" +
                        "varying vec4 v_multipleColor;\n" +
                        "varying vec2 v_texCoords;\n" +
                        "varying float v_intensity;\n" +
                        "uniform mat4 u_projTrans;\n" +
                        "\n" +
                        "void main() {\n" +
                        "    v_color = a_color;\n" +
                        "    v_multipleColor = a_multipleColor;\n" +
                        "    v_texCoords = a_texCoord0;\n" +
                        "    v_intensity = a_intensity;\n" +
                        "    gl_Position = u_projTrans * a_position;\n" +
                        "}",
                "varying vec4 v_color;\n" +
                        "varying vec4 v_multipleColor;\n" +
                        "varying vec2 v_texCoords;\n" +
                        "varying float v_intensity;\n" +
                        "uniform sampler2D u_texture;\n" +
                        "\n" +
                        "void main() {\n" +
                        "    gl_FragColor = vec4(texture2D(u_texture, v_texCoords).rgb * v_multipleColor * v_intensity, texture2D(u_texture, v_texCoords).a);\n" +
                        "}"
        );

        if (!HDR_COLOR.isCompiled()) {
            Gdx.app.log("Shader", "HDR Color shader compile error!");
            Gdx.app.log("Shader", HDR_COLOR.getLog());
        }
    }
}