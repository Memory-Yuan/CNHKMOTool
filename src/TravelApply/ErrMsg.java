package TravelApply;

public class ErrMsg {
    public ErrMsg(String msg, int type){
        this.msg = msg;
        this.type = type;
        this.tag = "";
        this.plusMsg = "";
    }
    
    public ErrMsg(String msg, int type, String tag, String plus){
        this.msg = msg;
        this.type = type;
        this.tag = tag;
        this.plusMsg = plus;
    }
    
    private String msg;
    private int type;   //0: err, 1:warning, 2:info
    private String tag;
    private String plusMsg;
    
    public String getMsg(){
        return this.msg;
    }
    
    public void setMsg(String s){
        this.msg = s;
    }
    
    public int getType(){
        return this.type;
    }
    
    public void setType(int i){
        this.type = i;
    }
    
    public String getTag(){
        return this.tag;
    }
    
    public void setTag(String s){
        this.tag = s;
    }
    
    public String getPlusMsg(){
        return this.plusMsg;
    }
    
    public void setPlusMsg(String s){
        this.plusMsg = s;
    }
}
