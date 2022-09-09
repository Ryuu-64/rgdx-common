package org.ryuu.libgdxlibrary.behavior.core;

import java.util.ArrayList;
import java.util.List;

public abstract class Node {
    public final int id;
    public final String name;
    public final List<String> tags;

    protected Node() {
        id = hashCode();
        name = String.valueOf(hashCode());
        tags = new ArrayList<>();
    }

    protected Node(String name) {
        id = hashCode();
        this.name = name;
        tags = new ArrayList<>();
    }

    protected Node(String name, List<String> tags) {
        id = hashCode();
        this.name = name;
        this.tags = tags;
    }
}