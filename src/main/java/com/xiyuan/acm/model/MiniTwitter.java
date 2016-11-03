package com.xiyuan.acm.model;

import com.xiyuan.util.XYLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * http://www.lintcode.com/zh-cn/problem/mini-twitter/
 * Created by xiyuan_fengyu on 2016/11/3.
 */
public class MiniTwitter {

    private HashMap<Integer, List<Tweet>> posts = new HashMap<>();
    private HashMap<Integer, List<Integer>> follows = new HashMap<>();
    private static final int maxFetchNum = 10;

    public MiniTwitter() {
    }

    public Tweet postTweet(int user_id, String tweet_text) {
        Tweet tweet = Tweet.create(user_id, tweet_text);
        List<Tweet> postList;
        if (posts.containsKey(user_id)) {
            postList = posts.get(user_id);
        }
        else {
            postList = new ArrayList<>();
            posts.put(user_id, postList);
        }
        postList.add(tweet);
        return tweet;
    }

    /**
     * @param user_id
     * @return a list of 10 new posts recently and sort by timeline
     */
    public List<Tweet>  getTimeline(int user_id) {
        List<Tweet> result = new ArrayList<>();
        if (posts.containsKey(user_id)) {
            List<Tweet> list = posts.get(user_id);
            for (int i = list.size() - 1, end = i - maxFetchNum; i > end && i > -1; i--) {
                result.add(list.get(i));
            }
        }
        return result;
    }

    /**
     * @param user_id
     * @return a list of 10 new feeds recently and sort by timeline
     */
    public List<Tweet> getNewsFeed(int user_id) {
        List<Tweet> result = new ArrayList<>();
        NewsFeedHeap heap = new NewsFeedHeap(user_id);
        int index = maxFetchNum;
        while (index-- > 0 && heap.hasNext()) {
            result.add(heap.fetch());
        }
        return result;
    }

    /**
     * follow from user_id follows to_user_id
     * @param from_user_id
     * @param to_user_id
     */
    public void follow(int from_user_id, int to_user_id) {
        if (follows.containsKey(from_user_id)) {
            List<Integer> followList = follows.get(from_user_id);
            if (!followList.contains(to_user_id)) {
                followList.add(to_user_id);
            }
        }
        else {
            List<Integer> followList = new ArrayList<>();
            followList.add(to_user_id);
            follows.put(from_user_id, followList);
        }
    }

    /**
     * unfollow from user_id follows to_user_id
     * @param from_user_id
     * @param to_user_id
     */
    public void unfollow(int from_user_id, int to_user_id) {
        if (follows.containsKey(from_user_id)) {
            follows.get(from_user_id).remove((Integer) to_user_id);
        }
    }

    private class NewsFeedHeap {

        private List<NewsFeedNode> datas = new ArrayList<>();

        public NewsFeedHeap(int userId) {
            if (posts.containsKey(userId)) {
                List<Tweet> selfPosts = posts.get(userId);
                if (selfPosts.size() > 0) {
                    insert(new NewsFeedNode(selfPosts));
                }
            }

            if (follows.containsKey(userId)) {
                List<Integer> followList = follows.get(userId);
                int followSize = followList.size();
                if (followSize > 0) {
                    for (Integer aFollowList : followList) {
                        int followId = aFollowList;
                        if (posts.containsKey(followId)) {
                            List<Tweet> otherPosts = posts.get(followId);
                            if (otherPosts.size() > 0) {
                                insert(new NewsFeedNode(otherPosts));
                            }
                        }
                    }
                }
            }
        }

        private void insert(NewsFeedNode node) {
            datas.add(node);
            shiftUp(datas.size() - 1);
        }

        public Tweet fetch() {
            int size = datas.size();
            NewsFeedNode top = datas.get(0);
            Tweet tweet = top.fetch();
            if (top.index == -1) {
                if (size == 1) {
                    datas.remove(0);
                }
                else {
                    datas.set(0, datas.get(size - 1));
                    datas.remove(size - 1);
                    shiftDown(0);
                }
            }
            else {
                shiftDown(0);
            }
            return tweet;
        }

        private void shiftUp(int index) {
            while (index > 0) {
                int parentI = (index - 1) / 2;
                NewsFeedNode parent = datas.get(parentI);
                NewsFeedNode cur = datas.get(index);
                if (compare(parent, cur) >= 0) {
                    break;
                }
                swap(parentI, index);
                index = parentI;
            }
        }

        private void shiftDown(int index) {
            int size = datas.size();
            int half = size / 2;
            while (index < half) {
                int leftI = index * 2 + 1;
                int rightI = leftI + 1;
                if (rightI < size && compare(datas.get(leftI), datas.get(rightI)) < 0) {
                    leftI = rightI;
                }
                if (compare(datas.get(index), datas.get(leftI)) >= 0) {
                    break;
                }
                swap(index, leftI);
                index = leftI;
            }
        }

        private int compare(NewsFeedNode o1, NewsFeedNode o2) {
            return o1.topId - o2.topId;
        }

        private void swap(int index1, int index2) {
            NewsFeedNode temp = datas.get(index1);
            datas.set(index1, datas.get(index2));
            datas.set(index2, temp);
        }

        private boolean hasNext() {
            return !datas.isEmpty();
        }

        private class NewsFeedNode {
            private List<Tweet> postList;
            private int index;
            private int topId;
            public NewsFeedNode(List<Tweet> postList) {
                this.postList = postList;
                this.index = postList.size() - 1;
                refreshTopId();
            }

            public Tweet fetch() {
                Tweet tweet = postList.get(index);
                index--;
                refreshTopId();
                return tweet;
            }

            private void refreshTopId() {
                if (index > -1 && index < postList.size()) {
                    topId = postList.get(index).id;
                }
                else {
                    topId = -1;
                }
            }
        }
    }

    public static void test() {
        MiniTwitter twitter = new MiniTwitter();
        XYLog.d(twitter.postTweet(1, "LintCode is Good!!!"));
        XYLog.d(twitter.getNewsFeed(1));
        XYLog.d(twitter.getTimeline(1));
        twitter.follow(2, 1);
        XYLog.d(twitter.getNewsFeed(2));
        twitter.unfollow(2, 1);
        XYLog.d(twitter.getNewsFeed(2));
    }

}
