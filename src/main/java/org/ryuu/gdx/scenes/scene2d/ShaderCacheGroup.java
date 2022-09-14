package org.ryuu.gdx.scenes.scene2d;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Group;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.ryuu.gdx.graphics.opengl.IShaderProgram;

@ToString
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
}