package com.xiyuan.acm.factory;

import com.xiyuan.acm.model.ListNode;

/**
 * Created by xiyuan_fengyu on 2016/9/1.
 */
public class ListNodeFactory {

    public static ListNode build(String str) {
        String[] split = str.split("->");
        Integer[] arr = new Integer[split.length];
        for (int i = 0, len = split.length; i < len; i++) {
            try {
                arr[i] = Integer.parseInt(split[i].trim());
            }
            catch (Exception e) {
                arr[i] = null;
            }
        }

        ListNode tempHead = new ListNode(0);
        ListNode cur = tempHead;
        for (Integer i: arr) {
            if (i == null) {
                break;
            }
            else {
                cur.next = new ListNode(i);
                cur = cur.next;
            }
        }
        return tempHead.next;
    }

}
