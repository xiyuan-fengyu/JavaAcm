package com.xiyuan.acm.model;

import java.util.HashMap;
import java.util.Map;

/**
 * http://www.lintcode.com/zh-cn/problem/add-and-search-word/
 * Created by xiyuan_fengyu on 2016/11/2.
 */
public class WordDictionary {

    private CharNode root = new CharNode();

    private static final Character tail = '\0';

    // Adds a word into the data structure.
    public void addWord(String word) {
        if (word == null) {
            root.addChild(new CharNode());
        }
        else {
            char[] chars = word.toCharArray();
            CharNode cur = root;
            for (char c : chars) {
                CharNode child = cur.getChild(c);
                if (child == null) {
                    child = new CharNode(c);
                    cur.addChild(child);
                }
                cur = child;
            }
            cur.addChild(new CharNode(tail));
        }
    }

    // Returns if the word is in the data structure. A word could
    // contain the dot character '.' to represent any one letter.
    public boolean search(String word) {
        if (word == null) {
            return root.getChild(null) != null;
        }
        else if (word.isEmpty()) {
            return root.getChild(tail) != null;
        }
        return searchNode(word, 0, root);
    }

    private boolean searchNode(String word, int index, CharNode node) {
        if (node == null) {
            return false;
        }
        else if (index < word.length()) {
            char c = word.charAt(index);
            if (c != '.') {
                return searchNode(word, index + 1, node.getChild(c));
            }
            else {
                for (Map.Entry<Character, CharNode> keyVal: node.children.entrySet()) {
                    Character key = keyVal.getKey();
                    if (key != tail && searchNode(word, index + 1, keyVal.getValue())) {
                        return true;
                    }
                }
            }
            return false;
        }
        else {
            return node.getChild(tail) != null;
        }
    }


    private class CharNode {

        public final Character val;

        private final HashMap<Character, CharNode> children = new HashMap<>();

        private CharNode() {
            val = null;
        }

        private CharNode(Character val) {
            this.val = val;
        }

        boolean addChild(CharNode child) {
            if (children.containsKey(child.val)) {
                return false;
            }
            else {
                children.put(child.val, child);
                return true;
            }
        }

        private CharNode getChild(Character c) {
            return children.get(c);
        }
    }

}
