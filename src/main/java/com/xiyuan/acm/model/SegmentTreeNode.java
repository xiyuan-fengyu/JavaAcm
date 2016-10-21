package com.xiyuan.acm.model;

/**
 * Created by xiyuan_fengyu on 2016/10/21.
 */
public class SegmentTreeNode extends BasicTreeNode<SegmentTreeNode.Segment> {

    public int start;

    public int end;

    public SegmentTreeNode left;

    public SegmentTreeNode right;

    public SegmentTreeNode(int start, int end) {
        super(null);
        val = new Segment(start, end);
        this.start = start;
        this.end = end;
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
        private int start;
        private int end;
        public Segment(int start, int end) {
            this.start = start;
            this.end = end;
        }
        @Override
        public String toString() {
            return "(" + start + ", " + end + ")";
        }
    }

}
