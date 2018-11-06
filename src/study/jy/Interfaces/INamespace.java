package study.jy.Interfaces;


import study.jy.ZKCallback;

public interface INamespace {
    public abstract void create(String path,String value) throws  Exception;
    public abstract void set_value(String path,String value) throws Exception;
    public abstract String get_value(String path) throws Exception;
    public abstract void watch(String path, ICallBack callback) throws Exception;


}
