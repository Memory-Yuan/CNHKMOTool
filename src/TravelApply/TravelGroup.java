package TravelApply;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.Level;

public class TravelGroup {
    //--------------------------------------------------
    private String id;                  //@必填
    private Integer version = 2;        //?? @必填(填2)
    private String travelAgencyNo = ""; //?? @必填
    private String cnTravelAgencyNo = "L-ZJ-CJ00055";                   //組團社代號@
    private String cnTravelAgencyName = "浙江商務國際旅行社有限公司";   //組團社名稱@
    private String applyDate;           //申請日期 @必填
    private String niaApplyDate;        //申請日期
    private String tourStartDate;       //入境日期
    private String tourEndDate;         //出境日期
    private Byte entryPort = 1;         //入境地點(桃園機場)
    private Byte exitPort = 1;          //出境地點(桃園機場)
    private String tourName;            //行程名稱
    private Short groupCount;           //旅客人數
    private String tourCarType = "1";   //??(填1)
    private String applyType = "D";     //大陸來台觀光:D、小三通:C @必填
    private String itemType = "3";      //申辦項目(大陸地區人民來臺觀光(個人旅遊)) @必填
    private String tourApplyType = "7";     //(大陸地區人民來臺觀光(個人旅遊))
    private String permitApplyCount;        //人數(不含領隊)
    private String activeStatus = "0";      //??(填0)
    private Integer noControlSeqNo = 0;     //??(填0)
    private String status = "T10";          //??(填T10)
    private String paymentRemark = "N";     //??(填N)
    private Integer procOrder = 0;          //??(填0)
    private String deleteFlag = "N";        //??(填N)
    private String contactNameOfMainland;       //緊急聯絡人-姓名
    private String contactGenderOfMainland;     //緊急聯絡人-性別
    private String contactTelNoOfMainland;      //緊急聯絡人-電話
    private String contactMobileNoOfMainland;   //緊急聯絡人-手機
    private String contactAddressOfMainland;    //緊急聯絡人-地址
    private String contactTitleOfMainland;      //緊急聯絡人-稱謂
    private List<Traveller> travellerList = new ArrayList<Traveller>();
    /* -- 不必填---------------------------------------------------------------
    private String tourGroupId;         //??
    private String tourType;            //??
    private String entryPortDesc;
    private String entryFlyNo;          //入境航班
    private String exitPortDesc;
    private String exitFlyNo;           //入境航班
    private Byte groupType;             //??
    private String guideName;           //導遊(本島)名字
    private String guideTelNo;          //導遊(本島)電話
    private String guideId;             //導遊(本島)ID
    private String outerIslandGuideName;    //導遊(離島)名字
    private String outerIslandGuideTelNo;   //導遊(離島)電話
    private String outerIslandGuideId;      //導遊(離島)ID
    private String tourCarNo;               //遊覽車車號
    private String tourCarCompany;          //??
    private Short tourCarNoUsedYears;       //??
    private String driverName;              //??
    private String driverTelNo;             //??
    private String receiveBureau;           //??
    private String beforeEntryCity;         //中轉國
    private Short costPerDay;               //接待費用
    private String getPermitType;           //??
    private String issuranceNo;             //旅遊保險證
    private String inconvenientIssuranceNo; //旅遊不便險證
    private String dataDate;                //??
    private String transactionDate;         //??
    private String transactionTime;         //??
    private String tourGourNo;              //??
    private String beforeApplyApproveStatus;        //??
    private String beforeApplyApproveStatusDesc;    //??
    private Date beforeApplyApproveDate;            //??
    private String beforeEntryApproveStatus;        //??
    private String beforeEntryApproveStatusDesc;    //??
    private Date beforeEntryApproveDate;            //??
    private String rewardType;              //??
    private String decideDate;              //??
    private Byte decideMark;                //??
    private String approveCount;            //??
    private String updateDate;
    private String updateTime;
    private String noControlDate;           //??
    private Short noControlCount;           //??
    private String receiptNo;               //??
    private String leaderHasVisa;           //??
    private String associationMark;         //??
    private String additionalMark;          //??
    private String phase;                   //??
    private String applyContactUser;        //申請人
    private String applyContactUserEmail = "vincent@pctravel.com.tw";   //申請人Email
    private Short entryCount;               //??
    private Short exitCount;                //??
    private String cnAgencyContractStatus;  //??
    private String procType;                //??
    private Date tourBureauAgreeTime;       //??
    private String tourBureauAgreeUserId;   //??
    private Date niaAgreeTime;              //??
    private String niaAgreeUserId;          //??
    private String tourBureauP02Status;     //??
    private String tourBureauP03Status;     //??
    private String tourBureauP04Status;     //??
    private String tourBureauP05Status;     //??
    --------------------------------------------------------------------------*/

