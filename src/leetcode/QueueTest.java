package leetcode;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

public class QueueTest {
    public static void main(String[] args) {
        //测试优先队列
        /*
         * 优先队列是有序的，顺序为传入比较器的顺序
         * 从小到大的顺序是(a,b)->a.compareTo(b)
         * 从大到小的顺序是(a,b)->b.compareTo(a)
         * */
        Queue<Integer> queue = new PriorityQueue<>((a, b) -> b.compareTo(a));
        //插入数据到比较队列中
        int[] arr = new int[]{58, 42, 68, 46, 102, 35, 186, 475, 110};
        //将数据依次导入优先队列中
        for (int num : arr) {
            queue.offer(num);
        }
        System.out.println(Arrays.toString(arr));
        System.out.print("[");
        //遍历数据
        while (!queue.isEmpty()) {
            //取出并返回队列首部元素
            System.out.print(queue.poll() + ", ");
        }
        System.out.println("]");
    }
}
