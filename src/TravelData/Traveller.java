package TravelData;

import java.lang.reflect.Field;

public class Traveller {
    private String id;                  //@必填
    private String travelGroupId;       //@必填
    private Integer version = 4;        //?? @必填(填4)
    private Short seqNo;                //順序
    private String passportNo;          //大陸來臺通行證號
//    private String tourGroupId;
    private String applyQualification = "B";    //申請資格A~F 預設B 可能要自己填@
    private String applyType = "7";             //申請類別(大陸地區人民來臺觀光(個人旅遊))
//    private String leaderPermitNo;            //收件號(不用填)
    private String applyReason = "192";         //申請原因(個人都是192)
    private String decideMark = "0";            //??(填0)
//    private String invalidMark;                 //??(不用填)
    private String chineseName;
    private String englishName;
    private String gender;              //可能要自己填@
    private String birthDate;
    private Byte birthPlaceCode = 4;    //??(填4)
    private Short birthPlace1;          //出生地 省(市) 可能要自己填@
    private String birthPlace2;         //出生地 縣(市) 可能要自己填@
    private Byte education;             //教育程度 可能要自己填@
//    private String educationDesc;       //(不用填)
    private Byte occupation = 16;            //職業類別 可能要自己填@
    private String occupationDesc;      //職業
    private String address;             //居住地
//    private String twAddress;           //在臺緊急連絡親友地址
//    private String twTelNo;             //在臺緊急連絡親友電話
    private String personId;            //身分證號
//    private String beenCnPartyJob;          //(不用填)
//    private String beenCnPartyJobDesc;      //(不用填)
//    private String cnPartyJob;              //(不用填)
//    private String cnPartyJobDesc;          //(不用填)
    private String partnerOfTaiwan;         //為臺灣人民之配偶
    private String memberType = "M";        //身份(G:領隊、M:成員)
    private String applyVisa = "1";         //單次證申請 (填1)@必填
//    private String resume;                  //經歷(不用填)
    private String kinsfolk;                //(KINSFOLK) 隨行親友姓名
    private String activeStatus = "0";      //??(填0)
    private String completion = "w";              //資料完成度(w/s) @必填
//    private String receiveNo;               //??(不用填)
    private String deleteFlag = "N";        //??(填N)
//    private String withParent;              //父母是否同行(不用填)
//    private String requiredDocsStatus;      //??(不用填)
//    private Short requiredDocPages;         //??(不用填)
    private String passportExpiryDate;      //通行證有效日期
//    private String cnLeaderExpiryDate;      //領隊證有效期(不用填)
//    private String cnLeaderNo;              //領隊證編號(不用填)
    private String relativeTitle;           //隨行親友稱謂
//    private Date revokeDate;                //??(不用填)
//    private String tourBureauApplyMark;     //??(不用填)
    private String livingCity;              //@居住城市 可能要自己填
//    private TravelTourGroup travelTourGroup;
//    private static Object transients;
//    private static Object mapping;
//    private static Object constraints;
//    private static Object hasMany;
//    private static Object belongsTo;
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String paramString)
  {
    this.id = paramString;
  }   
  
  public String getTravelGroupId()
  {
    return this.travelGroupId;
  }
  
  public void setTravelGroupId(String paramString)
  {
    this.travelGroupId = paramString;
  }   
  
  public Integer getVersion()
  {
    return this.version;
  }
    
  public Short getSeqNo()
  {
    return this.seqNo;
  }
  
  public void setSeqNo(Short paramShort)
  {
    this.seqNo = paramShort;
  }
  
  public String getPassportNo()
  {
    return this.passportNo;
  }
  
  public void setPassportNo(String paramString)
  {
    this.passportNo = paramString.trim();
  }
  
//  public String getTourGroupId()
//  {
//    return this.tourGroupId;
//  }
//  
//  public void setTourGroupId(String paramString)
//  {
//    this.tourGroupId = paramString;
//  }
  
  public String getApplyQualification()
  {
    return this.applyQualification;
  }
  
  public void setApplyQualification(String paramString)
  {
    this.applyQualification = paramString;
  }
  
  public String getApplyType()
  {
    return this.applyType;
  }
  
  public void setApplyType(String paramString)
  {
    this.applyType = paramString;
  }
  
//  public String getLeaderPermitNo()
//  {
//    return this.leaderPermitNo;
//  }
//  
//  public void setLeaderPermitNo(String paramString)
//  {
//    this.leaderPermitNo = paramString;
//  }
  
  public String getApplyReason()
  {
    return this.applyReason;
  }
  
  public void setApplyReason(String paramString)
  {
    this.applyReason = paramString;
  }
  
  public String getDecideMark()
  {
    return this.decideMark;
  }
  
