import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * server 对象
 */
public class NettyServer {

    private static final int PORT = 9527;

    // 定义boss轮询组和工作线程组
    private static final NioEventLoopGroup workGroup = new NioEventLoopGroup(2);
    private static final NioEventLoopGroup bossGroup = new NioEventLoopGroup(2);


    // 定义编解码器
    private static final StringDecoder DECODER = new StringDecoder(CharsetUtil.UTF_8);
    private static final StringEncoder ENCODER = new StringEncoder(CharsetUtil.UTF_8);



    public static void start() {
        start(PORT);
    }

    public static void start(int port) {

        // 初始化server
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workGroup)
                .localAddress(port)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .childHandler(new ChannelInitializer<SocketChannel>() {

                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();

                        // 加上此行之后发送消息需要换行才能收到
                        pipeline.addLast(new DelimiterBasedFrameDecoder(1024*1024, Delimiters.lineDelimiter()));
                        // the encoder and decoder are static as these are sharable
                        pipeline.addLast(DECODER);
                        pipeline.addLast(ENCODER);


                        pipeline.addLast(new IdleStateHandler(0, 0, 10, TimeUnit.SECONDS));
                        pipeline.addLast(new ChannelInboundHandlerAdapter(){

                            @Override
                            public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                                if (evt instanceof IdleStateEvent) {
                                    System.out.println(">>>>>10秒未进行消息通讯，自动断开连接");
                                    ctx.close();
                                } else {
                                    super.userEventTriggered(ctx, evt);
                                }
                            }
                        });


                        // 自定义业务逻辑处理
                        pipeline.addLast(new SomeEventHandler());


                    }

                });


        try {
            ChannelFuture channelFuture = bootstrap.bind().sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}
