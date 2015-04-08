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
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
//import org.apache.poi.hwpf.usermodel.TableCell;
//import org.apache.poi.hwpf.usermodel.TableRow;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
//import org.apache.poi.xwpf.usermodel.XWPFTableCell;
//import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;

public class ApplyData {
    public ApplyData(File f){
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
    private final String HeadShotName = "1 照片.jpg";
    
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
    
    private void word2003Resolve(File file){
        try{
            if(!file.getName().endsWith(".doc")) {
                throw new FileFormatException();
            }
            
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
            HWPFDocument doc = new HWPFDocument(fs);
            Range range = doc.getRange();
            Table table = null;

            for (int i=0; i<range.numParagraphs(); i++) {
                Paragraph par = range.getParagraph(i);
                if(par.isInTable()){
                    table = range.getTable(par);
                    break;
                }
            }
            if(table == null){
                CommonHelp.logger.log(Level.ERROR, String.format("[%s] Word文件找不到資料表格！", this.getTourName()));
                this.errOfResolvingList.add(new ErrMsg("Word文件找不到資料表格！", 0)); return;
            }
            /*
            *   印出所有Table資料，確認用。
            *
            for (int rowIdx=0; rowIdx<table.numRows(); rowIdx++) {
                TableRow row = table.getRow(rowIdx);
                System.out.println("row "+rowIdx);
                for (int colIdx=0; colIdx<row.numCells(); colIdx++) {
                    TableCell cell = row.getCell(colIdx);
                    System.out.println("column: "+colIdx+", text: "+cell.getParagraph(0).text());
                }
            }
            */
            try{
                this.travelgroup.setTourStartDate(table.getRow(0).getCell(2).getParagraph(0).text());
                this.travelgroup.setContactNameOfMainland(table.getRow(4).getCell(1).getParagraph(0).text());
                this.travelgroup.setContactTitleOfMainland(table.getRow(4).getCell(3).getParagraph(0).text());
                this.travelgroup.setContactMobileNoOfMainland(table.getRow(5).getCell(1).getParagraph(0).text());
                this.travelgroup.setContactGenderOfMainland(table.getRow(5).getCell(3).getParagraph(0).text());
                this.travelgroup.setContactTelNoOfMainland(table.getRow(5).getCell(5).getParagraph(0).text());
                this.travelgroup.setContactAddressOfMainland(table.getRow(6).getCell(1).getParagraph(0).text());
            }catch(Exception e){
                CommonHelp.logger.log(Level.ERROR, String.format("[%s][TravelGroup] 資料解析有誤！", this.getTourName()), e);
            }

            Traveller traveller = null;
            String mainTravellerName = null;
            for(int i = 0; i < this.applyPeopleFolderQty; i++){
                traveller = new Traveller();
                traveller.setSeqNo((short)i);
                try{
                    traveller.setChineseName(table.getRow(11+7*i).getCell(1).getParagraph(0).text());
                    traveller.setGender(table.getRow(11+7*i).getCell(3).getParagraph(0).text());
                    traveller.setBirthDate(table.getRow(11+7*i).getCell(5).getParagraph(0).text());
                    traveller.setEnglishName(table.getRow(12+7*i).getCell(1).getParagraph(0).text());
                    traveller.setPassportNo(table.getRow(12+7*i).getCell(3).getParagraph(0).text());
                    traveller.setPassportExpiryDate(table.getRow(12+7*i).getCell(5).getParagraph(0).text());
                    traveller.setPersonId(table.getRow(13+7*i).getCell(1).getParagraph(0).text());
                    traveller.setEducation(traveller.getEducationIdx(table.getRow(14+7*i).getCell(1).getParagraph(0).text()));
                    traveller.setOccupationDesc(table.getRow(14+7*i).getCell(3).getParagraph(0).text());
                    traveller.setAddress(table.getRow(15+7*i).getCell(3).getParagraph(0).text());
                    traveller.setLivingCity(traveller.getLivingCityCode(table.getRow(15+7*i).getCell(3).getParagraph(0).text()));

//                    if(!traveller.isValidTraveller()){ continue; }
                    if(i == 0){
                        this.travellerList.add(traveller);
                        mainTravellerName = traveller.getChineseName();
                        continue;
                    }
                    traveller.setRelative(mainTravellerName);
                    traveller.setRelativeTitle(table.getRow(16+7*i).getCell(1).getParagraph(0).text());
                    traveller.setPartnerOfTaiwan(table.getRow(16+7*i).getCell(3).getParagraph(0).text());
                }catch(IndexOutOfBoundsException e){
                    //@modify
                    CommonHelp.logger.log(Level.ERROR, String.format("[%s][Traveller] 資料解析有誤！", this.getTourName()), e);
                }catch (Exception e) {
                    CommonHelp.logger.log(Level.ERROR, String.format("[%s][Traveller] 資料解析有誤！", this.getTourName()), e);
                }
                this.travellerList.add(traveller);
            }
            
//            this.travelgroup.setGroupCount((short)this.travellerList.size());
//            this.travelgroup.setPermitApplyCount(Integer.toString(this.travellerList.size()));
            
        } catch(FileFormatException e) {
            CommonHelp.logger.log(Level.ERROR, String.format("[%s] 請選擇正確的檔案格式 - Microsotf Word", this.getTourName()));
            this.errOfResolvingList.add(new ErrMsg("請選擇正確的檔案格式 - Microsotf Word。", 0));
        } catch (FileNotFoundException e) {
            CommonHelp.logger.log(Level.ERROR, String.format("[%s] 找不到文件！", this.getTourName()));
            this.errOfResolvingList.add(new ErrMsg("找不到文件！", 0));
        } catch (IOException e) {
            CommonHelp.logger.log(Level.ERROR, String.format("[%s]", this.getTourName()), e);
            this.errOfResolvingList.add(new ErrMsg("出現錯誤，請再試一次，或聯絡工程師來為你解決。", 0));
        } catch (Exception e) {
            CommonHelp.logger.log(Level.ERROR, String.format("[%s]", this.getTourName()), e);
            this.errOfResolvingList.add(new ErrMsg("出現錯誤，請再試一次，或聯絡工程師來為你解決。", 0));
        }
    }
    
    public void word2007Resolve(File file){
        try{
            if(!file.getName().endsWith(".docx")) {
                throw new FileFormatException();
            }
            XWPFDocument doc = new XWPFDocument(new FileInputStream(file));
            List<XWPFTable> tableList = doc.getTables();

            if(tableList == null){
                CommonHelp.logger.log(Level.WARN, String.format("[%s] Word文件找不到資料表格！", this.getTourName()));
                this.errOfResolvingList.add(new ErrMsg("Word文件找不到資料表格！", 0)); return;
            }
            /*
            * 印出所有Table資料，確認用。
            *
            XWPFTable xwpfTable = tableList.get(0);
            List<XWPFTableRow> row = xwpfTable.getRows();
            int rowidx = 0, colidx = 0;
            for (XWPFTableRow xwpfTableRow : row) {
                System.out.println("row " + rowidx);
                rowidx++; colidx = 0;
                List<XWPFTableCell> cell = xwpfTableRow.getTableCells();
                for (XWPFTableCell xwpfTableCell : cell) {
                    if(xwpfTableCell!=null){
                        System.out.println("column " + colidx + ", text: " + xwpfTableCell.getText());
                        colidx++;
                    }
                }
            }
            */
            XWPFTable table = tableList.get(0);
            try{
                this.travelgroup.setTourStartDate(table.getRow(0).getCell(2).getText());
                this.travelgroup.setContactNameOfMainland(table.getRow(4).getCell(1).getText());
                this.travelgroup.setContactTitleOfMainland(table.getRow(4).getCell(3).getText());
                this.travelgroup.setContactMobileNoOfMainland(table.getRow(5).getCell(1).getText());
                this.travelgroup.setContactGenderOfMainland(table.getRow(5).getCell(3).getText());
                this.travelgroup.setContactTelNoOfMainland(table.getRow(5).getCell(5).getText());
                this.travelgroup.setContactAddressOfMainland(table.getRow(6).getCell(1).getText());
            }catch(Exception e){
                CommonHelp.logger.log(Level.ERROR, String.format("[%s][TravelGroup] 資料解析有誤！", this.getTourName()), e);
            }
            
            Traveller traveller = null;
            String mainTravellerName = null;
            for(int i = 0; i < this.applyPeopleFolderQty; i++){
                traveller = new Traveller();
                traveller.setSeqNo((short)i);
                try{
                    traveller.setChineseName(table.getRow(11+7*i).getCell(1).getText());
                    traveller.setGender(table.getRow(11+7*i).getCell(3).getText());
                    traveller.setBirthDate(table.getRow(11+7*i).getCell(5).getText());
                    traveller.setEnglishName(table.getRow(12+7*i).getCell(1).getText());
                    traveller.setPassportNo(table.getRow(12+7*i).getCell(3).getText());
                    traveller.setPassportExpiryDate(table.getRow(12+7*i).getCell(5).getText());
                    traveller.setPersonId(table.getRow(13+7*i).getCell(1).getText());
                    traveller.setEducation(traveller.getEducationIdx(table.getRow(14+7*i).getCell(1).getText()));
                    traveller.setOccupationDesc(table.getRow(14+7*i).getCell(3).getText());
                    traveller.setAddress(table.getRow(15+7*i).getCell(3).getText());
                    traveller.setLivingCity(traveller.getLivingCityCode(table.getRow(15+7*i).getCell(3).getText()));

//                    if(!traveller.isValidTraveller()){ continue; }
                    if(i == 0){
                        this.travellerList.add(traveller);
                        mainTravellerName = traveller.getChineseName();
                        continue;
                    }
                    traveller.setRelative(mainTravellerName);
                    traveller.setRelativeTitle(table.getRow(16+7*i).getCell(1).getText());
                    traveller.setPartnerOfTaiwan(table.getRow(16+7*i).getCell(3).getText());
                }catch(IndexOutOfBoundsException e){
                    //@modify
                    CommonHelp.logger.log(Level.ERROR, String.format("[%s][Traveller] 資料解析有誤！", this.getTourName()), e);
                }catch (Exception e) {
                    CommonHelp.logger.log(Level.ERROR, String.format("[%s][Traveller] 資料解析有誤！", this.getTourName()), e);
                }
                this.travellerList.add(traveller);
            }
            
//            this.travelgroup.setGroupCount((short)this.travellerList.size());
//            this.travelgroup.setPermitApplyCount(Integer.toString(this.travellerList.size()));
            
        } catch(FileFormatException e) {
            CommonHelp.logger.log(Level.ERROR, String.format("[%s] 請選擇正確的檔案格式 - Microsotf Word", this.getTourName()));
            this.errOfResolvingList.add(new ErrMsg("請選擇正確的檔案格式 - Microsotf Word。", 0));
        } catch (FileNotFoundException e) {
            CommonHelp.logger.log(Level.ERROR, String.format("[%s] 找不到文件！", this.getTourName()));
            this.errOfResolvingList.add(new ErrMsg("找不到文件！", 0));
        } catch (IOException e) {
            CommonHelp.logger.log(Level.ERROR, String.format("[%s]", this.getTourName()), e);
            this.errOfResolvingList.add(new ErrMsg("出現錯誤，請再試一次，或聯絡工程師來為你解決。", 0));
        } catch (Exception e) {
            CommonHelp.logger.log(Level.ERROR, String.format("[%s]", this.getTourName()), e);
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
                CommonHelp.logger.log(Level.ERROR, String.format("[%s] 找不到申請文件", this.getTourName()));
                return;
            }
            if(this.applyDoc.getName().endsWith(".doc")){
                this.word2003Resolve(this.applyDoc);
            }else if(this.applyDoc.getName().endsWith(".docx")){
                this.word2007Resolve(this.applyDoc);
            }else{
                CommonHelp.logger.log(Level.ERROR, String.format("[%s] 檔案格式錯誤: %s", this.getTourName(), this.applyDoc.getName()));
                return;
            }

            if(this.travellerList.isEmpty()){
                CommonHelp.logger.log(Level.ERROR, String.format("[%s] 無法解析申請資料，旅客人數為0", this.getTourName()));
                return;
            }

            if(this.applyAttachList.isEmpty() || !this.mappingProcess()){
                CommonHelp.logger.log(Level.ERROR, String.format("[%s] 找不到附加圖片資料，或圖片資料無法與旅客資料對應。  無附件旅客: %s; 待認領附件: %s", this.getTourName(), this.getTravellerNamesOfNoAttach(), this.getRestApplyFolderNames()));
            }

            if(this.travellerList.size() != this.applyPeopleFolderQty){
                String m = String.format("[%s] 申請文件解析出的申請人數(%s)與申請人資料夾數(%s)不一致，請確認。", this.getTourName(), this.travellerList.size(), this.applyPeopleFolderQty);
                m += String.format("旅客: %s; 申請人資料夾: %s", this.getTravellerNames(), this.getApplyFolderNames());
                CommonHelp.logger.log(Level.ERROR, m);
            }
        
        }catch(Exception e){
            CommonHelp.logger.log(Level.ERROR, String.format("[%s]", this.getTourName()), e);
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

        if(travellerList.size() < this.applyPeopleFolderQty){
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
