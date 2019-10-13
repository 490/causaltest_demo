package layui.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/database")
public class DatabaseController
{
    @GetMapping("/cassandra")
    //@ResponseBody
    public String cassandra()
    {
        return "cassandra";
    }
    @GetMapping("/hbase")
    //@ResponseBody
    public String hbase()
    {
        return "hbase";
    }
    @GetMapping("/mongo")
    //@ResponseBody
    public String mongo()
    {
        return "mongo";
    }
    @GetMapping("/mysql")
    //@ResponseBody
    public String mysql()
    {
        return "mysql";
    }
    @GetMapping("/postgres")
    //@ResponseBody
    public String postgres()
    {
        return "postgres";
    }
    @GetMapping("/redis")
    //@ResponseBody
    public String redis()
    {
        return "redis";
    }
}
