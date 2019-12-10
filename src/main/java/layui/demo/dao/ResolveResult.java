package layui.demo.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import layui.demo.model.Result;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Repository
public class ResolveResult
{
    public static void main(String[] args) throws Exception{
        String lastTime = "";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date d1 = df.parse(lastTime);
        long dtmp = d1.getTime();
        System.out.println(dtmp);

    }
    public String resolve() throws IOException, ParseException
    {
       // Date date = new Date();
      //  String time = JSON.toJSONStringWithDateFormat(date, "yyyy-MM-dd HH:mm:ss.SSS");
      //  System.out.println(time);
        String filepath = "/data/zhaole/causaltest/result.txt";
        FileReader fileReader = new FileReader(filepath);

        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line =bufferedReader.readLine();
        String _type="";
        String _num="";
        String _time="";
        List<Result> resultList = new ArrayList<Result>();
        List<Result> exceptionList = new ArrayList<Result>();
        List<Result> violationList = new ArrayList<Result>();
        StringBuilder sb = new StringBuilder();

        while (line!=null)
        {
            line = bufferedReader.readLine();
            if(line!=null && line.startsWith("{"))
            {
               // System.out.println(line);
                JSONObject jsonObject = JSONObject.parseObject(line);
                _type = jsonObject.getString("type");
                _num = jsonObject.getString("num");
                _time = jsonObject.getString("time");
                Result result = new Result();
                result.set_num(_num);
                result.set_time(_time);
                if(_type.equals("exception"))
                    exceptionList.add(result);
                else if(_type.equals("violation"))
                    violationList.add(result);

                resultList.add(result);
            }
        }
        bufferedReader.close();
        fileReader.close();

        int size = resultList.size();

        if(size == 0)
        {
            sb.append("[");
            for(int i = 0;i <10 ;i++)
            {
                sb.append("{\"tm\":");
                Date date = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                sb.append("\""+simpleDateFormat.format(date)+"\"");
                sb.append(",\"excp\":0");
                sb.append(",\"vio\":0");
                sb.append("}]");
            }
            return sb.toString();
        }


        int perGapNum = size % 10;
        //System.out.println(size+","+perGapNum);
        String firstTime = "";
        String lastTime = "";
        if (size>0)
        {
             firstTime  = resultList.get(0).get_time();
             lastTime = resultList.get(resultList.size()-1).get_time();
        }


        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date d1 = df.parse(firstTime);
        Date d2 = df.parse(lastTime);
        long diff = (d2.getTime()-d1.getTime())/10;
       // System.out.println(d1);
        List<Integer> expList = new ArrayList<Integer>();
        List<Integer> vioList = new ArrayList<Integer>();

        long dtmp = d1.getTime();

        while(dtmp<d2.getTime())
        {
            //System.out.println(dtmp);
            int expnum =0;
            int vionum = 0;
            for(Result result:exceptionList)
            {
                if(df.parse(result.get_time()).getTime() > (dtmp+diff) )
                {
                    break;
                }
                else
                    expnum = Integer.parseInt(result.get_num());
            }
            expList.add(expnum);

            for(Result result:violationList)
            {
                if(df.parse(result.get_time()).getTime() > (dtmp+diff) )
                {
                    break;
                }
                else
                    vionum = Integer.parseInt(result.get_num());
            }
            vioList.add(vionum);

            dtmp += diff;

        }
        dtmp = d1.getTime();
        //System.out.println("exp:"+expList+"\n"+"vio:"+vioList);
        sb.append("[");
        int exptmp = 0;
        int viotmp = 0;
        for(int i = 0;i <10 ;i++)
        {
            sb.append("{\"tm\":");
            Date date = new Date(dtmp);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            sb.append("\""+simpleDateFormat.format(date)+"\"");
            sb.append(",\"excp\":");
            if(expList.size()>=i)
            {
                sb.append("\""+expList.get(i)+"\"");
                exptmp = expList.get(i);
            }
            else{
                sb.append("\""+exptmp+"\"");
            }
            sb.append(",\"vio\":");
            if(vioList.size()>=i)
            {
                sb.append("\""+vioList.get(i)+"\"");
                viotmp = vioList.get(i);
            }
            else{
                sb.append("\""+viotmp+"\"");
            }
            sb.append("},");
            dtmp += diff;
        }
        sb.append("{\"tm\":");
        Date date = new Date(dtmp);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        sb.append("\""+simpleDateFormat.format(date)+"\"");
        sb.append(",\"excp\":");
        if(expList.size()>=10)
        {
            sb.append("\""+expList.get(10)+"\"");
        }
        else{
            sb.append("\""+exptmp+"\"");
        }
        sb.append(",\"vio\":");
        if(vioList.size()>=10)
        {
            sb.append("\""+vioList.get(10)+"\"");
        }
        else{
            sb.append("\""+viotmp+"\"");
        }
        sb.append("}");
        sb.append("]");
        return sb.toString();
    }
}
