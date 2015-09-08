package TravelApply;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.swing.ImageIcon;
import org.apache.logging.log4j.Level;

public class Traveller {
    //-------------------------------------------------------------------------
    private String id;                  //@必填
    private String travelGroupId;       //@必填
    private Integer version = 4;        //?? @必填(填4)
    private Short seqNo;                //順序
    private String passportNo;          //大陸來臺通行證號
    private String applyQualification = "B";    //申請資格A~F 預設B 可能要自己填@
    private String applyType = "7";     //申請類別(大陸地區人民來臺觀光(個人旅遊))
    private String applyReason = "192"; //申請原因(個人都是192)
    private String decideMark = "0";    //??(填0)
    private String chineseName;
    private String englishName;
    private String gender;              //0: 男, 1: 女，可能要自己填@
    private String birthDate;
    private Integer birthPlaceCode = 4; //??(填4)
    private Short birthPlace1;          //出生地 省(市) 可能要自己填@
    private String birthPlace2;         //出生地 縣(市) 可能要自己填@
    private Integer education;          //教育程度 可能要自己填@
    private Integer occupation;         //職業類別 可能要自己填@
    private String occupationDesc;      //職業
    private String address;             //居住地
    private String personId;            //身分證號
    private String partnerOfTaiwan;     //為臺灣人民之配偶
    private String memberType = "M";    //身份(G:領隊、M:成員)
    private String applyVisa = "1";     //單次證申請 (填1)@必填
    private String kinsfolk;            //(KINSFOLK) 隨行親友姓名
    private String activeStatus = "0";  //??(填0)
    private String completion = "w";    //資料完成度(w/s) @必填
    private String deleteFlag = "N";    //??(填N)
    private String passportExpiryDate;  //通行證有效日期
    private String relativeTitle;       //隨行親友稱謂
    private String livingCity;          //@居住城市 可能要自己填
    private List<Attach> attachList = new ArrayList<Attach>();
    private Insurance insurance = new Insurance();
    private String groupName;
    private final String[] exList = {"exList", "attachList", "insurance", "groupName"};
    /* -- 不必填---------------------------------------------------------------
    private String tourGroupId;
    private String leaderPermitNo;      //收件號
    private String invalidMark;         //??
    private String educationDesc;
    private String twAddress;           //在臺緊急連絡親友地址
    private String twTelNo;             //在臺緊急連絡親友電話
    private String beenCnPartyJob;
    private String beenCnPartyJobDesc;
    private String cnPartyJob;
    private String cnPartyJobDesc;
    private String resume;              //經歷
    private String receiveNo;           //??
    private String withParent;          //父母是否同行
    private String requiredDocsStatus;  //??
    private Short requiredDocPages;     //??
    private String cnLeaderExpiryDate;  //領隊證有效期
    private String cnLeaderNo;          //領隊證編號
    private Date revokeDate;            //??
    private String tourBureauApplyMark; //??
    --------------------------------------------------------------------------*/
    
    public String getId(){
        return this.id;
    }

    public void setId(String paramString){
        this.id = paramString;
    }

    public void setTravelGroupId(String paramString){
        this.travelGroupId = paramString;
    }

    public Short getSeqNo(){
        return this.seqNo;
    }

    public void setSeqNo(int paramShort){
        this.seqNo = (short)paramShort;
    }

    public String getPassportNo()
    {
      return this.passportNo;
    }

    public void setPassportNo(String paramString){
        this.passportNo = paramString.trim();
    }

    public String getApplyQualification(){
        return this.applyQualification;
    }

    public void setApplyQualification(String paramString){
        this.applyQualification = paramString;
    }

    public int getApplyQualificationIdxByCode(){
        String[] aqArr = {"B", "C", "A", "D", "F"};
        return Arrays.asList(aqArr).indexOf(this.applyQualification);
    }

    public String getApplyQualificationCodeByIdx(int i){
        String[] aqArr = {"B", "C", "A", "D", "F"};
        return aqArr[i];
    }

    public String getChineseName(){
        return this.chineseName == null? "" : this.chineseName;
    }

    public void setChineseName(String paramString){
        this.chineseName = CommonHelp.transToTC(paramString.trim());
    }

