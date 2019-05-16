package cn.mxranger.mytomcatV2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * ClassName Servlet
 * Author    MxRanger
 * Date      2019/5/15
 * Time      20:17
 */

//所有服务端的java小程序都要实现的接口
public interface Servlet {
    //初始化
    public void init();
    //服务
    public void Service(InputStream in, OutputStream out) throws IOException;
    //销毁
    public void destory();
}
