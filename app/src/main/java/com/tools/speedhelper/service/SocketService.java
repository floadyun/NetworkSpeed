package com.tools.speedhelper.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.base.lib.util.AbStrUtil;
import com.tools.speedhelper.socket.StaticUtil;
import com.tools.speedhelper.socket.UdpClientConnector;

/**
 * VR播放服务
 */
public class SocketService extends Service {

	private static Handler handler;

	private static SocketService mScoketService;

	private static final String channelID = "UpdateCheck_channel_id";
	private static final String channelName = "UpdateCheck_channelname";

	public static SocketService getVRService(){
		return mScoketService;
	}

	private SharedPreferences preferences;

	private UdpClientConnector udpClientConnector;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		handler = new Handler();
		mScoketService = this;

		initUDPSocket();

		preferences = getApplicationContext().getSharedPreferences(
				StaticUtil.REAL_9, Context.MODE_PRIVATE);

		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
			createNotificationChannel();
			Notification notification = new Notification.Builder(getApplicationContext(), channelID).build();
			startForeground(1, notification);
		}
	}
	private void createNotificationChannel(){
		NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		NotificationChannel notificationChannel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_LOW);
		notificationManager.createNotificationChannel(notificationChannel);
	}
	private void initUDPSocket(){
		udpClientConnector = UdpClientConnector.getInstance();
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		udpClientConnector.createConnector(preferences.getString("ip",StaticUtil.SOCKET_IP),preferences.getInt("port",StaticUtil.SOCKET_PORT));
		flags = START_STICKY;
		return super.onStartCommand(intent, flags, startId);
	}
	/**
	 * 发送消息至服务端
	 * @param message
	 */
	public void sendMessageToServer(final String message){
		udpClientConnector.sendStr(message);
	}
	/**
	 * 配置改变的时候调用
	 */
	public void changeConfig(String ip, String port){
		udpClientConnector.createConnector(ip,Integer.valueOf(port));
	}
}
