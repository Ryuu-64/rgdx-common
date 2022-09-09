package org.ryuu.libgdxlibrary;

import com.badlogic.gdx.Gdx;

import java.util.HashSet;
import java.util.Set;

public class Logger {
    private Logger() {
    }

    private static final Set<String> ignoreTags = new HashSet<>();

    public static void debug(String tag, String message) {
        if (ignoreTags.contains(tag)) {
            return;
        }

        Gdx.app.debug(tag, message);
    }

    public static void ignoreTag(String ignoreTag) {
        ignoreTags.add(ignoreTag);
    }

    public static void removeIgnoreTag(String ignoreTag) {
        ignoreTags.remove(ignoreTag);
    }
}