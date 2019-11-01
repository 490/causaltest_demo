package layui.demo.controller;


import com.alibaba.fastjson.JSON;
import layui.demo.model.TomcatLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ResultController
{
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    private static final Logger logger = LoggerFactory.getLogger(ResultController.class);
    private Process process;

    private InputStream inputStream;
    private BufferedReader reader;

    String path = "/home/zl/Documents/test/access_log.2019-11-01.log";



    @RequestMapping(value="/resultshow")
    @ResponseBody
    public List<TomcatLog> result() throws Exception
    {
        logger.info("result logger");
        List<TomcatLog> logList = new ArrayList<>();
        process = Runtime.getRuntime().exec("tail -f " + path);
        inputStream = process.getInputStream();
        reader = new BufferedReader(new InputStreamReader(inputStream));
        int i = 0;
        String line;
        while ((line = reader.readLine()) != null)
        {
            TomcatLog tomcatLog = new TomcatLog();

            tomcatLog.setId( System.currentTimeMillis());
            tomcatLog.setLog(line);
            tomcatLog.setCount(i++);
            logger.info("resultshowcontroller  "+JSON.toJSONString(tomcatLog));
            logList.add(tomcatLog);
        }
        return logList;
    }
}
