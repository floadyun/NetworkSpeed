package com.tools.speedhelper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.lib.baseui.AppBaseActivity;
import com.base.lib.util.AbStrUtil;
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
    }
    /**
     * 退出
     */
    public void exitApp(View view){
        finishSelf();
    }
    private void start(){
        downloadWave.Set(Util.centerEndX);
        uploadWave.Set(Util.centerEndY);
        speedManager = new SpeedManager.Builder()
                .setNetDelayListener(new NetDelayListener() {
                    @Override
                    public void result(String delay) {
                        delayText.setText(AbStrUtil.formatDouble(Double.valueOf(delay),0));
                    }
                })
                .setSpeedListener(new SpeedListener() {
                    @Override
                    public void onStart() {
                        startLayout.setVisibility(View.GONE);
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
        downloadText.setText(downResult[0]);
        downloadUnitText.setText(downResult[1]);
        setSpeedView(downSpeed, downResult);

        String[] upResult = ConverUtil.formatSpeed(upSpeed);
        uploadText.setText(upResult[0]);
        uploadUnitText.setText(upResult[1]);
        downloadWave.Set(Double.valueOf(downResult[0]).intValue());
        uploadWave.Set(Double.valueOf(upResult[0]).intValue());
    }
    private void setSpeedView(long speed, String[] result) {
        if (null != result && 2 == result.length) {
            speedometer.setCurrentSpeed(result[0]);
            speedometer.setUnit(result[1]);
            speedometer.speedPercentTo(ConverUtil.getSpeedPercent(speed));
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
