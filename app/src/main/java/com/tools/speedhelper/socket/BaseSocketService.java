package com.tools.speedhelper.socket;

import java.net.Socket;

/**
 * Created by zhuyinan on 2016/4/13.
 */
public abstract class BaseSocketService {


    protected Socket socket;
    public boolean runFlag;


    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    /**
     * 停止服务
     */
    public abstract void onStop();

    /**
     * 开始服务
     */
    public abstract void onStart();

    /**
     * 发送信息
     *
     * @param msg
     * @return
     */
    public abstract boolean onSend(String msg);


    /**
     * 接收到数据
     */
    public abstract void onReceive(String msg);

    /**
     * 连接断开
     */
    public abstract void onDisconnect();

    /**
     * 连接建立
     */
    public abstract void onConnectWin();


    /**
     * 连接失败
     */
    public abstract void onConnectFail(Exception e);


}
