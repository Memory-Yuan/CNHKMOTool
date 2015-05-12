package TravelApply;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.Level;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableIterator;
//import org.apache.poi.hwpf.usermodel.TableRow;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
//import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;

public class ApplyData {
    public ApplyData(File f){
        CommonHelp.logger.log(Level.INFO, String.format("Start---------------------------------------- path: %s", f.getAbsolutePath()));
        this.applyFolder = f;
        this.initData();
        this.handleApplyData();
    }
    
    private void initData(){
        this.travelgroup = new TravelGroup();
        this.travellerList = this.travelgroup.getTravellerList();
        this.setTourName(this.applyFolder.getName());
        this.applyAttachList = new ArrayList<ApplyAttach>();
        this.errOfResolvingList = new ArrayList<ErrMsg>();
        this.applyPeopleFolderQty = 0;
        this.status = 0;
    }
    
    private void initDocData(){
        this.travelgroup = new TravelGroup();
        this.travellerList = this.travelgroup.getTravellerList();
        this.errOfResolvingList = new ArrayList<ErrMsg>();
        this.status = 0;
    }
    
    private int status; //0: 未處理、1: 成功、2: 失敗
    private File applyFolder;
    private File applyDoc;
    private TravelGroup travelgroup;
    private List<Traveller> travellerList;
    private List<ApplyAttach> applyAttachList;
    private List<ErrMsg> errOfResolvingList;
    private int applyPeopleFolderQty;
    private final String HeadShotName = "HeadShot.jpg";
    
    private FileFilter hiddenFilter = new FileFilter() {
        @Override
        public boolean accept(File file) {
            return !file.isHidden();
        }
    };
    
    public boolean isSuccess(){
        return this.status == 1;
    }
    
    public int getStatus(){
        return this.status;
    }
    
    public void setStatus(int s){
        this.status = s;
    }
    
    public String getTourName(){
        return this.travelgroup.getTourName();
    }
    
    public void setTourName(String tn){
        this.travelgroup.setTourName(tn);
    }
    
    public File getApplyFolder(){
        return this.applyFolder;
    }
    
    public File getApplyDoc(){
        return this.applyDoc;
    }
    
    public void setApplyDoc(File f){
        this.applyDoc = f;
    }
    
    public TravelGroup getTravelgroup(){
        return this.travelgroup;
    }

    public List<ApplyAttach> getApplyAttachList(){
        return this.applyAttachList;
    }
    
    public List<ApplyAttach> getRestApplyAttachList(){
        List<ApplyAttach> rest = new ArrayList<ApplyAttach>();
        for(ApplyAttach aa : this.applyAttachList){
            if(!aa.isMapping()){ rest.add(aa); }
        }
        return rest;
    }
    
    public String getTravellerNames(){
        String s = "";
        for(Traveller traveller : this.travellerList){
            s += String.format("「%s」 ", traveller.getChineseName());
        }
        return s;
    }
    
    public String getTravellerNamesOfNoAttach(){
        String s = "";
        for(Traveller traveller : this.travellerList){
            if(traveller.getAttachList().isEmpty()){ s += String.format("「%s」 ", traveller.getChineseName()); }
        }
        return s;
    }
    
    public String getApplyFolderNames(){
        String s = "";
        for(ApplyAttach aa : this.applyAttachList){ s += String.format("「%s」 ", aa.getBelongTo()); }
        return s;
    }
    
    public String getRestApplyFolderNames(){
        String s = "";
        for(ApplyAttach aa : this.applyAttachList){
            if(!aa.isMapping()){ s += String.format("「%s」 ", aa.getBelongTo()); }
        }
        return s;
    }
    
    private boolean isMoreThanOne(File folder){
        /*
        *   只要資料夾有除了資料夾以外的檔案，就判定申請人數是1人。
        */
        File[] files = folder.listFiles(hiddenFilter);
        for(File f : files){
            if(f.isFile()){
                this.applyPeopleFolderQty = 1;
                return false;
            }
        }
        return true;
    }
    
