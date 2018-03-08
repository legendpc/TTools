package com.yzd.tantantool;

import android.app.Notification;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yzd.tantantool.service.FakeService;
import com.yzd.tantantool.service.TanTanMonitorService;


public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";

    private final static int NOTIFICATION_ID = 1;

    private TanTanMonitorService mMonitorService;
    private FakeService mFakeService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    private void init() {

        mMonitorService = new TanTanMonitorService();

        mMonitorService.startForeground(NOTIFICATION_ID, new Notification.Builder(this).setContentTitle(getResources().getString(R.string.service_started)).setTicker(getResources().getText(R.string.tip)).build());
    }

}
