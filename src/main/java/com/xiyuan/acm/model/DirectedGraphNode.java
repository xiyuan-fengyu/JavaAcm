package com.xiyuan.acm.model;

import java.util.ArrayList;

/**
 * Created by xiyuan_fengyu on 2016/9/24.
 */
public class DirectedGraphNode {
    public int label;
    public ArrayList<DirectedGraphNode> neighbors;
    public DirectedGraphNode(int x) {
        label = x;
        neighbors = new ArrayList<DirectedGraphNode>();
    }

    public void addNeighbors(DirectedGraphNode ... nodes) {
        if (nodes != null) {
            for (DirectedGraphNode node: nodes) {
                neighbors.add(node);
            }
        }
    }

    @Override
    public String toString() {
        return "" + label;
    }
};
