package com.example.demo.handler;

import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 由于nio是同步非阻塞的，则需要将业务逻辑放置自定义的线程池中处理
 */
public class WorkThread implements Runnable {

    private static ExecutorService executorService = Executors.newFixedThreadPool(10);

    private ChannelHandlerContext ctx;
    private String message;

    private static Logger logger = Logger.getLogger(SomethingServerHandler.class.getName());

    public WorkThread(ChannelHandlerContext ctx, String message) {
        this.ctx = ctx;
        this.message = message;
    }

    public static void startWork(ChannelHandlerContext ctx, String message) {
        executorService.execute(new WorkThread(ctx, message));
    }

    @Override
    public void run() {
        logger.info(message);
        try {
            // 模拟耗时操作
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ctx.channel().writeAndFlush(message);
    }


}
