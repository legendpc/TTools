package com.yzd.tantantool.service;

import android.accessibilityservice.AccessibilityService;
import android.graphics.Rect;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;
import android.widget.Toast;


import com.yzd.tantantool.R;

import java.util.List;


public class MyAutoService extends AccessibilityService {
    private final static String TAG = "MyAutoService";
    private final static String PACKAGE_NAME_TANTAN = "com.p1.mobile.putong";
    private AccessibilityNodeInfo accessibilityNodeInfo;
    private AccessibilityNodeInfo mLastButton;
    private AccessibilityNodeInfo mLoveButton;

    private android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            boolean isPerformed = accessibilityNodeInfo.performAction(GESTURE_SWIPE_RIGHT);
            Log.d(TAG, "isPeformed :" + isPerformed);
        }
    };

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        String packageName = event.getPackageName().toString();
        event.getSource();

        if (PACKAGE_NAME_TANTAN.equals(packageName)) {
            Log.d(TAG, event.getPackageName().toString() + event.getEventType());
            switch (event.getEventType()) {
                case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                    AccessibilityNodeInfo mNodeInfo = event.getSource();
                    Rect rect = new Rect();
                    mNodeInfo.getBoundsInScreen(rect);
                    boolean isPerformed = mNodeInfo.performAction(GESTURE_SWIPE_RIGHT);
                    accessibilityNodeInfo = mNodeInfo;
                    traversalNodeInfo(mNodeInfo);
                    if (mLoveButton != null) {
                        Log.d(TAG, "last lovebutton class name:" + mLoveButton.getClassName().toString() + "\n");
                        performLoveButtonClick(mLoveButton);
                    }
                    Log.d(TAG, "child counts is :" + mNodeInfo.getChildCount() + "\nrect is :" + rect.toString() + "\n performed is :" + isPerformed);
                    break;
            }
        }
    }

    private void performLoveButtonClick(final AccessibilityNodeInfo mLoveButton) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        boolean isClicked = mLoveButton.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        Log.d(TAG, "love Button is clicked = " + isClicked);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void traversalNodeInfo(AccessibilityNodeInfo mNodeInfo) {
        int mChildCount = mNodeInfo.getChildCount();
        mLastButton = mNodeInfo;
        if (mLoveButton != null)
            if (mChildCount > 0) {
                for (int i = 0; i < mChildCount; i++) {
                    if (i - 1 > 0) {
                        mLoveButton = mNodeInfo.getChild(i - 1);
                    }
                    traversalNodeInfo(mNodeInfo.getChild(i));
                }
            }
    }

    @Override
    public void onInterrupt() {
        Toast.makeText(this, getResources().getString(R.string.service_interuppted), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Toast.makeText(this, getResources().getString(R.string.service_connected), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected boolean onGesture(int gestureId) {
        return super.onGesture(gestureId);
    }

    @Override
    protected boolean onKeyEvent(KeyEvent event) {
        return super.onKeyEvent(event);
    }

    @Override
    public List<AccessibilityWindowInfo> getWindows() {
        return super.getWindows();
    }

    @Override
    public AccessibilityNodeInfo getRootInActiveWindow() {
        return super.getRootInActiveWindow();
    }

    @Override
    public AccessibilityNodeInfo findFocus(int focus) {
        return super.findFocus(focus);
    }

    private void performSwipeRight(final AccessibilityNodeInfo accessibilityNodeInfo) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        mHandler.sendEmptyMessage(0);
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }

}
