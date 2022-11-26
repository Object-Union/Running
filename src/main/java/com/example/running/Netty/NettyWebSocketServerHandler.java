package com.example.running.Netty;

import com.example.running.Bean.Chat;
import com.example.running.Service.ChatService;
import com.example.running.Util.JsonUtils;
import com.example.running.Util.SpringTool;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.ObjectUtils;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;

@Log4j2
public class NettyWebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    /**
     * 定义一个Channel组，管理所有的Channel
     * GlobalEventExecutor.INSTANCE:全局事件执行器
     */
    private static final ChannelGroup CHANNEL_GROUP = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private static final ConcurrentHashMap<Integer, ChannelId> USER_CHANNEL = new ConcurrentHashMap<>();

    ChatService chatService = (ChatService) SpringTool.getBean("ChatService");

    /**
     * 客户端连接时触发
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        InetSocketAddress socket = (InetSocketAddress) ctx.channel().remoteAddress();
        // 获取连接通道唯一标识
        ChannelId ID = ctx.channel().id();
        // 如果map中不包含此连接，就保存连接
        Channel channel = CHANNEL_GROUP.find(ID);
        if (!ObjectUtils.isEmpty(channel)) {
            log.info("客户端[ " + ID + " ]已是连接状态, 连接通道数量: " + CHANNEL_GROUP.size());
        } else {
            CHANNEL_GROUP.add(ctx.channel());
            log.info("客户端[ " + ID + " ]连接netty服务器--> " + socket.getAddress().getHostAddress() + ":" + socket.getPort());
            log.info("连接通道数量: " + CHANNEL_GROUP.size());
        }
    }

    /**
     * 客户端断开连接时触发
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        log.info("客户端[ " + ctx.channel().id() + " ]退出netty服务器");
        log.info("连接通道数量: " + CHANNEL_GROUP.size());
    }

    /**
     * 服务器接收到信息时触发
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) {
        Chat chat = null;
        try {
            chat = JsonUtils.ReadToObject(textWebSocketFrame.text(), Chat.class);
        } catch (JsonProcessingException e) {
            channelHandlerContext.channel().writeAndFlush(new TextWebSocketFrame("发送数据格式错误!"));
        }
        if (chat != null) {
            Chat save = chatService.sendMessage(chat);
            log.info("[ " + chat.getSenderId() + " ]" + " ---> [ " + chat.getRecipientId() + " ]: " + chat.getContent());
            if (!USER_CHANNEL.containsKey(chat.getSenderId()) || USER_CHANNEL.get(chat.getSenderId()) != channelHandlerContext.channel().id()) {
                USER_CHANNEL.put(chat.getSenderId(), channelHandlerContext.channel().id());
            }
            if (USER_CHANNEL.containsKey(chat.getRecipientId())) {
                ChannelId recipientChannelId = USER_CHANNEL.get(chat.getRecipientId());
                Channel channel = CHANNEL_GROUP.find(recipientChannelId);
                if (channel != null) {
                    log.info("已发送");
                    try {
                        channel.writeAndFlush(new TextWebSocketFrame(JsonUtils.ReadToJSON(save)));
                    } catch (JsonProcessingException e) {
                        channelHandlerContext.channel().writeAndFlush(new TextWebSocketFrame("Chat类转化JSON失败!"));
                    }
                } else {
                    chatService.storeUncheckChat(chat);
                    USER_CHANNEL.remove(chat.getRecipientId());
                }
            } else {
                chatService.storeUncheckChat(chat);
            }
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;
            String uri = request.uri();
            if (uri.contains("?")) {
                String id = uri.substring(uri.lastIndexOf("=") + 1);
                USER_CHANNEL.put(Integer.valueOf(id), ctx.channel().id());
                request.setUri("/chat");
            }
        }
        super.channelRead(ctx, msg);
    }

    /**
     * 连接出现异常时触发
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
        cause.printStackTrace();
        log.error(ctx.channel().id() + " 发生了错误,此连接被关闭" + "此时连通数量: " + CHANNEL_GROUP.size());
    }
}