//  public void setDecideMark(String paramString)
//  {
//    this.decideMark = paramString;
//  }
  
//  public String getInvalidMark()
//  {
//    return this.invalidMark;
//  }
//  
//  public void setInvalidMark(String paramString)
//  {
//    this.invalidMark = paramString;
//  }
  
  public String getChineseName()
  {
    return this.chineseName;
  }
  
  public void setChineseName(String paramString)
  {
    this.chineseName = paramString.trim();
  }
  
  public String getEnglishName()
  {
    return this.englishName;
  }
  
  public void setEnglishName(String paramString)
  {
    this.englishName = paramString.trim();
  }
  
  public String getGender()
  {
    return this.gender;
  }
  
  public void setGender(String paramString)
  {
    if(paramString.trim().equals("女")){
      this.gender = "1";
    }else if(paramString.trim().equals("男")){
      this.gender = "0";
    }
  }
  
  public String getBirthDate()
  {
    return this.birthDate;
  }
  
  public void setBirthDate(String paramString)
  {
    this.birthDate = paramString.trim();
  }
  
  public Byte getBirthPlaceCode()
  {
    return this.birthPlaceCode;
  }
  
//  public void setBirthPlaceCode(Byte paramByte)
//  {
//    this.birthPlaceCode = paramByte;
//  }
  
  public Short getBirthPlace1()
  {
    return this.birthPlace1;
  }
  
  public void setBirthPlace1(Short paramShort)
  {
    this.birthPlace1 = paramShort;
  }
  
  public String getBirthPlace2()
  {
    return this.birthPlace2;
  }
  
  public void setBirthPlace2(String paramString)
  {
    this.birthPlace2 = paramString;
  }
  
  public Byte getEducation()
  {
    return this.education;
  }
  
  public void setEducation(String paramString)
  {
      switch(paramString.trim()){
          case "博士":
              this.education = 1;
              break;
          case "碩士":
              this.education = 2;
              break;
          case "大學":
              this.education = 3;
              break;
          case "專科":
              this.education = 4;
              break;
          case "五專":
              this.education = 5;
              break;
          case "高中":
              this.education = 6;
              break;
          case "國中":
              this.education = 7;
              break;
          case "國小":
              this.education = 8;
              break;
          case "無":
              this.education = 9;
              break;
      }
    
  }

//  public String getEducationDesc()
//  {
//    return this.educationDesc;
//  }
//  
//  public void setEducationDesc(String paramString)
//  {
//    this.educationDesc = paramString;
//  }
  public Byte getOccupation()
  {
    return this.occupation;
  }
  
  public void setOccupation(Byte paramByte)
  {
    this.occupation = paramByte;
  }
  
  public String getOccupationDesc()
  {
    return this.occupationDesc;
  }
  
  public void setOccupationDesc(String paramString)
  {
    this.occupationDesc = paramString.trim();
  }
  
  public String getAddress()
  {
    return this.address;
  }
  
  public void setAddress(String paramString)
  {
    this.address = paramString.trim();
  }
  
//  public String getTwAddress()
//  {
//    return this.twAddress;
//  }
//  
//  public void setTwAddress(String paramString)
//  {
//    this.twAddress = paramString;
//  }
//  
//  public String getTwTelNo()
//  {
//    return this.twTelNo;
//  }
//  
//  public void setTwTelNo(String paramString)
//  {
//    this.twTelNo = paramString;
//  }
  
  public String getPersonId()
  {
    return this.personId;
  }
  
  public void setPersonId(String paramString)
  {
    this.personId = paramString.trim();
  }
  
//  public String getBeenCnPartyJob()
//  {
//    return this.beenCnPartyJob;
//  }
//  
//  public void setBeenCnPartyJob(String paramString)
//  {
//    this.beenCnPartyJob = paramString;
//  }
//  
//  public String getBeenCnPartyJobDesc()
//  {
//    return this.beenCnPartyJobDesc;
//  }
//  
//  public void setBeenCnPartyJobDesc(String paramString)
//  {
//    this.beenCnPartyJobDesc = paramString;
//  }
  
//  public String getCnPartyJob()
//  {
//    return this.cnPartyJob;
//  }
//  
//  public void setCnPartyJob(String paramString)
//  {
//    this.cnPartyJob = paramString;
//  }
//  
//  public String getCnPartyJobDesc()
//  {
//    return this.cnPartyJobDesc;
//  }
//  
//  public void setCnPartyJobDesc(String paramString)
//  {
//    this.cnPartyJobDesc = paramString;
//  }
  
  public String getPartnerOfTaiwan()
  {
    return this.partnerOfTaiwan;
  }
  
  public void setPartnerOfTaiwan(String paramString)
  {
    if(paramString.trim().equals("是")){
        this.partnerOfTaiwan = "1";
    }else if(paramString.trim().equals("否")){
        this.partnerOfTaiwan = "0";
    }
  }
  
  public String getMemberType()
  {
    return this.memberType;
  }
  
  public void setMemberType(String paramString)
  {
    this.memberType = paramString;
  }
  
  public String getApplyVisa()
  {
    return this.applyVisa;
  }
  
  public void setApplyVisa(String paramString)
  {
    this.applyVisa = paramString;
  }
  
