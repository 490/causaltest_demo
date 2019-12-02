package layui.demo.service;

import layui.demo.dao.ResolveResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;

@Service
public class ResultService
{
    @Autowired
    ResolveResult resoleResult;
    public String resolve() throws IOException, ParseException
    {
        return resoleResult.resolve();
    }
}
