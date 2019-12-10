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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

@Controller

public class MysqlController
{
    @Autowired
    ConfigFile configFile;
    private static final Logger logger = LoggerFactory.getLogger(MysqlController.class);

    private Process process;
    private InputStream inputStream;
    private ProcessBuilder processBuilder = new ProcessBuilder();
    @RequestMapping(value="/database/mysql")
    public String cassandra(@RequestParam(value = "website",required = false) String website,
                            @RequestParam(value = "count",required = false) String count,
                            @RequestParam(value = "consistency",required = false) String consistency,
                            Model model)
    {
        if(website!=null && count != null && consistency !=null)
        {
            configFile.setConfig(website,count,consistency,"Mysql");
            String result = "Website=["+website+"], Test times=["+count+"], Consistency=["+consistency+"]";
            logger.info(result);
            model.addAttribute("result",result);
            configFile.redeploy();

        }
        return "mysql";
    }
    @RequestMapping("/database/mysql/crash")
    public String del(@RequestParam(name="id") String id)
    {
        logger.info(id);
        String[] cmd={};
        switch (id){
            case "1":
            {
                cmd= new String[]{"/bin/sh","-c","/data/zhaole/causaltest/bin/mysqldnb.sh"};
                break;
            }
            case "2":
            {
                cmd= new String[]{"/bin/sh","-c","/data/zhaole/causaltest/bin/mysqldnb_re.sh"};
                break;
            }

            default:{
                cmd= new String[]{"/bin/sh","-c","/data/zhaole/causaltest/bin/mysqldnb_re.sh"};
                break;
            }
        }
        try {
            processBuilder.command(cmd);
            processBuilder.directory(new File("/data/zhaole/causaltest"));
            process = processBuilder.start();
            return "mysql";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
    @RequestMapping(value="/database/mysql/run")
    public String run(Model model)
    {
        try {
            String [] cmd={"/bin/sh","-c","/data/zhaole/causaltest/bin/runtest.sh > /data/zhaole/causaltest/runtimelog.txt 2>&1"};
            processBuilder.command(cmd);
            processBuilder.directory(new File("/data/zhaole/causaltest"));
            process = processBuilder.start();
            return "mysql";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
