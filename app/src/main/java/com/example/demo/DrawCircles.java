package com.example.demo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.example.heimbsle69869.demo.R;

import java.util.ArrayList;

public class DrawCircles extends AppCompatActivity {
    private MySurfaceView mySurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_circles);
        mySurfaceView = new MySurfaceView(this);
        setContentView(mySurfaceView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mySurfaceView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mySurfaceView.stop();
    }

    private class MySurfaceView extends SurfaceView implements Runnable {
        private ArrayList<Circle> circles = null;
        private Paint paint;
        private SurfaceHolder holder;
        private Thread thread = null;
        private volatile boolean running =false;

        public MySurfaceView(Context c) {
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
                    makeCircles(100, canvas);
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





    /*
    private static class CirclesView extends View
    {
        private Paint paint;
        private ArrayList<Circle> circles = null;

        public CirclesView(Context c) {
            super(c);
            paint = new Paint();
            paint.setAntiAlias(true);
        }

        protected void onDraw(Canvas canvas) {
            if (circles == null) {
                makeCircles(100, canvas);
            }

            drawCircles(canvas);
            moveCircles();

            invalidate();
        }

        private void makeCircles(int numCircles, Canvas canvas) {
            circles = new ArrayList<>();
            for (int i = 0; i < numCircles; i++) {
                circles.add(new Circle(canvas.getWidth(), canvas.getHeight()));
            }
        }

        private void drawCircles(Canvas canvas) {
            paint.setStyle(Paint.Style.FILL);
            for (Circle c : circles) {
                paint.setColor(c.color());
                canvas.drawCircle(c.x(), c.y(), c.radius(), paint);
            }
        }

        private void moveCircles() {
            for (Circle c : circles) {
                c.move();
            }
        }

    }*/
}
