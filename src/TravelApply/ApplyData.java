package TravelApply;

import java.io.File;
import java.util.List;
import javax.swing.DefaultListModel;

public class ApplyData {
    private String travelName;
    private File applyFolder;
    private File applyDoc;
    private TravelGroup travelgroup;
//    private List<Traveller> travellerList;
    private DefaultListModel travellerModel;
    private List<ApplyAttach> applyAttachList;
    private boolean applyAttachListDerty;
    
    public String getTravelName(){
        return this.travelName;
    }
    
    public void setTravelName(String tn){
        this.travelName = tn;
    }
    
    public TravelGroup getTravelgroup(){
        return this.travelgroup;
    }
    
    public void setTravelgroup(TravelGroup tg){
        this.travelgroup = tg;
    }
    
//    public List<Traveller> getTravellerList(){
//        return this.travellerList;
//    }
//    
//    public void setTravellerList(List<Traveller> lt){
//        this.travellerList = lt;
//    }
    public DefaultListModel getTravellerModel(){
        return this.travellerModel;
    }
    
    public void getTravellerModel(DefaultListModel trm){
        this.travellerModel = trm;
    }
    
    public List<ApplyAttach> travellerModel(){
        return this.applyAttachList;
    }
    
    public void setApplyAttachList(List<ApplyAttach> la){
        this.applyAttachList = la;
    }
    
    public boolean getApplyAttachListDerty(){
        return this.applyAttachListDerty;
    }
    
    public void getApplyAttachListDerty(boolean adt){
        this.applyAttachListDerty = adt;
    }
}
