package org.ryuu.libgdxlibrary.behavior.core;

public abstract class Connection<TNode extends Node> {
    public final TNode sourceNode;
    public final TNode targetNode;

    protected Connection(TNode sourceNode, TNode targetNode) {
        this.sourceNode = sourceNode;
        this.targetNode = targetNode;
    }
}