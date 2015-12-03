package TravelApply;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
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
            SimpleDateFormat formatter;
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
                formatter = new SimpleDateFormat(FormatString);
            }

            Date tmpD = formatter.parse(d);
            formatter = new SimpleDateFormat(FormatString);
            return formatter.format(tmpD);
        }catch(Exception e){
            return d;
        }
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
    
     /** 
     * 格式化XML文檔 
     * @param document xml doc
     * @param charset 編碼
     * @return 格式化後String
     */ 
    public static String formatXML(Document document, String charset) {
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding(charset);
        StringWriter sw = new StringWriter();
        XMLWriter xw = new XMLWriter(sw, format);
        try {
                xw.write(document);
                xw.flush();
                xw.close(); 
        } catch (IOException e) {
        }
        return sw.toString();
    }
    
    public static String readFile(File file) throws IOException {
        InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8"); 
        BufferedReader br = new BufferedReader(isr); 
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            return sb.toString();
        } catch(Exception e){
            logger.log(Level.ERROR, "[readFile]失敗。", e);
            return "";
        } finally {
            isr.close();
            br.close();
        }
    }
    
    public static void saveFile(String path, String content) throws IOException {
        File file = new File(path);
        try{
            BufferedWriter bufWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,false), "utf-8"));
            bufWriter.write(content); 
            bufWriter.close();
        }catch(Exception e){
            CommonHelp.logger.log(Level.ERROR, "[saveFile]失敗！", e);
        }
    }
    
    public static boolean chkConnect(String chkUrl){
        HttpURLConnection conn = null;
        try{
            URL url = new URL(chkUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                return true;
            }
        } catch (Exception e){
            CommonHelp.logger.log(Level.ERROR, "[chkConnect]失敗。", e);
        } finally{
            if(conn != null){ conn.disconnect(); }
        }
        return false;
    }
    
    public static Integer daysBetween(String smdate, String bdate) throws ParseException{
        if(smdate == null || bdate == null){
            return 0;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bdate));
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);
        
        return Integer.parseInt(String.valueOf(between_days));
    }   
}
