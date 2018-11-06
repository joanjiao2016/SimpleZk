package study.jy;

import study.jy.Exceptions.DuplicatePathException;
import study.jy.Exceptions.IllegalPathException;
import study.jy.Exceptions.NodeNotExistException;
import study.jy.Interfaces.ICallBack;
import study.jy.Interfaces.INamespace;
import java.util.HashMap;

public class SimpleZk implements INamespace {
    private HashMap<String,ZkNode> zkNodes ;
    private ZkNode rootNode;

    public SimpleZk() {
        this.zkNodes = new HashMap<String, ZkNode>();

        //configure root node
        this.rootNode = new ZkNode();
        this.rootNode.setParent(null);
        this.rootNode.setNodePath("/");
        this.rootNode.setNodeValue("ROOT");
        zkNodes.put("/",this.rootNode);

    }

    public ZkNode getRootNode(){
        return  this.rootNode;
    }
    public HashMap<String,ZkNode> getZkNodes(){
        return this.zkNodes;
    }


    @Override
    public void create(String path, String value) throws Exception {
        path = this.formatPath(path);
        if(! path.startsWith("/") ||  path.endsWith("/")) {
            System.out.println("CREATE NODE ERROR: Path:"+path+" is illegal");
            throw new IllegalPathException("CREATE NODE ERROR: Path:"+path+" is illegal");
        }

        int lastIndex = path.lastIndexOf("/");
        //判断节点是否存在，已经存在则报错不允许创建
        if(this.zkNodes.containsKey(path)){
            System.out.println("CREATE NODE ERROR: Path:"+path+" already exists");
            throw new DuplicatePathException("CREATE NODE ERROR: Path:"+path+" already exists");
        }

        // 判断父节点是否存在，不存在不允许创建
        ZkNode parentNode;
        if(lastIndex == 0){
            parentNode = this.rootNode;
        }else{
            String parentPath = path.substring(0,lastIndex);
            parentNode = zkNodes.get(parentPath);
        }
        if(parentNode == null){
            System.out.println("CREATE NODE ERROR! path: "+ path + " , parent path dosn't exist!");
            throw new NodeNotExistException("CREATE NODE ERROR! path: "+ path + " , parent path dosn't exist!");
        }

        //创建新节点
        ZkNode newNode = new ZkNode();
        newNode.setNodeValue(value);
        newNode.setParent(parentNode);
        newNode.setNodePath(path);
        newNode.setObservers();
        this.zkNodes.put(path,newNode);
        //为父节点添加子节点
        parentNode.getChildrenNodes().add(newNode);

    }

    @Override
    public void set_value(String path, String value) throws Exception{
        path = this.formatPath(path);
        ZkNode curNode = this.zkNodes.get(path);
        if( curNode == null ){
            System.out.println("SET NODE ERROR: Node " + path + " dosn't exist!");
            throw new NodeNotExistException("SET NODE ERROR: Node " + path + " dosn't exist!");
        }else{
            curNode.setNodeValue(value);
        }

        //添加更新事件,并通知所有的父节点
        ZkEvent newEvent = new ZkEvent();
        newEvent.setEventPath(path);
        newEvent.setValue(value);

        //为观察者增加事件
        for(ZkNode znode: curNode.getObservers()){
            znode.notifyEvent(newEvent);
        }

    }

    @Override
    public String get_value(String path) throws Exception {
        path = this.formatPath(path);
        ZkNode curNode = this.zkNodes.get(path);
        if( ! this.zkNodes.containsKey(path) ){
            System.out.println("GET VALUE ERROR: Node path " + path + " dosn't exist!");
            throw new NodeNotExistException("GET VALUE ERROR: Node path " + path + " dosn't exist!");

        }
        return curNode.getNodeValue();

    }

    @Override
    public void watch(String path, ICallBack callBack) throws  Exception {
        path = this.formatPath(path);
        if( ! this.zkNodes.containsKey(path) ){
            System.out.println("WATCH ERROR: Node path " + path + " dosn't exist!");
            throw new NodeNotExistException("WATCH ERROR: Node path " + path + " dosn't exist!")  ;
        }

        ZkNode curNode = this.zkNodes.get(path);

        for (ZkEvent event: curNode.getEvents()){
            String ePath = event.getEventPath();
            String eValue = event.getEventValue();
            callBack.process(ePath,eValue);
        }
    }

    private String formatPath(String path){
        if(path.indexOf("//")!=-1){
            return formatPath(path.replace("//","/"));
        }
        return path;
    }

}
