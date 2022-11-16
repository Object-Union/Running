package com.example.running;

import com.example.running.Common.NettyCommon;
import com.example.running.Netty.NettyWebSocketServer;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;
import java.net.InetSocketAddress;

@Log4j2
@SpringBootApplication
public class RunningApplication implements CommandLineRunner {

    @Resource(name = "NettyWebSocketServer")
    private NettyWebSocketServer nettyWebSocketServer;

    public static void main(String[] args) {
        SpringApplication.run(RunningApplication.class, args);
    }

    @Override
    public void run(String... args) {
        InetSocketAddress websocketAddress = new InetSocketAddress(NettyCommon.NETTY_WEBSOCKET_SERVER, NettyCommon.NETTY_WEBSOCKET_PORT);
        log.info("NettyWebSocket is running on --> " + "ws://" + NettyCommon.NETTY_WEBSOCKET_SERVER + ":" + NettyCommon.NETTY_WEBSOCKET_PORT + NettyCommon.NETTY_WEBSOCKET_URL);
        new Thread(() -> nettyWebSocketServer.start(websocketAddress), "WebSocket").start();
    }
}
