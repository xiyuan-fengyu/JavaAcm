package com.xiyuan.acm2.model;

import java.util.*;

/**
 * Created by xiyuan_fengyu on 2017/4/27.
 */
public class StrsUndirectedGraph {

    private HashMap<String, Set<String>> strs = new HashMap<>();

    public StrsUndirectedGraph(Iterable<String> aStrs) {
        for (String str : aStrs) {
            addStr(str);
        }
    }

    public void addStr(String str) {
        if (!strs.containsKey(str)) {
            Set<String> similarStr = new HashSet<>();
            for (Map.Entry<String, Set<String>> entry : strs.entrySet()) {
                if (isSimilar(entry.getKey(), str)) {
                    entry.getValue().add(str);
                    similarStr.add(entry.getKey());
                }
            }
            strs.put(str, similarStr);
        }
    }

    public int minDistance(String str1, String str2) {
        if (str1.equals(str2)) return 0;
        if (!strs.containsKey(str1) || !strs.containsKey(str2)) return -1;
        HashSet<String> visited = new HashSet<>();
        Queue<String> queue = new ArrayDeque<>();
        for (String s : strs.get(str1)) {
            queue.offer(s);
            visited.add(s);
        }
        return minDistance(queue, visited, str2);
    }

    private int minDistance(Queue<String> queue, HashSet<String> visited, String str2) {
        int distance = 1;
        while (true) {
            if (queue.isEmpty()) {
                return -1;
            }
            else {
                int size = queue.size();
                for (int i = 0; i < size; i++) {
                    String str = queue.poll();
                    if (str.equals(str2)) {
                        return distance;
                    }
                    else {
                        for (String s : strs.get(str)) {
                            if (!visited.contains(s)) {
                                visited.add(s);
                                queue.offer(s);
                            }
                        }
                    }
                }
                distance++;
            }
        }
    }

    public List<List<String>> minDisPaths(String str1, String str2) {
        List<List<String>> result = new ArrayList<>();
        if (str1.equals(str2)) {
            result.add(Collections.singletonList(str1));
            return result;
        }
        if (!strs.containsKey(str1) || !strs.containsKey(str2)) return result;

        HashSet<String> visited = new HashSet<>();
        Queue<Map.Entry<String, List<ArrayList<String>>>> queue = new ArrayDeque<>();
        Map<String, List<ArrayList<String>>> nexts = new HashMap<>();
        for (String s : strs.get(str1)) {
            ArrayList<String> path = new ArrayList<>();
            path.add(str1);
            List<ArrayList<String>> paths = new ArrayList<>();
            paths.add(path);
            queue.offer(new AbstractMap.SimpleEntry<>(s, paths));
            visited.add(s);
        }
        while (true) {
            if (queue.isEmpty()) {
                return result;
            }
            else {
                int size = queue.size();
                boolean shouldBreak = false;
                nexts.clear();
                for (int i = 0; i < size; i++) {
                    Map.Entry<String, List<ArrayList<String>>> entry = queue.poll();
                    String str = entry.getKey();
                    if (str.equals(str2)) {
                        shouldBreak = true;
                        List<ArrayList<String>> paths = entry.getValue();
                        for (ArrayList<String> path : paths) {
                            path.add(str);
                            result.add(path);
                        }
                    }
                    else if (!shouldBreak) {
                        for (String s : strs.get(str)) {
                            if (!s.equals(str1)) {
                                if (!visited.contains(s)) {
                                    List<ArrayList<String>> paths = new ArrayList<>();
                                    for (ArrayList<String> list : entry.getValue()) {
                                        ArrayList<String> clone = (ArrayList<String>) list.clone();
                                        clone.add(str);
                                        paths.add(clone);
                                    }
                                    if (nexts.containsKey(s)) {
                                        nexts.get(s).addAll(paths);
                                    }
                                    else {
                                        nexts.put(s, paths);
                                    }
                                }
                            }
                        }
                    }
                }
                if (shouldBreak) break;

                for (Map.Entry<String, List<ArrayList<String>>> entry : nexts.entrySet()) {
                    visited.add(entry.getKey());
                    queue.add(entry);
                }
            }

        }
        return result;
    }

    private boolean isSimilar(String str1, String str2) {
        int count = 0;
        for (int i = 0, len = str1.length(); i < len; i++) {
            if (str1.charAt(i) != str2.charAt(i)) {
                if (++count > 1) {
                    return false;
                }
            }
        }
        return true;
    }

}
