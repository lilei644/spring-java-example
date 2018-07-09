
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.CountDownLatch;

/**
 * zookeeper实现分布式锁，可以开两个进程运行，模拟分布式
 */
public class ZookeeperLock implements Runnable {

    private static int count = 0;
    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    private static CuratorFramework curatorFramework;
    private static InterProcessMutex interProcessMutex;

    @Override
    public void run() {
        try {
            countDownLatch.await();

            // 休眠随机数，制造进程之间同的抢锁顺序
            Thread.sleep((long) (Math.random() * 5000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        try {
            // 加锁
            interProcessMutex.acquire();

            // 处理逻辑
            count++;
            System.out.println(count);
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

                // 释放锁资源
                interProcessMutex.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    public static void main(String[] args) throws Exception {

        // 初始化zookeeper连接
        curatorFramework = CuratorFrameworkFactory.builder().
                connectString("127.0.0.1:2181").
                retryPolicy(new ExponentialBackoffRetry(1000, 3)).
                build();
        curatorFramework.start();

        // 初始化锁和节点
        interProcessMutex = new InterProcessMutex(curatorFramework, "/MyNode");


        // 新建线程
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i]=  new Thread(new ZookeeperLock());
            threads[i].start();
        }

        Thread.sleep(1000);
        System.out.println(">>>>开始线程调用");
        countDownLatch.countDown();


        // 关闭连接
        for (int i = 0; i < 10; i++) {
            threads[i].join();
        }
        System.out.println(">>>>结束线程调用");
        curatorFramework.close();

    }


}
