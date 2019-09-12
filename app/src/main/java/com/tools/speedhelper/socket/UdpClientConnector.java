package com.tools.speedhelper.socket;

import android.os.Handler;
import android.os.Message;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by hadisi on 2016/7/22.
 */

public class UdpClientConnector {

    private static UdpClientConnector mUdpClientConnector;

    private ConnectLinstener mListener;

    private Thread mSendThread;

    private byte receiveData[] = new byte[1024];
    private String mSendHexString;

    private boolean isSend = false;

    private String serverIp;

    private int serverPort;

    public interface ConnectLinstener {
        void onReceiveData(String data);
    }

    public void setOnConnectLinstener(ConnectLinstener linstener) {
        this.mListener = linstener;
    }

    public static UdpClientConnector getInstance() {
        if (mUdpClientConnector == null) {
            mUdpClientConnector = new UdpClientConnector();
        }
        return mUdpClientConnector;
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1000:
                    if (mListener != null) {
                        mListener.onReceiveData(msg.getData().getString("data"));
                    }
                    break;
            }
        }
    };
    /**
     * 创建udp发送连接（服务端ip地址、端口号、超时时间）
     */
    public void createConnector(String ip,int port) {
        this.serverIp = ip;
        this.serverPort = port;
        if (mSendThread == null) {
            mSendThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        if (!isSend)
                            continue;
                        DatagramSocket socket = null;
                        try {
                            socket = new DatagramSocket();
                            socket.setSoTimeout(10*1000);
                            InetAddress serverAddress = InetAddress.getByName(serverIp);
                            byte data[] = mSendHexString.getBytes("utf-8");
                            DatagramPacket sendPacket = new DatagramPacket(data, data.length, serverAddress, serverPort);
                            socket.send(sendPacket);
                            socket.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        isSend = false;
                    }
                }
            });
            mSendThread.start();
        }
    }
    /**
     * 发送数据
     *
     * @param str
     */
    public void sendStr(final String str) {
        mSendHexString = str;
        isSend = true;
    }
}
