package study.jy;

import java.util.ArrayList;



public class ZkNode {
    private String nodeValue;
    private ArrayList<ZkEvent> events;
    private String nodePath;
    private ZkNode parent;
    private ArrayList<ZkNode> childrenNodes;
    private ArrayList<ZkNode> observers;



    public ZkNode() {
        this.childrenNodes = new ArrayList<ZkNode>();
        this.events = new ArrayList<ZkEvent>();
        this.observers = new ArrayList<ZkNode>();
    }

    public String getNodeValue(){
        return this.nodeValue;
    }
    public void setNodeValue(String value){
        this.nodeValue = value;
    }
    public String getNodePath(){
        return this.nodePath;
    }
    public void setNodePath(String path){
        this.nodePath = path;
    }
    public ZkNode getParent(){
        return this.parent;
    }
    public void setParent(ZkNode parent){
        this.parent = parent;
    }
    public ArrayList<ZkNode> getChildrenNodes(){
        return this.childrenNodes;
    }
    public ArrayList<ZkEvent> getEvents() {
        return events;
    }
    public ArrayList<ZkNode> getObservers() {
        return observers;
    }


    public void setObservers() {
        this.observers.add(this);
        ZkNode pa = this.parent;
        while(pa!=null){
            this.observers.add(pa);
            pa=pa.getParent();
        }
    }

    public void notifyEvent(ZkEvent event){
        this.events.add(event);

    }
}
