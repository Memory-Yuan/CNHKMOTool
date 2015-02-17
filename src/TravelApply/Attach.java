package TravelApply;

import java.io.File;
import javax.swing.ImageIcon;

public class Attach {
    private String type = "2";
    private File file;
    private ImageIcon imageIcon;
    
    public String getType(){
        return this.type;
    }

    public void setType(String s){
        this.type = s;
    }
    
    public File getFile(){
        return this.file;
    }

    public void setFile(File f){
        this.file = f;
    }
    
    public ImageIcon getImageIcon(){
        return this.imageIcon;
    }

    public void setImageIcon(ImageIcon ii){
        this.imageIcon = ii;
    }
}
