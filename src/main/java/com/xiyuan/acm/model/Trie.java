package com.xiyuan.acm.model;

import java.util.HashMap;

/**
 * http://www.lintcode.com/zh-cn/problem/implement-trie/
 * Created by xiyuan_fengyu on 2016/11/2.
 */
public class Trie {

    private TrieNode root;

    private static final Character tail = '\0';

    public Trie() {
        root = new TrieNode();
    }

    // Inserts a word into the trie.
    public void insert(String word) {
        if (word == null) {
            root.addChild(new TrieNode());
        }
        else {
            char[] chars = word.toCharArray();
            TrieNode cur = root;
            for (char c : chars) {
                TrieNode child = cur.getChild(c);
                if (child == null) {
                    child = new TrieNode(c);
                    cur.addChild(child);
                }
                cur = child;
            }
            cur.addChild(new TrieNode(tail));
        }
    }

    // Returns if the word is in the trie.
    public boolean search(String word) {
        if (word == null) {
            return root.getChild(null) != null;
        }
        TrieNode cur = root;
        for (int i = 0, len = word.length(); i < len; i++) {
            cur = cur.getChild(word.charAt(i));
            if (cur == null) {
                return false;
            }
        }
        return cur.getChild(tail) != null;
    }

    // Returns if there is any word in the trie
    // that starts with the given prefix.
    public boolean startsWith(String prefix) {
        if (prefix == null) {
            return root.getChild(null) != null;
        }
        TrieNode cur = root;
        for (int i = 0, len = prefix.length(); i < len; i++) {
            cur = cur.getChild(prefix.charAt(i));
            if (cur == null) {
                return false;
            }
        }
        return true;
    }


}

class TrieNode {

    public final Character val;

    private final HashMap<Character, TrieNode> children = new HashMap<>();

    TrieNode() {
        val = null;
    }

    TrieNode(Character val) {
        this.val = val;
    }

    boolean addChild(TrieNode child) {
        if (children.containsKey(child.val)) {
            return false;
        }
        else {
            children.put(child.val, child);
            return true;
        }
    }

    TrieNode getChild(Character c) {
        return children.get(c);
    }

}
