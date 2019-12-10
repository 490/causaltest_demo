package layui.demo.controller;

import layui.demo.dao.ConfigFile;
import layui.demo.service.ResultService;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

@Controller

public class CassandraController {
    @Autowired
    ConfigFile configFile;
    private static final Logger logger = LoggerFactory.getLogger(CassandraController.class);
    private Process process;
    private InputStream inputStream;
    private ProcessBuilder processBuilder = new ProcessBuilder();
    @RequestMapping(value="/database/cassandra")
    public String cassandra(@RequestParam(value = "website",required = false) String website,
                            @RequestParam(value = "count",required = false) String count,
                            @RequestParam(value = "consistency",required = false) String consistency,
                            @RequestParam(value = "wcl",required = false) String wcl,
                            @RequestParam(value = "rcl",required = false) String rcl,
                            @RequestParam(value = "keyspace",required = false) String keyspace,
                            Model model)
    {
        if(website!=null && count != null && consistency !=null)
        {
            configFile.setConfig(website,count,consistency,"Cassandra",wcl,rcl);
            String result = "Website=["+website+"], Test times=["+count+"], Consistency=["+consistency+"]"
                    + "Read Consistency Level=["+rcl+"], Write Consistency Level=["+wcl+"]"+", keyspace=["
                    +keyspace+"]";
            logger.info(result);
            model.addAttribute("result",result);
            configFile.redeploy();
        }
        return "cassandra";
    }

    @RequestMapping("/database/cassandra/crash")
    public String del(@RequestParam(name="id") String id)
    {
        logger.info(id);
        String[] cmd={};
        switch (id){
            case "1":
            {
                cmd= new String[]{"/bin/sh","-c","/data/zhaole/causaltest/bin/cassandracoor.sh"};
                break;
            }
            case "2":
            {
                cmd= new String[]{"/bin/sh","-c","/data/zhaole/causaltest/bin/cassandradata.sh"};
                break;
            }
            case "3":
            {
                cmd= new String[]{"/bin/sh","-c","/data/zhaole/causaltest/bin/cassandracoor_re.sh"};
                break;
            }
            case "4":
            {
                cmd= new String[]{"/bin/sh","-c","/data/zhaole/causaltest/bin/cassandradata_re.sh"};
                break;
            }
            default:{
                cmd= new String[]{"/bin/sh","-c","/data/zhaole/causaltest/bin/cassandradata.sh"};
                break;
            }
        }
        try {
            processBuilder.command(cmd);
            processBuilder.directory(new File("/data/zhaole/causaltest"));
            process = processBuilder.start();
            return "cassandra";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @RequestMapping(value="/database/cassandra/run")
    public String run(Model model)
    {
        try {
            String [] cmd={"/bin/sh","-c","/data/zhaole/causaltest/bin/runtest.sh > /data/zhaole/causaltest/runtimelog.txt 2>&1"};
            processBuilder.command(cmd);
            processBuilder.directory(new File("/data/zhaole/causaltest"));
            process = processBuilder.start();
            return "cassandra";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
