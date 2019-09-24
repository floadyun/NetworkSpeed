package com.tools.speedhelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.base.lib.baseui.AppBaseActivity;
import com.base.lib.util.AbStrUtil;
import com.tools.speedhelper.service.SocketService;
import com.tools.speedhelper.util.Util;
import com.tools.speedhelper.widget.SineWave;
import com.tools.speedlib.SpeedManager;
import com.tools.speedlib.listener.NetDelayListener;
import com.tools.speedlib.listener.SpeedListener;
import com.tools.speedlib.utils.ConverUtil;
import com.tools.speedlib.views.AwesomeSpeedView;

public class MainActivity extends AppBaseActivity {
    private AwesomeSpeedView speedometer;
    private TextView downloadText,downloadUnitText,uploadText,uploadUnitText,delayText;
    private LinearLayout startLayout;
    private SineWave downloadWave,uploadWave;
    SpeedManager speedManager;
    private long firstTime;
    private int clickCount = 0;
    private String delayTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        speedometer = findViewById(R.id.speedometer);

        downloadText = findViewById(R.id.download_speed_text);
        downloadUnitText = findViewById(R.id.download_unit_text);
        uploadText = findViewById(R.id.upload_speed_text);
        uploadUnitText = findViewById(R.id.upload_unit_text);
        delayText = findViewById(R.id.delay_text);
        downloadWave = findViewById(R.id.speed_download_view);
        uploadWave = findViewById(R.id.speed_upload_view);
        startLayout = findViewById(R.id.start_layout);

        findViewById(R.id.start_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });
        startService();
    }
    /**
     * 启动服务
     */
    public void startService(){
        Intent intent = new Intent(this, SocketService.class);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            startForegroundService(intent);
        } else {
            startService(intent);
        }
    }
    /**
     * 跳转至设置
     */
    public void gotoSetting(View view){
        clickCount++;
        if((System.currentTimeMillis()-firstTime) > 3000)  //System.currentTimeMillis()无论何时调用，肯定大于2000
        {
            firstTime = System.currentTimeMillis();
            clickCount = 0;
        }else{
            if(clickCount==1){
                clickCount = 0;
                startActivity(new Intent(this,SettingActivity.class));
            }
        }
    }
    private void start(){
        startLayout.setVisibility(View.GONE);
        downloadWave.Set(Util.centerEndX);
        uploadWave.Set(Util.centerEndY);
        speedManager = new SpeedManager.Builder()
                .setNetDelayListener(new NetDelayListener() {
                    @Override
                    public void result(String delay) {
                        delayTime = AbStrUtil.formatDouble(Double.valueOf(delay),0);
                        delayText.setText(delayTime);
                    }
                })
                .setSpeedListener(new SpeedListener() {
                    @Override
                    public void onStart() {
                        downloadWave.clearData();
                        uploadWave.clearData();
                    }
                    @Override
                    public void speeding(long downSpeed, long upSpeed) {
                        setSpeedText(downSpeed,upSpeed);
                    }
                    @Override
                    public void finishSpeed(long finalDownSpeed, long finalUpSpeed) {
                        setSpeedText(finalDownSpeed,finalUpSpeed);
                        startLayout.setVisibility(View.VISIBLE);
                    }
                })
                .setPindCmd("59.61.92.196")
                .setSpeedCount(100)
                .setSpeedTimeOut(2000)
                .builder();
        speedManager.startSpeed();
    }
    /**
     * 设置上传下载速度
     * @param downSpeed
     * @param upSpeed
     */
    private void setSpeedText(long downSpeed, long upSpeed){
        String[] downResult = ConverUtil.formatSpeed(downSpeed);
        double dSpeed = Double.valueOf(downResult[0]);
        if(dSpeed<500){//保证速率500以上
            dSpeed = dSpeed+(5-(int) dSpeed/100)*100;
        }
        double uSpeed = dSpeed/3;
        String downText = ConverUtil.roundByScale(dSpeed,2);
        String upText = ConverUtil.roundByScale(uSpeed,2);
        downloadText.setText(downText);
        downloadUnitText.setText(downResult[1]);
        setSpeedView(downSpeed,dSpeed,downResult);

      //  String[] upResult = ConverUtil.formatSpeed(upSpeed);
        uploadText.setText(upText);
        uploadUnitText.setText(downResult[1]);
        downloadWave.Set((int)dSpeed);
        uploadWave.Set((int)uSpeed);

        SocketService.getVRService().sendMessageToServer("DL="+downText+downResult[1]+",UP="+upText+downResult[1]+",Ping="+delayTime+"ms");
    }
    private void setSpeedView(long speed,double downSpeed, String[] result) {
        if (null != result && 2 == result.length) {
            speedometer.setCurrentSpeed(ConverUtil.roundByScale(downSpeed,2));
            speedometer.setUnit(result[1]);
//            speedometer.speedPercentTo(ConverUtil.getSpeedPercent(speed));
            speedometer.speedPercentTo((int) (downSpeed*100/1000));
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(speedManager!=null){
            speedManager.finishSpeed();
        }
    }
}
