package org.ryuu.libgdxlibrary.behavior.fsm;

import org.ryuu.libgdxlibrary.behavior.core.Node;
import org.ryuu.functional.Action;
import org.ryuu.functional.Action2Arg;
import org.ryuu.functional.IAction;
import org.ryuu.functional.IAction2Arg;

import java.util.ArrayList;
import java.util.List;

public class FSMNode extends Node {
    public final Action2Arg<FSMNode, FSMNode> enter = new Action2Arg<>();
    public final Action update = new Action();
    public final Action2Arg<FSMNode, FSMNode> exit = new Action2Arg<>();
    public final List<FSMConnection> connections = new ArrayList<>();

    public FSMNode(IAction2Arg<FSMNode, FSMNode> enter, IAction update, IAction2Arg<FSMNode, FSMNode> exit) {
        super();
        this.enter.add(enter);
        this.update.add(update);
        this.exit.add(exit);
    }

    public FSMNode(String name, IAction2Arg<FSMNode, FSMNode> enter, IAction update, IAction2Arg<FSMNode, FSMNode> exit) {
        super(name);
        this.enter.add(enter);
        this.update.add(update);
        this.exit.add(exit);
    }

    public FSMNode(String name, List<String> tags, IAction2Arg<FSMNode, FSMNode> enter, IAction update, IAction2Arg<FSMNode, FSMNode> exit) {
        super(name, tags);
        this.enter.add(enter);
        this.update.add(update);
        this.exit.add(exit);
    }

    public void addConnection(FSMConnection connection) {
        this.connections.add(connection);
    }

    @Override
    public String toString() {
        return "FSMNode{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tags=" + tags +
                '}';
    }
}