package org.ryuu.gdx.scenes.scene2d.ui.utils;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class Tables {
    private Tables() {
    }

    public static void remove(Table table, Actor actor) {
        table.getCells().removeValue(table.getCell(actor), true);
        table.pack();
    }
}