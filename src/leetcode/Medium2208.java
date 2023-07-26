package leetcode;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class Medium2208 {
    public static void main(String[] args) {
        System.out.println(halveArray(new int[]{5, 19, 8, 1}));
    }

    public static int halveArray(int[] nums) {
        //优先队列（相当于堆）
        Queue<Double> queue = new PriorityQueue<>((a, b) -> a.compareTo(b));
        //匿名实现类的匿名对象
        Queue<Double> queue1 = new PriorityQueue<>(new Comparator<Double>() {
            @Override
            public int compare(Double o1, Double o2) {
                return o1.compareTo(o2);
            }
        });
        int sum = 0;
        //将数据一一加入队列
        for (int num : nums) {
            queue.offer((double) num);
            sum += num;
        }
        int res = 0;
        double sum2 = 0;
        while (sum >= sum2) {
            //返回并移除队列的头元素
            double x = queue.poll();
            sum2 += x / 2;
            //
            queue.offer(x / 2);
            res++;
        }
        return res;
    }
}
