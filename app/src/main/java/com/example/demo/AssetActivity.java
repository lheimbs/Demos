package com.example.demo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.heimbsle69869.demo.R;

import java.io.InputStream;
import java.util.Random;
import java.util.Scanner;

public class AssetActivity extends AppCompatActivity {
    private Logger logger;
    private String text;
    private static Random rand = new Random();
    private static int REQUESTCODE_EXTERNALFILEACCESS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset);

        TextView textView = new TextView(this);
        setContentView(textView);
        logger = new Logger(this.getClass().getSimpleName(), textView, "");

        text = readTextAsset("text.txt");

        if (!isExternalWriteAccessAllowed())
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUESTCODE_EXTERNALFILEACCESS);
        else
            writeExternalStorage(text);
    }

    private boolean isExternalWriteAccessAllowed() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            logger.log("External write access denied!");
            return false;
        }
        else {
            logger.log("External write access granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestcode, String permissions[], int[] grantResults) {
        if (requestcode == REQUESTCODE_EXTERNALFILEACCESS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                logger.log("External write access granted");
                writeExternalStorage(text);
            }
            else
                logger.log("External write access denied!");
        }
    }

    private void writeExternalStorage(String text) {
        ExternalFile extFile = new ExternalFile("randomAssetContent.txt");
        logger.log(extFile.writeExternalFile(text));
    }

    private String readTextAsset(String asset) {
        logger.log("readTextAsset()");
        AssetManager manager = getAssets();
        String text = "";
        try {
            InputStream in = manager.open(asset, AssetManager.ACCESS_RANDOM);
            text = getRandomText(in);
            logger.log(text);
        } catch (Exception e) {
            e.printStackTrace();
            logger.log("Can't open " + asset + "!");
        }
        return text;
    }

    private String getRandomText(InputStream is) {
        Scanner s = new Scanner(is, "UTF8").useDelimiter("\\x04");
        String allText = s.hasNext() ? s.next() : "";
        int len = allText.length();
        int start = rand.nextInt(len-1);
        int end;
        do { end = rand.nextInt((len-start) + 1) + start; } while (end >= len || end <= start);
        return allText.substring(start,end);
    }
}
