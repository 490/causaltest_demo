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

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;

@Controller
//@RequestMapping("/cmd")
public class CommandController
{
    private static final Logger logger = LoggerFactory.getLogger(CommandController.class);

    @GetMapping("/pwd")
    //@ResponseBody
    public String pwd(Model model)
    {
        try {
            String command = "pwd";
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
            return "cassandra";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @GetMapping(value="/database/habse/cmd")
    public String hbase(@RequestParam("website") String website,
                        @RequestParam("count") String count,
                        @RequestParam("consistency") String consistency)
    {
        logger.info(website+","+count+","+consistency);
        return "hbase";
    }
}
