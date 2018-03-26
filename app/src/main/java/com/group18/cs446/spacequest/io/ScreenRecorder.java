package com.group18.cs446.spacequest.io;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.group18.cs446.spacequest.io.FileHandler;

import java.io.File;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class ScreenRecorder {

    private static final int SCREEN_CAPTURE_PERMISSION_CODE = 1;
    private static final String TAG = "ScreenRecorder";

    private int screenDensity;
    private DisplayMetrics metrics = new DisplayMetrics();
    private MediaRecorder mediaRecorder;
    private VirtualDisplay virtualDisplay;
    private MediaProjectionManager mediaProjectionManager;
    private MediaProjection mediaProjection;
    private File destinationFile;
    private boolean init = false;

    public void init(Activity activity) {
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenDensity = metrics.densityDpi;
        mediaRecorder = new MediaRecorder();
        destinationFile = FileHandler.createVideoFile(activity.getApplicationContext());
        initRecorder();

        mediaProjectionManager = (MediaProjectionManager) activity.getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ActionBar actionBar = activity.getActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(false); // disable the button
            actionBar.setDisplayHomeAsUpEnabled(false); // remove the left caret
            actionBar.setDisplayShowHomeEnabled(false); // remove the icon
        }

    }

    private void initRecorder() {
        if(!init) {
            try {
                init = true;
                mediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
                mediaRecorder.setVideoEncodingBitRate((int) (8 * 1280 * 720 * 25 * 0.07));//512 * 1000);
                mediaRecorder.setVideoFrameRate(25);
                mediaRecorder.setVideoSize(metrics.widthPixels, metrics.heightPixels);
                mediaRecorder.setOutputFile(destinationFile.getAbsolutePath());
                mediaRecorder.prepare();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void startRecording(Activity activity) {
        if (mediaProjection == null) {
            activity.startActivityForResult(mediaProjectionManager.createScreenCaptureIntent(), SCREEN_CAPTURE_PERMISSION_CODE);
            return;
        }
        virtualDisplay = createVirtualDisplay();
        //mediaRecorder.start();
        Log.i("spacequest", "Recording started");
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void handleActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != SCREEN_CAPTURE_PERMISSION_CODE) {
            Log.e(TAG, "Unknown request: " + requestCode);
            return;
        }

        if (resultCode != RESULT_OK) {
            Log.e(TAG, "Permission denied");
            return;
        }

        mediaProjection = mediaProjectionManager.getMediaProjection(resultCode, data);
        //mediaProjection.registerCallback(mediaProjectionCallback, null);
        //initRecorder();
        virtualDisplay = createVirtualDisplay();
        mediaRecorder.start();
        Log.i("spacequest", "Recording started");
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void destroy() {
        if (mediaProjection != null) {
            mediaProjection.stop();
            mediaProjection = null;
        }
    }

    public File stop() {
        try {
            mediaRecorder.stop();
            mediaRecorder.reset();
        } catch (Exception e){
            e.printStackTrace();
        }

        if (virtualDisplay == null) {
            return null;
        }

        virtualDisplay.release();
        Log.i("spacequest", "Recording stopped");
        return destinationFile;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private VirtualDisplay createVirtualDisplay() {
        return mediaProjection.createVirtualDisplay("GamePlayActivity", metrics.widthPixels, metrics.heightPixels,
                screenDensity, DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR, mediaRecorder.getSurface(), null, null);
    }

    /*private MediaProjection.Callback mediaProjectionCallback = new MediaProjection.Callback() {
        @Override
        public void onStop() {
            super.onStop();
            try {
                mediaRecorder.stop();
                mediaRecorder.reset();
            } catch (Exception e){
                e.printStackTrace();
            }
            initRecorder();
            mediaProjection = null;
            virtualDisplay.release();
        }
    };*/
}
