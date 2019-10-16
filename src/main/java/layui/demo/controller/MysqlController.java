package layui.demo.controller;

import layui.demo.dao.ConfigFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class MysqlController
{
    @Autowired
    ConfigFile configFile;
    private static final Logger logger = LoggerFactory.getLogger(CommandController.class);


    @RequestMapping(value="/database/mysql")
    public String cassandra(@RequestParam("website") String website,
                            @RequestParam("count") String count,
                            @RequestParam("consistency") String consistency)
    {
        logger.info(website+","+count+","+consistency);
        if(website!=null && count != null && consistency !=null)
        configFile.setConfig(website,count,consistency,"Mysql");
        logger.info("set conf file completed.");
        return "mysql";
    }
}
