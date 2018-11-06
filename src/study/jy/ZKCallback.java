package study.jy;

import study.jy.Interfaces.ICallBack;

public class ZKCallback implements ICallBack {

        public void process(String path,String value){
            System.out.println( "Event! path:" + path + " was reset as " + value );
        }


}