    public void setId(String paramString){
        this.id = paramString;
    }

    public void setApplyDate(){
        Date dNow = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        this.applyDate = formatter.format(dNow);
    }

    public void setNiaApplyDate(){
        Date dNow = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        this.niaApplyDate = formatter.format(dNow);
    }

    public String getTourStartDate(){
        return this.tourStartDate;
    }

    public void setTourStartDate(String paramString){
        if(paramString.trim() == null || paramString.trim().isEmpty()){
            this.tourStartDate = null;
            this.tourEndDate = null;
            return;
        }
        try{
            String sDate = CommonHelp.dateFormatFix(paramString.trim());
            String eDate = CommonHelp.calculateTourDate(sDate, 14);
            this.tourStartDate = sDate;
            this.tourEndDate = eDate;
        }catch (ParseException e) {}
    }

    public String getTourEndDate(){
        return this.tourEndDate;
    }

    public String getTourName(){
        return this.tourName;
    }

    public void setTourName(String paramString){
        this.tourName = paramString.trim();
    }

    public void setGroupCount(int paramInt){
        this.groupCount = (short)paramInt;
    }

    public void setPermitApplyCount(int paramImt){
        this.permitApplyCount = Integer.toString(paramImt);
    }

    public String getStatus(){
        return this.status;
    }

    public String getContactNameOfMainland(){
        return this.contactNameOfMainland;
    }

    public void setContactNameOfMainland(String paramString){
        this.contactNameOfMainland = CommonHelp.transToTC(paramString.trim());
    }

    public String getContactGenderOfMainland(){
        return this.contactGenderOfMainland;
    }

    public void setContactGenderOfMainland(String paramString){
        String g = CommonHelp.transToTC(paramString.trim());
        if(g.equals("女")){
            this.contactGenderOfMainland = "1";
        }else if(g.equals("男")){
            this.contactGenderOfMainland = "0";
        }else if(g.equals("1")){
            this.contactGenderOfMainland = "1";
        }else if(g.equals("0")){
            this.contactGenderOfMainland = "0";
        }else{
            this.contactGenderOfMainland = null;
        }
    }

    public String getContactGenderOfMainlandMean(){
        if(this.contactGenderOfMainland == null){
            return "";
        }else{
            return this.contactGenderOfMainland.equals("1") ? "女" : "男";
        }
    }

    public String getContactTelNoOfMainland(){
        return this.contactTelNoOfMainland;
    }

    public void setContactTelNoOfMainland(String paramString){
        this.contactTelNoOfMainland = paramString.trim();
    }

    public String getContactMobileNoOfMainland(){
        return this.contactMobileNoOfMainland;
    }

    public void setContactMobileNoOfMainland(String paramString){
        this.contactMobileNoOfMainland = paramString.trim();
    }

    public String getContactAddressOfMainland(){
        return this.contactAddressOfMainland;
    }

    public void setContactAddressOfMainland(String paramString){
        this.contactAddressOfMainland = CommonHelp.transToTC(paramString.trim());
    }

    public String getContactTitleOfMainland(){
        return this.contactTitleOfMainland;
    }

    public void setContactTitleOfMainland(String paramString){
        this.contactTitleOfMainland = CommonHelp.transToTC(paramString.trim());
    }

    public List<Traveller> getTravellerList(){
        return this.travellerList;
    }

    public void setTravellerList(List<Traveller> lt){
        this.travellerList = lt;
    }

    public void removeTraveller(Traveller tr){
        this.travellerList.remove(tr);
    }

    public void resetSeqNo(){
      int i = 0;
      for(Traveller tr : this.travellerList){
          tr.setSeqNo(i);
          i++;
      }
    }

