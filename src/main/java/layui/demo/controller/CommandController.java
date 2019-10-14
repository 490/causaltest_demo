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
import java.io.*;

@Controller
//@RequestMapping("/cmd")
public class CommandController
{
    private static final Logger logger = LoggerFactory.getLogger(CommandController.class);
    //String path = "/data/zhaole/causaltest/causalwebserver/src/main/resources/conf.properties";
    String path="/home/zl/Documents/test/conf.properties";
    @GetMapping("/pwd")
    //@ResponseBody
    public String pwd(Model model)
    {
        try {
            String command = "vim ";
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
        setConfig(website,count,consistency);
        logger.info("set conf file completed.");
        return "hbase";
    }

    public void setConfig(String website,String count,String consistency)
    {
        BufferedReader br = null;
        String line = null;
        StringBuffer bufAll = new StringBuffer();  //保存修改过后的所有内容，不断增加

        try {
                br = new BufferedReader(new FileReader(path));
                while ((line = br.readLine()) != null)
                {
                    StringBuffer buf = new StringBuffer();
                    if (line.startsWith("operationcount="))
                    {
                        buf.append(line);
                        buf.replace(15,line.length(),count);
                        buf.append(System.getProperty("line.separator"));
                        bufAll.append(buf);
                    }
                    else if (line.startsWith("database="))
                    {
                        buf.append(line);
                        buf.replace(9,line.length(),"Hbase");
                        buf.append(System.getProperty("line.separator"));
                        bufAll.append(buf);
                    }
                    else if (line.startsWith("website="))
                    {
                        buf.append(line);
                        buf.replace(8,line.length(),website);
                        buf.append(System.getProperty("line.separator"));
                        bufAll.append(buf);
                    }
                    else if (line.startsWith("consistency="))
                    {
                        buf.append(line);
                        buf.replace(12,line.length(),consistency);
                        buf.append(System.getProperty("line.separator"));
                        bufAll.append(buf);
                    }else{
                        buf.append(line);
                        buf.append(System.getProperty("line.separator"));
                        bufAll.append(buf);
                    }
                }
        } catch (Exception e)
        {
                e.printStackTrace();
        } finally
        {
            if (br != null)
            {
                try {
                    br.close();
                } catch (IOException e) {
                    br = null;
                }
            }
        }
        BufferedWriter bw = null;
        try
        {
            bw = new BufferedWriter(new FileWriter(path));
            bw.write(bufAll.toString());

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(bw != null)
            {
                try {
                    bw.close();
                }catch (IOException e){
                    bw = null;
                }
            }
        }


    }

}
