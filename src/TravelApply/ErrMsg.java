package TravelApply;

public class ErrMsg {
    public ErrMsg(String s, int i){
        this.msg = s;
        this.type = i;
    }
    
    private String msg;
    private int type;   //0: err, 1:warning, 2:info
    
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
}
