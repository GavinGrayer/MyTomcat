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

public class AAServlet implements Servlet {
    @Override
    public void init() {
        System.out.println("AAServlet...init");
    }

    @Override
    public void Service(InputStream in, OutputStream out) throws IOException {
        System.out.println("AAServlet...service");
        out.write("hello world AAServlet".getBytes());
        out.flush();
    }

    @Override
    public void destory() {
        System.out.println("AAServlet...destory");
    }
}
