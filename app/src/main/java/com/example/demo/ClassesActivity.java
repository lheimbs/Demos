package com.example.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.heimbsle69869.demo.R;

import java.util.Random;

public class ClassesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes);

        A a = new A(2);
        TextView myTextView = findViewById(R.id.myTextView);
        myTextView.setText(a.toString());
    }

    public class A {

        private B[] bs;

        A(int nBs) {
            bs = new B[nBs];
            for (int i = 0; i<nBs; i++) {
                bs[i] = new B();
            }
        }

        public String toString() {
            StringBuilder builder = new StringBuilder();
            for (B i : bs) {
                builder.append(i.toString());
                builder.append("\n");
            }
            return builder.toString();
        }
    }

    public class B {
        private String name;
        private double wert;
        private Random generate = new Random();

        B() {
            wert = generate.nextDouble();
            name = this.getClass().getName();
        }

        public String toString() {
            return name + " - " + Double.toString(wert);
        }
    }
}
