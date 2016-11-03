package com.xiyuan.acm.model;

/**
 * Created by xiyuan_fengyu on 2016/11/3.
 */
public class Tweet {

    public int id;

    public int user_id;

    public String text;

    private static int curNewId = 0;

    public static Tweet create(int user_id, String tweet_text) {
        Tweet tweet = new Tweet();
        tweet.user_id = user_id;
        tweet.text = tweet_text;
        synchronized (Tweet.class) {
            tweet.id = ++curNewId;
        }
        return tweet;
    }

}
