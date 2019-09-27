package com.example.demo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.ToggleButton;

import com.example.heimbsle69869.demo.R;


public class WS15Activity2 extends AppCompatActivity {
    private TBPresser tbp;
    public ToggleButton tb1, tb2, tb3, tb4, tb5;
    public SeekBar sb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ws15_activity_2);

        tb1 = findViewById(R.id.toggleButton1);
        tb2 = findViewById(R.id.toggleButton2);
        tb3 = findViewById(R.id.toggleButton3);
        tb4 = findViewById(R.id.toggleButton4);
        tb5 = findViewById(R.id.toggleButton5);
        sb = findViewById(R.id.seekBar);

        tb1.setChecked(true);
        tb2.setChecked(false);
        tb3.setChecked(false);
        tb4.setChecked(false);
        tb5.setChecked(false);

        tbp = new TBPresser();
        tbp.execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        View.OnClickListener tbListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tbp.cancel(true);
                tb1.setChecked(false);
                tb2.setChecked(false);
                tb3.setChecked(false);
                tb4.setChecked(false);
                tb5.setChecked(false);
            }
        };

        tb1.setOnClickListener(tbListener);
        tb2.setOnClickListener(tbListener);
        tb3.setOnClickListener(tbListener);
        tb4.setOnClickListener(tbListener);
        tb5.setOnClickListener(tbListener);
    }

    @Override
    public void onBackPressed() {
        tbp.cancel(true);

        Intent intent = new Intent();

        if (tbp.getStatus() == AsyncTask.Status.FINISHED)
            setResult(0, intent);
        else {
            setResult(tbp.counter / 10, intent);
        }

        super.onBackPressed();
    }

    public class TBPresser extends AsyncTask<Void, Integer, Integer> {
        public int counter = 10;

        @Override
        protected Integer doInBackground(Void... voids) {

            while (true) {
                if (isCancelled())
                    break;
                if (counter > 50)
                    counter = 1;

                publishProgress(counter);
                counter++;

                try {
                    Thread.sleep(100);
                }
                catch (Exception e) { e.printStackTrace(); }
            }
            return counter/10;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int counter = values[0];

            Log.i("counter", Integer.toString(counter-(counter/10) * 10));
            sb.setProgress(counter-(counter/10) * 10);

            if (counter % 10 == 0) {
                shiftTB(counter/10);
            }
        }

        private void shiftTB(int tbIndex) {
            Log.i("Index", Integer.toString(tbIndex));
            if (tbIndex == 1) {
                tb1.setChecked(true);
                tb2.setChecked(false);
                tb3.setChecked(false);
                tb4.setChecked(false);
                tb5.setChecked(false);
            }
            else if (tbIndex == 2) {
                tb1.setChecked(false);
                tb2.setChecked(true);
                tb3.setChecked(false);
                tb4.setChecked(false);
                tb5.setChecked(false);
            }
            else if (tbIndex == 3) {
                tb1.setChecked(false);
                tb2.setChecked(false);
                tb3.setChecked(true);
                tb4.setChecked(false);
                tb5.setChecked(false);
            }
            else if (tbIndex == 4) {
                tb1.setChecked(false);
                tb2.setChecked(false);
                tb3.setChecked(false);
                tb4.setChecked(true);
                tb5.setChecked(false);
            }
            else if (tbIndex == 5) {
                tb1.setChecked(false);
                tb2.setChecked(false);
                tb3.setChecked(false);
                tb4.setChecked(false);
                tb5.setChecked(true);
            }
        }
    }


}
