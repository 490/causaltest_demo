package layui.demo.controller;

import layui.demo.service.ResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EchartsController
{
    @Autowired
    ResultService resultService;
    private static final Logger logger = LoggerFactory.getLogger(EchartsController.class);

    @RequestMapping(value="/echarts")
    @ResponseBody
    public String result() throws Exception
    {
       // logger.info("echarts controller  :");
      //  logger.info(resultService.resolve());
        return resultService.resolve();
    }

}
