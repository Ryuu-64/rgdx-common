package org.ryuu.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import lombok.Getter;
import lombok.Setter;
import org.ryuu.functional.IAction;
import org.ryuu.functional.IAction2Arg;
import org.ryuu.gdx.scenes.scene2d.GetAfterVisibleChange;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.ryuu.gdx.scenes.scene2d.ui.utils.Cells.*;

public class CellVisibleTable extends Table implements Disposable {
    private final Map<Actor, Cell<Actor>> parameterCellMap = new HashMap<>();
    @Getter
    @Setter
    private IAction setPosition;
    private final ArrayList<GetAfterVisibleChange> afterVisibleChangeSenders = new ArrayList<>();
    private final IAction2Arg<Actor, Boolean> afterCellActorVisibleChange = (actor, visible) -> {
        Cell<Actor> cell = getCell(actor);
        if (visible) {
            show(cell);
        } else {
            hide(cell);
        }
    };

    private void show(Cell<Actor> cell) {
        Cell<Actor> parameterCell = parameterCellMap.get(cell.getActor());
        if (parameterCell == null) {
            return;
        }
        copyValueOf(cell, parameterCell);
        invalidate();
        pack();
        setPosition.invoke();
    }

    private void hide(Cell<Actor> cell) {
        Cell<Actor> parameterCell = new Cell<>();
        copyValueOf(parameterCell, cell);
        parameterCellMap.put(cell.getActor(), parameterCell);
        //noinspection unchecked
        copyValueOf(cell, Cell.defaults());
        invalidate();
        pack();
        setPosition.invoke();
    }

    @Override

    public <T extends Actor> Cell<T> add(T actor) {
        if (actor instanceof GetAfterVisibleChange) {
            GetAfterVisibleChange afterVisibleChange = (GetAfterVisibleChange) actor;
            afterVisibleChange.getAfterVisibleChange().add(afterCellActorVisibleChange);
            afterVisibleChangeSenders.add(afterVisibleChange);
        }
        return super.add(actor);
    }

    @Override
    public void dispose() {
        for (GetAfterVisibleChange afterVisibleChange : afterVisibleChangeSenders) {
            afterVisibleChange.getAfterVisibleChange().remove(afterCellActorVisibleChange);
        }
    }
}