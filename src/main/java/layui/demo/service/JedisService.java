package layui.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.*;

@Service
public class JedisService implements InitializingBean
{
    private static final Logger logger = LoggerFactory.getLogger(JedisService.class);
    private JedisPool pool;
    public Jedis getJedis()
    {
        return pool.getResource();
    }

    @Override
    public void afterPropertiesSet() throws Exception
    {
        pool = new JedisPool("redis://192.168.7.125:6379/10");
    }
}
