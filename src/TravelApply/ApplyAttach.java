package TravelApply;

import java.util.List;

public class ApplyAttach {
    private String belongTo;
    private List<Attach> attachList;
    
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
}
