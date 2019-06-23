package com.csonezp.linkedlist;

/**
 * Created by csonezp on 2019/6/21.
 */
public class Link_list_circle {
    public boolean hascycle(ListNode head) {
        //快慢指针
        ListNode slow, fast;
        slow = fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (fast == slow) {
                return true;
            }
        }
        return false;
    }

}
