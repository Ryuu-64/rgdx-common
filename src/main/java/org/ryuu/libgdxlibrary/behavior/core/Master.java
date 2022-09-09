package org.ryuu.libgdxlibrary.behavior.core;

import com.badlogic.gdx.utils.Disposable;
import org.ryuu.functional.Action;

import java.util.List;

public abstract class Master<TNode extends Node> implements Disposable {
    protected final List<TNode> allNode;
    protected final TNode primeNode;
    protected Action update;

    protected Master(List<TNode> allNode, TNode primeNode, Action update) {
        this.allNode = allNode;
        this.primeNode = primeNode;
        this.update = update;
    }

}
