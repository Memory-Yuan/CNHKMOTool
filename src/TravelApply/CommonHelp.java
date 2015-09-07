package TravelApply;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import taobe.tec.jcc.JChineseConvertor;

public class CommonHelp {
    public CommonHelp(){}
    
    private static final String FormatString = "yyyyMMdd";
    public static Logger logger = LogManager.getLogger();
    
    public static String transToTC(String str){
        String result = null;
        try{
            result = new String(str.getBytes("UTF-8"), "UTF-8");
            JChineseConvertor convertor =  JChineseConvertor.getInstance();
            result = convertor.s2t(str);
        } catch (Exception e){e.printStackTrace();}
        return result;
    }
    
    public static String dateFormatFix(String d){
        d = transToTC(d.trim());
        try{
            SimpleDateFormat formatter = new SimpleDateFormat(FormatString);
            if(d.matches("^(19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|1\\d|2\\d|3[0-1])$")){
                return d;
            }else if(d.matches("^(19|20)\\d{2}-(0[1-9]|1[0-2])-(0[1-9]|1\\d|2\\d|3[0-1])$")){
                formatter = new SimpleDateFormat("yyyy-MM-dd");
            }else if(d.matches("^(19|20)\\d{2}\\s(0[1-9]|1[0-2])\\s(0[1-9]|1\\d|2\\d|3[0-1])$")){
                formatter = new SimpleDateFormat("yyyy MM dd");
            }else if(d.matches("^(19|20)\\d{2}/(0[1-9]|1[0-2])/(0[1-9]|1\\d|2\\d|3[0-1])$")){
                formatter = new SimpleDateFormat("yyyy/MM/dd");
            }else if(d.matches("^(19|20)\\d{2}\\.(0[1-9]|1[0-2])\\.(0[1-9]|1\\d|2\\d|3[0-1])$")){
                formatter = new SimpleDateFormat("yyyy.MM.dd");
            }else if(d.matches("^(19|20)\\d{2}年(0[1-9]|1[0-2])月(0[1-9]|1\\d|2\\d|3[0-1])日$")){
                formatter = new SimpleDateFormat("yyyy年MM月dd日");
            }else{
                return d;
            }
            Date tmpD = formatter.parse(d);
            formatter = new SimpleDateFormat("yyyyMMdd");
            return formatter.format(tmpD);
        }catch(Exception e){}
        return d;
    }
    
    public static String calculateTourDate(String startDate, int num) throws ParseException{
        SimpleDateFormat formatter=new SimpleDateFormat(FormatString);
        Date sdt=formatter.parse(startDate);
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(sdt);
        rightNow.add(Calendar.DAY_OF_YEAR,num);
        Date edt=rightNow.getTime();
        String endData = formatter.format(edt);
        return endData;
    }
    
    public static String getNowTimeToSS(){
        Date d = new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSS");
        String str=sdf.format(d);
        return str;
    }
    
    public String removeExtension(String name){
        int pos = name.lastIndexOf(".");
        if (pos > 0) {
            name = name.substring(0, pos);
        }
        return name;
        
    }
    
    public static void logErrMsgList(List<ErrMsg> errList){
        for(ErrMsg m : errList){
            if(m.getType() == 0){
                logger.log(
                        Level.ERROR,
                        String.format("%s %s %s", m.getTag(), m.getMsg(), m.getPlusMsg())
                );
            }
        }
    }

    public static Date getDateFromString(String s){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat(FormatString);
            Date date = sdf.parse(s);
            return date;
        }catch(Exception e){}
        return null;
    }
    
    public static String getFormatDate(Date date){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat(FormatString);
            return sdf.format(date);
        }catch(Exception e){}
        return "";
    }
    
}
