package cn.mxranger.mytomcatV1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * ClassName TestServer
 * Author    MxRanger
 * Date      2019/5/14
 * Time      17:00
 */

public class TestServer {
    //1、定义一个变量，存放服务端WebContent目录的绝对路径
    public static String WEB_ROOT = System.getProperty("user.dir") + "/WebContent";

    //2、定义静态变量，用于存放本次请求的静态页面名称
    private static String url = "";

    public static void main(String[] args) throws IOException{
        //System.out.println(WEB_ROOT);
        ServerSocket serverSocket = null;
        Socket socket = null;
        OutputStream out = null;
        InputStream in = null;

        try {
            //3、 建立一个socket对象，监听本机的8080端口
            serverSocket = new ServerSocket(8080);
            while (true){
                //4、 等待来自客户端的请求获取和客户端对应的Socket对象
                socket = serverSocket.accept();
                //System.out.println("socket:"+socket.toString());
                //5、 通过获取到的Socket对象获取到输入、输出流对象
                out = socket.getOutputStream();
                in = socket.getInputStream();

                //6、 获取HTTP协议的请求部分，截取客户端要访问的资源名称，将这个资源名称赋值给url
                parse(in);

                //7、发送静态资源
                sendStaticResource(out);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //[6]释放资源
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
        }
    }


    /*
    * 发送静态资源
    * */
    private static void sendStaticResource(OutputStream out) throws IOException {
        //定义一个字节数组，用于存放本次请求的静态资源demo01.html的内容
        byte[] bytes = new byte[2018];
        // 定义一个文件输入流，用户获取静态资源demoOl.html中的内容
        FileInputStream fis = null;
        try {
            //创建文件对象File,代表本次要请求的资源demo01.html
            File file = new File(WEB_ROOT,url);
            // 如果文件存在
            if (file.exists()){
                //向客户端输出HTTP协议的响应行/响应头
                out.write("HTTP/1.1 200 OK\n".getBytes());
                out.write("Content-Type:text/html;charset=utf-8\n".getBytes());
                out.write("Server:Apache-Coyote/1.1\n".getBytes());
                //响应头和响应组之前是有换行的
                out.write("\n".getBytes());
                // 获取到文件输入流对象
                fis = new FileInputStream(file);
                //读取静态资源demo01.html中的内容到数组中
                int ch = fis.read(bytes);
                while (ch!=-1){
                    // 将读取到数组中的内容通过输出流发送到客户端
                    out.write(bytes,0,ch);
                    ch = fis.read(bytes);
                }

            }else{
                // 如果文件不存在
                // 向客户端响应文件不存在消息
                out.write("HTTP/1.1 404 not found\n".getBytes());
                out.write("Content-Type:text/html;charset=utf-8\n".getBytes());
                out.write("Server:Apache-Coyote/1.1\n".getBytes());
                out.write("\n".getBytes());
                String errMsg = "file not found";
                out.write(errMsg.getBytes());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //释放文件输入流对象
            if(fis!=null){
                fis.close();
                fis = null;
            }

        }



    }
    /*
    *  获取HTTP协议的请求部分，截取客户端要访问的资源名称，将这个资源名称赋值给url
    * */
    private static void parse(InputStream in) throws IOException {
        //定义一个变量，存放HTTP协议谪求部分数据
        StringBuffer content = new StringBuffer(2048);
        //定义一个数组，存放HTTP协议请求部分数据
        byte[] buffer = new byte[2048];
        //定义一个变量i,代表读取数据到数组中之后，教据量的大小
        int i=-1;
        //读取客户端发送过来的数据，将数据读取到字节数组buffer中.i代表读取数据量的大小311字节
        i = in.read(buffer);
        // 通历字节数组，将数组中的数据追加到content变量中
        for (int j=0;j<i;j++){
            content.append((char)buffer[j]);
        }
        // 打印HTTP协议请求部分数据
        System.out.println("content::"+content);
        //截取客尸端要请求的资源路径demo.html，复制给url
        parseUrl(content.toString());
    }

    private static void parseUrl(String content) {
        //存放请求行的2个空格的位置
        int index1,index2;
        //获取http请求部分第1空格的位置
        index1 = content.indexOf(" ");
        if(index1!=-1){
            //获取http请求部分第2空格的位置
            index2 = content.indexOf(" ",index1 + 1);
            if(index2 > index1){
                //截取客尸端要请求的资源名称
                url = content.substring(index1 + 2 , index2);
            }
        }
    }

}
