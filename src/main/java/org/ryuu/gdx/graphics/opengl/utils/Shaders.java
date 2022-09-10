package org.ryuu.gdx.graphics.opengl.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

// TODO add hdr light color shader
public class Shaders {
    private Shaders() {
    }

    public static final ShaderProgram GRAY_SCALE;
    public static final ShaderProgram COLOR_MULTIPLIER;

    static {
        GRAY_SCALE = new ShaderProgram("attribute vec4 a_position;\n" + "attribute vec4 a_color;\n" + "attribute vec2 a_texCoord0;\n" + "\n" + "uniform mat4 u_projTrans;\n" + "\n" + "varying vec4 v_color;\n" + "varying vec2 v_texCoords;\n" + "\n" + "void main() {\n" + "    v_color = a_color;\n" + "    v_texCoords = a_texCoord0;\n" + "    gl_Position = u_projTrans * a_position;\n" + "}", "#ifdef GL_ES\n" + "    precision mediump float;\n" + "#endif\n" + "\n" + "varying vec4 v_color;\n" + "varying vec2 v_texCoords;\n" + "uniform sampler2D u_texture;\n" + "\n" + "void main() {\n" + "  vec4 c = v_color * texture2D(u_texture, v_texCoords);\n" + "  float grey = (c.r + c.g + c.b) / 3.0;\n" + "  gl_FragColor = vec4(grey, grey, grey, c.a);\n" + "}");

        if (!GRAY_SCALE.isCompiled()) {
            Gdx.app.log("Shader", "Grayscale shader compile error!");
            Gdx.app.log("Shader", GRAY_SCALE.getLog());
        }

        COLOR_MULTIPLIER = new ShaderProgram("attribute vec4 a_position;\n" + "attribute vec4 a_color;\n" + "attribute vec2 a_texCoord0;\n" + "attribute vec4 a_multiplierColor;\n" + "\n" + "uniform mat4 u_projTrans;\n" + "\n" + "varying vec4 v_color;\n" + "varying vec2 v_texCoords;\n" + "varying vec4 v_multiplierColor;\n" + "\n" + "void main() {\n" + "    v_color = a_color;\n" + "    v_texCoords = a_texCoord0;\n" + "    v_multiplierColor = a_multiplierColor;\n" + "    gl_Position = u_projTrans * a_position;\n" + "}", "#ifdef GL_ES\n" + "    precision mediump float;\n" + "#endif\n" + "\n" + "varying vec4 v_color;\n" + "varying vec2 v_texCoords;\n" + "varying vec4 v_multiplierColor;\n" + "uniform sampler2D u_texture;\n" + "\n" + "void main() {\n" + "  gl_FragColor = v_multiplierColor * v_color * texture2D(u_texture, v_texCoords);\n" + "}");

        if (!COLOR_MULTIPLIER.isCompiled()) {
            Gdx.app.log("Shader", "Grayscale shader compile error!");
            Gdx.app.log("Shader", COLOR_MULTIPLIER.getLog());
        }
    }
}