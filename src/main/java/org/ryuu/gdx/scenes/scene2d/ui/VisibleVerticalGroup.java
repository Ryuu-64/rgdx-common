package org.ryuu.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.utils.SnapshotArray;
import lombok.Getter;
import lombok.Setter;

import static com.badlogic.gdx.scenes.scene2d.Touchable.childrenOnly;
import static com.badlogic.gdx.utils.Align.*;

public class VisibleVerticalGroup extends WidgetGroup {
    private float prefWidth;
    private float prefHeight;
    @Getter
    @Setter
    private int align = top;
    @Getter
    @Setter
    private float spacing;
    @Getter
    @Setter
    private float padTop;
    @Getter
    @Setter
    private float padLeft;
    @Getter
    @Setter
    private float padBottom;
    @Getter
    @Setter
    private float padRight;
    private boolean isSizeInvalid = true;
    private final SnapshotArray<Actor> visibleChildren = new SnapshotArray<>();

    public VisibleVerticalGroup() {
        setTouchable(childrenOnly);
    }

    @Override
    public void invalidate() {
        super.invalidate();
        isSizeInvalid = true;
    }

    @Override
    public void layout() {
        if (isSizeInvalid) {
            computePrefSize();
        }

        float columnWidth = prefWidth - padLeft - padRight;
        float y = prefHeight - padTop + spacing;

        if ((align & top) != 0) {
            y += getHeight() - prefHeight;
        } else if ((align & bottom) == 0) {
            y += (getHeight() - prefHeight) / 2;
        }

        float startX;
        if ((align & left) != 0) {
            startX = padLeft;
        } else if ((align & right) != 0) {
            startX = getWidth() - padRight - columnWidth;
        } else {
            startX = padLeft + (getWidth() - padLeft - padRight - columnWidth) / 2;
        }

        updateVisibleChildren();
        for (int i = 0; i != visibleChildren.size; i++) {
            Actor child = visibleChildren.get(i);

            float width;
            float height;
            Layout layout = null;
            if (child instanceof Layout) {
                layout = (Layout) child;
                width = layout.getPrefWidth();
                height = layout.getPrefHeight();
            } else {
                width = child.getWidth();
                height = child.getHeight();
            }

            if (layout != null) {
                width = Math.max(width, layout.getMinWidth());
                float maxWidth = layout.getMaxWidth();
                if (maxWidth > 0 && width > maxWidth) {
                    width = maxWidth;
                }
            }

            float x = startX;
            if ((align & right) != 0) {
                x += columnWidth - width;
            } else if ((align & left) == 0) {
                x += (columnWidth - width) / 2;
            }

            y -= height + spacing;

            child.setBounds(x, y, width, height);

            if (layout != null) {
                layout.validate();
            }
        }
    }

    @Override
    public float getPrefWidth() {
        if (isSizeInvalid) {
            computePrefSize();
        }
        return prefWidth;
    }

    @Override
    public float getPrefHeight() {
        if (isSizeInvalid) {
            computePrefSize();
        }
        return prefHeight;
    }

    @Override
    protected void drawDebugBounds(ShapeRenderer shapes) {
        super.drawDebugBounds(shapes);
        if (!getDebug()) {
            return;
        }
        shapes.set(ShapeRenderer.ShapeType.Line);
        if (getStage() != null) {
            shapes.setColor(getStage().getDebugColor());
        }
        shapes.rect(
                getX() + padLeft, getY() + padBottom,
                getOriginX(), getOriginY(),
                getWidth() - padLeft - padRight, getHeight() - padBottom - padTop,
                getScaleX(), getScaleY(),
                getRotation()
        );
    }

    private void updateVisibleChildren() {
        visibleChildren.clear();
        for (Actor actor : getChildren()) {
            if (actor.isVisible()) {
                visibleChildren.add(actor);
            }
        }
    }

    private void computePrefSize() {
        isSizeInvalid = false;
        updateVisibleChildren();
        int childrenCount = visibleChildren.size;
        prefHeight = padTop + padBottom + spacing * (childrenCount - 1);
        for (int i = 0; i < childrenCount; i++) {
            Actor child = visibleChildren.get(i);
            if (child instanceof Layout) {
                Layout layout = (Layout) child;
                prefWidth = Math.max(prefWidth, layout.getPrefWidth());
                prefHeight += layout.getPrefHeight();
            } else {
                prefWidth = Math.max(prefWidth, child.getWidth());
                prefHeight += child.getHeight();
            }
        }
        prefWidth += padLeft + padRight;
    }

    public VisibleVerticalGroup setPadding(float pad) {
        padTop = pad;
        padLeft = pad;
        padBottom = pad;
        padRight = pad;
        return this;
    }

    public VisibleVerticalGroup setPadding(float top, float left, float bottom, float right) {
        padTop = top;
        padLeft = left;
        padBottom = bottom;
        padRight = right;
        return this;
    }
}