package com.example.demo;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.example.heimbsle69869.demo.R;

import java.util.LinkedList;

public class SS17 extends AppCompatActivity {
    private SeekBar circlesSeekBar;
    private CirclesFragment circlesFragment;
    private SharedViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(this.getClass().getName());
        setContentView(R.layout.activity_ss17);

        model = ViewModelProviders.of(this).get(SharedViewModel.class);

        circlesFragment = (CirclesFragment) getSupportFragmentManager().findFragmentById(R.id.circlesFragment);
        circlesFragment.getView().setBackgroundColor(Color.WHITE);

        circlesSeekBar = findViewById(R.id.circlesSeekBar);
        circlesSeekBar.setProgress(0);
        model.setNumCircles(0);
        circlesSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                model.setNumCircles(progress);
                //currentCircles = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
         });
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = p.edit();
        editor.putInt("currentCircles", circlesSeekBar.getProgress());
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(this);

        int currCircles = p.getInt("currentCircles", 0);
        circlesSeekBar.setProgress(currCircles);
    }

    public static class CirclesFragment extends Fragment {
        private CircleSurfaceView circleSurfaceView;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            circleSurfaceView = new CircleSurfaceView(getActivity());
            circleSurfaceView.setZOrderOnTop(true);
            circleSurfaceView.setBackgroundColor(Color.WHITE);
            //circleSurfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
            return circleSurfaceView;
        }

        @Override
        public void onResume() {
            super.onResume();
            circleSurfaceView.start();
        }

        @Override
        public void onPause() {
            super.onPause();
            circleSurfaceView.stop();
        }

        private class CircleSurfaceView extends SurfaceView implements Runnable {
            private LinkedList<Circle> circles;
            private Paint paint;
            private SurfaceHolder holder;
            private Thread thread = null;
            private volatile boolean running =false;
            private SharedViewModel model;
            private int currentCircles;
            private int newCircles;
            private int xMax, yMax;


            public CircleSurfaceView(Context c) {
                super(c);
                circles = new LinkedList<>();
                model = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
                holder = getHolder();
                holder.addCallback(new SurfaceHolder.Callback() {
                    @Override
                    public void surfaceCreated(SurfaceHolder surfaceHolder) {
                        Log.i("NumCircles", "SurfaceCreated");
                        Canvas canvas = surfaceHolder.lockCanvas();
                        xMax = canvas.getWidth();
                        yMax = canvas.getHeight();
                        surfaceHolder.unlockCanvasAndPost(canvas);


                        currentCircles = model.getNumCircles();
                        initCircles(currentCircles);

                    }

                    @Override
                    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

                    }

                    @Override
                    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

                    }
                });
                newCircles = circles.size();
                paint = new Paint();
                paint.setAntiAlias(true);
            }

            public void start() {
                running = true;
                thread = new Thread(this);
                thread.start();
            }

            public void stop() {
                running = false;
                while (true) {
                    try {
                        thread.join();
                        break;
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void run() {

                while (running) {
                    newCircles = model.getNumCircles();

                    if (!holder.getSurface().isValid() || circles == null) { continue; }

                    if (newCircles < currentCircles) {
                        for (int i = 0; i<currentCircles-newCircles; i++) {
                            circles.pollLast();
                        }
                        currentCircles = newCircles;
                    }
                    else if (newCircles > currentCircles) {
                        for (int i = 0; i < newCircles - currentCircles; i++) {
                            circles.add(new Circle(xMax, yMax));
                        }
                        currentCircles = newCircles;
                    }
                    Log.i("NumCircles", Integer.toString(circles.size()));
                    Canvas canvas = holder.lockCanvas();
                    drawCircles(canvas);
                    holder.unlockCanvasAndPost(canvas);
                    moveCircles();
                }
            }

            private void initCircles(int numCircles) {
                for (int i = 0; i < numCircles; i++) {
                    circles.add(new Circle(xMax, yMax));
                }
                currentCircles = numCircles;
            }

            private void drawCircles(Canvas canvas) {
                paint.setStyle(Paint.Style.FILL);
                canvas.drawRGB(255,255,255);
                for (Circle c: circles) {
                    paint.setColor(c.color());
                    canvas.drawCircle(c.x(), c.y(), c.radius(), paint);
                }
            }

            private void moveCircles() {
                for (Circle c:circles) {
                    c.move();
                }
            }
        }
    }
}