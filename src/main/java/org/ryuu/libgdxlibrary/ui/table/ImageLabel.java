package org.ryuu.libgdxlibrary.ui.table;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import org.ryuu.functional.Action;
import org.ryuu.libgdxlibrary.util.Actors;

import java.util.HashMap;
import java.util.Stack;

public class ImageLabel extends Table {
    private final HashMap<String, TextureRegion> map = new HashMap<>(1 << 4);
    public final float height;
    private final float offset;
    public final Action onContentChange = new Action();

    public ImageLabel(float height, float offset) {
        super();
        this.offset = offset;
        this.height = height;
        setTransform(true);
    }

    public void addNumberMap(String path, int from, int to) {
        for (int i = from; i <= to; i++) {
            map.put(String.valueOf(i), Assets.newTextureRegion(path + i + ".png"));
        }
    }

    public ImageLabel set(int num) {
        clean();
        return append(num);
    }

    public ImageLabel append(int num) {
        Stack<Integer> integers = new Stack<>();
        if (num != 0) {
            while (num != 0) {
                integers.push(num % 10);
                num /= 10;
            }
        } else {
            integers.push(0);
        }
        while (!integers.isEmpty()) {
            Image image = new Image(map.get(String.valueOf(integers.pop())));
            Vector2 imageSize = Actors.getIsotropicScaleSize(image, height, false);
            add(image).size(imageSize.x, imageSize.y).padRight(offset);
        }
        pack();
        onContentChange.invoke();
        return this;
    }

    public ImageLabel set(String string) {
        return clean().append(string);
    }

    public ImageLabel append(String string) {
        for (Character key : string.toCharArray()) {
            Image image = new Image(map.get(String.valueOf(key)));
            Vector2 imageSize = Actors.getIsotropicScaleSize(image, height, false);
            add(image).size(imageSize.x, imageSize.y).padRight(offset);
        }

        pack();
        onContentChange.invoke();
        return this;
    }

    public ImageLabel clean() {
        clear();
        pack();
        return this;
    }

    public void addTextMap(String key, TextureRegion value) {
        map.put(key, value);
    }

    @Override
    public void setColor(Color color) {
        super.setColor(color);
        for (Actor child : getChildren()) {
            child.setColor(color);
        }
    }
}