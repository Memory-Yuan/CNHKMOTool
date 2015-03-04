package TravelApply;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
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
        this.allDataProcess();
    }
    
    private void initData(){
        this.travelgroup = new TravelGroup();
        this.setTourName(this.applyFolder.getName());
        this.travellerModel = new DefaultListModel();
        this.applyAttachList = new ArrayList<ApplyAttach>();
        this.processErrList = new ArrayList<String>();
        this.applyPeopleFolder = 0;
        this.status = 0;
    }
    
    private int status; //0: 未處理、1: 成功、2: 失敗
    private File applyFolder;
    private File applyDoc;
    private TravelGroup travelgroup;
    private DefaultListModel travellerModel;
    private List<ApplyAttach> applyAttachList;
    private List<String> processErrList;
    private int applyPeopleFolder;
    
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
    
    public DefaultListModel getTravellerModel(){
        return this.travellerModel;
    }
    
    public List<ApplyAttach> travellerModel(){
        return this.applyAttachList;
    }
    
    public List<String> getProcessErrMsg(){
        return this.processErrList;
    }
    
    public void clearProcessErr(){
        this.processErrList = new ArrayList<String>();
    }
    
    private boolean isMoreThanOne(File folder){
        /*
        *   只要資料夾有除了資料夾以外的檔案，就判定申請人數是1人。
        */
        File[] files = folder.listFiles(hiddenFilter);
        for(File f : files){
            if(f.isFile()){
                this.applyPeopleFolder = 1;
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
                if(fn.equals("1 照片.jpg")){ a.setType("1"); }
                la.add(a);
            }else if(f.isDirectory() && enter){
                this.applyPeopleFolder++;
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
    
    public void word2003Resolve(File file){
        this.word2003Resolve(file, false);
    }
    
    private void word2003Resolve(File file, boolean isFix){
        try{
            if(!file.getName().endsWith(".doc")) {
                throw new FileFormatException();
            } else {
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
                if(table == null){ this.processErrList.add("無法解析檔案！"); return; }
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
                String sDate = CommonHelp.dateFormatFix(table.getRow(0).getCell(2).getParagraph(0).text());
                String eDate = CommonHelp.calculateTourDate(sDate, 14);
                this.travelgroup.setTourStartDate(sDate);
                this.travelgroup.setTourEndDate(eDate);
                this.travelgroup.setContactNameOfMainland(CommonHelp.transToTC(table.getRow(4).getCell(1).getParagraph(0).text()));
                this.travelgroup.setContactTitleOfMainland(CommonHelp.transToTC(table.getRow(4).getCell(3).getParagraph(0).text()));
                this.travelgroup.setContactMobileNoOfMainland(table.getRow(5).getCell(1).getParagraph(0).text());
                this.travelgroup.setContactGenderOfMainland(CommonHelp.transToTC(table.getRow(5).getCell(3).getParagraph(0).text()));
                this.travelgroup.setContactTelNoOfMainland(table.getRow(5).getCell(5).getParagraph(0).text());
                this.travelgroup.setContactAddressOfMainland(CommonHelp.transToTC(table.getRow(6).getCell(1).getParagraph(0).text()));

                try{
                    Traveller traveller = null;
                    int fixR = isFix ? 1 : 0;
                    String mainTravellerName = null;
                    for(int i = 0; true; i++){
                        int fixR2 = i > 0 && isFix ? 1 : 0;
                        traveller = new Traveller();
                        traveller.setSeqNo((short)i);
                        traveller.setChineseName(CommonHelp.transToTC(table.getRow(11+(7*i)+fixR2).getCell(1).getParagraph(0).text()));
                        traveller.setGender(CommonHelp.transToTC(table.getRow(11+(7*i)+fixR2).getCell(3).getParagraph(0).text()));
                        try{
                            traveller.setBirthDate(CommonHelp.dateFormatFix(table.getRow(11+(7*i)+fixR2).getCell(5).getParagraph(0).text()));
                        }catch(Exception e){}
                        traveller.setEnglishName(table.getRow(12+(7*i)+fixR).getCell(1).getParagraph(0).text());
                        traveller.setPassportNo(table.getRow(12+(7*i)+fixR).getCell(3).getParagraph(0).text());
                        traveller.setPassportExpiryDate(CommonHelp.dateFormatFix(table.getRow(12+(7*i)+fixR).getCell(5).getParagraph(0).text()));
                        traveller.setPersonId(table.getRow(13+(7*i)+fixR).getCell(1).getParagraph(0).text());
                        traveller.setEducation(CommonHelp.transToTC(table.getRow(14+(7*i)+fixR).getCell(1).getParagraph(0).text()));
                        traveller.setOccupationDesc(CommonHelp.transToTC(table.getRow(14+(7*i)+fixR).getCell(3).getParagraph(0).text()));
                        traveller.setAddress(CommonHelp.transToTC(table.getRow(15+(7*i)+fixR).getCell(3).getParagraph(0).text()));
                        traveller.setLivingCity(CommonHelp.transToTC(table.getRow(15+(7*i)+fixR).getCell(3).getParagraph(0).text()));
                        
                        if(!traveller.isValidTraveller()){ break; }
                        if(i == 0){
                            this.travellerModel.addElement(traveller);
                            mainTravellerName = traveller.getChineseName();
                            continue;
                        }
                        traveller.setRelative(mainTravellerName);
                        traveller.setRelativeTitle(CommonHelp.transToTC(table.getRow(16+(7*i)+fixR).getCell(1).getParagraph(0).text()));
                        traveller.setPartnerOfTaiwan(CommonHelp.transToTC(table.getRow(16+(7*i)+fixR).getCell(3).getParagraph(0).text()));

                        this.travellerModel.addElement(traveller);
                    }
                }catch(IndexOutOfBoundsException e){
//                    System.out.println("table資料已讀完，這個錯誤是正常的。");
//                    e.printStackTrace();
                }
                this.travelgroup.setGroupCount((short)this.travellerModel.size());
                this.travelgroup.setPermitApplyCount(Integer.toString(this.travellerModel.size()));
            }
        } catch(FileFormatException e) {
            e.printStackTrace();
            this.processErrList.add("請選擇正確的檔案格式 - Microsotf Word。\n詳細:\n" + e.getMessage());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            this.processErrList.add("找不到檔案！\n詳細:\n" + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            this.processErrList.add("出現錯誤，請再試一次，或聯絡工程師來為你解決。\n詳細:\n" + e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
            this.processErrList.add("出現錯誤，請再試一次，或聯絡工程師來為你解決。\n詳細:\n" + e.getMessage());
        }
    }
    
    public void word2007Resolve(File file){
        try{
            if(!file.getName().endsWith(".docx")) {
                throw new FileFormatException();
            } else {
                XWPFDocument doc = new XWPFDocument(new FileInputStream(file));
                List<XWPFTable> tableList = doc.getTables();
                
                if(tableList == null){ this.processErrList.add("無法解析檔案！"); return; }
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
                String sDate = CommonHelp.dateFormatFix(table.getRow(0).getCell(2).getText());
                String eDate = CommonHelp.calculateTourDate(sDate, 14);
                this.travelgroup.setTourStartDate(sDate);
                this.travelgroup.setTourEndDate(eDate);
                this.travelgroup.setContactNameOfMainland(CommonHelp.transToTC(table.getRow(4).getCell(1).getText()));
                this.travelgroup.setContactTitleOfMainland(CommonHelp.transToTC(table.getRow(4).getCell(3).getText()));
                this.travelgroup.setContactMobileNoOfMainland(table.getRow(5).getCell(1).getText());
                this.travelgroup.setContactGenderOfMainland(CommonHelp.transToTC(table.getRow(5).getCell(3).getText()));
                this.travelgroup.setContactTelNoOfMainland(table.getRow(5).getCell(5).getText());
                this.travelgroup.setContactAddressOfMainland(CommonHelp.transToTC(table.getRow(6).getCell(1).getText()));

                try{
                    Traveller traveller = null;
                    String mainTravellerName = null;
                    for(int i = 0; true; i++){
                        traveller = new Traveller();
                        traveller.setSeqNo((short)i);
                        traveller.setChineseName(CommonHelp.transToTC(table.getRow(11+(7*i)).getCell(1).getText()));
                        traveller.setGender(CommonHelp.transToTC(table.getRow(11+(7*i)).getCell(3).getText()));
                        traveller.setBirthDate(CommonHelp.dateFormatFix(table.getRow(11+(7*i)).getCell(5).getText()));
                        traveller.setEnglishName(table.getRow(12+(7*i)).getCell(1).getText());
                        traveller.setPassportNo(table.getRow(12+(7*i)).getCell(3).getText());
                        traveller.setPassportExpiryDate(CommonHelp.dateFormatFix(table.getRow(12+(7*i)).getCell(5).getText()));
                        traveller.setPersonId(table.getRow(13+(7*i)).getCell(1).getText());
                        traveller.setEducation(CommonHelp.transToTC(table.getRow(14+(7*i)).getCell(1).getText()));
                        traveller.setOccupationDesc(CommonHelp.transToTC(table.getRow(14+(7*i)).getCell(3).getText()));
                        traveller.setAddress(CommonHelp.transToTC(table.getRow(15+(7*i)).getCell(3).getText()));
                        traveller.setLivingCity(CommonHelp.transToTC(table.getRow(15+(7*i)).getCell(3).getText()));

                        if(!traveller.isValidTraveller()){ break; }
                        if(i == 0){
                            this.travellerModel.addElement(traveller);
                            mainTravellerName = traveller.getChineseName();
                            continue;
                        }
                        traveller.setRelative(mainTravellerName);
                        traveller.setRelativeTitle(CommonHelp.transToTC(table.getRow(16+(7*i)).getCell(1).getText()));
                        traveller.setPartnerOfTaiwan(CommonHelp.transToTC(table.getRow(16+(7*i)).getCell(3).getText()));

                        this.travellerModel.addElement(traveller);
                    }
                }catch(IndexOutOfBoundsException e){
//                    System.out.println("table資料已讀完，這個錯誤是正常的。");
//                    e.printStackTrace();
                }
                this.travelgroup.setGroupCount((short)this.travellerModel.size());
                this.travelgroup.setPermitApplyCount(Integer.toString(this.travellerModel.size()));
            }
        } catch(FileFormatException e) {
            e.printStackTrace();
            this.processErrList.add("請選擇正確的檔案格式 - Microsotf Word。\n詳細:\n" + e.getMessage());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            this.processErrList.add("找不到檔案！\n詳細:\n" + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            this.processErrList.add("出現錯誤，請再試一次，或聯絡工程師來為你解決。\n詳細:\n" + e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
            this.processErrList.add("出現錯誤，請再試一次，或聯絡工程師來為你解決。\n詳細:\n" + e.getMessage());
        }
    }
    
    public boolean mappingProcess(){
        return this.mappingProcess(true);
    }
    
    private boolean mappingProcess(boolean oneMoreChance){
        boolean re = false;
        for(int i = 0; i < this.travellerModel.size(); i++){
            Traveller traveller = (Traveller)this.travellerModel.get(i);
            if(traveller.getAttachList().size() != 0 && !oneMoreChance){ continue; }
            for(ApplyAttach aa : this.applyAttachList){
                boolean found = false;
                if(aa.getBelongTo().indexOf(traveller.getChineseName()) >=0 ){
                    found = true;
                }else if(i == 0 && aa.getBelongTo().indexOf("主") >= 0){
                    found = true;
                }else if(this.applyAttachList.size() == 1){
                    found = true;
                }
                if(found){
                    traveller.setAttachList(aa.getAttachList());
                    this.applyAttachList.remove(aa);
                    break;
                }
            }
            if(traveller.getAttachList().size() == 0){
                re = true;
            }
        }
        
        if(re){
            if(oneMoreChance){
                return this.mappingProcess(false);
            }else{
                return false;
            }
        }

        return true;
    }
    
    public void allDataProcess(){
        try{

        this.getFolderFile();
        
        if(this.applyDoc == null){
            this.processErrList.add("找不到申請文件，請手動選擇。");
            return;
        }
        if(this.applyDoc.getName().endsWith(".doc")){
            this.word2003Resolve(this.applyDoc);
            if(this.travellerModel.size() == 0){ this.word2003Resolve(this.applyDoc, true); }
        }else if(this.applyDoc.getName().endsWith(".docx")){
            this.word2007Resolve(this.applyDoc);
        }else{
            this.processErrList.add("請選擇正確的檔案格式 - Microsotf Word。");
            return;
        }

        if(this.travellerModel.size() == 0){
            this.processErrList.add("無法解析申請資料。如果你使用的是Word2003版本，可以嘗試轉成2007以上版本。");
            return;
        }
        
        if(this.applyAttachList.size() == 0 || !this.mappingProcess()){
            this.processErrList.add("找不到可以附加的圖片，請手動選擇。");
        }
        
        if(travellerModel.size() != this.applyPeopleFolder){
            this.processErrList.add("申請文件解析出的申請人數(" + travellerModel.size() + ")與申請人資料夾數(" + this.applyPeopleFolder + ")不一致，請確認。");
        }
        
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void justDocProcess(){
        this.initData();
        
        if(this.applyDoc.getName().endsWith(".doc")){
            this.word2003Resolve(this.applyDoc);
            if(this.travellerModel.size() == 0){ this.word2003Resolve(this.applyDoc, true); }
        }else if(this.applyDoc.getName().endsWith(".docx")){
            this.word2007Resolve(this.applyDoc);
        }else{
            this.processErrList.add("請選擇正確的檔案格式 - Microsotf Word。");
            return;
        }

        if(this.travellerModel.size() == 0){
            this.processErrList.add("無法解析申請資料。如果你使用的是Word2003版本，可以嘗試轉成2007以上版本。");
            return;
        }
        
        this.processErrList.add("請手動選擇附件。");
    }
    
    public List<String> getErrMsgList(){
        List<String> errMsgList = new ArrayList<String>();
        if(this.getTourName() == null || this.getTourName().isEmpty()){
            errMsgList.add("請輸入行程名稱。");
        }
        
        if(this.applyDoc == null){
            errMsgList.add("找不到申請文件，請手動選擇。");
            return errMsgList;
        }
        
        String fn = this.applyDoc.getName().toLowerCase();
        if(!fn.endsWith(".doc") && !fn.endsWith(".docx")){
            errMsgList.add("請選擇正確的檔案格式 - Microsotf Word。");
            return errMsgList;
        }

        if(this.travellerModel.size() == 0){
            errMsgList.add("無法解析申請資料。如果你使用的是Word2003版本，可以嘗試轉成2007以上版本。");
            return errMsgList;
        }
        
        for(int i = 0; i < travellerModel.size(); i++){
            Traveller traveller = (Traveller)travellerModel.get(i);
            if(traveller.getAttachList().size() == 0){
                errMsgList.add("請手動選擇附加圖片。");
            }else if(!traveller.isSetHeadShot()){
                errMsgList.add("請設定 " + traveller.getChineseName() + " 的大頭照。");
            }
        }
                
        if(travellerModel.size() < this.applyPeopleFolder){
            errMsgList.add("申請文件解析出的申請人數(" + travellerModel.size() + ")與申請人資料夾數(" + this.applyPeopleFolder + ")不一致，請確認。");
        }
        
        return errMsgList;
    }
    
    public boolean isPass(){
        if(this.processErrList.size() != 0){ return false; }
        if(this.getErrMsgList().size() != 0){ return false; }
        return true;
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
