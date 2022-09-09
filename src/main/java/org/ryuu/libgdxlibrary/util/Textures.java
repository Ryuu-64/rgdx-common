package org.ryuu.libgdxlibrary.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import org.ryuu.libgdxlibrary.manager.AssetManager;

import static com.badlogic.gdx.graphics.Texture.TextureFilter.Linear;

public class Textures {
    private Textures() {
    }

    public static Vector2 getIsotropicScaleSize(Texture texture, float targetSize, boolean isWidth) {
        return Util.getIsotropicScaleSize(texture.getWidth(), texture.getHeight(), targetSize, isWidth);
    }

    public static void setDefaultFilter() {
        Array<Texture> textures = new Array<>();
        AssetManager.getAll(Texture.class, textures);
        for (Texture texture : textures) {
            texture.setFilter(Linear, Linear);
        }
    }
}