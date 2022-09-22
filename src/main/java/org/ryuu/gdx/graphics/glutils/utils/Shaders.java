package org.ryuu.gdx.graphics.glutils.utils;

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
                        "uniform mat4 u_projTrans;\n" +
                        "varying vec4 v_color;\n" +
                        "varying vec2 v_texCoords;\n" +
                        "\n" +
                        "void main() {\n" +
                        "    v_color = a_color;\n" +
                        "    v_texCoords = a_texCoord0;\n" +
                        "    gl_Position = u_projTrans * a_position;\n" +
                        "}",
                "#ifdef GL_ES\n" +
                        "precision mediump float;\n" +
                        "#endif\n" +
                        "\n" +
                        "varying vec4 v_color;\n" +
                        "varying vec2 v_texCoords;\n" +
                        "uniform sampler2D u_texture;\n" +
                        "\n" +
                        "void main() {\n" +
                        "    vec4 color = texture2D(u_texture, v_texCoords) * v_color;\n" +
                        "    float grayScale = (color.r * 0.299 + color.g * 0.587 + color.b * 0.114);\n" +
                        "    gl_FragColor = vec4(grayScale, grayScale, grayScale, color.a);\n" +
                        "}"
        );

        if (!GRAY_SCALE.isCompiled()) {
            Gdx.app.log(Shaders.class.toString(), "Grayscale shader compile error!");
            Gdx.app.log(Shaders.class.toString(), GRAY_SCALE.getLog());
        }
    }

    public static final ShaderProgram HDR;

    static {
        HDR = new ShaderProgram(
                "attribute vec4 a_position;\n" +
                        "attribute vec4 a_color;\n" +
                        "attribute vec4 a_hdrColor;\n" +
                        "attribute vec2 a_texCoord0;\n" +
                        "attribute float a_intensity;\n" +
                        "uniform mat4 u_projTrans;\n" +
                        "varying vec4 v_color;\n" +
                        "varying vec4 v_hdrColor;\n" +
                        "varying vec2 v_texCoords;\n" +
                        "varying float v_intensity;\n" +
                        "\n" +
                        "void main() {\n" +
                        "    v_color = a_color;\n" +
                        "    v_hdrColor = a_hdrColor;\n" +
                        "    v_texCoords = a_texCoord0;\n" +
                        "    v_intensity = a_intensity;\n" +
                        "    gl_Position = u_projTrans * a_position;\n" +
                        "}",
                "varying vec4 v_color;\n" +
                        "varying vec4 v_hdrColor;\n" +
                        "varying vec2 v_texCoords;\n" +
                        "varying float v_intensity;\n" +
                        "uniform sampler2D u_texture;\n" +
                        "\n" +
                        "void main() {\n" +
                        "    vec4 hdrColor = v_hdrColor * v_intensity;\n" +
                        "    gl_FragColor = texture2D(u_texture, v_texCoords) * v_color * hdrColor;\n" +
                        "}"
        );

        if (!HDR.isCompiled()) {
            Gdx.app.log(Shaders.class.toString(), "HDR shader compile error!");
            Gdx.app.log(Shaders.class.toString(), HDR.getLog());
        }
    }
}