    public String getInsertStr(){
      String insertStr = null;
      try {
          StringBuffer nameSb = new StringBuffer();
          StringBuffer valueSb = new StringBuffer();
          Class<?> objClass = this.getClass();
          Field[] fields = objClass.getDeclaredFields();
          int i = 1, fieldsLength = fields.length;
          for(Field field : fields) {
              String name = field.getName();
              Object value = field.get(this);
              String type = ((Class) field.getType()).getSimpleName();

              if(value == null || isExclude(name)){
                  continue;
              }

              nameSb.append(name + ", ");
              if(type.equals("String")){
                  valueSb.append("'" + value.toString() + "', ");
              }else{
                  valueSb.append(value.toString() + ", ");
              }
              i++;
          }

          insertStr = String.format("insert into %s(%s CreateDate, LastUpdateTime) "
              + "values(%s current_timestamp, current_timestamp)",
              objClass.getSimpleName(), nameSb.toString(), valueSb.toString());

      } catch(Exception e) {
          CommonHelp.logger.log(Level.ERROR, String.format("[TravelGroup][%s] 建立insertSQL失敗。", this.tourName), e);
          e.printStackTrace();
          return null;
      }
      return insertStr;
    }

    private boolean isExclude(String s){
        String[] exList = {"travellerList", "logger"};
        for(String ex : exList){
            if(s.equals(ex)){
                return true;
            }
        }
        return false;
    }

    public List<ErrMsg> getErrMsgList(){
        List<ErrMsg> errList = new ArrayList<ErrMsg>();
        if(this.travellerList.isEmpty()){
            errList.add(new ErrMsg("無法解析申請資料，旅客人數為0。", 0, "[TravelGroup]", String.format("\"%s\"", this.tourName)));
        }

        if(this.tourName == null || this.tourName.isEmpty()){ errList.add(new ErrMsg("行程名稱未填寫。", 1));
        }else if(this.tourName.length() > 100){
            errList.add(new ErrMsg("行程名稱格式有誤。", 0, "[TravelGroup]", String.format("\"%s\"", this.tourName)));
        }

        if(this.tourStartDate == null || this.tourStartDate.isEmpty()){ errList.add(new ErrMsg("入境日期未填寫。", 1));
        }else if(!this.tourStartDate.matches("^(19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|1\\d|2\\d|3[0-1])$")){
            errList.add(new ErrMsg("入境日期格式錯誤。", 0, "[TravelGroup]", String.format("\"%s\"", this.tourStartDate)));
        }

        if(this.contactNameOfMainland == null || this.contactNameOfMainland.isEmpty()){ errList.add(new ErrMsg("緊急聯絡人姓名未填寫。", 1));
        }else if(this.contactNameOfMainland.length() > 127){
            errList.add(new ErrMsg("緊急聯絡人姓名格式錯誤。", 0, "[TravelGroup]", String.format("\"%s\"", this.contactNameOfMainland)));
        }

        if(this.contactTitleOfMainland == null || this.contactTitleOfMainland.isEmpty()){ errList.add(new ErrMsg("緊急聯絡人關係未填寫。", 1));
        }else if(this.contactTitleOfMainland.length() > 127){
            errList.add(new ErrMsg("緊急聯絡人關係格式錯誤。", 0, "[TravelGroup]", String.format("\"%s\"", this.contactTitleOfMainland)));
        }

        if(this.contactGenderOfMainland == null){ errList.add(new ErrMsg("緊急聯絡人性別未填寫。", 1)); }

        if(this.contactMobileNoOfMainland == null || this.contactMobileNoOfMainland.isEmpty()){ errList.add(new ErrMsg("緊急聯絡人手機未填寫。", 1));
        }else if(this.contactMobileNoOfMainland.length() > 127){
            errList.add(new ErrMsg("緊急聯絡人手機格式錯誤。", 0, "[TravelGroup]", String.format("\"%s\"", this.contactMobileNoOfMainland)));
        }

        if(this.contactTelNoOfMainland == null || this.contactTelNoOfMainland.isEmpty()){ errList.add(new ErrMsg("緊急聯絡人電話未填寫。", 1));
        }else if(this.contactTelNoOfMainland.length() > 127){
            errList.add(new ErrMsg("緊急聯絡人電話格式錯誤。", 0, "[TravelGroup]", String.format("\"%s\"", this.contactTelNoOfMainland)));
        }

        if(this.contactAddressOfMainland == null || this.contactAddressOfMainland.isEmpty()){ errList.add(new ErrMsg("緊急聯絡人地址未填寫。", 1));
        }else if(this.contactAddressOfMainland.length() > 127){
            errList.add(new ErrMsg("緊急聯絡人地址格式錯誤。", 0, "[TravelGroup]", String.format("\"%s\"", this.contactAddressOfMainland)));
        }
        return errList;
    }

    /**
     * 回傳此TravelGroup的資料驗證結果。
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

}