//  public String getResume()
//  {
//    return this.resume;
//  }
//  
//  public void setResume(String paramString)
//  {
//    this.resume = paramString;
//  }
  
  public String getRelative()
  {
    return this.kinsfolk;
  }
  
  public void setRelative(String paramString)
  {
    this.kinsfolk = paramString.trim();
  }
  
  public String getActiveStatus()
  {
    return this.activeStatus;
  }
  
//  public void setActiveStatus(String paramString)
//  {
//    this.activeStatus = paramString;
//  }
  
  public String getCompletion()
  {
    return this.completion;
  }
  
  public void setCompletion(String paramString)
  {
    this.completion = paramString;
  }
  
//  public String getReceiveNo()
//  {
//    return this.receiveNo;
//  }
//  
//  public void setReceiveNo(String paramString)
//  {
//    this.receiveNo = paramString;
//  }
  
  public String getDeleteFlag()
  {
    return this.deleteFlag;
  }
  
//  public void setDeleteFlag(String paramString)
//  {
//    this.deleteFlag = paramString;
//  }
  
//  public String getWithParent()
//  {
//    return this.withParent;
//  }
//  
//  public void setWithParent(String paramString)
//  {
//    this.withParent = paramString;
//  }
//  
//  public String getRequiredDocsStatus()
//  {
//    return this.requiredDocsStatus;
//  }
//  
//  public void setRequiredDocsStatus(String paramString)
//  {
//    this.requiredDocsStatus = paramString;
//  }
//  
//  public Short getRequiredDocPages()
//  {
//    return this.requiredDocPages;
//  }
//  
//  public void setRequiredDocPages(Short paramShort)
//  {
//    this.requiredDocPages = paramShort;
//  }
  
  public String getPassportExpiryDate()
  {
    return this.passportExpiryDate;
  }
  
  public void setPassportExpiryDate(String paramString)
  {
    this.passportExpiryDate = paramString.trim();
  }
  
//  public String getCnLeaderExpiryDate()
//  {
//    return this.cnLeaderExpiryDate;
//  }
//  
//  public void setCnLeaderExpiryDate(String paramString)
//  {
//    this.cnLeaderExpiryDate = paramString;
//  }
//  
//  public String getCnLeaderNo()
//  {
//    return this.cnLeaderNo;
//  }
//  
//  public void setCnLeaderNo(String paramString)
//  {
//    this.cnLeaderNo = paramString;
//  }
  
  public String getRelativeTitle()
  {
    return this.relativeTitle;
  }
  
  public void setRelativeTitle(String paramString)
  {
    this.relativeTitle = paramString.trim();
  }
  
//  public Date getRevokeDate()
//  {
//    return this.revokeDate;
//  }
//  
//  public void setRevokeDate(Date paramDate)
//  {
//    this.revokeDate = paramDate;
//  }
//  
//  public String getTourBureauApplyMark()
//  {
//    return this.tourBureauApplyMark;
//  }
//  
//  public void setTourBureauApplyMark(String paramString)
//  {
//    this.tourBureauApplyMark = paramString;
//  }
  
  public String getLivingCity()
  {
    return this.livingCity;
  }
  
  public void setLivingCity(String paramString)
  {
      String[] liveCityArr = {"北京", "上海", "廈門", "天津", "重慶", "南京", "廣州", "杭州", "成都", "濟南", "西安", "福州", "深圳" };
      String lc = paramString.trim();
      if(lc.length() >= 0){
          for(int i = 0; i < liveCityArr.length; i++){
              if(lc.indexOf(liveCityArr[i]) >= 0){
                  this.livingCity = "F" + (i+1);
              }
          }
      }
  }
  
  public boolean isValidTraveller(){
      String[] chkArr = {"", "無", "如有生僻字請注明", null};
      for(String s : chkArr){
          if(this.chineseName.equals(s)){ return false; }
      }
      if(!this.birthDate.matches("^(19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|1\\d|2\\d|3[0-1])$")){
          return false;
      }
      if(!this.passportNo.matches("^T\\d{8}$")){
          return false;
      }
      if(!this.personId.matches("^\\d{18}$")){
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
            
            if(value == null){ 
                System.out.println(name + " is NULL.");
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
        e.printStackTrace();
        return null;
    }
    return insertStr;
  }
  
}
