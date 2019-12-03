package layui.demo.controller;
import layui.demo.service.WebSocketServer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * WebSocket 控制类
 *
 **/
@Controller
@RequestMapping("/webSocketCtrl")
public class WebSocketController
{

    @ResponseBody
    @RequestMapping(value = "/pushMessageToWeb", method = RequestMethod.POST)
    public String pushMessageToWeb(@RequestBody String message)
    {
        try {
            WebSocketServer.sendInfo(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

    @RequestMapping("/hello")
    public String helloHtml(HashMap<String, Object> map)
    {
        map.put("hello", "这是一个thymeleaf页面");
        return "websocket";
    }

}
