package study.jy;


public class ZkEvent {
    private String eventPath;
    private String eventValue;

    public String getEventPath(){
        return this.eventPath;
    }
    public void setEventPath(String path){
        this.eventPath = path;
    }

    public String getEventValue(){
        return this.eventValue;
    }
    public void setValue(String value){
        this.eventValue = value;
    }


}
