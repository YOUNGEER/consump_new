package com.youyou.xiaofeibao.version2;

import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.youyou.xiaofeibao.R;
import com.youyou.xiaofeibao.framework.activity.BaseTitleActivity;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 作者：young on 2016/10/24 17:49
 */

public class WebViewDemo extends BaseTitleActivity {

    @ViewInject(R.id.webview)
    WebView mWebView;

    @Override
    protected int getTitleText() {
        return R.string.share;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.v2_webview_demo;
    }

    @Override
    protected void initData() {
        super.initData();
        //辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度等
        WebChromeClient chromeClient = new WebChromeClient();

    }


    private void socketTest() {
        try {
            //1.创建一个服务器端Socket，即ServerSocket，指定绑定的端口，并监听此端口
            ServerSocket serverSocket = new ServerSocket(12345);
            InetAddress address = InetAddress.getLocalHost();
            String ip = address.getHostAddress();
            Socket socket = null;
            //2.调用accept()等待客户端连接
            System.out.println("~~~服务端已就绪，等待客户端接入~，服务端ip地址: " + ip);
            socket = serverSocket.accept();
            //3.连接后获取输入流，读取客户端信息
            InputStream is = null;
            InputStreamReader isr = null;
            BufferedReader br = null;
            OutputStream os = null;
            PrintWriter pw = null;
            is = socket.getInputStream();     //获取输入流
            isr = new InputStreamReader(is, "UTF-8");
            br = new BufferedReader(isr);
            String info = null;
            while ((info = br.readLine()) != null) {//循环读取客户端的信息
                System.out.println("客户端发送过来的信息" + info);
            }
            socket.shutdownInput();//关闭输入流
            socket.close();
        } catch (Exception e) {
            System.out.println("异常类型" + e.getMessage());
        }
    }
}
