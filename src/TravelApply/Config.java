
package TravelApply;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import org.apache.logging.log4j.Level;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class Config {
    
    public Config(){
        this.setConfigFromFile();
    }
    private JSONObject jsonConfig;
    private final String DefConfigPath = "config_def.json";
    private final String ConfigPath = "config.json";
    
    public JSONObject getConfig(){
        return this.jsonConfig;
    }
    
    private void setConfigFromFile(){
        try{
            File config    = new File(this.ConfigPath),
                 defConfig = new File(this.DefConfigPath);
            if(!defConfig.exists()){
                throw new FileNotFoundException();
            }
            JSONParser parser = new JSONParser();
            String defContent = CommonHelp.readFile(defConfig);
            JSONObject defJsonObj = (JSONObject)parser.parse(defContent);
            
            if(config.exists()){
                String content = CommonHelp.readFile(config);
                JSONObject jsonObj = (JSONObject)parser.parse(content);
                String defVer = defJsonObj.get("version").toString();
                String ver = jsonObj.get("version").toString();
                if(ver.equals(defVer)){
                    this.jsonConfig = jsonObj;
                    return;
                }else{
                    config.delete();
                }
            }
            this.jsonConfig = defJsonObj;
            return;
        } catch(FileNotFoundException e){
            CommonHelp.logger.log(Level.ERROR, "[getConfig]失敗，找不到檔案！", e);
        } catch(IOException e){
            CommonHelp.logger.log(Level.ERROR, "[getConfig]失敗！", e);
        } catch(Exception e){
            CommonHelp.logger.log(Level.ERROR, "[getConfig]失敗！", e);
        }
        this.jsonConfig = null;
    }

    public boolean saveToFile(){
        if(this.jsonConfig == null){
            CommonHelp.logger.log(Level.ERROR, "[儲存設定]jsonConfig is null!");
            return false;
        }
        File file = new File(ConfigPath);
        try{
            BufferedWriter bufWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,false), "utf-8"));
            bufWriter.write(jsonConfig.toJSONString()); 
            bufWriter.close();
        }catch(IOException e){
            CommonHelp.logger.log(Level.ERROR, "[儲存設定]失敗！", e);
            return false;
        }
        return true;
    }
    
    public String getStringValByKey(String key){
        return this.jsonConfig.get(key).toString();
    }
    
    public JSONArray getTravelAgencyList(){
        return (JSONArray)this.jsonConfig.get("TravelAgencyList");
    }
    
    public int getSelAgcIdx(){
        return Integer.valueOf(this.getStringValByKey("TravelAgency"));
    }
    
    public void setSelAgcIdx(int i){
        this.jsonConfig.put("TravelAgency", i);
    }
    
    public JSONObject getAgcByIdx(int i){
        return (JSONObject)this.getTravelAgencyList().get(i);
    }
    
    public JSONObject getSelAgcObj(){
        return this.getAgcByIdx(this.getSelAgcIdx());
    }
    
    public String getAgcNameByIdx(int i){
        return this.getAgcByIdx(i).get("Name").toString();
    }
    
    public String getSelAgcName(){
        return this.getSelAgcObj().get("Name").toString();
    }
    
    public String getAgcNoByIdx(int i){
        return this.getAgcByIdx(i).get("No").toString();
    }
    
    public String getSelAgcNo(){
        return this.getSelAgcObj().get("No").toString();
    }
    
    public int getAgcTTDByIdx(int i){
        return Integer.valueOf(this.getAgcByIdx(i).get("TotalTourDays").toString());
    }
    
    public void setAgcTTDByIdx(int i, int days){
        this.getAgcByIdx(i).put("TotalTourDays", days);
    }
    
    public int getSelAgcTTD(){
        return Integer.valueOf(this.getSelAgcObj().get("TotalTourDays").toString());
    }
    
    public void setSelAgcTTD(int days){
        this.getSelAgcObj().put("TotalTourDays", days);
    }
    
    public JSONArray getResolveModeList(){
        return (JSONArray)this.jsonConfig.get("ResolveModeList");
    }
    
    public int getSelModeIdx(){
        return Integer.valueOf(this.getStringValByKey("ResolveMode"));
    }
    
    public void setSelModeIdx(int i){
        this.jsonConfig.put("ResolveMode", i);
    }
    
    public JSONObject getModeObjByIdx(int i){
        return (JSONObject)this.getResolveModeList().get(i);
    }
    
    public JSONObject getSelModeObj(){
        return this.getModeObjByIdx(this.getSelModeIdx());
    }
    
    public int[] getPosition(JSONObject jsonobj, String key){
        JSONArray ja = (JSONArray)jsonobj.get(key);
        if(ja == null){ return null; }
        int[] a = new int[ja.size()];
        for(int i = 0; i < ja.size(); i++){
            a[i] = (int)(long)ja.get(i);
        }
        return a;
    }
    
    public String getDBPath(){
        return this.getStringValByKey("DBPath");
    }
    
    public void setDBPath(String s){
        this.jsonConfig.put("DBPath", s);
    }
    
    public String getHeadShotName(){
        return this.getStringValByKey("HeadShotName");
    }
    
    public void setHeadShotName(String s){
        this.jsonConfig.put("HeadShotName", s);
    }
    
    public JSONObject getInsuranceObj(){
        return (JSONObject)this.jsonConfig.get("Insurance");
    }
    
    public String getInsuranceAPIAddress(){
        return this.getInsuranceObj().get("APIAddress").toString();
    }
    
    public void setInsuranceAPIAddress(String s){
        this.getInsuranceObj().put("APIAddress", s);
    }
    
    public String getInsuranceNoAddress(){
        return this.getInsuranceObj().get("NoAddress").toString();
    }
    
    public void setInsuranceNoAddress(String s){
        this.getInsuranceObj().put("NoAddress", s);
    }
    
    public String getInsuranceName(){
        return this.getInsuranceObj().get("Name").toString();
    }
    
    public void setInsuranceName(String s){
        this.getInsuranceObj().put("Name", s);
    }
    
    public String getInsuranceEmail(){
        return this.getInsuranceObj().get("Email").toString();
    }
    
    public void setInsuranceEmail(String s){
        this.getInsuranceObj().put("Email", s);
    }
    
}
