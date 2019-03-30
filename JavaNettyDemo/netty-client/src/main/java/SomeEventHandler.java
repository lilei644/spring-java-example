import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 接收到数据的处理逻辑方法
 */
public class SomeEventHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        String ip = ctx.channel().remoteAddress().toString();
        System.out.println(">>>>激活连接，IP：" + ip);

        ctx.writeAndFlush("hello world!\n");

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println(">>>接收到消息：" + msg);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        System.out.println(">>>>获取到异常：" + cause.getMessage());

        // 关闭连接
        ctx.channel().close();

    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        System.out.println(">>>>连接断开：" + ctx.channel().remoteAddress().toString());

    }
}
