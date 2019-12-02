package layui.demo.dao;

import layui.demo.controller.CommandController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class ConfigFile
{
    String path="/data/zhaole/causaltest/causalwebserver/src/main/resources/conf.properties";
    private static final Logger logger = LoggerFactory.getLogger(ConfigFile.class);

    //cassandra
    public void setConfig(String website,String count,String consistency,String database,String wcl,String rcl)
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
                    buf.replace(9,line.length(),database);
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
                }
                else if((line.startsWith("cassandra_readconsistencylevel=")))
                {
                    buf.append(line);
                    buf.replace(31,line.length(),rcl);
                    buf.append(System.getProperty("line.separator"));
                    bufAll.append(buf);
                }
                else if((line.startsWith("cassandra_writeconsistencylevel=")))
                {
                    buf.append(line);
                    buf.replace(32,line.length(),wcl);
                    buf.append(System.getProperty("line.separator"));
                    bufAll.append(buf);
                }
                else{
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


    ///mongo
    public void setConfig(String website,String count,String consistency,String database,
                          String readpreference, String writeconcern, String readconcern)
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
                    buf.replace(9,line.length(),database);
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
                }
                else if((line.startsWith("mongodb_readpreference=")))
                {
                    buf.append(line);
                    buf.replace(23,line.length(),readpreference);
                    buf.append(System.getProperty("line.separator"));
                    bufAll.append(buf);
                }
                else if((line.startsWith("mongodb_writeconcern=")))
                {
                    buf.append(line);
                    buf.replace(21,line.length(),writeconcern);
                    buf.append(System.getProperty("line.separator"));
                    bufAll.append(buf);
                }
                else if((line.startsWith("mongodb_readconcern=")))
                {
                    buf.append(line);
                    buf.replace(20,line.length(),readconcern);
                    buf.append(System.getProperty("line.separator"));
                    bufAll.append(buf);
                }
                else{
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


    //redis
    public void setConfig(String website,String count,String consistency,String database,String readmode)

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
                    buf.replace(9,line.length(),database);
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
                }
                else if (line.startsWith("redis_readmode="))
                {
                    buf.append(line);
                    buf.replace(14,line.length(),readmode);
                    buf.append(System.getProperty("line.separator"));
                    bufAll.append(buf);
                }
                else{
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

    //normal
    public void setConfig(String website,String count,String consistency,String database)

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
                    buf.replace(9,line.length(),database);
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
                }
                else{
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
        //logger.info(bufAll.toString());
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
