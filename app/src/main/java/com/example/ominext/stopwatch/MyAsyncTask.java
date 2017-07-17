package com.example.ominext.stopwatch;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.widget.TextView;

/**
 * Created by Ominext on 7/14/2017.
 */

public class MyAsyncTask extends AsyncTask<Void, Integer, Void> {
    Activity activity;

    public MyAsyncTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        for (int i = 0; i <= 1000; i++) {
            SystemClock.sleep(1000);
            publishProgress(i);
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        int value = values[0];
//        TextView textView = (TextView) activity.findViewById(R.id.text_milliseconds);
//        textView.setText(value);
    }


}