    public String getEnglishName(){
        return this.englishName;
    }

    public void setEnglishName(String paramString){
        this.englishName = paramString.trim();
    }

    public String getGender(){
        return this.gender;
    }

    public void setGender(String paramString){
        String g = CommonHelp.transToTC(paramString.trim());
        if(g.equals("女")){
          this.gender = "1";
        }else if(g.equals("男")){
          this.gender = "0";
        }else if(g.equals("1")){
          this.gender = "1";
        }else if(g.equals("0")){
          this.gender = "0";
        }else{
            this.gender = null;
        }
    }

//  public String getGenderIdx(String paramString){
//      if(paramString.trim().equals("女")){
//          return "1";
//      }else if(paramString.trim().equals("男")){
//          return "0";
//      }else{
//          return null;
//      }
//  }

    public String getGenderMean(){
        if(this.gender == null){
            return "";
        }else{
            return this.gender.equals("1") ? "女" : "男";
        }
    }

    public String getBirthDate(){
        return this.birthDate;
    }

    public void setBirthDate(String paramString){
        this.birthDate = CommonHelp.dateFormatFix(paramString.trim());
    }

    public Short getBirthPlace1(){
        return this.birthPlace1;
    }
    
    public Integer getBirthPlace1Idx(String s){
        String bp = CommonHelp.transToTC(s.trim());
        if(bp == "" || bp.isEmpty()){
            return 0;
        }
        String placeStr = "請選擇, 台北, 高雄, 廣州, 上海, 南京, 漢口, 重慶, 青島, 天津, 北京, 西安, 大連, 瀋陽, 哈爾濱, 台灣, 福建, 廣東, 廣西, 雲南, 貴州, 海南, 江蘇, 浙江, 安徽, 江西, 湖南, 湖北, 四川, 山東, 山西, 河南, 河北, 陜西, 甘肅, 遼寧, 遼北, 安東, 吉林, 松江, 合江, 嫩江, 黑龍江, 興安, 熱河, 察哈爾, 綏遠, 寧夏, 蒙古, 新疆, 青海, 西康, 西藏";
        String[] placeArr = placeStr.split(", ");
        for(int i = 0; i < placeArr.length; i++){
            if(bp.indexOf(placeArr[i]) >= 0){
                return i;
            }
        }
        return 0;
    }
    
    public void setBirthPlace1(int i){
        if(i == 0){
            this.birthPlace1 = null;
        }else{
            this.birthPlace1 = (short)i;
        }
    }
    
    public String getBirthPlace2(){
        return this.birthPlace2;
    }
    
    public void setBirthPlace2(String paramString){
        this.birthPlace2 = CommonHelp.transToTC(paramString.trim());
    }
    
    public Integer getEducation(){
        return this.education;
    }

    public void setEducation(int paramInteger){
        if(paramInteger == 0 || paramInteger == -1){
            this.education = null;
        }else{
            this.education = paramInteger;
        }
    }

    public Integer getEducationIdx(String s){
        String[] eduList = {"", "博士", "碩士", "大學", "專科", "五專", "高中", "國中", "國小", "無"};
        return Arrays.asList(eduList).indexOf(CommonHelp.transToTC(s.trim()));
    }

    public String getEducationMean(){
        if(this.education == null){
            return "";
        }
        String[] eduList = {"", "博士", "碩士", "大學", "專科", "五專", "高中", "國中", "國小", "無"};
        return eduList[this.education];
    }

    public Integer getOccupation(){
        return this.occupation;
    }

    public void setOccupation(Integer paramInteger){
        if(paramInteger == 0){
            this.occupation = null;
        }else{
            this.occupation = paramInteger;
        }
    }

