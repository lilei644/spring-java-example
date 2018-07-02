package com.example.demo.handler;

import com.example.demo.config.ChannelRepository;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * event handler to process receiving messages
 */
@Component
@Qualifier("somethingServerHandler")
@ChannelHandler.Sharable
public class SomethingServerHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    private ChannelRepository channelRepository;

    private String ip;

    private static Logger logger = Logger.getLogger(SomethingServerHandler.class.getName());

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Assert.notNull(this.channelRepository, "[Assertion failed] - ChannelRepository is required; it must not be null");

        ctx.fireChannelActive();
        logger.info(ctx.channel().remoteAddress());
        String channelKey = ctx.channel().remoteAddress().toString();
        channelRepository.put(channelKey, ctx.channel());

        ip = channelKey;
        ctx.writeAndFlush("Your channel key is " + channelKey + "\n\r");

        logger.info("Binded Channel Count is " + this.channelRepository.size());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

    //    channelRepository.get(ip).writeAndFlush("This is a Test Connect\n");


        String stringMessage = (String) msg;

    //    ctx.channel().writeAndFlush(stringMessage); // \r\n
        WorkThread.startWork(ctx, stringMessage);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error(cause.getMessage(), cause);
        //ctx.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx){
        Assert.notNull(this.channelRepository, "[Assertion failed] - ChannelRepository is required; it must not be null");
        Assert.notNull(ctx);

        String channelKey = ctx.channel().remoteAddress().toString();
        this.channelRepository.remove(channelKey);

        logger.info("Binded Channel Count is " + this.channelRepository.size());
    }

    public void setChannelRepository(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }
}
