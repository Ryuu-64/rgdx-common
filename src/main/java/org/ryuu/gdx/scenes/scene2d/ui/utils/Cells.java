package org.ryuu.gdx.scenes.scene2d.ui.utils;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;

public class Cells {
    /**
     * TODO
     *  cell.merge method is package-private
     *  cell.getExpandX and cell.getExpandY method may cause NullPointerException
     *  try add cell.uniform(parameter.getUniformX(), parameter.getUniformY());
     */
    public static void copyValueOf(Cell<Actor> self, Cell<Actor> beCopy) {
        self.minWidth(beCopy.getMinWidth());
        self.minHeight(beCopy.getMinHeight());
        self.prefWidth(beCopy.getPrefWidth());
        self.prefHeight(beCopy.getPrefHeight());
        self.maxWidth(beCopy.getMaxWidth());
        self.maxHeight(beCopy.getMaxHeight());
        self.spaceTop(beCopy.getSpaceTop());
        self.spaceLeft(beCopy.getSpaceLeft());
        self.spaceBottom(beCopy.getSpaceBottom());
        self.spaceRight(beCopy.getSpaceRight());
        self.padTop(beCopy.getPadTop());
        self.padLeft(beCopy.getPadLeft());
        self.padBottom(beCopy.getPadBottom());
        self.padRight(beCopy.getPadRight());
        self.fill(beCopy.getFillX(), beCopy.getFillY());
        self.align(beCopy.getAlign());
        self.expand(beCopy.getExpandX(), beCopy.getExpandY());
        self.colspan(beCopy.getColspan());
    }
}
