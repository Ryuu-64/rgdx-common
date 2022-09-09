package org.ryuu.libgdxlibrary.asset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.TiledMap;
import org.ryuu.libgdxlibrary.manager.AssetManager;

import java.util.Stack;

public class AssetLoader {
    private static final Stack<FileHandle> directoryStack = new Stack<>();

    private AssetLoader() {
    }

    public static void load(String assetPath) {
        FileHandle handle = Gdx.files.internal(assetPath);
        if (!handle.exists()) {
            throw new RuntimeException("asset path not exist: " + assetPath);
        }

        loadFileOrPushDirectoryIntoStack(handle);
        while (!directoryStack.isEmpty()) {
            handle = directoryStack.pop();
            FileHandle[] handles = handle.list();
            for (FileHandle fileHandle : handles) {
                loadFileOrPushDirectoryIntoStack(fileHandle);
            }
        }
    }

    private static void loadFileOrPushDirectoryIntoStack(FileHandle fileHandle) {
        if (!fileHandle.isDirectory()) {
            String path = fileHandle.path();
            Class<?> type = getTypeBySuffix(path);
            if (type == null) {
                throw new RuntimeException("there is no type for path : " + path);
            }
            AssetManager.load(path, type);
        } else {
            directoryStack.push(fileHandle);
        }
    }

    private static Class<?> getTypeBySuffix(String fileName) {
        String[] split = fileName.split("\\.");
        String suffix = split[split.length - 1];

        switch (suffix) {
            case "png":
            case "jpg":
                return Texture.class;
            case "fnt":
                return BitmapFont.class;
            case "tmx":
                return TiledMap.class;
            case "mp3":
            case "wav":
            case "ogg":
                return Sound.class;
//                return Music.class;
        }

        return null;
    }
}