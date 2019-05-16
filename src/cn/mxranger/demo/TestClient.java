package cn.mxranger.demo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * ClassName TestClient
 * Author    MxRanger
 * Date      2019/5/14
 * Time      16:10
 *
 * 当成浏览器客户端，服务端用的黑马现成的网站做响应
 */

public class TestClient {

    public static void main(String[] args){
        Socket socket = null;
        InputStream in = null;
        OutputStream out = null;
        try {
            //[1] 建立一个socket对象，连接域名80端口
            socket = new Socket("www.baidu.com",80);
            //[2]获取输出流对象
            in = socket.getInputStream();
            //[3]获取输入流对象
            out = socket.getOutputStream();

            //[4] 将http协议的请求部分发送到服务端
            //请求行 /subject/about/index.html
            out.write("GET / HTTP/1.1\n".getBytes());
            //请求头
            out.write("HOST:www.baidu.com\n".getBytes());
            out.write("\n".getBytes());

            //[5]读取来自服务端的数据打印到控制台
            int i = in.read();
            while (i!=-1){
                System.out.print((char)i);
                i = in.read();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //[6]释放资源
            try {
                if(null!=in){
                    in.close();
                    in = null;
                }
                if(null!=out){
                    out.close();
                    out = null;
                }
                if(null!=socket){
                    socket.close();
                    socket = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
