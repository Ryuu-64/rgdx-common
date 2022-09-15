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
import org.ryuu.gdx.graphics.opengl.IShaderProgram;

@ToString
public class ShaderCacheImage extends Image implements IShaderProgram {
    @Getter
    @Setter
    private ShaderProgram shaderProgram;

    public ShaderCacheImage() {
        super();
    }

    public ShaderCacheImage(NinePatch patch) {
        super(patch);
    }

    public ShaderCacheImage(TextureRegion region) {
        super(region);
    }

    public ShaderCacheImage(Texture texture) {
        super(texture);
    }

    public ShaderCacheImage(Skin skin, String drawableName) {
        super(skin, drawableName);
    }

    public ShaderCacheImage(Drawable drawable) {
        super(drawable);
    }

    public ShaderCacheImage(Drawable drawable, Scaling scaling) {
        super(drawable, scaling);
    }

    public ShaderCacheImage(Drawable drawable, Scaling scaling, int align) {
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