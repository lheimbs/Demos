package com.example.demo;

import android.graphics.Color;
import android.graphics.PointF;

import java.util.Random;

public class Circle {
    private static Random rand = new Random();
    private final float radius;
    private final PointF maxPos = new PointF();
    private PointF position = new PointF();
    private PointF speed =new PointF();
    private int color;

    public Circle(int xMax, int yMax) {
        maxPos.x = xMax;
        maxPos.y = yMax;
        final float vMax = 10;
        position.x = xMax*rand.nextFloat();
        position.y = yMax*rand.nextFloat();
        speed.x = vMax*rand.nextFloat();
        speed.y = vMax*rand.nextFloat();
        radius = xMax*(0.1f + rand.nextFloat())/10;
        color = Color.argb(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256),rand.nextInt(256));
    }

    public int color() {
        return color;
    }

    public float x() {
        return position.x;
    }

    public float y() {
        return position.y;
    }

    public float radius() {
        return radius;
    }

    public void move() {
        position.x += speed.x;
        position.y += speed.y;
        if (position.x < 0 || position.x >= maxPos.x) {
            speed.x = -speed.x;
        }
        if (position.y < 0 || position.y >= maxPos.y) {
            speed.y = -speed.y;
        }
    }
}