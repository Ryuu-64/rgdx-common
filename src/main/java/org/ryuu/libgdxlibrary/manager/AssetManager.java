package org.ryuu.libgdxlibrary.manager;

import com.badlogic.gdx.utils.Array;

@SuppressWarnings("GDXJavaStaticResource")
public class AssetManager {
    private static final com.badlogic.gdx.assets.AssetManager instance = new com.badlogic.gdx.assets.AssetManager();

    private AssetManager() {
    }

    public static synchronized <T> T get(String fileName, Class<T> type) {
        return instance.get(fileName, type);
    }

    public static synchronized <T> void load(String fileName, Class<T> type) {
        instance.load(fileName, type);
    }

    @SuppressWarnings("UnusedReturnValue")
    public static synchronized <T> Array<T> getAll(Class<T> type, Array<T> out) {
        return instance.getAll(type, out);
    }

    public static synchronized float getProgress() {
        return instance.getProgress();
    }

    public static void finishLoading() {
        instance.finishLoading();
    }
}