    public void getFolderFile(){
        this.getFolderFile(this.applyFolder, isMoreThanOne(this.applyFolder));
    }
    
    private void getFolderFile(File folder, boolean enter){
        
        File[] fileList = folder.listFiles(hiddenFilter);
        List<Attach> la = new ArrayList<Attach>();
        Attach a = null;
        for(File f : fileList){
            String fn = f.getName().toLowerCase();
            if(fn.endsWith(".doc") || fn.endsWith(".docx")){
                this.applyDoc = f;
            }else if(fn.endsWith(".jpg") || fn.endsWith(".jpeg") || fn.endsWith(".png")){
                a = new Attach();
                a.setFile(f);
                if(fn.equals(HeadShotName)){ a.setType("1"); }
                la.add(a);
            }else if(f.isDirectory() && enter){
                this.applyPeopleFolderQty++;
                this.getFolderFile(f, false);
            }
        }
        if(la.size() > 0){
            ApplyAttach aa = new ApplyAttach();
            aa.setAttachList(la);
            aa.setBelongTo(folder.getName());
            this.applyAttachList.add(aa);
        }
    }
    
    private String getCellContent(Object obj, int r, int c){
        if(obj instanceof Table){
            return ((Table)obj).getRow(r) .getCell(c).getParagraph(0).text();
        }else{
            return ((XWPFTable)obj).getRow(r) .getCell(c).getText();
        }
    }
    
