package layui.demo.service;

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


    // 与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Process process;
    private InputStream inputStream;
    /**
     * 连接建立成功调用的方法
     * @param session 连接会话
     */
    @OnOpen
    public void onOpen(Session session)
    {

        try {
            // 执行tail -f命令
            String [] cmd={"/bin/sh","-c","tailf /data/zhaole/causaltest/result.txt"};
            //String [] cmd={"/bin/sh","-c","tailf /home/zl/Documents/test/pingcmd.txt"};

            process = Runtime.getRuntime().exec(cmd);
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
        try {
            if(inputStream != null)
                inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(process != null)
            process.destroy();
    }
    @OnError
    public void onError(Throwable thr) {
        thr.printStackTrace();
    }

}
