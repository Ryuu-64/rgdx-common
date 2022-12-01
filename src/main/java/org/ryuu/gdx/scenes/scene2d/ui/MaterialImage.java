package org.ryuu.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Scaling;
import lombok.Getter;
import lombok.Setter;
import org.ryuu.gdx.graphics.glutils.Material;
import org.ryuu.gdx.graphics.glutils.MaterialProperty;

public class MaterialImage extends Image implements MaterialProperty {
    @Getter
    @Setter
    private Material material;

    public MaterialImage() {
        super();
    }

    public MaterialImage(NinePatch patch) {
        super(patch);
    }

    public MaterialImage(TextureRegion region) {
        super(region);
    }

    public MaterialImage(Texture texture) {
        super(texture);
    }

    public MaterialImage(Skin skin, String drawableName) {
        super(skin, drawableName);
    }

    public MaterialImage(Drawable drawable) {
        super(drawable);
    }

    public MaterialImage(Drawable drawable, Scaling scaling) {
        super(drawable, scaling);
    }

    public MaterialImage(Drawable drawable, Scaling scaling, int align) {
        super(drawable, scaling, align);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (material == null || material.getShaderProgram() == null) {
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