    private void wordResolve(File file, String docType){
        try{
            if(!(file.getName().endsWith(".doc") || file.getName().endsWith(".docx"))) {
                throw new FileFormatException();
            }
            
            List<Object> tableList = new ArrayList<Object>();            
            if(docType.equals("doc")){
                POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
                HWPFDocument doc = new HWPFDocument(fs);
                Range range = doc.getRange();
                TableIterator it = new TableIterator(range);
                while(it.hasNext()){
                    tableList.add(it.next());
                }
            }else if(docType.equals("docx")){
                XWPFDocument doc = new XWPFDocument(new FileInputStream(file));
                tableList.addAll(doc.getTables());
            }
            if(tableList.isEmpty()){
                CommonHelp.logger.log(Level.WARN, String.format("[wordResolve] Word文件找不到資料表格！ path: %s", this.applyDoc.getAbsolutePath()));
                this.errOfResolvingList.add(new ErrMsg("Word文件找不到資料表格！", 0)); return;
            }
            
            Traveller traveller;
            String mainTravellerName = null;
            int rowBase, rowAdj;
            for(int i = 0; i < tableList.size(); i++){
                Object tableObj = tableList.get(i);
                traveller = new Traveller();
                if(i == 0){ continue; }
                if(i == 1){
                    try{
                        this.travelgroup.setTourStartDate               (getCellContent(tableObj, 0, 2));
                        this.travelgroup.setContactNameOfMainland       (getCellContent(tableObj, 6, 2));
                        this.travelgroup.setContactTitleOfMainland      (getCellContent(tableObj, 7, 2));
                        this.travelgroup.setContactGenderOfMainland     (getCellContent(tableObj, 8, 2));
                        this.travelgroup.setContactMobileNoOfMainland   (getCellContent(tableObj, 9, 2));
                        this.travelgroup.setContactTelNoOfMainland      (getCellContent(tableObj, 10, 2));
                        this.travelgroup.setContactAddressOfMainland    (getCellContent(tableObj, 11, 2));
                    }catch(Exception e){
                        CommonHelp.logger.log(Level.ERROR, String.format("[wordResolve][TravelGroup] 資料解析有誤！ path: %s", this.applyDoc.getAbsolutePath()), e);
                    }
                    
                    rowBase = 15;
                    rowAdj = 2;
                }else{
                    rowBase = i == 2 ? 5 : 2;
                    rowAdj = 0;
                }
                
                try{
                    traveller.setSeqNo(i-1);
                    traveller.setChineseName        (getCellContent(tableObj, 0+rowBase, 2));
                    traveller.setBirthDate          (getCellContent(tableObj, 1+rowBase, 2));
                    traveller.setPassportNo         (getCellContent(tableObj, 2+rowBase, 2));
                    traveller.setGender             (getCellContent(tableObj, 3+rowBase, 2));
                    traveller.setAddress            (getCellContent(tableObj, 4+rowBase+rowAdj, 2));
                    traveller.setLivingCity         (traveller.getLivingCityCode(traveller.getAddress()));
                    traveller.setEnglishName        (getCellContent(tableObj, 5+rowBase+rowAdj, 2));
                    traveller.setPassportExpiryDate (getCellContent(tableObj, 6+rowBase+rowAdj, 2));
                    traveller.setPersonId           (getCellContent(tableObj, 7+rowBase+rowAdj, 2));
                    traveller.setBirthPlace2        (getCellContent(tableObj, 8+rowBase+rowAdj, 2));
                    traveller.setBirthPlace1        (traveller.getBirthPlace1Idx(traveller.getBirthPlace2()));
                    traveller.setEducation          (traveller.getEducationIdx(getCellContent(tableObj, 9+rowBase+rowAdj, 2)));
                    //手機 table.getRow(10+rowBase+rowAdj)
                    if(i == 1){
                        mainTravellerName = traveller.getChineseName();
                        traveller.setOccupationDesc (getCellContent(tableObj, 4+rowBase, 2));
                        traveller.setOccupation     (traveller.getOccupationId(traveller.getOccupationDesc()));
                    }else{
                        traveller.setRelative       (mainTravellerName);
                        traveller.setRelativeTitle  (getCellContent(tableObj, 11+rowBase+rowAdj, 2));
                    }
                }catch(IndexOutOfBoundsException e){
                    //@modify
                    CommonHelp.logger.log(Level.ERROR, String.format("[wordResolve][Traveller] 資料解析有誤！ path: %s", this.applyDoc.getAbsolutePath()), e);
                }catch (Exception e) {
                    CommonHelp.logger.log(Level.ERROR, String.format("[wordResolve][Traveller] 資料解析有誤！ path: %s", this.applyDoc.getAbsolutePath()), e);
                }
                this.travellerList.add(traveller);
                if(this.travellerList.size() >= applyPeopleFolderQty){ break; }
                
                /*
                *   印出所有Table資料，確認用。
                *
                System.out.println("table " + i);
                for (int rowIdx=0; rowIdx<table.numRows(); rowIdx++) {
                    TableRow row = table.getRow(rowIdx);
                    System.out.println("row "+rowIdx);
                    for (int colIdx=0; colIdx<row.numCells(); colIdx++) {
                        TableCell cell = row.getCell(colIdx);
                        System.out.println("column: "+colIdx+", text: "+cell.getParagraph(0).text());
                    }
                }
                */
            }
        } catch(FileFormatException e) {
            CommonHelp.logger.log(Level.ERROR, String.format("[wordResolve] 請選擇正確的檔案格式 - Microsotf Word. path: %s", this.applyDoc.getAbsolutePath()));
            this.errOfResolvingList.add(new ErrMsg("請選擇正確的檔案格式 - Microsotf Word。", 0));
        } catch (FileNotFoundException e) {
            CommonHelp.logger.log(Level.ERROR, String.format("[wordResolve] 找不到文件！ path: %s", this.applyFolder.getAbsolutePath()));
            this.errOfResolvingList.add(new ErrMsg("找不到文件！", 0));
        } catch (IOException e) {
            CommonHelp.logger.log(Level.ERROR, String.format("[wordResolve] path: %s", this.applyFolder.getAbsolutePath()), e);
            this.errOfResolvingList.add(new ErrMsg("出現錯誤，請再試一次，或聯絡工程師來為你解決。", 0));
        } catch (Exception e) {
            CommonHelp.logger.log(Level.ERROR, String.format("[wordResolve] path: %s", this.applyFolder.getAbsolutePath()), e);
            this.errOfResolvingList.add(new ErrMsg("出現錯誤，請再試一次，或聯絡工程師來為你解決。", 0));
        }
    }
    
