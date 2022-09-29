package org.ryuu.gdx.scenes.scene2d;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Group;
import lombok.Getter;
import lombok.Setter;
import org.ryuu.gdx.graphics.glutils.Material;

public class MaterialGroup extends Group {
    @Getter
    @Setter
    private Material material;

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (material == null) {
            super.draw(batch, parentAlpha);
        } else {
            ShaderProgram batchShader = batch.getShader();
            batch.setShader(material.getShaderProgram());
            material.applyShaderParameters();
            super.draw(batch, parentAlpha);
            batch.setShader(batchShader);
        }
    }
}