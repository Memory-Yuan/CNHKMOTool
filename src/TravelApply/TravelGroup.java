package TravelApply;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TravelGroup {
//    private static final long serialVersionUID;
//    private String tourGroupId;         //??(不用填)
    private String id;                  //@必填
    private Integer version = 2;        //?? @必填(填2)
    private String travelAgencyNo = ""; //?? @必填
    private String cnTravelAgencyNo = "L-ZJ-CJ00055";    //組團社代號@
    private String cnTravelAgencyName = "浙江商務國際旅行社有限公司";  //組團社名稱@
//    private String tourType;            //??(不用填)
    private String applyDate;           //申請日期 @必填
    private String niaApplyDate;        //申請日期
    private String tourStartDate;       //入境日期
    private String tourEndDate;         //出境日期
    private Byte entryPort = 1;         //入境地點(桃園機場)
//    private String entryPortDesc;       //(不用填)
//    private String entryFlyNo;          //入境航班(不用填)
    private Byte exitPort = 1;          //出境地點(桃園機場)
//    private String exitPortDesc;        //(不用填)
//    private String exitFlyNo;           //入境航班(不用填)
    private String tourName;            //行程名稱
//    private Byte groupType;             //??(不用填)
    private Short groupCount;           //旅客人數
//    private String guideName;           //導遊(本島)名字(不用填)
//    private String guideTelNo;          //導遊(本島)電話(不用填)
//    private String guideId;             //導遊(本島)ID(不用填)
//    private String outerIslandGuideName;    //導遊(離島)名字(不用填)
//    private String outerIslandGuideTelNo;   //導遊(離島)電話(不用填)
//    private String outerIslandGuideId;      //導遊(離島)ID(不用填)
    private String tourCarType = "1";       //??(填1)
//    private String tourCarNo;               //遊覽車車號(不用填)
//    private String tourCarCompany;          //??(不用填)
//    private Short tourCarNoUsedYears;       //??(不用填)
//    private String driverName;              //??(不用填)
//    private String driverTelNo;             //??(不用填)
//    private String receiveBureau;           //??(不用填)
//    private String beforeEntryCity;         //中轉國(不用填)
//    private Short costPerDay;               //接待費用(不用填)
//    private String getPermitType;           //??(不用填)
//    private String issuranceNo;             //旅遊保險證(不用填)
//    private String inconvenientIssuranceNo; //旅遊不便險證(不用填)
//    private String dataDate;                //??(不用填)
//    private String transactionDate;         //??(不用填)
//    private String transactionTime;         //??(不用填)
//    private String tourGourNo;              //??(不用填)
//    private String beforeApplyApproveStatus;        //??(不用填)
//    private String beforeApplyApproveStatusDesc;    //??(不用填)
//    private Date beforeApplyApproveDate;            //??(不用填)
//    private String beforeEntryApproveStatus;        //??(不用填)
//    private String beforeEntryApproveStatusDesc;    //??(不用填)
//    private Date beforeEntryApproveDate;            //??(不用填)
    private String applyType = "D";         //大陸來台觀光:D、小三通:C @必填
    private String itemType = "3";          //申辦項目(大陸地區人民來臺觀光(個人旅遊)) @必填
//    private String rewardType;              //??(不用填)
    private String tourApplyType = "7";     //(大陸地區人民來臺觀光(個人旅遊))
//    private String decideDate;              //??(不用填)
//    private Byte decideMark;                //??(不用填)
    private String permitApplyCount;        //人數(不含領隊)
//    private String approveCount;            //??(不用填)
    private String activeStatus = "0";      //??(填0)
//    private String updateDate;              //(不用填)
//    private String updateTime;              //(不用填)
//    private String noControlDate;           //??(不用填)
    private Integer noControlSeqNo = 0;     //??(填0)
//    private Short noControlCount;           //??(不用填)
//    private String receiptNo;               //??(不用填)
//    private String leaderHasVisa;           //??(不用填)
//    private String associationMark;         //??(不用填)
//    private String additionalMark;          //??(不用填)
//    private String phase;                   //??(不用填)
    private String status = "T10";            //??(填T10)
    private String paymentRemark = "N";     //??(填N)
//    private String applyContactUser;        //申請人(不用填)
//    private String applyContactUserEmail = "vincent@pctravel.com.tw";   //申請人Email(不用填)
//    private Short entryCount;               //??(不用填)
//    private Short exitCount;                //??(不用填)
//    private String cnAgencyContractStatus;  //??(不用填)
    private Integer procOrder = 0;          //??(填0)
//    private String procType;                //??(不用填)
    private String deleteFlag = "N";        //??(填N)
//    private Date tourBureauAgreeTime;       //??(不用填)
//    private String tourBureauAgreeUserId;   //??(不用填)
//    private Date niaAgreeTime;              //??(不用填)
//    private String niaAgreeUserId;          //??(不用填)
//    private String tourBureauP02Status;     //??(不用填)
//    private String tourBureauP03Status;     //??(不用填)
//    private String tourBureauP04Status;     //??(不用填)
//    private String tourBureauP05Status;     //??(不用填)
    private String contactNameOfMainland;       //緊急聯絡人-姓名
    private String contactGenderOfMainland;     //緊急聯絡人-性別
    private String contactTelNoOfMainland;      //緊急聯絡人-電話
    private String contactMobileNoOfMainland;   //緊急聯絡人-手機
    private String contactAddressOfMainland;    //緊急聯絡人-地址
    private String contactTitleOfMainland;      //緊急聯絡人-稱謂
//    private Set<TravelTourAttach> travelTourAttachs;
//    private Set<TravelTour> travelTours;
//    private Set<Traveller> travellers;
//    private static Object transients;
//    private static Object mapping;
//    private static Object constraints;
//    private static Object hasMany;

//  public String getTourGroupId()
//  {
//    return this.tourGroupId;
//  }
//  
//  public void setTourGroupId(String paramString)
//  {
//    this.tourGroupId = paramString;
//  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String paramString)
  {
    this.id = paramString;
  }   
    
  public Integer getVersion()
  {
    return this.version;
  }
  
//  public void setVersion(Integer paramInteger)
//  {
//    this.version = paramInteger;
//  }
  
  public String getTravelAgencyNo()
  {
    return this.travelAgencyNo;
  }
//  
//  public void setTravelAgencyNo(String paramString)
//  {
//    this.travelAgencyNo = paramString;
//  }
  
  public String getCnTravelAgencyNo()
  {
    return this.cnTravelAgencyNo;
  }
  
  public void setCnTravelAgencyNo(String paramString)
  {
    this.cnTravelAgencyNo = paramString;
  }
  
  public String getCnTravelAgencyName()
  {
    return this.cnTravelAgencyName;
  }
  
  public void setCnTravelAgencyName(String paramString)
  {
    this.cnTravelAgencyName = paramString;
  }
  
//  public String getTourType()
//  {
//    return this.tourType;
//  }
//  
//  public void setTourType(String paramString)
//  {
//    this.tourType = paramString;
//  }
  
  public String getApplyDate()
  {
    return this.applyDate;
  }
  
  public void setApplyDate()
  {
    Date dNow = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
    this.applyDate = formatter.format(dNow);
  }
  
  public String getNiaApplyDate()
  {
    return this.niaApplyDate;
  }
  
  public void setNiaApplyDate()
  {
    Date dNow = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
    this.niaApplyDate = formatter.format(dNow);
  }
  
  public String getTourStartDate()
  {
    return this.tourStartDate;
  }
  
  public void setTourStartDate(String paramString)
  {
    this.tourStartDate = paramString.trim();
  }
  
  public String getTourEndDate()
  {
    return this.tourEndDate;
  }
  
  public void setTourEndDate(String paramString)
  {
    this.tourEndDate = paramString.trim();
  }

  public Byte getEntryPort()
  {
    return this.entryPort;
  }
  
//  public void setEntryPort(Byte paramByte)
//  {
//    this.entryPort = paramByte;
//  }
  
//  public String getEntryPortDesc()
//  {
//    return this.entryPortDesc;
//  }
//  
//  public void setEntryPortDesc(String paramString)
//  {
//    this.entryPortDesc = paramString;
//  }
//  
//  public String getEntryFlyNo()
//  {
//    return this.entryFlyNo;
//  }
//  
//  public void setEntryFlyNo(String paramString)
//  {
//    this.entryFlyNo = paramString;
//  }
  
  public Byte getExitPort()
  {
    return this.exitPort;
  }
  
//  public void setExitPort(Byte paramByte)
//  {
//    this.exitPort = paramByte;
//  }
  
//  public String getExitPortDesc()
//  {
//    return this.exitPortDesc;
//  }
//  
//  public void setExitPortDesc(String paramString)
//  {
//    this.exitPortDesc = paramString;
//  }
//  
//  public String getExitFlyNo()
//  {
//    return this.exitFlyNo;
//  }
//  
//  public void setExitFlyNo(String paramString)
//  {
//    this.exitFlyNo = paramString;
//  }
  
  public String getTourName()
  {
    return this.tourName;
  }
  
  public void setTourName(String paramString)
  {
    this.tourName = paramString.trim();
  }
  
//  public Byte getGroupType()
//  {
//    return this.groupType;
//  }
//  
//  public void setGroupType(Byte paramByte)
//  {
//    this.groupType = paramByte;
//  }
//  
  public Short getGroupCount()
  {
    return this.groupCount;
  }
  
  public void setGroupCount(Short paramShort)
  {
    this.groupCount = paramShort;
  }
//  
//  public String getGuideName()
//  {
//    return this.guideName;
//  }
//  
//  public void setGuideName(String paramString)
//  {
//    this.guideName = paramString;
//  }
//  
//  public String getGuideTelNo()
//  {
//    return this.guideTelNo;
//  }
//  
//  public void setGuideTelNo(String paramString)
//  {
//    this.guideTelNo = paramString;
//  }
//  
//  public String getGuideId()
//  {
//    return this.guideId;
//  }
//  
//  public void setGuideId(String paramString)
//  {
//    this.guideId = paramString;
//  }
//  
//  public String getOuterIslandGuideName()
//  {
//    return this.outerIslandGuideName;
//  }
//  
//  public void setOuterIslandGuideName(String paramString)
//  {
//    this.outerIslandGuideName = paramString;
//  }
//  
//  public String getOuterIslandGuideTelNo()
//  {
//    return this.outerIslandGuideTelNo;
//  }
//  
//  public void setOuterIslandGuideTelNo(String paramString)
//  {
//    this.outerIslandGuideTelNo = paramString;
//  }
//  
//  public String getOuterIslandGuideId()
//  {
//    return this.outerIslandGuideId;
//  }
//  
//  public void setOuterIslandGuideId(String paramString)
//  {
//    this.outerIslandGuideId = paramString;
//  }
  
  public String getTourCarType()
  {
    return this.tourCarType;
  }
  
//  public void setTourCarType(String paramString)
//  {
//    this.tourCarType = paramString;
//  }
  
//  public String getTourCarNo()
//  {
//    return this.tourCarNo;
//  }
//  
//  public void setTourCarNo(String paramString)
//  {
//    this.tourCarNo = paramString;
//  }
//  
//  public String getTourCarCompany()
//  {
//    return this.tourCarCompany;
//  }
//  
//  public void setTourCarCompany(String paramString)
//  {
//    this.tourCarCompany = paramString;
//  }
//  
//  public Short getTourCarNoUsedYears()
//  {
//    return this.tourCarNoUsedYears;
//  }
//  
//  public void setTourCarNoUsedYears(Short paramShort)
//  {
//    this.tourCarNoUsedYears = paramShort;
//  }
//  
//  public String getDriverName()
//  {
//    return this.driverName;
//  }
//  
//  public void setDriverName(String paramString)
//  {
//    this.driverName = paramString;
//  }
//  
//  public String getDriverTelNo()
//  {
//    return this.driverTelNo;
//  }
//  
//  public void setDriverTelNo(String paramString)
//  {
//    this.driverTelNo = paramString;
//  }
//  
//  public String getReceiveBureau()
//  {
//    return this.receiveBureau;
//  }
//  
//  public void setReceiveBureau(String paramString)
//  {
//    this.receiveBureau = paramString;
//  }
//  
//  public String getBeforeEntryCity()
//  {
//    return this.beforeEntryCity;
//  }
//  
//  public void setBeforeEntryCity(String paramString)
//  {
//    this.beforeEntryCity = paramString;
//  }
//  
//  public Short getCostPerDay()
//  {
//    return this.costPerDay;
//  }
//  
//  public void setCostPerDay(Short paramShort)
//  {
//    this.costPerDay = paramShort;
//  }
//  
//  public String getGetPermitType()
//  {
//    return this.getPermitType;
//  }
//  
//  public void setGetPermitType(String paramString)
//  {
//    this.getPermitType = paramString;
//  }
//  
//  public String getIssuranceNo()
//  {
//    return this.issuranceNo;
//  }
//  
//  public void setIssuranceNo(String paramString)
//  {
//    this.issuranceNo = paramString;
//  }
//  
//  public String getInconvenientIssuranceNo()
//  {
//    return this.inconvenientIssuranceNo;
//  }
//  
//  public void setInconvenientIssuranceNo(String paramString)
//  {
//    this.inconvenientIssuranceNo = paramString;
//  }
//  
//  public String getDataDate()
//  {
//    return this.dataDate;
//  }
//  
//  public void setDataDate(String paramString)
//  {
//    this.dataDate = paramString;
//  }
//  
//  public String getTransactionDate()
//  {
//    return this.transactionDate;
//  }
//  
//  public void setTransactionDate(String paramString)
//  {
//    this.transactionDate = paramString;
//  }
//  
//  public String getTransactionTime()
//  {
//    return this.transactionTime;
//  }
//  
//  public void setTransactionTime(String paramString)
//  {
//    this.transactionTime = paramString;
//  }
  
//  public String getTourGourNo()
//  {
//    return this.tourGourNo;
//  }
//  
//  public void setTourGourNo(String paramString)
//  {
//    this.tourGourNo = paramString;
//  }
//  
//  public String getBeforeApplyApproveStatus()
//  {
//    return this.beforeApplyApproveStatus;
//  }
//  
//  public void setBeforeApplyApproveStatus(String paramString)
//  {
//    this.beforeApplyApproveStatus = paramString;
//  }
//  
//  public String getBeforeApplyApproveStatusDesc()
//  {
//    return this.beforeApplyApproveStatusDesc;
//  }
//  
//  public void setBeforeApplyApproveStatusDesc(String paramString)
//  {
//    this.beforeApplyApproveStatusDesc = paramString;
//  }
//  
//  public Date getBeforeApplyApproveDate()
//  {
//    return this.beforeApplyApproveDate;
//  }
//  
//  public void setBeforeApplyApproveDate(Date paramDate)
//  {
//    this.beforeApplyApproveDate = paramDate;
//  }
//  
//  public String getBeforeEntryApproveStatus()
//  {
//    return this.beforeEntryApproveStatus;
//  }
//  
//  public void setBeforeEntryApproveStatus(String paramString)
//  {
//    this.beforeEntryApproveStatus = paramString;
//  }
//  
//  public String getBeforeEntryApproveStatusDesc()
//  {
//    return this.beforeEntryApproveStatusDesc;
//  }
//  
//  public void setBeforeEntryApproveStatusDesc(String paramString)
//  {
//    this.beforeEntryApproveStatusDesc = paramString;
//  }
//  
//  public Date getBeforeEntryApproveDate()
//  {
//    return this.beforeEntryApproveDate;
//  }
//  
//  public void setBeforeEntryApproveDate(Date paramDate)
//  {
//    this.beforeEntryApproveDate = paramDate;
//  }
  
  public String getApplyType()
  {
    return this.applyType;
  }
  
//  public void setApplyType(String paramString)
//  {
//    this.applyType = paramString;
//  }
  
  public String getItemType()
  {
    return this.itemType;
  }
  
//  public void setItemType(String paramString)
//  {
//    this.itemType = paramString;
//  }
  
//  public String getRewardType()
//  {
//    return this.rewardType;
//  }
//  
//  public void setRewardType(String paramString)
//  {
//    this.rewardType = paramString;
//  }
  
  public String getTourApplyType()
  {
    return this.tourApplyType;
  }
  
//  public void setTourApplyType(String paramString)
//  {
//    this.tourApplyType = paramString;
//  }
  
//  public String getDecideDate()
//  {
//    return this.decideDate;
//  }
//  
//  public void setDecideDate(String paramString)
//  {
//    this.decideDate = paramString;
//  }
//  
//  public Byte getDecideMark()
//  {
//    return this.decideMark;
//  }
//  
//  public void setDecideMark(Byte paramByte)
//  {
//    this.decideMark = paramByte;
//  }
  
  public String getPermitApplyCount()
  {
    return this.permitApplyCount;
  }
  
  public void setPermitApplyCount(String paramString)
  {
    this.permitApplyCount = paramString;
  }
  
//  public String getApproveCount()
//  {
//    return this.approveCount;
//  }
//  
//  public void setApproveCount(String paramString)
//  {
//    this.approveCount = paramString;
//  }
  
  public String getActiveStatus()
  {
    return this.activeStatus;
  }
  
//  public void setActiveStatus(String paramString)
//  {
//    this.activeStatus = paramString;
//  }
  
//  public String getUpdateDate()
//  {
//    return this.updateDate;
//  }
//  
//  public void setUpdateDate(String paramString)
//  {
//    this.updateDate = paramString;
//  }
//  
//  public String getUpdateTime()
//  {
//    return this.updateTime;
//  }
//  
//  public void setUpdateTime(String paramString)
//  {
//    this.updateTime = paramString;
//  }
//  
//  public String getNoControlDate()
//  {
//    return this.noControlDate;
//  }
//  
//  public void setNoControlDate(String paramString)
//  {
//    this.noControlDate = paramString;
//  }
  
  public Integer getNoControlSeqNo()
  {
    return this.noControlSeqNo;
  }
  
//  public void setNoControlSeqNo(Integer paramInteger)
//  {
//    this.noControlSeqNo = paramInteger;
//  }
  
//  public Short getNoControlCount()
//  {
//    return this.noControlCount;
//  }
//  
//  public void setNoControlCount(Short paramShort)
//  {
//    this.noControlCount = paramShort;
//  }
//  
//  public String getReceiptNo()
//  {
//    return this.receiptNo;
//  }
//  
//  public void setReceiptNo(String paramString)
//  {
//    this.receiptNo = paramString;
//  }
//  
//  public String getLeaderHasVisa()
//  {
//    return this.leaderHasVisa;
//  }
//  
//  public void setLeaderHasVisa(String paramString)
//  {
//    this.leaderHasVisa = paramString;
//  }
//  
//  public String getAssociationMark()
//  {
//    return this.associationMark;
//  }
//  
//  public void setAssociationMark(String paramString)
//  {
//    this.associationMark = paramString;
//  }
//  
//  public String getAdditionalMark()
//  {
//    return this.additionalMark;
//  }
//  
//  public void setAdditionalMark(String paramString)
//  {
//    this.additionalMark = paramString;
//  }
//  
//  public String getPhase()
//  {
//    return this.phase;
//  }
//  
//  public void setPhase(String paramString)
//  {
//    this.phase = paramString;
//  }
  
  public String getStatus()
  {
    return this.status;
  }
  
//  public void setStatus(String paramString)
//  {
//    this.status = paramString;
//  }
  
  public String getPaymentRemark()
  {
    return this.paymentRemark;
  }
  
//  public void setPaymentRemark(String paramString)
//  {
//    this.paymentRemark = paramString;
//  }
  
//  public String getApplyContactUser()
//  {
//    return this.applyContactUser;
//  }
  
//  public void setApplyContactUser(String paramString)
//  {
//    this.applyContactUser = paramString;
//  }
  
//  public String getApplyContactUserEmail()
//  {
//    return this.applyContactUserEmail;
//  }
  
//  public void setApplyContactUserEmail(String paramString)
//  {
//    this.applyContactUserEmail = paramString;
//  }
  
//  public Short getEntryCount()
//  {
//    return this.entryCount;
//  }
//  
//  public void setEntryCount(Short paramShort)
//  {
//    this.entryCount = paramShort;
//  }
//  
//  public Short getExitCount()
//  {
//    return this.exitCount;
//  }
//  
//  public void setExitCount(Short paramShort)
//  {
//    this.exitCount = paramShort;
//  }
//  
//  public String getCnAgencyContractStatus()
//  {
//    return this.cnAgencyContractStatus;
//  }
//  
//  public void setCnAgencyContractStatus(String paramString)
//  {
//    this.cnAgencyContractStatus = paramString;
//  }
  
  public Integer getProcOrder()
  {
    return this.procOrder;
  }
  
//  public void setProcOrder(Integer paramInteger)
//  {
//    this.procOrder = paramInteger;
//  }
  
//  public String getProcType()
//  {
//    return this.procType;
//  }
//  
//  public void setProcType(String paramString)
//  {
//    this.procType = paramString;
//  }
  
  public String getDeleteFlag()
  {
    return this.deleteFlag;
  }
  
//  public void setDeleteFlag(String paramString)
//  {
//    this.deleteFlag = paramString;
//  }
  
//  public Date getTourBureauAgreeTime()
//  {
//    return this.tourBureauAgreeTime;
//  }
//  
//  public void setTourBureauAgreeTime(Date paramDate)
//  {
//    this.tourBureauAgreeTime = paramDate;
//  }
//  
//  public String getTourBureauAgreeUserId()
//  {
//    return this.tourBureauAgreeUserId;
//  }
//  
//  public void setTourBureauAgreeUserId(String paramString)
//  {
//    this.tourBureauAgreeUserId = paramString;
//  }
//  
//  public Date getNiaAgreeTime()
//  {
//    return this.niaAgreeTime;
//  }
//  
//  public void setNiaAgreeTime(Date paramDate)
//  {
//    this.niaAgreeTime = paramDate;
//  }
//  
//  public String getNiaAgreeUserId()
//  {
//    return this.niaAgreeUserId;
//  }
//  
//  public void setNiaAgreeUserId(String paramString)
//  {
//    this.niaAgreeUserId = paramString;
//  }
//  
//  public String getTourBureauP02Status()
//  {
//    return this.tourBureauP02Status;
//  }
//  
//  public void setTourBureauP02Status(String paramString)
//  {
//    this.tourBureauP02Status = paramString;
//  }
//  
//  public String getTourBureauP03Status()
//  {
//    return this.tourBureauP03Status;
//  }
//  
//  public void setTourBureauP03Status(String paramString)
//  {
//    this.tourBureauP03Status = paramString;
//  }
//  
//  public String getTourBureauP04Status()
//  {
//    return this.tourBureauP04Status;
//  }
//  
//  public void setTourBureauP04Status(String paramString)
//  {
//    this.tourBureauP04Status = paramString;
//  }
//  
//  public String getTourBureauP05Status()
//  {
//    return this.tourBureauP05Status;
//  }
//  
//  public void setTourBureauP05Status(String paramString)
//  {
//    this.tourBureauP05Status = paramString;
//  }
  
  public String getContactNameOfMainland()
  {
    return this.contactNameOfMainland;
  }
  
  public void setContactNameOfMainland(String paramString)
  {
    this.contactNameOfMainland = paramString.trim();
  }
  
  public String getContactGenderOfMainland()
  {
    return this.contactGenderOfMainland;
  }
  
  public void setContactGenderOfMainland(String paramString)
  {
      if(paramString.trim().equals("女")){
          this.contactGenderOfMainland = "1";
      }else{
          this.contactGenderOfMainland = "0";
      }
  }
  
  public String getContactGenderOfMainlandMean()
  {
      if(this.contactGenderOfMainland == null){
          return "";
      }else{
          return this.contactGenderOfMainland.equals("1") ? "女" : "男";
      }
  }
  
  public String getContactTelNoOfMainland()
  {
    return this.contactTelNoOfMainland;
  }
  
  public void setContactTelNoOfMainland(String paramString)
  {
    this.contactTelNoOfMainland = paramString.trim();
  }
  
  public String getContactMobileNoOfMainland()
  {
    return this.contactMobileNoOfMainland;
  }
  
  public void setContactMobileNoOfMainland(String paramString)
  {
    this.contactMobileNoOfMainland = paramString.trim();
  }
  
  public String getContactAddressOfMainland()
  {
    return this.contactAddressOfMainland;
  }
  
  public void setContactAddressOfMainland(String paramString)
  {
    this.contactAddressOfMainland = paramString.trim();
  }
  
  public String getContactTitleOfMainland()
  {
    return this.contactTitleOfMainland;
  }
  
  public void setContactTitleOfMainland(String paramString)
  {
    this.contactTitleOfMainland = paramString.trim();
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
            
            if(value == null){ 
//                System.out.println(name + " is NULL.");
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
        e.printStackTrace();
        return null;
    }
    return insertStr;
  }

}