    public Integer getOccupationId(String s){
        String occuDesc = CommonHelp.transToTC(s.trim());
        if(occuDesc == "" || occuDesc.isEmpty()){
            return 17;
        }
        
        HashMap occuMap = new HashMap();
        occuMap.put(14, "學生");

        for (Object key : occuMap.keySet()) {
            String val = (String)occuMap.get(key);
            if(occuDesc.indexOf(val) >= 0){
                return (int)key;
            }
        }
        return 0;
            
//<option value="1">軍</option>
//<option value="2">公</option>
//<option value="3">教</option>
//<option value="4">私</option>
//<option value="5">商</option>
//<option value="6">農</option>
//<option value="7">工</option>
//<option value="8">醫</option>
//<option value="9">宗</option>
//<option value="10">演</option>
//<option value="11">新聞</option>
//<option value="12">漁</option>
//<option value="13">輪</option>
//<option value="14">學</option>
//<option value="15">自</option>
//<option value="16">其他</option>
//<option value="17">無</option>
//<option value="18">警</option>
    }
    
    public String getOccupationDesc(){
        return this.occupationDesc;
    }

    public void setOccupationDesc(String paramString){
        this.occupationDesc = CommonHelp.transToTC(paramString.trim());
//        if(this.occupationDesc.equals("")){ this.occupation = 17; }
    }

    public String getAddress(){
        return this.address;
    }

    public void setAddress(String paramString){
        this.address = CommonHelp.transToTC(paramString.trim());
    }

    public String getPersonId(){
        return this.personId;
    }

    public void setPersonId(String paramString){
        this.personId = paramString.trim();
    }

    public String getPartnerOfTaiwan(){
        return this.partnerOfTaiwan;
    }

    public void setPartnerOfTaiwan(String paramString){
        String is =  CommonHelp.transToTC(paramString.trim());
        if(is.equals("是")){
            this.partnerOfTaiwan = "1";
        }else if(is.equals("否")){
            this.partnerOfTaiwan = "0";
        }else{
            this.partnerOfTaiwan = null;
        }
    }

    public String getPartnerOfTaiwanMean(){
        if(this.partnerOfTaiwan == null){
            return "";
        }
        return this.partnerOfTaiwan.equals("1") ? "是" : "否";
    }

    public String getRelative(){
        return this.kinsfolk;
    }

    public void setRelative(String paramString){
        this.kinsfolk = paramString.trim();
    }

    public String getPassportExpiryDate(){
        return this.passportExpiryDate;
    }

    public void setPassportExpiryDate(String paramString){
        this.passportExpiryDate = CommonHelp.dateFormatFix(paramString.trim());
    }

    public String getRelativeTitle(){
        return this.relativeTitle;
    }

    public void setRelativeTitle(String paramString){
        this.relativeTitle = CommonHelp.transToTC(paramString.trim());
    }

    public String getLivingCity(){
        return this.livingCity;
    }

    public void setLivingCity(Integer paramInteger){
        if(paramInteger == 0){
            this.livingCity = null;
        }else{
            this.livingCity = "F" + paramInteger;
        }

    }

    public String getLivingCityMean(){
        if(this.livingCity == null){
            return "無";
        }
        String[] liveCityArr = {"北京", "上海", "廈門", "天津", "重慶", "南京", "廣州", "杭州", "成都", "濟南", "西安", "福州", "深圳" };
        return liveCityArr[getLivingCityIdx()];
    }

    public Integer getLivingCityIdx(){
        if(this.livingCity == null){return null;}
        return Integer.valueOf(this.livingCity.substring(1)) - 1;
    }

    public Integer getLivingCityCode(String s){
        String[] liveCityArr = {"北京", "上海", "廈門", "天津", "重慶", "南京", "廣州", "杭州", "成都", "濟南", "西安", "福州", "深圳" };
        String lc = CommonHelp.transToTC(s.trim());
        if(lc.length() >= 0){
            for(int i = 0; i < liveCityArr.length; i++){
                if(lc.indexOf(liveCityArr[i]) >= 0){
                    return i+1;
                }
            }
        }
        return 0;
    }

    public List<Attach> getAttachList(){
        return this.attachList;
    }

    public void setAttachList(List<Attach> la){
        this.attachList = la;
    }
  
    public void addAttach(List<File> files) {
        addAttach(files, true);
    }

    private void addAttach(List<File> files, boolean reOnce) {
        try{
            for (File file : files) {
                if (file.isDirectory() && reOnce) {
                    addAttach(Arrays.asList(file.listFiles()), false);
                } else {
                    String fn = file.getName().toLowerCase();
                    if (fn.endsWith(".jpg") || fn.endsWith(".jpeg") || fn.endsWith(".png")) {
                        Attach a = new Attach();
                        a.setFile(file);
                        this.attachList.add(a);
                    }
                }
            }
        }catch(Exception e){
            CommonHelp.logger.log(Level.ERROR, String.format("[%s] addAttach失敗。", this.chineseName), e);
        }
    }
  
