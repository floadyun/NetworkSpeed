package com.tools.speedhelper;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import com.base.lib.baseui.AppBaseActivity;
import com.base.lib.util.ToastUtil;
import com.tools.speedhelper.service.SocketService;
import com.tools.speedhelper.socket.StaticUtil;

/**
 * @copyright : 深圳市喜投金融服务有限公司
 * Created by yixf on 2019/6/12
 * @description:IP设置
 */
public class SettingActivity extends AppBaseActivity {

    private EditText ipText, portText,phoneText,intervalText;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_setting);

        ipText = findViewById(R.id.ip_text);
        portText = findViewById(R.id.port_text);
        phoneText = findViewById(R.id.phone_id_text);
        intervalText = findViewById(R.id.interval_text);

        preferences = getSharedPreferences(
                StaticUtil.REAL_9, Context.MODE_PRIVATE);

        ipText.setText(preferences.getString("ip",StaticUtil.SOCKET_IP));
        portText.setText(""+preferences.getInt("port", StaticUtil.SOCKET_PORT));
        phoneText.setText(preferences.getString("phoneId","1"));
        intervalText.setText(preferences.getString("interval",StaticUtil.INTEVAL));
    }
    public void settingOperate(View view){
        switch (view.getId()){
            case R.id.setting_confirm_btn:
                if(saveIpConfig(ipText.getText().toString(),portText.getText().toString(),phoneText.getText().toString(),intervalText.getText().toString())){
                    showToastText("配置成功");
                    finish();
                }
                break;
            case R.id.setting_cancel_btn:
                finish();
                break;
        }
    }
    public boolean saveIpConfig(String ip, String port, String phoneId, String interval) {
        boolean isConfig = false;
        if (!ip.equals("") && !port.equals("")&& !TextUtils.isEmpty(phoneId)&&!TextUtils.isEmpty(interval)) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("ip", ip);
            editor.putInt("port", Integer.valueOf(port));
            editor.putString("phoneId",phoneId);
            editor.putString("interval",interval);
            editor.commit();

            SocketService.getVRService().changeConfig(ip,port);

            isConfig = true;
        }else{
            ToastUtil.showToast(this,"请输入正确的配置信息");
        }
        return isConfig;
    }
}
