package layui.demo.controller;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

@Controller
//@RequestMapping("/cmd")
public class CommandController
{
    private static final Logger logger = LoggerFactory.getLogger(CommandController.class);
    //String path = "/data/zhaole/causaltest/causalwebserver/src/main/resources/conf.properties";
    String path="/home/zl/Documents/test/conf.properties";
    @RequestMapping("/pwd")
    //@ResponseBody
    public String pwd(Model model)
    {
        logger.info("enter pwd");
        try {
            String command = "pwd ";
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
            model.addAttribute("pwd",out);
            logger.info(out+error);
            return "hbase";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
    //@RequestMapping("/test")
    //@ResponseBody
    public String pwd(Model model,@RequestParam(value = "name",required = false) String name)
    {
        if(name!=null)
        {
            logger.info(name + ".");
            model.addAttribute("msg", name);
        }
        return "hbase";
    }




}