    public void removeAttachs(List<Attach> la){
        this.attachList.removeAll(la);
    }
    
    public Insurance getInsurance(){
        return this.insurance;
    }
    
    public void setInsurance(Insurance i){
        this.insurance = i;
    }
    
    public boolean isValidTraveller(){
        String[] chkArr = {"", "無", "如有生僻字請注明", null};
        for(String s : chkArr){
            if(this.chineseName.equals(s)){
  //              System.out.println("無效的名字");
                return false;
            }
        }
        if(!this.passportNo.matches("^T\\d{8}$")){
  //          System.out.println("通行證號格式錯誤！");
            return false;
        }
        if(!this.personId.matches("^\\w{18}$")){
  //          System.out.println("身分證號格式錯誤！");
            return false;
        }
        return true;
    }

    public String getInsertStr(){
      String insertStr = null;
      try {
          StringBuffer nameSb = new StringBuffer();
          StringBuffer valueSb = new StringBuffer();
          Class<?> objClass = this.getClass();
          Field[] fields = objClass.getDeclaredFields();

          for(int i = 0; i < fields.length; i++){
              String name = fields[i].getName();
              Object value = fields[i].get(this);
              String type = ((Class) fields[i].getType()).getSimpleName();

              if(value == null || isExclude(name)){
                  continue;
              }

              nameSb.append(name + ", ");
              if(type.equals("String")){
                  valueSb.append("'" + value.toString() + "', ");
              }else{
                  valueSb.append(value.toString() + ", ");
              }

          }

          insertStr = String.format("insert into %s(%s CreateDate, LastUpdateTime) "
              + "values(%s current_timestamp, current_timestamp)",
              objClass.getSimpleName(), nameSb.toString(), valueSb.toString());

      } catch(Exception e) {
          CommonHelp.logger.log(Level.ERROR, String.format("[Traveller][%s] 建立insertSQL失敗。", this.chineseName), e);
          return null;
      }
      return insertStr;
    }

    private boolean isExclude(String s){
        for(String ex : this.exList){
            if(s.equals(ex)){
                return true;
            }
        }
        return false;
    }

