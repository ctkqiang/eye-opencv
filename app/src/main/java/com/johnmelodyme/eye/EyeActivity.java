package com.johnmelodyme.eye;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCamera2View;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class EyeActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {
    private static final String tag = "WARNING";
    Mat MRGBA;
    JavaCamera2View javaCamera2View;
    //https://www.youtube.com/watch?v=Z2vrioEr9OI&list=PLYKqj_5SByj41YzTMOypYMWqyXMSwkXaV&index=2

    BaseLoaderCallback baseLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case BaseLoaderCallback.SUCCESS: {
                    javaCamera2View.enableView();
                    break;
                }

                default: {
                    super.onManagerConnected(status);
                    break;
                }
            }
        }
    };

    /**
     *  Check Open CV
     */
    static {
        if (!OpenCVLoader.initDebug()) {
            Log.d(tag, "Unable to load OpenCV");
        } else {
            Log.d(tag, "OpenCV loaded");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configuration of Java Camera.
        javaCamera2View = (JavaCamera2View) findViewById(R.id.visual);
        javaCamera2View.setVisibility(SurfaceView.VISIBLE);
//        javaCamera2View.setCvCameraViewListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // TODO: Add dester permission
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (javaCamera2View != null) {
            javaCamera2View.disableView();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (javaCamera2View != null) {
            javaCamera2View.disableView();
        }
    }

    /**
     * Dispatch onResume() to fragments.  Note that for better inter-operation
     * with older versions of the platform, at the point of this call the
     * fragments attached to the activity are <em>not</em> resumed.
     */
    @Override
    protected void onResume() {
        super.onResume();

        if (!OpenCVLoader.initDebug()) {
            Log.d(tag, "Unable to load OpenCV");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_4_0, this, baseLoaderCallback);
        } else {
            Log.d(tag, "OpenCV loaded");
            baseLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    /**
     * This method is invoked when camera preview has started. After this method is invoked
     * the frames will start to be delivered to client via the onCameraFrame() callback.
     *
     * @param width  -  the width of the frames that will be delivered
     * @param height - the height of the frames that will be delivered
     */
    @Override
    public void onCameraViewStarted(int width, int height) {
        MRGBA = new Mat(height, width, CvType.CV_8UC4);
    }

    /**
     * This method is invoked when camera preview has been stopped for some reason.
     * No frames will be delivered via onCameraFrame() callback after this method is called.
     */
    @Override
    public void onCameraViewStopped() {
        MRGBA.release();
    }

    /**
     * This method is invoked when delivery of the frame needs to be done.
     * The returned values - is a modified frame which needs to be displayed on the screen.
     * TODO: pass the parameters specifying the format of the frame (BPP, YUV or RGB and etc)
     *
     * @param inputFrame
     */
    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        MRGBA = inputFrame.rgba();
        return MRGBA;
    }
}