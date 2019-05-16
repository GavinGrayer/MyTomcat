package cn.mxranger.mytomcatV2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * ClassName AAServlet
 * Author    MxRanger
 * Date      2019/5/15
 * Time      20:20
 */

public class BBServlet implements Servlet {
    @Override
    public void init() {
        System.out.println("BBServlet...init");
    }

    @Override
    public void Service(InputStream in, OutputStream out) throws IOException {
        System.out.println("BBServlet...service");
        out.write("hello world BBServlet".getBytes());
        out.flush();
    }

    @Override
    public void destory() {
        System.out.println("BBServlet...destory");
    }
}
