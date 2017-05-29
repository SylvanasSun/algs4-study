package chapter1_fundamentals.C1_3_BagsQueuesStacks.exercise;

/**
 * 编写一个方法find(),接受一条链表和一个字符串key作为参数.
 * 如果链表中的某个节点的item域的值为key,则方法返回true,否则返回false.
 *
 * Created by SylvanasSun on 2017/5/29.
 */
public class Ex_1_3_21 {

    public boolean find(LinkedNode<String> node, String key) {
        while (node != null) {
            if (node.value.equals(key))
                return true;
            node = node.next;
        }
        return false;
    }

}
