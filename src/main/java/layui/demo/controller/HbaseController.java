package layui.demo.controller;

import layui.demo.dao.ConfigFile;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayOutputStream;

@Controller

public class HbaseController
{
    @Autowired
    ConfigFile configFile;
    private static final Logger logger = LoggerFactory.getLogger(CommandController.class);
    String path = "/data/zhaole/causaltest/causalwebserver/src/main/resources/conf.properties";
    @RequestMapping(value="/database/hbase")
    public String hbase(@RequestParam(value = "website",required = false) String website,
                        @RequestParam(value = "count",required = false) String count,
                        @RequestParam(value = "consistency",required = false) String consistency,
                        Model model)
    {
        if(website!=null && count != null && consistency !=null)
        {
            configFile.setConfig(website,count,consistency,"Hbase");
            String result = "Website=["+website+"], Test times=["+count+"], Consistency=["+consistency+"]";
            logger.info(result);
            model.addAttribute("result",result);

            try {
                String command = "/data/zhaole/causaltest/bin/redeploy.sh ";
                //接收正常结果流
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                //接收异常结果流
                ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
                CommandLine commandline = CommandLine.parse(command);
                DefaultExecutor exec = new DefaultExecutor();
                exec.setExitValues(null);
                //设置一分钟超时
                ExecuteWatchdog watchdog = new ExecuteWatchdog(60*1000);
                exec.setWatchdog(watchdog);
                PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream,errorStream);
                exec.setStreamHandler(streamHandler);
                exec.execute(commandline);
                //不同操作系统注意编码，否则结果乱码
                String out = outputStream.toString("utf-8");
                String error = errorStream.toString("utf-8");
                model.addAttribute("pwdresult",out+error);
                logger.info(out);
                return "hbase";
            } catch (Exception e) {
                e.printStackTrace();
                return e.getMessage();
            }

        }

        return "hbase";
    }
    @RequestMapping("/database/hbase/crash")
    public String del(@RequestParam(name="id") String id)
    {
        logger.info(id);
        return "hbase";
    }
    @RequestMapping(value="/database/hbase/run")
    public String run(Model model)
    {
        logger.info("hbase run");
        try {
            String command = "/data/zhaole/causaltest/bin/runtest.sh ";
            //接收正常结果流
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            //接收异常结果流
            ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
            CommandLine commandline = CommandLine.parse(command);
            DefaultExecutor exec = new DefaultExecutor();
            exec.setExitValues(null);
            //设置一分钟超时
            ExecuteWatchdog watchdog = new ExecuteWatchdog(60*1000);
            exec.setWatchdog(watchdog);
            PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream,errorStream);
            exec.setStreamHandler(streamHandler);
            exec.execute(commandline);
            //不同操作系统注意编码，否则结果乱码
            String out = outputStream.toString("utf-8");
            String error = errorStream.toString("utf-8");
            model.addAttribute("pwdresult",out+error);
            logger.info(out);
            return "hbase::table_refresh";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
/*
      for(int i = 0;i<10;i++)
      {
          model.addAttribute("pwdresult",i+"\n");
          logger.info(i+"");

      }*/
      //::table_refresh
       // return "hbase::table_refresh";
    }
}
