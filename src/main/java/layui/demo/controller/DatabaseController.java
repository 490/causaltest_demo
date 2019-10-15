package layui.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/database")
public class DatabaseController
{
    @RequestMapping("/cassandra")
    //@ResponseBody
    public String cassandra()
    {
        return "cassandra";
    }
    @RequestMapping("/hbase")
    //@ResponseBody
    public String hbase()
    {
        return "hbase";
    }
    @RequestMapping("/mongo")
    //@ResponseBody
    public String mongo()
    {
        return "mongo";
    }
    @RequestMapping("/mysql")
    //@ResponseBody
    public String mysql()
    {
        return "mysql";
    }
    @RequestMapping("/postgres")
    //@ResponseBody
    public String postgres()
    {
        return "postgres";
    }
    @RequestMapping("/redis")
    //@ResponseBody
    public String redis()
    {
        return "redis";
    }
}
/*
*
$.ajax({
                                    url: '/database/habse/cmd',
                                    async:false,

                                    success: function(data){
                                        console.log(data);
                                    }
                                });
* */