/**
 * 訂單號統一存在雲端。
 */
package TravelApply;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import org.apache.logging.log4j.Level;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Insurance {
    public Insurance(){
        this.status = 0;
    }
    
    private String xml;
    private int status;     //0: 未處理、1: 成功、2: 失敗
    private String err;
    private final String INSUCOID = "PN";                           //保代編號（由華南產物提供）
    private final String INSUCONAME = "品安";                       //保代名稱
    private final String ICUSTOMER = "70452857";                    //旅行社公司統一編號
    private final String NCH_NAME = "川流國際旅行社股份有限公司";   //旅行社中文名稱
    private final String ECH_NAME = "";                             //＠旅行社英文名稱
//    private final String DAYS = "";                                 //旅遊天數
    private final String BAIRCODE = "遊覽車";                        //＠出發航班班次/交通工具
    private final String EAIRCODE = "";                             //＠
    private final String DEADTSUMIN = "2000000";                    //總死殘總保額，單位元
    private final String MEDICALTSUMIN = "200000";                  //總醫療總保額，單位元
    private final String PDISCOUNT = "35";                          //＠結帳保費折扣率, 由表定保費除以實收保費算出
    private final String PRECEIPT = "35";                           //＠收據保費折扣率(查表)
    private final String PCOMMISSION = "35";                        //＠淨保費折扣率(查表)
    private final String MCOUNT = "1";                              //團員人數，因為一次申請一個人，所以填1
//    private final String MPREM = "";                                //＠表定總保費
//    private final String MPREM_RCP = "";                            //＠實收總保費 = 表定總保費 * 折扣率 (四捨五入取整數)
    private final String DESTIN = "B";                              //旅遊別代號
    private final String TRIPAREA = "台灣-直接來台";                //旅遊地區中文名稱
    private final String TRIPAREAE = "Taiwan";                      //旅遊地區英文名稱
//    private final String GROUPNO = "";                              //＠團號，用資料夾名稱
//    private final String LEADER = "";                               //＠領隊名稱
    private final String COMUPS = "川流國際旅行社股份有限公司";     //被保險人名稱
    private final String RPOL_STS = "1";                            //保單狀態：1：正常傳送（含異動）、C：取消。
    private final String INSSRC = "IA00419";                        //保源代號,（由華南產物提供）
    private final String EMAIL = "julian.lin@pctravel.com.tw";      //＠保期後異動件審核結果email 通知
    private final String FAX_NAME = "林宜錚";                       //＠承辦人姓名
//    private final String FILE = "";                                 //檔案名稱，用資料夾名稱
    private final String DATA_ISEQ = "1";                           //序號，因為一次申請一個人，所以填1
    private final String DATA_BIRDAT = "";                          //生日，不用填寫，請留空白
    private final String DATA_DEADTSUMIN = "2000000";               //死殘保額，單位元
    private final String DATA_MEDICALTSUMIN = "200000";             //醫療保額，單位元
//    private final String DATA_DEADPREMUM = "";                      //＠死殘保費，單位元
    private final String DATA_MEDICALPREMUM = "0";                  //＠醫療保費，單位元
    private final String DATA_EXTRAPREMUM = "0";                    //不使用，請填入0
    private final String DATA_PERSONS = "1";                        //人數，請填入1
    private final HashMap<Integer, Integer> InsuranceFee = new HashMap<Integer, Integer>(){{
        //天數／保費
        put(15, 99);
        put(16, 103);
    }};
    
    public void setXML(String xml){
        this.xml = xml;
    }
    
    public String getXML(){
        return this.xml;
    }

    public int getStatus(){
        return this.status;
    }
    
    public void setStatus(int s){
        this.status = s;
    }
    
    public String getErr(){
        return this.err;
    }
    
    public boolean postXML(String postUrl) {
        PrintWriter pw = null;
        HttpURLConnection conn = null;
        try{
            if(this.xml == null || this.xml.isEmpty()){
                CommonHelp.logger.log(Level.ERROR, String.format("[Insurance][postXML] XML是空的。"));
                this.err = "保單資料不存在。";
                return false;
            }
            URL url = new URL(postUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-type", "text/xml");
            pw = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), StandardCharsets.UTF_8), true);
            pw.write(this.xml);
            pw.flush();
            if(conn.getResponseCode() != HttpURLConnection.HTTP_OK){
                CommonHelp.logger.log(Level.ERROR, String.format("[Insurance][postXML] API連線失敗，ResponseCode: %s。", conn.getResponseCode()));
                this.err = "API連線失敗。";
                return false;
            }
            
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(conn.getInputStream());
//            CommonHelp.saveFile(String.format("./test/%s.xml", CommonHelp.getNowTimeToSS()), CommonHelp.formatXML(document, "utf-8"));
            Element statusCode = document.getRootElement().element("STATUS_CODE");
            if(statusCode == null){
                CommonHelp.logger.log(Level.ERROR, String.format("[Insurance][postXML] XML解析失敗，找不到STATUS_CODE。%s%s", System.getProperty("line.separator"), document.asXML()));
                this.err = "保險申請失敗，XML解析失敗。";
                return false;
            }
            if(statusCode.getText().equals("0000")){
                this.err = "";
                return true;
            }else{
                String errMsg = String.format("保險申請失敗，status_code: %s", statusCode.getText());
                Element statusDesc = document.getRootElement().element("STATUS_DESC");
                if(statusDesc != null){
                    errMsg += String.format("，status_desc: %s", statusDesc.getText());
                }
                CommonHelp.logger.log(Level.ERROR, "[Insurance][postXML]" + errMsg);
                this.err = errMsg;
                return false;
            }
        } catch (IOException e) {
            CommonHelp.logger.log(Level.ERROR, "[Insurance][postXML] 失敗。", e);
            this.err = "保險申請失敗。" + e.getMessage();
            return false;
        } catch (DocumentException e) {
            CommonHelp.logger.log(Level.ERROR, "[Insurance][postXML] 失敗。", e);
            this.err = "保險申請失敗。" + e.getMessage();
            return false;
        } catch (Exception e){
            CommonHelp.logger.log(Level.ERROR, "[Insurance][postXML] 失敗。", e);
            this.err = "保險申請失敗。" + e.getMessage();
            return false;
        } finally{
            if(pw != null){ pw.close(); }
            if(conn != null){ conn.disconnect(); }
        }
    }

    public boolean createXML(TravelGroup tg, Traveller tr, int no, int insType) {
        try{
            Document doc = DocumentHelper.createDocument();
            Element root = doc.addElement("TRAVELDATA");
            Date dNow = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("YYMMdd");
            String today = formatter.format(dNow);
            String secno = String.format("GR%s%04d", today, no);
            Integer ttd = Integer.valueOf(tg.getCnTravelAgency().get("TotalTourDays").toString());
            Integer mprem = this.InsuranceFee.get(ttd);
            long mprem_rcp = Math.round((double)mprem / 100 * Integer.valueOf(this.PCOMMISSION));

            ( root.addElement("INSUCOID")     ).addText(this.INSUCOID);
            ( root.addElement("INSUCONAME")   ).addText(this.INSUCONAME);
            ( root.addElement("SECNO")        ).addText(secno);
            ( root.addElement("ICUSTOMER")    ).addText(this.ICUSTOMER);
            ( root.addElement("NCH_NAME")     ).addText(this.NCH_NAME);
            ( root.addElement("ECH_NAME")     ).addText(this.ECH_NAME);
            ( root.addElement("DBEGIN")       ).addText(tg.getTourStartDate());
            ( root.addElement("DEND")         ).addText(tg.getTourEndDate());
            ( root.addElement("DAYS")         ).addText(ttd.toString());
            ( root.addElement("BAIRCODE")     ).addText(this.BAIRCODE);
            ( root.addElement("DEADTSUMIN")   ).addText(this.DEADTSUMIN);
            ( root.addElement("MEDICALTSUMIN")).addText(this.MEDICALTSUMIN);
            ( root.addElement("PDISCOUNT")    ).addText(this.PDISCOUNT);
            ( root.addElement("PRECEIPT")     ).addText(this.PRECEIPT);
            ( root.addElement("PCOMMISSION")  ).addText(this.PCOMMISSION);
            ( root.addElement("MCOUNT")       ).addText(this.MCOUNT);
            ( root.addElement("MPREM")        ).addText(mprem.toString());
            ( root.addElement("MPREM_RCP")    ).addText(String.valueOf(mprem_rcp));
            ( root.addElement("DESTIN")       ).addText(this.DESTIN);
            ( root.addElement("TRIPAREA")     ).addText(this.TRIPAREA);
            ( root.addElement("TRIPAREAE")    ).addText(this.TRIPAREAE);
            ( root.addElement("GROUPNO")      ).addText(tr.getGroupName());
            ( root.addElement("LEADER")       ).addText(tr.getPersonId());
            ( root.addElement("COMUPS")       ).addText(this.COMUPS);
            ( root.addElement("RPOL_STS")     ).addText(this.RPOL_STS);
            ( root.addElement("INSSRC")       ).addText(this.INSSRC);
            ( root.addElement("EMAIL")        ).addText(this.EMAIL);
            ( root.addElement("FAX_NAME")     ).addText(this.FAX_NAME);
            ( root.addElement("FILE")         ).addText(tr.getGroupName());
            Element item = root.addElement("ITEM");
            Element data = item.addElement("DATA");
            data.addAttribute("ISEQ",           this.DATA_ISEQ);
            data.addAttribute("IDNUMB",         tr.getPassportNo());
            data.addAttribute("NAME",           tr.getChineseName());
            data.addAttribute("NAMEE",          tr.getEnglishName());
            data.addAttribute("SEX",            tr.getGender().equals("0")? "1" : "2");
            data.addAttribute("BIRDAT",         this.DATA_BIRDAT);
            data.addAttribute("DEADTSUMIN",     this.DATA_DEADTSUMIN);
            data.addAttribute("MEDICALTSUMIN",  this.DATA_MEDICALTSUMIN);
            data.addAttribute("DEADPREMUM",     mprem.toString());
            data.addAttribute("MEDICALPREMUM",  this.DATA_MEDICALPREMUM);
            data.addAttribute("EXTRAPREMUM",    this.DATA_EXTRAPREMUM);
            data.addAttribute("PERSONS",        this.DATA_PERSONS);

            this.xml = doc.asXML();
//            this.xml = CommonHelp.formatXML(doc, "utf-8");
//            CommonHelp.saveFile(String.format("./test/%s_%s.xml", tg.getTourName(), CommonHelp.getNowTimeToSS()), this.xml);
            return true;

        } catch (Exception e){
            this.err = "建立保單資料失敗";
            CommonHelp.logger.log(Level.ERROR, String.format("[Insurance][%s][%s] 建立保單XML失敗。", tg.getTourName(), tr.getChineseName()), e);
            return false;
        }
    }
    
    public void saveXML(Document doc, String path){
        try{
            FileWriter fw = new FileWriter(path); // 可自訂
            // 下面這行：預設自動換行、Tab 為 2 個空白
            // OutputFormat of = OutputFormat.createPrettyPrint(); // 格式化XML
            OutputFormat of = new OutputFormat(); // 格式化XML
            of.setIndentSize(4); // 設定 Tab 為 4 個空白
            of.setNewlines(true);// 設定 自動換行
            XMLWriter xw = new XMLWriter(fw, of);
            xw.write(doc);
            xw.close();
        } catch (Exception e){}
    }
    
    public static int getInsuranceNo(String noUrl){
        HttpURLConnection conn = null;
        try{
            URL url = new URL(noUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            String line;
            StringBuilder result = new StringBuilder();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
            JSONParser parser = new JSONParser();
            JSONObject jsonobj = (JSONObject)parser.parse(result.toString());
            return Integer.valueOf(jsonobj.get("no").toString());
        } catch (IOException e) {
            CommonHelp.logger.log(Level.ERROR, "[Insurance]取得編號失敗。", e);
        } catch (Exception e){
            CommonHelp.logger.log(Level.ERROR, "[Insurance]取得編號失敗。", e);
        } finally{
            if(conn != null){ conn.disconnect(); }
        }
        return -2;
    }
    
    public static void setInsuranceNo(String noUrl, int no){
        HttpURLConnection conn = null;
        try{
            String urlParameters  = String.format("insno=%s", no);
            byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
            int    postDataLength = postData.length;
            URL    url            = new URL(noUrl);
            conn = (HttpURLConnection) url.openConnection();           
            conn.setDoOutput( true );
            conn.setInstanceFollowRedirects( false );
            conn.setRequestMethod( "POST" );
            conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded"); 
            conn.setRequestProperty( "charset", "utf-8");
            conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
            conn.setUseCaches( false );
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.write(postData);
            wr.close();
            if(conn.getResponseCode() != HttpURLConnection.HTTP_OK){
                CommonHelp.logger.log(Level.ERROR, String.format("[Insurance]設定編號失敗，ResponseCode: %s。", conn.getResponseCode()));
            }
        } catch (Exception e){
            CommonHelp.logger.log(Level.ERROR, "[Insurance]設定編號失敗。", e);
        } finally{
            if(conn != null){ conn.disconnect(); }
        }
    }

}
