package org.ryuu.gdx.graphics.glutils.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class Shaders {
    @SuppressWarnings("GDXJavaStaticResource")
    public static final ShaderProgram GRAY_SCALE;
    @SuppressWarnings("GDXJavaStaticResource")
    public static final ShaderProgram HDR;
    public static final String HDR_COLOR_ATTRIBUTE = "a_hdrColor";
    public static final String INTENSITY_ATTRIBUTE = "a_intensity";
    @SuppressWarnings("GDXJavaStaticResource")
    public static final ShaderProgram CLIPPING;
    public static final String BOTTOM_LEFT_ATTRIBUTE = "a_bottomLeft";
    public static final String TOP_RIGHT_ATTRIBUTE = "a_topRight";

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
            Gdx.app.error(Shaders.class.toString(), GRAY_SCALE.getLog());
        }
    }

    static {
        CLIPPING = new ShaderProgram(
                "attribute vec4 a_position;\n" +
                        "attribute vec4 a_color;\n" +
                        "attribute vec2 a_texCoord0;\n" +
                        "attribute vec2 a_bottomLeft;\n" +
                        "attribute vec2 a_topRight;\n" +
                        "uniform mat4 u_projTrans;\n" +
                        "varying vec4 v_color;\n" +
                        "varying vec2 v_texCoords;\n" +
                        "varying vec2 v_bottomLeft;\n" +
                        "varying vec2 v_topRight;\n" +
                        "\n" +
                        "void main() {\n" +
                        "    v_color = a_color;\n" +
                        "    v_texCoords = a_texCoord0;\n" +
                        "    v_bottomLeft = a_bottomLeft;\n" +
                        "    v_topRight = a_topRight;\n" +
                        "    gl_Position = u_projTrans * a_position;\n" +
                        "}",
                "#ifdef GL_ES\n" +
                        "precision mediump float;\n" +
                        "#endif\n" +
                        "\n" +
                        "varying vec4 v_color;\n" +
                        "varying vec2 v_texCoords;\n" +
                        "varying vec2 v_bottomLeft;\n" +
                        "varying vec2 v_topRight;\n" +
                        "uniform sampler2D u_texture;\n" +
                        "\n" +
                        "void main() {\n" +
                        "    vec4 color = texture2D(u_texture, v_texCoords) * v_color;\n" +
                        "    color *= step(v_bottomLeft.x, v_texCoords.x) * step(v_texCoords.x, v_topRight.x);\n" +
                        "    color *= step(v_texCoords.y, 1.0 - v_bottomLeft.y) * step(1.0 - v_topRight.y, v_texCoords.y);\n" +
                        "    gl_FragColor = color;\n" +
                        "}"
        );

        if (!CLIPPING.isCompiled()) {
            Gdx.app.error(Shaders.class.toString(), CLIPPING.getLog());
        }
    }

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
                "#ifdef GL_ES\n" +
                        "precision mediump float;\n" +
                        "#endif\n" +
                        "\n" +
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
            Gdx.app.error(Shaders.class.toString(), HDR.getLog());
        }
    }

    private Shaders() {
    }
}