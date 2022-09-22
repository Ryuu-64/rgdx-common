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
import lombok.ToString;
import org.ryuu.gdx.graphics.glutils.ShaderProgramProperty;

@ToString
public class ShaderImage extends Image implements ShaderProgramProperty {
    @Getter
    @Setter
    private ShaderProgram shaderProgram;

    public ShaderImage() {
        super();
    }

    public ShaderImage(NinePatch patch) {
        super(patch);
    }

    public ShaderImage(TextureRegion region) {
        super(region);
    }

    public ShaderImage(Texture texture) {
        super(texture);
    }

    public ShaderImage(Skin skin, String drawableName) {
        super(skin, drawableName);
    }

    public ShaderImage(Drawable drawable) {
        super(drawable);
    }

    public ShaderImage(Drawable drawable, Scaling scaling) {
        super(drawable, scaling);
    }

    public ShaderImage(Drawable drawable, Scaling scaling, int align) {
        super(drawable, scaling, align);
    }

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