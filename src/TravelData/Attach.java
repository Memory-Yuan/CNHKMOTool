package TravelData;

import java.io.File;

public class Attach {
    private String type = "2";
    private File file;
    
    
    public String getType(){
        return this.type;
    }

    public void setHeadShot(String s){
        this.type = s;
    }
    
    public File getFile(){
        return this.file;
    }

    public void setFile(File f){
        this.file = f;
    }
}
