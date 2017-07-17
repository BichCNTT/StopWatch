package com.example.ominext.stopwatch;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicBoolean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.text_timer_value)
    TextView textTimerValue;
    @BindView(R.id.button_start)
    Button buttonStart;
    @BindView(R.id.button_stop)
    Button buttonStop;
    @BindView(R.id.button_lap)
    Button buttonLap;
    @BindView(R.id.button_reset)
    Button buttonReset;
    @BindView(R.id.container)
    LinearLayout container;

    Handler handler = new Handler();
    AtomicBoolean isRunning = new AtomicBoolean(false);

    long startTime = 0L;
    long timeSwapBuff = 0L;
    long updateTime = 0L;
    long timeInMilliSeconds = 0L;

    int d = 0;
    Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            //System.currentTimeMillis(): It is system time. If the system is changed to a new time, it will also be changed.
            //SystemClock.uptimeMillis(): The time starts from when device is power on, it does not include the sleep time.
            //SystemClock.elapsedRealtime(): The time starts from when device is power on, it includes the sleep time.
            timeInMilliSeconds = SystemClock.uptimeMillis() - startTime;
            updateTime = timeSwapBuff + timeInMilliSeconds;
            //đổi ra giây
            int secs = (int) (updateTime / 1000);
            //đổi ra phút
            int mins = secs / 60;
            secs %= 60;
            int milliseconds = (int) (updateTime % 1000);
            textTimerValue.setText("" + mins + ":" + secs + ":" + milliseconds);
            handler.postDelayed(this, 0);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.button_start, R.id.button_stop, R.id.button_lap})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_start:
                if (d < 2) {
                    startTime = SystemClock.uptimeMillis();
                    handler.postDelayed(updateTimerThread, 0);
                    d = 2;
                }
                //thời gian tính từ lúc app đc bật lên, ko bao gồm thời gian ngủ
//                doStart();
                break;
            case R.id.button_stop:
                if (d > 0) {
                    timeSwapBuff += timeInMilliSeconds;
                    handler.removeCallbacks(updateTimerThread);
                    d = 0;
                }
                break;
            case R.id.button_lap:
                LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View addView = inflater.inflate(R.layout.row, null);
                TextView textViewValue = (TextView) addView.findViewById(R.id.text_content);
                textViewValue.setText(textTimerValue.getText().toString());
                container.addView(addView);
                break;
        }
    }

    @OnClick(R.id.button_reset)
    public void onViewClicked() {
        textTimerValue.setText("00:00:0000");
        timeInMilliSeconds = 0L;
        timeSwapBuff = 0L;
        startTime = SystemClock.uptimeMillis();
        updateTime = 0L;
        timeSwapBuff += timeInMilliSeconds;
        handler.removeCallbacks(updateTimerThread);
        d = 0;
        container.removeAllViews();
    }
}
