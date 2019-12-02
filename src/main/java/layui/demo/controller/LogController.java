package layui.demo.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.InputStream;

@Controller
public class LogController
{
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    private static final Logger logger = LoggerFactory.getLogger(LogController.class);
    private Process process;

    private InputStream inputStream;
    private BufferedReader reader;

    String path = "/data/zhaole/causaltest/result.txt";
    @RequestMapping(value="/resultshow")
    @ResponseBody
    public String result() throws Exception
    {
        return "";
    }
    /*private JedisPool pool  = new JedisPool("redis://192.168.7.125:6379/10");

    public String get(String key)
    {
        //Scard 命令返回集合中元素的数量
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.get(key);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return "";
    }


    @RequestMapping(value="/resultshow")
    @ResponseBody
    public Result result() throws Exception
    {
        List<Result> resultList = new ArrayList<>();
    process = Runtime.getRuntime().exec("tail -f " + path);
    inputStream = process.getInputStream();
    reader = new BufferedReader(new InputStreamReader(inputStream));
    int i = 0;
    String line;
    //while ((line = reader.readLine()) != null)
    while(i<5)
    {
        line = reader.readLine();

        logger.info("resultshowcontroller  "+JSON.toJSONString(resultList));
    }


        Result result = new Result();
        result.setResulttime(System.currentTimeMillis());
        if(get("violation") != null)
        {
            result.setViolationnum(Integer.parseInt(get("violation")));

        }
        if(get("error")!=null)
        {
            result.setViolationnum(Integer.parseInt(get("exception")));
        }


        return result;
    }*/
}
