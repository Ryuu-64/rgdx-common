package org.ryuu.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.SnapshotArray;
import lombok.Getter;
import lombok.Setter;

import static com.badlogic.gdx.scenes.scene2d.Touchable.childrenOnly;
import static com.badlogic.gdx.utils.Align.*;

public class VisibleVerticalGroup extends WidgetGroup {
    private float prefWidth;
    private float prefHeight;
    private float lastPrefWidth;
    private boolean isSizeInvalid = true;
    private FloatArray columnSizes;
    @Getter
    private int align = top;
    private int columnAlign;
    @Getter
    private boolean isReverse;
    @Setter
    private boolean isRound = true;
    @Getter
    private boolean isWrap;
    @Getter
    private boolean isExpand;
    @Getter
    private float space;
    @Getter
    private float wrapSpace;
    @Getter
    private float fill;
    @Getter
    private float padTop;
    @Getter
    private float padLeft;
    @Getter
    private float padBottom;
    @Getter
    private float padRight;

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
            computeSize();
        }

        if (isWrap) {
            layoutWrapped();
            return;
        }

        boolean round = this.isRound;
        int align = this.align;
        float space = this.space;
        float padLeft = this.padLeft;
        float fill = this.fill;
        float columnWidth = (isExpand ? getWidth() : prefWidth) - padLeft - padRight;
        float y = prefHeight - padTop + space;

        if ((align & top) != 0)
            y += getHeight() - prefHeight;
        else if ((align & bottom) == 0) // center
            y += (getHeight() - prefHeight) / 2;

        float startX;
        if ((align & left) != 0)
            startX = padLeft;
        else if ((align & right) != 0)
            startX = getWidth() - padRight - columnWidth;
        else
            startX = padLeft + (getWidth() - padLeft - padRight - columnWidth) / 2;

        align = columnAlign;

        SnapshotArray<Actor> children = getIsVisibleChildren();
        int i = 0;
        int n = children.size;
        int incr = 1;
        if (isReverse) {
            i = n - 1;
            n = -1;
            incr = -1;
        }
        for (; i != n; i += incr) {
            Actor child = children.get(i);

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

            if (fill > 0) width = columnWidth * fill;

            if (layout != null) {
                width = Math.max(width, layout.getMinWidth());
                float maxWidth = layout.getMaxWidth();
                if (maxWidth > 0 && width > maxWidth) width = maxWidth;
            }

            float x = startX;
            if ((align & right) != 0)
                x += columnWidth - width;
            else if ((align & left) == 0) // center
                x += (columnWidth - width) / 2;

            y -= height + space;
            if (round)
                child.setBounds(Math.round(x), Math.round(y), Math.round(width), Math.round(height));
            else
                child.setBounds(x, y, width, height);

            if (layout != null) layout.validate();
        }
    }

    @Override
    public float getPrefWidth() {
        if (isSizeInvalid) {
            computeSize();
        }
        return prefWidth;
    }

    @Override
    public float getPrefHeight() {
        if (isWrap) {
            return 0;
        }
        if (isSizeInvalid) {
            computeSize();
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

    private SnapshotArray<Actor> getIsVisibleChildren() {
        SnapshotArray<Actor> targetChildren = new SnapshotArray<>();
        for (Actor actor : getChildren()) {
            if (actor.isVisible()) {
                targetChildren.add(actor);
            }
        }
        return targetChildren;
    }

    private void computeSize() {
        isSizeInvalid = false;
        SnapshotArray<Actor> children = getIsVisibleChildren();
        int n = children.size;
        prefWidth = 0;
        if (isWrap) {
            prefHeight = 0;
            if (columnSizes == null)
                columnSizes = new FloatArray();
            else
                columnSizes.clear();
            FloatArray columnSizes = this.columnSizes;
            float space = this.space;
            float wrapSpace = this.wrapSpace;
            float pad = padTop + padBottom;
            float groupHeight = getHeight() - pad;
            float x = 0;
            float y = 0;
            float columnWidth = 0;
            int i = 0;
            int increment = 1;
            if (isReverse) {
                i = n - 1;
                n = -1;
                increment = -1;
            }
            for (; i != n; i += increment) {
                Actor child = children.get(i);

                float width;
                float height;
                if (child instanceof Layout) {
                    Layout layout = (Layout) child;
                    width = layout.getPrefWidth();
                    height = layout.getPrefHeight();
                    if (height > groupHeight) height = Math.max(groupHeight, layout.getMinHeight());
                } else {
                    width = child.getWidth();
                    height = child.getHeight();
                }

                float incrementY = height + (y > 0 ? space : 0);
                if (y + incrementY > groupHeight && y > 0) {
                    columnSizes.add(y);
                    columnSizes.add(columnWidth);
                    prefHeight = Math.max(prefHeight, y + pad);
                    if (x > 0) x += wrapSpace;
                    x += columnWidth;
                    columnWidth = 0;
                    y = 0;
                    incrementY = height;
                }
                y += incrementY;
                columnWidth = Math.max(columnWidth, width);
            }
            columnSizes.add(y);
            columnSizes.add(columnWidth);
            prefHeight = Math.max(prefHeight, y + pad);
            if (x > 0) x += wrapSpace;
            prefWidth = Math.max(prefWidth, x + columnWidth);
        } else {
            prefHeight = padTop + padBottom + space * (n - 1);
            for (int i = 0; i < n; i++) {
                Actor child = children.get(i);
                if (child instanceof Layout) {
                    Layout layout = (Layout) child;
                    prefWidth = Math.max(prefWidth, layout.getPrefWidth());
                    prefHeight += layout.getPrefHeight();
                } else {
                    prefWidth = Math.max(prefWidth, child.getWidth());
                    prefHeight += child.getHeight();
                }
            }
        }
        prefWidth += padLeft + padRight;
        if (isRound) {
            prefWidth = Math.round(prefWidth);
            prefHeight = Math.round(prefHeight);
        }
    }

    private void layoutWrapped() {
        float prefWidth = getPrefWidth();
        if (prefWidth != lastPrefWidth) {
            lastPrefWidth = prefWidth;
            invalidateHierarchy();
        }

        int align = this.align;
        boolean round = this.isRound;
        float space = this.space;
        float padLeft = this.padLeft;
        float fill = this.fill;
        float wrapSpace = this.wrapSpace;
        float maxHeight = prefHeight - padTop - padBottom;
        float columnX = padLeft;
        float groupHeight = getHeight();
        float yStart = prefHeight - padTop + space;
        float y = 0;
        float columnWidth = 0;

        if ((align & right) != 0)
            columnX += getWidth() - prefWidth;
        else if ((align & left) == 0) // center
            columnX += (getWidth() - prefWidth) / 2;

        if ((align & top) != 0)
            yStart += groupHeight - prefHeight;
        else if ((align & bottom) == 0) // center
            yStart += (groupHeight - prefHeight) / 2;

        groupHeight -= padTop;
        align = columnAlign;

        FloatArray columnSizes = this.columnSizes;
        SnapshotArray<Actor> children = getIsVisibleChildren();
        int i = 0;
        int n = children.size;
        int incr = 1;
        if (isReverse) {
            i = n - 1;
            n = -1;
            incr = -1;
        }
        for (int r = 0; i != n; i += incr) {
            Actor child = children.get(i);

            float width;
            float height;
            Layout layout = null;
            if (child instanceof Layout) {
                layout = (Layout) child;
                width = layout.getPrefWidth();
                height = layout.getPrefHeight();
                if (height > groupHeight) height = Math.max(groupHeight, layout.getMinHeight());
            } else {
                width = child.getWidth();
                height = child.getHeight();
            }

            if (y - height - space < padBottom || r == 0) {
                r = Math.min(r, columnSizes.size - 2); // in case an actor changed size without invalidating this layout.
                y = yStart;
                if ((align & bottom) != 0)
                    y -= maxHeight - columnSizes.get(r);
                else if ((align & top) == 0) // center
                    y -= (maxHeight - columnSizes.get(r)) / 2;
                if (r > 0) {
                    columnX += wrapSpace;
                    columnX += columnWidth;
                }
                columnWidth = columnSizes.get(r + 1);
                r += 2;
            }

            if (fill > 0) {
                width = columnWidth * fill;
            }

            if (layout != null) {
                width = Math.max(width, layout.getMinWidth());
                float maxWidth = layout.getMaxWidth();
                if (maxWidth > 0 && width > maxWidth) width = maxWidth;
            }

            float x = columnX;
            if ((align & right) != 0)
                x += columnWidth - width;
            else if ((align & left) == 0) // center
                x += (columnWidth - width) / 2;

            y -= height + space;
            if (round)
                child.setBounds(Math.round(x), Math.round(y), Math.round(width), Math.round(height));
            else
                child.setBounds(x, y, width, height);

            if (layout != null) layout.validate();
        }
    }

    public int getColumns() {
        return isWrap ? columnSizes.size >> 1 : 1;
    }

    public VisibleVerticalGroup reverse() {
        this.isReverse = true;
        return this;
    }

    public VisibleVerticalGroup reverse(boolean reverse) {
        this.isReverse = reverse;
        return this;
    }

    public VisibleVerticalGroup space(float space) {
        this.space = space;
        return this;
    }

    public VisibleVerticalGroup wrapSpace(float wrapSpace) {
        this.wrapSpace = wrapSpace;
        return this;
    }

    public VisibleVerticalGroup pad(float pad) {
        padTop = pad;
        padLeft = pad;
        padBottom = pad;
        padRight = pad;
        return this;
    }

    public VisibleVerticalGroup pad(float top, float left, float bottom, float right) {
        padTop = top;
        padLeft = left;
        padBottom = bottom;
        padRight = right;
        return this;
    }

    public VisibleVerticalGroup padTop(float padTop) {
        this.padTop = padTop;
        return this;
    }

    public VisibleVerticalGroup padLeft(float padLeft) {
        this.padLeft = padLeft;
        return this;
    }

    public VisibleVerticalGroup padBottom(float padBottom) {
        this.padBottom = padBottom;
        return this;
    }

    public VisibleVerticalGroup padRight(float padRight) {
        this.padRight = padRight;
        return this;
    }

    public VisibleVerticalGroup align(int align) {
        this.align = align;
        return this;
    }

    public VisibleVerticalGroup center() {
        align = center;
        return this;
    }

    public VisibleVerticalGroup top() {
        align |= top;
        align &= ~bottom;
        return this;
    }

    public VisibleVerticalGroup left() {
        align |= left;
        align &= ~right;
        return this;
    }

    public VisibleVerticalGroup bottom() {
        align |= bottom;
        align &= ~top;
        return this;
    }

    public VisibleVerticalGroup right() {
        align |= right;
        align &= ~left;
        return this;
    }

    public VisibleVerticalGroup fill() {
        fill = 1f;
        return this;
    }

    public VisibleVerticalGroup fill(float fill) {
        this.fill = fill;
        return this;
    }

    public VisibleVerticalGroup expand() {
        isExpand = true;
        return this;
    }

    public VisibleVerticalGroup expand(boolean expand) {
        this.isExpand = expand;
        return this;
    }

    public VisibleVerticalGroup grow() {
        isExpand = true;
        fill = 1;
        return this;
    }

    public VisibleVerticalGroup wrap() {
        isWrap = true;
        return this;
    }

    public VisibleVerticalGroup wrap(boolean wrap) {
        this.isWrap = wrap;
        return this;
    }

    public VisibleVerticalGroup columnAlign(int columnAlign) {
        this.columnAlign = columnAlign;
        return this;
    }

    public VisibleVerticalGroup columnCenter() {
        columnAlign = center;
        return this;
    }

    public VisibleVerticalGroup columnTop() {
        columnAlign |= top;
        columnAlign &= ~bottom;
        return this;
    }

    public VisibleVerticalGroup columnLeft() {
        columnAlign |= left;
        columnAlign &= ~right;
        return this;
    }

    public VisibleVerticalGroup columnBottom() {
        columnAlign |= bottom;
        columnAlign &= ~top;
        return this;
    }

    public VisibleVerticalGroup columnRight() {
        columnAlign |= right;
        columnAlign &= ~left;
        return this;
    }
}