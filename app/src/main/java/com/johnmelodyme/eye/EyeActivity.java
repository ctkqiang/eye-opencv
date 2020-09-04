package com.johnmelodyme.eye;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import org.opencv.android.OpenCVLoader;

public class EyeActivity extends AppCompatActivity {
    private static final String err = "WARNING";
    private static final String ok = "LOG";

    static {
        if (!OpenCVLoader.initDebug()) {
            Log.d(err, "Unable to load OpenCV");
        } else {
            Log.d(ok, "OpenCV loaded");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}