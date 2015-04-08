package TravelApply;

import java.util.List;

public class ApplyAttach {
    public ApplyAttach(){
        this.mappingStatus = false;
    }
    private String belongTo;
    private List<Attach> attachList;
    private boolean mappingStatus;
    
    public String getBelongTo(){
        return this.belongTo;
    }
    
    public void setBelongTo(String s){
        this.belongTo = s;
    }
    
    public List<Attach> getAttachList(){
        return this.attachList;
    }
    
    public void setAttachList(List<Attach> la){
        this.attachList = la;
    }
    
    public boolean isMapping(){
        return mappingStatus;
    }
    
    public void setMappingStatus(boolean b){
        this.mappingStatus = b;
    }
}
