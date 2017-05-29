package chapter1_fundamentals.C1_3_BagsQueuesStacks.exercise;

/**
 * 编写一个方法delete(),接受一个int参数k,删除链表的第k个元素(如果它存在的话).
 *
 * Created by SylvanasSun on 2017/5/29.
 */
public class Ex_1_3_20 {

    public void delete(int k, LinkedNode node) {
        int i = 0;
        LinkedNode prev = null;
        while (node != null) {
            if (i == k) {
                if (prev != null) {
                    prev.next = node.next;
                } else {
                    node = node.next;
                }
            }
            prev = node;
            node = node.next;
            i++;
        }
    }

}
