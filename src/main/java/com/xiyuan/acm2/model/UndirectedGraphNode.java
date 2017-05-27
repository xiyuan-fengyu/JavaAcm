package com.xiyuan.acm2.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by xiyuan_fengyu on 2017/5/27.
 */
public class UndirectedGraphNode {

    public int label;

    public ArrayList<UndirectedGraphNode> neighbors;

    public UndirectedGraphNode(int aLabel) {
        label = aLabel;
        neighbors = new ArrayList<>();
    }

    public void addNeighbours(UndirectedGraphNode ... nodes) {
        if (nodes != null) {
            for (UndirectedGraphNode node : nodes) {
                this.neighbors.add(node);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        HashMap<UndirectedGraphNode, Boolean> cache = new HashMap<>();
        toStr(this, cache, builder);
        return builder.toString();
    }

    private static void toStr(UndirectedGraphNode node, HashMap<UndirectedGraphNode, Boolean> cache, StringBuilder builder) {
        if (!cache.containsKey(node)) {
            builder.append("\n").append(node.label).append(" -> [");
            for (int i = 0, size = node.neighbors.size(); i < size; i++) {
                builder.append(node.neighbors.get(i).label);
                if (i + 1 < size) {
                    builder.append(", ");
                }
            }
            builder.append("]");
            cache.put(node, true);
            for (UndirectedGraphNode neighbor : node.neighbors) {
                toStr(neighbor, cache, builder);
            }
        }
    }
}
