package com.xiyuan.acm.model;

/**
 * Created by xiyuan_fengyu on 2016/10/21.
 */
public class SegmentTreeNode extends BasicTreeNode<SegmentTreeNode.Segment> {

    public int start;

    public int end;

    public int max;

    public SegmentTreeNode left;

    public SegmentTreeNode right;

    public SegmentTreeNode(int start, int end) {
        this(start, end, 0);
    }

    public SegmentTreeNode(int start, int end, int max) {
        super(null);
        val = new Segment();
        this.start = start;
        this.end = end;
        this.max = max;
    }

    @Override
    public BasicTreeNode<Segment> left() {
        return left;
    }

    @Override
    public BasicTreeNode<Segment> right() {
        return right;
    }

    class Segment {
        @Override
        public String toString() {
            return "(" + start + ", " + end + ", max = " + max + ")";
        }
    }

}