    public boolean mappingProcess(){
        return this.mappingProcess(true);
    }
    
    private boolean mappingProcess(boolean oneMoreChance){
        boolean isAnyHaventAttach = false;
        for(int i = 0; i < this.travellerList.size(); i++){
            Traveller traveller = this.travellerList.get(i);
            if(!traveller.getAttachList().isEmpty()){ continue; }
            List<ApplyAttach> restApplyAttachList = this.getRestApplyAttachList();
            for(ApplyAttach aa : restApplyAttachList){
                boolean found = false;
                if(!traveller.getChineseName().isEmpty() && aa.getBelongTo().indexOf(traveller.getChineseName()) >=0){
                    found = true;
                }else if(i == 0 && aa.getBelongTo().indexOf("主") >= 0){
                    found = true;
                }else if(restApplyAttachList.size() == 1){
                    found = true;
                }
                if(found){
                    traveller.setAttachList(aa.getAttachList());
                    aa.setMappingStatus(true);
                    break;
                }
            }
            if(traveller.getAttachList().isEmpty()){
                isAnyHaventAttach = true;
            }
        }
        
        if(isAnyHaventAttach){
            if(oneMoreChance){
                return this.mappingProcess(false);
            }else{
                return false;
            }
        }

        return true;
    }
    
    public void handleApplyData(){
        this.handleApplyData(false);
    }
    
    public void handleApplyData(boolean isReloadDoc){
        try{
            if(isReloadDoc){
                this.initDocData();
            }else{
                this.getFolderFile();
            }
            
            if(this.applyDoc == null){
                CommonHelp.logger.log(Level.ERROR, String.format("[handleApplyData] 找不到申請文件。 path: %s", this.applyFolder.getAbsolutePath()));
                return;
            }
            if(this.applyDoc.getName().endsWith(".doc")){
                this.wordResolve(this.applyDoc, "doc");
            }else if(this.applyDoc.getName().endsWith(".docx")){
                this.wordResolve(this.applyDoc, "docx");
            }else{
                CommonHelp.logger.log(Level.ERROR, String.format("[handleApplyData] 檔案格式錯誤。 path: %s", this.applyDoc.getAbsolutePath()));
                return;
            }

            CommonHelp.logErrMsgList(this.travelgroup.getErrMsgList());
            for(Traveller tr : this.travellerList){
                CommonHelp.logErrMsgList(tr.getErrMsgList());
            }
            
            if(this.travellerList.isEmpty()){ return; }

            if(this.applyAttachList.isEmpty() || !this.mappingProcess()){
                CommonHelp.logger.log(
                        Level.ERROR,
                        String.format(
                                "[handleApplyData] 找不到附加圖片資料，或圖片資料無法與旅客資料對應。 無附件旅客: %s; 待認領附件: %s; path: %s",
                                this.getTravellerNamesOfNoAttach(), this.getRestApplyFolderNames(), this.applyFolder.getAbsolutePath()
                        )
                );
            }

            if(this.travellerList.size() != this.applyPeopleFolderQty){
                String m = String.format("[handleApplyData] 申請文件解析出的申請人數(%s)與申請人資料夾數(%s)不一致，請確認。", this.getTourName(), this.travellerList.size(), this.applyPeopleFolderQty);
                m += String.format(" 旅客: %s; 申請人資料夾: %s; path: %s", this.getTravellerNames(), this.getApplyFolderNames(), this.applyFolder.getAbsolutePath());
                CommonHelp.logger.log(Level.ERROR, m);
            }
        
        }catch(Exception e){
            CommonHelp.logger.log(Level.ERROR, String.format("[handleApplyData] path: %s", this.applyFolder.getAbsolutePath()), e);
        }
    }
    