    public List<ErrMsg> getErrMsgList(){
        List<ErrMsg> errList = new ArrayList<ErrMsg>();

        if(this.attachList.isEmpty()){ errList.add(new ErrMsg("請選擇附件。", 1));
        }else if(!this.isSetHeadShot()){ errList.add(new ErrMsg("請設定大頭照。", 1)); }

        if(this.chineseName == null || this.chineseName.isEmpty()){ errList.add(new ErrMsg("*中文姓名未填寫。", 0));
        }else if(this.chineseName.length() > 40){
            errList.add(new ErrMsg("*中文姓名格式有誤。", 0, "[Traveller]", String.format("\"%s\"", this.chineseName)));
        }

        if(this.englishName == null || this.englishName.isEmpty()){ errList.add(new ErrMsg("*英文姓名未填寫。", 0));
        }else if(this.englishName.length() > 50){
            errList.add(new ErrMsg("*英文姓名格式有誤。", 0, "[Traveller]", String.format("\"%s\"", this.englishName)));
        }

        if(this.birthDate == null || this.birthDate.isEmpty()){ errList.add(new ErrMsg("出生年月日未填寫。", 1));
        }else if(!this.birthDate.matches("^(19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|1\\d|2\\d|3[0-1])$")){
            errList.add(new ErrMsg("*出生年月日格式錯誤。", 0, "[Traveller]", String.format("\"%s\"", this.birthDate)));
        }

        if(this.personId == null || this.personId.isEmpty()){ errList.add(new ErrMsg("*身分證號未填寫。", 0));
        }else if(!this.personId.matches("^\\w{18}$")){
            errList.add(new ErrMsg("*身分證號格式錯誤。", 0, "[Traveller]", String.format("\"%s\"", this.personId)));
        }

        if(this.passportNo == null || this.passportNo.isEmpty()){ errList.add(new ErrMsg("*通行證號未填寫。", 0));
        }else if(!this.passportNo.matches("^T\\d{8}$")){
            errList.add(new ErrMsg("*通行證號格式錯誤。", 0, "[Traveller]", String.format("\"%s\"", this.passportNo)));
        }

        if(this.passportExpiryDate == null || this.passportExpiryDate.isEmpty()){ errList.add(new ErrMsg("*通行證有效期未填寫。", 0));
        }else if(!this.passportExpiryDate.matches("^(19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|1\\d|2\\d|3[0-1])$")){
            errList.add(new ErrMsg("*通行證有效期格式錯誤。", 0, "[Traveller]", String.format("\"%s\"", this.passportExpiryDate)));
        }

        if(this.gender == null){ errList.add(new ErrMsg("*性別未選擇。", 0)); }
        if(this.education == null){ errList.add(new ErrMsg("教育程度未選擇。", 1)); }
        if(this.livingCity == null){ errList.add(new ErrMsg("居住地未選擇。", 1)); }
        if(this.address == null || this.address.isEmpty()){ errList.add(new ErrMsg("地址未填寫。", 1));
        }else if(this.address.length() > 128){
            errList.add(new ErrMsg("*地址格式錯誤。", 0, "[Traveller]", String.format("\"%s\"", this.address)));
        }

        if(this.birthPlace1 == null){ errList.add(new ErrMsg("出生地-省(市)未選擇。", 1)); }
        if(this.birthPlace2 == null || this.birthPlace2.isEmpty()){ errList.add(new ErrMsg("出生地-縣(市)未填寫。", 1));
        }else if(this.birthPlace2.length() > 128){
            errList.add(new ErrMsg("*出生地-縣(市)格式錯誤。", 0, "[Traveller]", String.format("\"%s\"", this.birthPlace2)));
        }
        
        if(this.seqNo == 0){
            if(this.occupation == null){ errList.add(new ErrMsg("職業類別未選擇。", 1)); }
            if(this.occupationDesc == null || this.occupationDesc.isEmpty()){ errList.add(new ErrMsg("職業未填寫。", 1));
            }else if(this.occupationDesc.length() > 40){
                errList.add(new ErrMsg("*職業格式錯誤。", 0, "[Traveller]", String.format("\"%s\"", this.occupationDesc)));
            }
        }else{
            if(this.kinsfolk == null || this.kinsfolk.isEmpty()){ errList.add(new ErrMsg("隨行親友姓名未填寫。", 1));
            }else if(this.kinsfolk.length() > 20){
                errList.add(new ErrMsg("*隨行親友姓名格式錯誤。", 0, "[Traveller]", String.format("\"%s\"", this.kinsfolk)));
            }

            if(this.relativeTitle == null || this.relativeTitle.isEmpty()){ errList.add(new ErrMsg("隨行親友稱謂未填寫。", 1));
            }else if(this.relativeTitle.length() > 16){
                errList.add(new ErrMsg("*隨行親友稱謂格式錯誤。", 0, "[Traveller]", String.format("\"%s\"", this.relativeTitle)));
            }
        }

        return errList;
    }

    /**
     * 回傳此Traveller的資料驗證結果。
     * @return 0: OK, 1: warning, 2: error
     */
    public int getValidateStatus(){
        int vs = 0;
        for(ErrMsg m : this.getErrMsgList()){
            if(m.getType() == 0){ return 2;
            }else if(m.getType() == 1){ vs = 1; }
        }
        return vs;
    }

    public boolean isSetHeadShot(){
        for(Attach a : this.attachList){
            if(a.getType().equals("1")){ return true; }
        }
        return false;
    }

    public ImageIcon getHeadShot(){
        for(Attach a : this.attachList){
            if(a.getType().equals("1")){
                return a.getImageIcon();
            }
        }
        return null;
    }

    public void resetHeadShot(){
      for (Attach a : this.attachList) {
          a.setType("2");
      }
    }
    
    public String getGroupName(){
        return this.groupName;
    }
    
    public void setGroupName(String s){
        this.groupName = s;
    }
}
