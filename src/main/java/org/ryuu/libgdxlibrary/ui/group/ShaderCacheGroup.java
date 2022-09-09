package org.ryuu.libgdxlibrary.ui.group;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Group;
import lombok.Getter;
import lombok.Setter;
import org.ryuu.libgdxlibrary.shader.IShaderProgram;

public class ShaderCacheGroup extends Group implements IShaderProgram {
    @Getter
    @Setter
    private ShaderProgram shaderProgram;

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (shaderProgram == null) {
            super.draw(batch, parentAlpha);
        } else {
            ShaderProgram batchShader = batch.getShader();
            batch.setShader(shaderProgram);
            super.draw(batch, parentAlpha);
            batch.setShader(batchShader);
        }
    }

    @Override
    public String toString() {
        return super.toString() + "\n" +
                "ShaderCacheGroup{" +
                "shader=" + shaderProgram +
                '}';
    }
}