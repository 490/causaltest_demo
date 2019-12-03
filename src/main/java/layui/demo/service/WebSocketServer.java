package layui.demo.service;

import layui.demo.dao.WebSocketConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * WebSocket 服务
 *
 **/
@ServerEndpoint(value = "/log")
@Component
public class WebSocketServer
{
    private static final Logger log = LoggerFactory.getLogger(WebSocketServer.class);

    // 用来记录当前连接数的变量
    private static volatile int onlineCount = 0;

    // 线程安全Set，用来存放每个客户端对应的 WebSocket 对象
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<>();

    // 与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    private Process process;
    private InputStream inputStream;
    /**
     * 连接建立成功调用的方法
     * @param session 连接会话
     */
    @OnOpen
    public void onOpen(Session session)
    {
        this.session = session;
        // 加入set中
        webSocketSet.add(this);
        // 连接数加1
        addOnlineCount();
        log.info("有新连接，当前连接数为" + getOnlineCount());
        try {
            // 执行tail -f命令
            process = Runtime.getRuntime().exec("tailf /data/zhaole/causaltest/result.txt");
            inputStream = process.getInputStream();

            // 一定要启动新的线程，防止InputStream阻塞处理WebSocket的线程
            TailLogThread thread = new TailLogThread(inputStream, session);
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose()
    {
        // 从set中删除
        webSocketSet.remove(this);
        // 连接数减1
        subOnlineCount();
        log.info("有一连接关闭！当前连接数为：" + getOnlineCount());
        try {
            if(inputStream != null)
                inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(process != null)
            process.destroy();
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 消息
     * @param session 连接会话
     */
    @OnMessage
    public void onMessage(String message, Session session)
    {
        log.info("收到来自客户端的消息：" + message);

        // 群发消息
        for (WebSocketServer item : webSocketSet)
        {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发生错误时调用的方法
     *
     * @param session 连接会话
     * @param error 错误信息
     */
    @OnError
    public void onError(Session session, Throwable error)
    {
        log.error("发送错误");
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送消息
     *
     * @param message 消息
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException
    {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 群发自定义消息
     *
     * @param message 自定义消息
     * @throws IOException
     */
    public static void sendInfo(String message) throws IOException
    {
        log.info("推送消息内容："+message);
        for (WebSocketServer item : webSocketSet)
        {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                continue;
            }
        }
    }

    /**
     * 获取连接数
     * @return 连接数
     */
    private static synchronized int getOnlineCount()
    {
        return onlineCount;
    }

    /**
     * 连接数加1
     */
    private static synchronized void addOnlineCount()
    {
        WebSocketServer.onlineCount++;
    }

    /**
     * 连接数减1
     */
    private static synchronized void subOnlineCount()
    {
        WebSocketServer.onlineCount--;
    }

}
