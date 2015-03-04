package TravelApply;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import org.imgscalr.Scalr;

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
        try{
            BufferedImage image = ImageIO.read(f);
            if(image == null){ return; }
            ImageIcon ii = new ImageIcon(Scalr.resize(image, 50));
            this.imageIcon = ii;
        }catch(IOException ignore){}
    }
    
    public ImageIcon getImageIcon(){
        return this.imageIcon;
    }

//    public void setImageIcon(ImageIcon ii){
//        this.imageIcon = ii;
//    }
}
