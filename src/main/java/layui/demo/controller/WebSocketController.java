package layui.demo.controller;

import com.alibaba.fastjson.JSON;
import jdk.nashorn.internal.objects.NativeJSON;
import layui.demo.dao.LogUtil;
import layui.demo.model.Result;
import layui.demo.model.TomcatLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@EnableScheduling   //定时任务支持
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    private static final Logger logger = LoggerFactory.getLogger(WebSocketController.class);

    private Process process;

    private InputStream inputStream;
    private BufferedReader reader;
    private int lineCount = 0;
    private final int READ_SIZE = 128;		//日志文件默认读取大小，单位：KB
    private final int TIME_MILLS = 100;		//日志读取返回时间
    private final int READ_LINES = 10;		//日志读取每次返回的行数
    @RequestMapping("/test")
    public String index() {
        return "index";
    }

    /**
     * 这里用于客户端推送消息到服务端，推送地址为：setApplicationDestinationPrefixes("/app")设置得前缀加上@MessageMapping注解得地址
     * 此处为/app/send
     * 当前方法处理后将结果转发给@SendTo注解得地址，如果没有指定，则采用默认
     * @MessageMapping 指定要接收消息的地址，类似@RequestMapping。除了注解到方法上，也可以注解到类上
     * @SendTo 默认消息将被发送到与传入消息相同的目的地，但是目的地前面附加前缀（默认情况下为"/topic")
     * @param message 客户端推送过来得消息，一般生产环境会封装
     * @return
     * @throws Exception
     */
    @MessageMapping("/send")
    @SendTo("/topicTest/web-to-server-to-web")
    public String greeting(String message) throws Exception {
        // 模拟延时，以便测试客户端是否在异步工作
        Thread.sleep(500);
        return "Hello, " + message + "!";
    }
    TomcatLog tomcatLog = new TomcatLog();
    /**
     * 最基本的服务器端主动推送消息给客户端
     * @return
     * @throws Exception
     */
   /* private JedisPool pool  = new JedisPool("redis://192.168.7.125:6379/10");

    public long scard(String key)
    {
        //Scard 命令返回集合中元素的数量
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.scard(key);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }*/


    @Scheduled(initialDelay = 5000,fixedRate = 1000)
    public String serverTime() throws Exception {



        String path = "/data/zhaole/causaltest/result.txt";

        process = Runtime.getRuntime().exec("tail -f " + path);
        inputStream = process.getInputStream();
        reader = new BufferedReader(new InputStreamReader(inputStream));
        //StringBuilder reportContent = new StringBuilder();
       // long startTime = System.currentTimeMillis();
      //  long tempMill;
        String line;
        while ((line = reader.readLine()) != null)
        {
            // System.out.println(line);
            tomcatLog.setId( System.currentTimeMillis());
            tomcatLog.setLog(line);
            //reportContent.append(line).append("\r\n  <br>");
           // tempMill = System.currentTimeMillis();
            // 将实时日志通过WebSocket发送给客户端
          //  if (lineCount >= READ_LINES || tempMill - startTime >= TIME_MILLS)
          ///  {
                // session.getBasicRemote().sendText(reportContent.toString());
///logger.info(JSON.toJSONString(tomcatLog));
                messagingTemplate.convertAndSend("/topicTest/servertime",JSON.toJSONString(tomcatLog));//JSON.toJSONString(reportContent));
          //      startTime = tempMill;
          //      lineCount = 0;
          //      reportContent.setLength(0);
         //   } else {
          //      lineCount++;
          //  }
        }

        //DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       // messagingTemplate.convertAndSend("/topicTest/servertime", df.format(new Date()));
        return "servertime";
    }

    /**
     * 服务端推送消息到指定用户得客户端
     * 例如以下将会推送到
     * 因为设置了setUserDestinationPrefix("/userTest")，因此推送到/userTest/fanqi/info
     * 如果没有设置setUserDestinationPrefix()，则默认前端为user，将会推送到/user/fanqi/info
     * 客户端订阅/userTest/fanqi/info将会收到服务端推送得消息
     * @return
     * @throws Exception
     */
    @Scheduled(initialDelay = 5000,fixedRate = 1000)
    public String serverTimeToUser() throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        messagingTemplate.convertAndSendToUser("fanqi","/info", df.format(new Date()));
        return "serverTimeToUser";
    }
}