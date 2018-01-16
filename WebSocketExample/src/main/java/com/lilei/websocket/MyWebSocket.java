package com.lilei.websocket;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;

@Component
@ServerEndpoint(value = "/webSocket/{androidId}")
public class MyWebSocket {

    // 存放连接信息的集合
    private static Map<String, Session> sessionPool = new HashMap<>();
    private static Map<String, String> sessionIds = new HashMap<>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("androidId") String androidId) {
        this.session = session;
        sessionPool.put(androidId, session);
        sessionIds.put(session.getId(), androidId);
        System.out.println("有一连接开启！当前在线人数为" + getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        sessionPool.remove(sessionIds.get(session.getId()));
        sessionIds.remove(session.getId());
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息:" + message);
        sendMessage(message, sessionIds.get(session.getId()));
    }


    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }


    public void sendMessage(String message, String androidId) {
        Session nowSession = sessionPool.get(androidId);
        if (nowSession == null) {
            return;
        }
        try {
            nowSession.getBasicRemote().sendText(message);
        } catch (Exception e) {
            System.out.println("发送消息失败：" + message + "---" + androidId);
        }
    }


    /**
     * 群发自定义消息
     */
    public void sendAll(String message) {
        for (String key : sessionIds.keySet()) {
            sendMessage(message, key);
        }
    }

    public static synchronized int getOnlineCount() {
        return sessionPool.size();
    }
}