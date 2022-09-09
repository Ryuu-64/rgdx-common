package org.ryuu.libgdxlibrary.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import org.ryuu.libgdxlibrary.manager.AssetManager;


public final class Assets {
    private Assets() {
    }

    public static Texture getTexture(String path) {
        return AssetManager.get(path, Texture.class);
    }

    public static TextureRegion newTextureRegion(String path) {
        return new TextureRegion(AssetManager.get(path, Texture.class));
    }

    public static TextureRegionDrawable newTextureRegionDrawable(String path) {
        return new TextureRegionDrawable(AssetManager.get(path, Texture.class));
    }

    public static TextureRegionDrawable newTextureRegionDrawable(Texture texture) {
        return new TextureRegionDrawable(texture);
    }

    public static Image newImage(String path) {
        return new Image(AssetManager.get(path, Texture.class));
    }

    public static Image newImage(Drawable drawable) {
        return new Image(drawable);
    }
}
