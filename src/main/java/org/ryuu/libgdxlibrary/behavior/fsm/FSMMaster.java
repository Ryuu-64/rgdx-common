package org.ryuu.libgdxlibrary.behavior.fsm;

import org.ryuu.functional.Action;
import org.ryuu.functional.IAction;
import org.ryuu.libgdxlibrary.behavior.core.Master;

import java.util.List;

public class FSMMaster extends Master<FSMNode> {
    private final FSMNode anyState;
    private FSMNode currentState;
    private final IAction updateCache = this::update;
    private boolean isStop;

    public void start() {
        isStop = false;
    }

    public void stop() {
        isStop = true;
    }

    public FSMNode getCurrentState() {
        return currentState;
    }

    public boolean isState(FSMNode state) {
        return currentState == state;
    }

    public FSMMaster(List<FSMNode> nodes, FSMNode primeNode, FSMNode anyState, Action update) {
        super(nodes, primeNode, update);
        this.anyState = anyState;
        currentState = primeNode;
        this.update = update;
        update.add(updateCache);
    }

    private void update() {
        if (isStop) {
            return;
        }

        if (anyState != null) {
            if (transition(anyState)) return;
        }

        if (currentState != null) {
            if (transition(currentState)) return;
            currentState.update.invoke();
        }
    }

    private boolean transition(FSMNode state) {
        for (FSMConnection transition : state.connections) {
            if (transition.condition.invoke(this, transition.sourceNode, transition.targetNode)) {
                toState(transition.targetNode);
                return true;
            }
        }
        return false;
    }

    public void toState(FSMNode to) {
        FSMNode from = currentState;
        currentState.exit.invoke(from, to);
        currentState = to;
        currentState.enter.invoke(from, to);
    }

    @Override
    public void dispose() {
        update.remove(updateCache);
    }
}