    public List<ErrMsg> getErrMsgOfResolving(){
        return this.errOfResolvingList;
    }
    
    public void clearErrMsgOfResolving(){
        this.errOfResolvingList = new ArrayList<ErrMsg>();
    }
    
    public List<ErrMsg> getErrMsgList(){
        List<ErrMsg> errMsgList = new ArrayList<ErrMsg>();

        if(this.applyDoc == null){
            errMsgList.add(new ErrMsg("找不到申請文件，請手動選擇。", 0));
            return errMsgList;
        }
        
        String fn = this.applyDoc.getName().toLowerCase();
        if(!fn.endsWith(".doc") && !fn.endsWith(".docx")){
            errMsgList.add(new ErrMsg("請選擇正確的檔案格式 - Microsotf Word。", 0));
            return errMsgList;
        }

        if(travellerList.size() != this.applyPeopleFolderQty){
            errMsgList.add(new ErrMsg(String.format("申請文件解析出的申請人數(%s)與申請人資料夾數(%s)不一致，請確認。", this.travellerList.size(), this.applyPeopleFolderQty), 1));
        }
        
        errMsgList.addAll(this.travelgroup.getErrMsgList());
        
        return errMsgList;
    }
    
    public boolean isPass(){
//        if(!this.errOfResolvingList.isEmpty()){ return false; }
//        if(!this.getErrMsgList().isEmpty()){ return false; }
        if(this.travelgroup.getValidateStatus() == 2){ return false; }
        for(Traveller traveller : travellerList){
            if(traveller.getValidateStatus() == 2){ return false; }
        }
        return true;
    }
    
    public int getValidateStatus(){
        int tgv = this.travelgroup.getValidateStatus();
        
        int trv = 0;
        for(Traveller traveller : travellerList){
            int v = traveller.getValidateStatus();
            if(v == 2){ trv = 2; break;
            }else if(v == 1){ trv = 1; }
        }
        
        if(tgv == 2 || trv == 2){
            return 2;
        }else if(tgv == 1 || trv == 1){
            return 1;
        }else{
            return 0;
        }
    }
    
    /*
    * 圖片處理，目前不用了
    private File imageProcess(File file){
        try{
            if(file.length()/1024 >= IMG_SIZE || file.getName().toLowerCase().endsWith("png")){
                File theDir = new File(file.getParent() + "\\Modified");
                if (!theDir.exists()) {
                  try{
                      theDir.mkdir();
                   } catch(SecurityException e){
                       e.printStackTrace();
                       showMessage("出現錯誤，請再試一次，或聯絡工程師來為你解決。\n詳細:\n" + e.getMessage() , "err");
                   }
                }
                BufferedImage image = ImageIO.read(file);
                String fileName = removeExtension(file.getName());
                String filePath = theDir + "\\" + fileName + ".jpg";
                File output = new File(filePath);
                ImageIO.write(image, "jpg", output);
                file = output;
                
                if(file.length()/1024 >= IMG_SIZE){ file = imageZoomOut(file); }
            }
        }catch(IOException e){
            e.printStackTrace();
            showMessage("出現錯誤，請再試一次，或聯絡工程師來為你解決。\n詳細:\n" + e.getMessage() , "err");
        }
        return file;
    }
    
    private File imageZoomOut(File file){
        try{
            BufferedImage bimage = ImageIO.read(file);
            int width = bimage.getWidth();
            int height = bimage.getHeight();
            int hw = width > height? width : height;
            if(hw > 2000){ hw = 2000; }
            hw -= 100;
            BufferedImage image = Scalr.resize(bimage, hw);
            ImageIO.write(image, "jpg", file);
            
            if(file.length()/1024 >= IMG_SIZE){ file = imageZoomOut(file); }
        }catch(IOException e){
            e.printStackTrace();
            showMessage("出現錯誤，請再試一次，或聯絡工程師來為你解決。\n詳細:\n" + e.getMessage() , "err");
        }
        return file;
    }
    */
}
