package cn.mxranger.demo;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.TreeSet;

/**
 * ClassName TestServer
 * Author    MxRanger
 * Date      2019/5/14
 * Time      16:26
 *
 * 模拟服务端,接收浏览器请求返回响应数据
 */

public class TestServer {
    public static void main(String[] args){
        ServerSocket serverSocket = null;
        Socket socket = null;
        OutputStream out = null;
        try{
            //[1] 建立一个socket对象，监听本机的8080端口
            serverSocket = new ServerSocket(8080);
           while (true){
               //[2] 等待来自客户端的请求获取和客户端对应的Socket对象
               socket = serverSocket.accept();
               System.out.println("socket:"+socket.toString());
               //[3] 通过获取到的Socket对象获取到输出流对象
               out = socket.getOutputStream();

               //[4] 通过获取到的输出流对象将HTTP协议的相应部分发送到客户端
               out.write("HTTP/1.1 200 OK\n".getBytes());
               out.write("Content-Type:text/html;charset=utf-8\n".getBytes());
               out.write("Server:Apache-Coyote/1.1\n".getBytes());
               //响应头和响应组之前是有换行的
               out.write("\n\n".getBytes());
               StringBuffer buf = new StringBuffer();
               buf.append("<html></html>");
               buf.append("<head><title>标题</title></head>");
               buf.append("<body>");
               buf.append("<h1> hello world</h1>");
               buf.append("<a href='http://www.baidu.com'>百度</a>");
               buf.append("</body>");
               buf.append("</html>");
               out.write(buf.toString().getBytes());
               out.flush();
           }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //[6]释放资源
            try {
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
