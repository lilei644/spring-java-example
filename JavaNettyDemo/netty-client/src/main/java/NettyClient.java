import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * 客户端方法
 */
public class NettyClient {

    private static final String IP = "127.0.0.1";
    private static final int PORT = 9527;

    // 定义编解码器
    private static final StringDecoder DECODER = new StringDecoder(CharsetUtil.UTF_8);
    private static final StringEncoder ENCODER = new StringEncoder(CharsetUtil.UTF_8);



    public static void start() {
        start(IP, PORT);
    }

    public static void start(String ip, int port) {

        EventLoopGroup loopGroup = new NioEventLoopGroup();

        // 定义客户端
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(loopGroup)
                .remoteAddress(ip, port)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {

                    protected void initChannel(SocketChannel socketChannel) throws Exception {

                        ChannelPipeline pipeline = socketChannel.pipeline();
                        // 加上此行之后发送消息需要换行才能收到
                        pipeline.addLast(new DelimiterBasedFrameDecoder(1024*1024, Delimiters.lineDelimiter()));
                        // the encoder and decoder are static as these are sharable
                        pipeline.addLast(DECODER);
                        pipeline.addLast(ENCODER);
                        pipeline.addLast(new SomeEventHandler());
                    }
                });


        // 启动阻塞
        try {
            ChannelFuture channelFuture = bootstrap.connect().sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}

