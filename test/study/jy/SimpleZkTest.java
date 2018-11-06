package study.jy;
import org.junit.Test;
import study.jy.Exceptions.DuplicatePathException;
import study.jy.Exceptions.IllegalPathException;
import study.jy.Exceptions.NodeNotExistException;

import java.util.concurrent.ArrayBlockingQueue;


public class SimpleZkTest {

    @Test(expected = IllegalPathException.class)
    public void test_create_pathIllegal() throws Exception {
        SimpleZk zk = new SimpleZk();
        zk.create("a/bc", "illegal");
    }

    @Test(expected = NodeNotExistException.class)
    public void test_create_noParentPath() throws Exception {
        SimpleZk zk = new SimpleZk();
        zk.create("/b/c", "nop");
    }

    @Test(expected = DuplicatePathException.class)
    public void test_create_duplicatePath() throws Exception {
        SimpleZk zk = new SimpleZk();
        zk.create("/a", "A");
        zk.create("/a", "A1");
    }


    @Test
    public void test_create_printNodes() throws Exception {
        SimpleZk zk = new SimpleZk();
        zk.create("/a", "A");
        zk.create("/a//b", "AB");
        zk.create("//a/c", "AC");
        zk.create("/a/b/c", "ABC");
        zk.create("/a/b/d", "ABD");

        ArrayBlockingQueue<ZkNode> queue = new ArrayBlockingQueue<ZkNode>(100);
        queue.add(zk.getRootNode());
        while (!queue.isEmpty()) {
            ZkNode node = queue.remove();
            System.out.println("node path: " + node.getNodePath() + " , node value: " + node.getNodeValue());
            for (ZkNode tnode : node.getChildrenNodes()) {
                queue.add(tnode);
            }
        }
    }


    @Test(expected = NodeNotExistException.class)
    public void test_setValue_nodeNotExist() throws Exception{
        SimpleZk zk = new SimpleZk();
        zk.set_value("/c","C");

    }

    @Test
    public void test_setValue() throws Exception {
        SimpleZk zk = new SimpleZk();

        zk.create("/a","A");
        zk.set_value("/a", "A1");
        System.out.println("value of /a reset as " + zk.getZkNodes().get("/a").getNodeValue());
    }


    @Test(expected = NodeNotExistException.class)
    public  void test_getValue_pathNotExist() throws Exception {
        SimpleZk zk = new SimpleZk();
        zk.create("/a","A");
        zk.get_value("/a/c");
    }

    @Test
    public void test_getValue() throws Exception {
        SimpleZk zk = new SimpleZk();
        zk.create("/a","A");
        zk.create("/a/b","AB");

        String strAB = zk.get_value("/a/b");
        System.out.println("path value of /a/b is " + strAB);
    }



    @Test(expected = NodeNotExistException.class)
    public  void test_watch_pathNotExist() throws Exception {
        SimpleZk zk = new SimpleZk();
        //zk.create("/a","A");
        zk.watch("/a/c",new ZKCallback());
    }


    @Test
    public void test_watch() throws Exception {
        SimpleZk zk = new SimpleZk();

        zk.create("/a","A");
        zk.create("/a/b","AB");
        zk.create("/a/b/c","ABC");

        zk.set_value("/a","A1");
        zk.set_value("/a/b","AB1");
        zk.set_value("/a/b","AB2");
        zk.set_value("/a/b/c","ABC1");
        zk.set_value("/a/b/c","ABC2");
        zk.set_value("/a/b/c","ABC3");

        ZKCallback callback = new ZKCallback();
        zk.watch("/a",callback);

    }

}