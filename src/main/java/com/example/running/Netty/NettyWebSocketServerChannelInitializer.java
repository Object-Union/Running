package com.example.running.Netty;

import com.example.running.Common.NettyCommon;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;


public class NettyWebSocketServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) {
        // 因为基于http协议，所以我们要使用http的编码和解码器
        channel.pipeline().addLast(new HttpServerCodec());
        // 以块方式写入
        channel.pipeline().addLast(new ChunkedWriteHandler());
        // http数据在传输过程中是分段的，这个Handler可以将多个段聚合起来
        channel.pipeline().addLast(new HttpObjectAggregator(8192));
        // 对于WebSocket，它的数据是以 帧Frame 的形式传递的
        // 浏览器发送请求时: ws://localhost:8090/chat, chat表示请求的uri, 与下面的路径对应
        // WebSocketServerProtocolHandler 核心功能是将 http 协议升级为 ws 协议，保持长连接
        channel.pipeline().addLast(new NettyWebSocketServerHandler());  // 添加自定义处理类
        channel.pipeline().addLast(new WebSocketServerProtocolHandler(NettyCommon.NETTY_WEBSOCKET_URL, null, true, 65536 * 10));
    }
}
