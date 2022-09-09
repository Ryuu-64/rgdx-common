package org.ryuu.libgdxlibrary.behavior.fsm;

import org.ryuu.functional.IFunc3Arg;
import org.ryuu.libgdxlibrary.behavior.core.Connection;

public class FSMConnection extends Connection<FSMNode> {
    public final IFunc3Arg<FSMMaster, FSMNode, FSMNode, Boolean> condition;

    public FSMConnection(FSMNode sourceNode, FSMNode targetNode, IFunc3Arg<FSMMaster, FSMNode, FSMNode, Boolean> condition) {
        super(sourceNode, targetNode);
        this.condition = condition;
    }
}