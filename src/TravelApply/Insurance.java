/**
 * 訂單號問題
 * 
 */
package TravelApply;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.logging.log4j.Level;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class Insurance {
    public Insurance(){
        this.status = 0;
    }
    
    private String xml;
    private int status;     //0: 未處理、1: 成功、2: 失敗
    private String err;
//    private final String PostUrl = "https://b2bn.south-china.com.tw/eCommerceB2B/Secure/TravelDutyReceiver.ashx";
    private final String PostUrl = "http://memory.justest.com/InsuranceRequest.php";
    private final String INSUCOID = "PN";                           //保代編號（由華南產物提供）
    private final String INSUCONAME = "品安";                       //保代名稱
    private final String ICUSTOMER = "70452857";                    //旅行社公司統一編號
    private final String NCH_NAME = "川流國際旅行社股份有限公司";   //旅行社中文名稱
    private final String ECH_NAME = "";                             //＠旅行社英文名稱
    private final String DAYS = "14";                               //旅遊天數
    private final String BAIRCODE = "LOCAL";                        //＠出發航班班次/交通工具
    private final String EAIRCODE = "";                             //＠
    private final String DEADTSUMIN = "2000000";                    //總死殘總保額，單位元
    private final String MEDICALTSUMIN = "200000";                  //總醫療總保額，單位元
    private final String PDISCOUNT = "42";                          //＠結帳保費折扣率, 由表定保費除以實收保費算出
    private final String PRECEIPT = "35";                           //＠收據保費折扣率
    private final String PCOMMISSION = "35";                        //＠淨保費折扣率
    private final String MCOUNT = "1";                              //團員人數，因為一次申請一個人，所以填1
    private final String MPREM = "6402";                            //＠表定總保費
    private final String MPREM_RCP = "2689";                        //＠實收總保費
    private final String DESTIN = "B";                              //旅遊別代號
    private final String TRIPAREA = "台灣";                         //旅遊地區中文名稱
    private final String TRIPAREAE = "Taiwan";                      //旅遊地區英文名稱
//    private final String GROUPNO = "A101225德聯";                   //＠團號，用資料夾名稱
//    private final String LEADER = "范瑋琳";                         //＠領隊名稱
    private final String COMUPS = "川流國際旅行社股份有限公司";     //被保險人名稱
    private final String RPOL_STS = "1";                            //保單狀態：1：正常傳送（含異動）、C：取消。
    private final String INSSRC = "IA00419";                        //保源代號,（由華南產物提供）
    private final String EMAIL = "";                                //＠保期後異動件審核結果email 通知
    private final String FAX_NAME = "";                             //＠承辦人姓名
//    private final String FILE = "";                                 //檔案名稱，用資料夾名稱
    private final String DATA_ISEQ = "1";                           //序號，因為一次申請一個人，所以填1
    private final String DATA_BIRDAT = "";                          //生日，不用填寫，請留空白
    private final String DATA_DEADTSUMIN = "2000000";               //死殘保額，單位元
    private final String DATA_MEDICALTSUMIN = "200000";             //醫療保額，單位元
    private final String DATA_DEADPREMUM = "52";                    //＠死殘保費，單位元
    private final String DATA_MEDICALPREMUM = "0";                  //＠醫療保費，單位元
    private final String DATA_EXTRAPREMUM = "0";                    //不使用，請填入0
    private final String DATA_PERSONS = "1";                        //人數，請填入1
    
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
    
    public boolean postXML() {
        PrintWriter pw = null;
        HttpURLConnection conn = null;
        try{
            if(this.xml == null || this.xml.isEmpty()){
                CommonHelp.logger.log(Level.ERROR, String.format("[Insurance][postXML] XML是空的。"));
                this.err = "保單資料不存在。";
                return false;
            }
            URL url = new URL(this.PostUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-type", "text/xml");
            pw = new PrintWriter(conn.getOutputStream());
            pw.write(this.xml);
            pw.flush();
            if(conn.getResponseCode() != HttpURLConnection.HTTP_OK){
                CommonHelp.logger.log(Level.ERROR, String.format("[Insurance][postXML] API連線失敗，ResponseCode: %s。", conn.getResponseCode()));
                this.err = "API連線失敗。";
                return false;
            }
            
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(conn.getInputStream());
            this.saveXML(document, String.format("%s.xml", CommonHelp.getNowTimeToSS()));
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

    public boolean createXML(TravelGroup tg, Traveller tr) {
        try{
            Document doc = DocumentHelper.createDocument();
            Element root = doc.addElement("TRAVELDATA");
            Date dNow = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("YYMMdd");
            String today = formatter.format(dNow);
            String secno = String.format("GR%s0001", today);
            ( root.addElement("INSUCOID")     ).addText(this.INSUCOID);
            ( root.addElement("INSUCONAME")   ).addText(this.INSUCONAME);
            ( root.addElement("SECNO")        ).addText(secno);
            ( root.addElement("ICUSTOMER")    ).addText(this.ICUSTOMER);
            ( root.addElement("NCH_NAME")     ).addText(this.NCH_NAME);
            ( root.addElement("ECH_NAME")     ).addText(this.ECH_NAME);
            ( root.addElement("DBEGIN")       ).addText(tg.getTourStartDate());
            ( root.addElement("DEND")         ).addText(tg.getTourEndDate());
            ( root.addElement("DAYS")         ).addText(this.DAYS);
            ( root.addElement("BAIRCODE")     ).addText(this.BAIRCODE);
            ( root.addElement("DEADTSUMIN")   ).addText(this.DEADTSUMIN);
            ( root.addElement("MEDICALTSUMIN")).addText(this.MEDICALTSUMIN);
            ( root.addElement("PDISCOUNT")    ).addText(this.PDISCOUNT);
            ( root.addElement("PRECEIPT")     ).addText(this.PRECEIPT);
            ( root.addElement("PCOMMISSION")  ).addText(this.PCOMMISSION);
            ( root.addElement("MCOUNT")       ).addText(this.MCOUNT);
            ( root.addElement("MPREM")        ).addText(this.MPREM);
            ( root.addElement("MPREM_RCP")    ).addText(this.MPREM_RCP);
            ( root.addElement("DESTIN")       ).addText(this.DESTIN);
            ( root.addElement("TRIPAREA")     ).addText(this.TRIPAREA);
            ( root.addElement("TRIPAREAE")    ).addText(this.TRIPAREAE);
            ( root.addElement("GROUPNO")      ).addText(tg.getTourName());
            ( root.addElement("LEADER")       ).addText(tr.getChineseName());
            ( root.addElement("COMUPS")       ).addText(this.COMUPS);
            ( root.addElement("RPOL_STS")     ).addText(this.RPOL_STS);
            ( root.addElement("INSSRC")       ).addText(this.INSSRC);
            ( root.addElement("EMAIL")        ).addText(this.EMAIL);
            ( root.addElement("FAX_NAME")     ).addText(this.FAX_NAME);
            ( root.addElement("FILE")         ).addText(tg.getTourName());
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
            data.addAttribute("DEADPREMUM",     this.DATA_DEADPREMUM);
            data.addAttribute("MEDICALPREMUM",  this.DATA_MEDICALPREMUM);
            data.addAttribute("EXTRAPREMUM",    this.DATA_EXTRAPREMUM);
            data.addAttribute("PERSONS",        this.DATA_PERSONS);
            
            this.xml = doc.asXML();
            this.saveXML(doc, String.format("%s_%s.xml", tg.getTourName(), CommonHelp.getNowTimeToSS()));
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
}
