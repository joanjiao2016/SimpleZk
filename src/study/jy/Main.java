package study.jy;

import java.util.Date;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        SimpleZk zk = new SimpleZk();
        //String path = zk.formatPath("//a/////c/");
        //System.out.println(path);
	// write your code here
      //  Date curdate = new Date();
       // System.out.println(curdate.toString());
      /*  SimpleZk zk = new SimpleZk();
        System.out.println("root value:"+zk.getRootNode().getValue());
        String path= "/a";
        ZkNode nodea = zk.create(path,"a1");
        ZKCallback callback = new ZKCallback();
        System.out.println("node value: "+ nodea.getValue());

        ZkNode nodeb = zk.create("/a/b","b1");
        System.out.println("nodeb value: "+ nodeb.getValue());

        zk.set_value("/a/b","c1");
        System.out.println("nodeb value: "+ nodeb.getValue());
        zk.watch("/a/b",callback);
        String valuea=zk.get_value("/a");
        System.out.println("valuea: "+valuea);

        System.out.println("child of a is :"+nodea.getChildrenNodes().get("/a/b").getValue());
        System.out.println("parent of b is :" + nodeb.getParent().getValue());
        HashMap<String,ZkNode> zkNodes = zk.getZkNodes();
        if (zkNodes.containsKey("/a")) {
            String testV = zkNodes.get("/a").getValue();
            System.out.println("valuetestv:" + testV);
        }

        //ZKCallback callback = new ZKCallback();
        zk.watch("/a",callback);

       /* int lastIndex = path.lastIndexOf("/");
        System.out.println("lastIndex is : "+ lastIndex);
        String parentPath = path.substring(0,lastIndex);
        System.out.println("parentPath is : "+ parentPath);
        if ( parentPath == null){
            System.out.println("null");
        }*/
    }
}
