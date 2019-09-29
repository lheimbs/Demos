package com.example.demo;

import android.util.Log;
import android.widget.TextView;

class WorkerUsingThread implements Runnable {
    TextView textView;
    private volatile boolean running = false;
    private Thread thread;
    private String threadName = "worker thread";

    private void print(final String s) {
    /*    runOnUiThread(new Runnable() {
            @Override
            public void run() {
            if (textView != null)
                textView.setText(s);
            else
                Log.d("Info", "WorkerThread " + s);
            }
        });*/
    }

    @Override
    public void run() {
        int i = 0;
        while(running) {
            i++;
            print(String.valueOf(i));
            try {
                Thread.sleep(100);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
        print("" + i);
    }

    void start() {
        running =true;
        thread =new Thread(this);
        thread.setName(threadName);
        thread.start();
    }

    void stop() {
        running =false;
        while(true) {
            try {
                thread.join();
                break;
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}