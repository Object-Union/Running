package com.example.running.Netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;


@Log4j2
@Component(value = "NettyWebSocketServer")
public class NettyWebSocketServer {

    public void start(InetSocketAddress address) {
        // 配置服务端的NIO线程组
        // 1. 两个都是线程组
        // 2. BossGroup只处理连接请求
        // 3. WorkGroup处理客户端业务
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 创建服务器端启动对象，配置参数
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(bossGroup, workerGroup)  // 绑定线程池
                    .channel(NioServerSocketChannel.class) // 使用NioServerSocketChannel作为服务器的Channel实现
//                    .handler(new LoggingHandler(LogLevel.INFO)) // 加入日志
                    .localAddress(address)
                    .childHandler(new NettyWebSocketServerChannelInitializer())// 编码解码
                    .option(ChannelOption.SO_BACKLOG, 128)  // 服务端接受连接的队列长度，如果队列已满，客户端连接将被拒绝
                    .childOption(ChannelOption.SO_KEEPALIVE, true);  // 保持长连接，2小时无数据激活心跳机制

            // 绑定端口，开始接收进来的连接
            ChannelFuture future = bootstrap.bind(address).sync();
            log.info("nettyWebSocket服务器开始监听端口: " + address.getPort());
            //关闭channel和块，直到它被关闭
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
