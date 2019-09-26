package com.example.demo;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.example.heimbsle69869.demo.R;

import java.util.ArrayList;

public class SS17 extends AppCompatActivity {
    private SeekBar circlesSeekBar;
    private CirclesFragment circlesFragment;
    private SharedViewModel model;
    public int currentCircles = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(this.getClass().getName());
        setContentView(R.layout.activity_ss17);

        model = ViewModelProviders.of(this).get(SharedViewModel.class);

        circlesFragment = (CirclesFragment) getSupportFragmentManager().findFragmentById(R.id.circlesFragment);

        circlesSeekBar = findViewById(R.id.circlesSeekBar);
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

    public static class CirclesFragment extends Fragment {
        private CircleSurfaceView circleSurfaceView;
        private SharedViewModel model;

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            circleSurfaceView = new CircleSurfaceView(getActivity(), model);
            return circleSurfaceView;
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            model = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
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
            private ArrayList<Circle> circles = null;
            private Paint paint;
            private SurfaceHolder holder;
            private Thread thread = null;
            private volatile boolean running =false;

            public CircleSurfaceView(Context c, SharedViewModel model) {
                super(c);
                holder = getHolder();
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
                    if (!holder.getSurface().isValid()) { continue; }
                    Canvas canvas = holder.lockCanvas();
                    if (circles == null) {
                        makeCircles(model.getNumCircles(), canvas);
                    }
                    drawCircles(canvas);
                    holder.unlockCanvasAndPost(canvas);
                    moveCircles();
                }
            }

            private void makeCircles(int numCircles, Canvas canvas) {
                circles = new ArrayList<>();
                for (int i = 0; i < numCircles; i++) {
                    circles.add(new Circle(canvas.getWidth(), canvas.getHeight()));
                }
            }

            private void drawCircles(Canvas canvas) {
                paint.setStyle(Paint.Style.FILL);
                canvas.drawRGB(0,0,0);
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