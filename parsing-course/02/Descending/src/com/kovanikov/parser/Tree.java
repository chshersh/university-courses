package com.kovanikov.parser;

import org.StructureGraphic.v1.DSTreeNode;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

/**
 * Tree node class which is parse tree.
 * Uses StructureGraphic to visualize.
 */
public class Tree implements DSTreeNode {
    private String value;
    private List<Tree> children;

    public Tree(String value) {
        this.value = value;
    }

    public Tree(String value, Tree ... children) {
        this.value = value;
        this.children = Arrays.asList(children);
    }

    @Override
    public DSTreeNode[] DSgetChildren() {
        if (children == null) {
            return new DSTreeNode[0];
        } else {
            return children.toArray(new DSTreeNode[children.size()]);
        }
    }

    @Override
    public Object DSgetValue() {
        return value;
    }

    @Override
    public Color DSgetColor() {
        if (DSgetValue().equals("eps")) {
            return Color.BLUE;
        }
        return DSgetChildren().length == 0 ? Color.BLUE : Color.BLACK;
    }

    @Override
    public String toString() {
        String ans = value.toLowerCase();
        if (children != null) {
            ans = "(" + ans;
            for (Tree child : children) {
                ans += " " + child.toString();
            }
            ans += ")";
        }
        return ans;
    }
}