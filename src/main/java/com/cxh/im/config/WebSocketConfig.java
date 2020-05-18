package com.cxh.im.config;

import com.cxh.im.websocket.AgentWebSocket;
import com.cxh.im.websocket.CustomerWebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer
{

    @Autowired
    private AgentWebSocket agentWebSocket;
    @Autowired
    private CustomerWebSocket customerWebSocket;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry)
    {
        registry.addHandler(agentWebSocket, "/websocket/im/agent").addHandler(customerWebSocket, "/websocket/im/client")
                .setAllowedOrigins("*");
    }

}
