package com.example.ominext.stopwatch;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView textViewHours;
    TextView textViewSeconds;
    TextView textViewMilliSeconds;
    Button buttonStart;
    Button buttonStop;

    Handler handler;
    AtomicBoolean isRunning=new AtomicBoolean(false);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewHours=(TextView)findViewById(R.id.text_hours);
        textViewSeconds=(TextView)findViewById(R.id.text_seconds);
        textViewMilliSeconds=(TextView)findViewById(R.id.text_milliseconds);
        buttonStart=(Button)findViewById(R.id.button_start);
        buttonStop=(Button)findViewById(R.id.button_stop);
        buttonStart.setOnClickListener(this);
        buttonStop.setOnClickListener(this);
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                textViewSeconds.setText(msg.arg1);
            }
        };
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_start:
                doStart();
            break;
            case R.id.button_stop:
            break;
            default:
                break;
        }
    }

    private void doStart() {
        isRunning.set(false);
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=1;i<=100&&isRunning.get();i++){
                    SystemClock.sleep(1000);
                    Message message=handler.obtainMessage();
                    message.arg1=i;
                    handler.sendMessage(message);
                }
            }
        });
        isRunning.set(true);
        thread.start();
    }
}
