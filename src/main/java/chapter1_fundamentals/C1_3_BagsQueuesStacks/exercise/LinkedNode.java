package chapter1_fundamentals.C1_3_BagsQueuesStacks.exercise;

/**
 * Created by SylvanasSun on 2017/5/29.
 */
public class LinkedNode<E> {

    E value;
    LinkedNode<E> next;

    public LinkedNode() {
    }

    public LinkedNode(E value, LinkedNode<E> next) {
        this.value = value;
        this.next = next;
    }

}
