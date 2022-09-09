package org.ryuu.libgdxlibrary.shader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class Shaders {
    public static ShaderProgram GRAY_SCALE;
    public static ShaderProgram LIGHT_GRAY;

    static {
        GRAY_SCALE = new ShaderProgram("attribute vec4 a_position;\n" +
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
                "}", "#ifdef GL_ES\n" +
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

        LIGHT_GRAY = new ShaderProgram("attribute vec4 a_position;\n" +
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
                "}", "#ifdef GL_ES\n" +
                "    precision mediump float;\n" +
                "#endif\n" +
                "\n" +
                "varying vec4 v_color;\n" +
                "varying vec2 v_texCoords;\n" +
                "uniform sampler2D u_texture;\n" +
                "\n" +
                "void main() {\n" +
                "  vec4 c = v_color * texture2D(u_texture, v_texCoords);\n" +
                "  gl_FragColor = vec4(c.r *.75 , c.g *.75 , c.b *.75, c.a);\n" +
                "}"
        );

        if (!LIGHT_GRAY.isCompiled()) {
            Gdx.app.log("Shader", "Grayscale shader compile error!");
            Gdx.app.log("Shader", GRAY_SCALE.getLog());
        }
    }
}