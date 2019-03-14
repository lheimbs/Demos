package com.example.demo;

import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ExternalFile {
    private static String path;

    public ExternalFile(String filePath) {
        path = filePath.replace("/", File.separator);
    }

    private boolean isMediaMounted() {
        String state = Environment.getExternalStorageState();

        if (!Environment.MEDIA_MOUNTED.equals(state))
            return false;
        else
            return true;
    }

    public String writeExternalFile(String text) {
        String retVal;

        if (!isMediaMounted())
            retVal = "NO_MEDIA_MOUNTED";
        else {
            File dir = Environment.getExternalStorageDirectory();
            String filePath = dir.getAbsolutePath() + File.separator + path;

            retVal = filePath;

            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filePath)));
                writer.write(text);
                writer.close();
                retVal += " write successfull";
            }
            catch (IOException e) {
                retVal = "IOException";
            }
        }
        return retVal;
    }

    public String readExternalFile() {
        String retVal;

        if (!isMediaMounted())
            retVal = "NO_MEDIA_MOUNTED";
        else {
            File dir = Environment.getExternalStorageDirectory();
            String filePath = dir.getAbsolutePath() + File.separator + path;
            try {
                FileInputStream fis = new FileInputStream(filePath);
                Scanner scanner = new Scanner(fis, "UTF8").useDelimiter("\\x04");
                retVal = (scanner.hasNext() ? scanner.next() : "");
                try {
                    fis.close();
                } catch (IOException e) { retVal = "IOException"; }
            }
            catch (IOException e) {
                retVal = "IOException";
            }
        }
        return retVal;
    }

    public String deleteExternalFile() {
        String retVal;
        path = path.replace("/", File.separator);

        if (!isMediaMounted())
            retVal = "NO_MEDIA_MOUNTED";
        else {
            File dir = Environment.getExternalStorageDirectory();
            String filePath = dir.getAbsolutePath() + File.separator + path;
            File file = new File(filePath);

            if (!file.delete())
                retVal = "DELETE_FAILED";
            else
                retVal = "DELETE_SUCCESSFULL";
        }
        return retVal;
    }
}
