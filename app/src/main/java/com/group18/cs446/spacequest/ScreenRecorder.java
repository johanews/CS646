package com.group18.cs446.spacequest;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.util.DisplayMetrics;
import android.util.Log;

import com.group18.cs446.spacequest.io.FileHandler;

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

    public void init(Activity activity) {
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenDensity = metrics.densityDpi;

        System.out.println(FileHandler.createVideoFile(activity.getApplicationContext()));

        mediaRecorder = new MediaRecorder();
        initRecorder();

        mediaProjectionManager = (MediaProjectionManager) activity.getSystemService(Context.MEDIA_PROJECTION_SERVICE);

    }

    private void initRecorder() {
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        mediaRecorder.setVideoEncodingBitRate(512 * 1000);
        mediaRecorder.setVideoFrameRate(30);
        mediaRecorder.setVideoSize(metrics.widthPixels, metrics.heightPixels);
        mediaRecorder.setOutputFile(FileHandler.longVideoFileName);
        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startRecording(Activity activity) {
        if (mediaProjection == null) {
            activity.startActivityForResult(mediaProjectionManager.createScreenCaptureIntent(), SCREEN_CAPTURE_PERMISSION_CODE);
            return;
        }
        virtualDisplay = createVirtualDisplay();
        mediaRecorder.start();
        Log.i("spacequest", "Recording started");
    }

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
        mediaProjection.registerCallback(mediaProjectionCallback, null);
        virtualDisplay = createVirtualDisplay();
        mediaRecorder.start();
        Log.i("spacequest", "Recording started");
    }

    public void destroy() {
        if (mediaProjection != null) {
            mediaProjection.stop();
            mediaProjection = null;
        }
    }

    public void stop() {
        mediaRecorder.stop();
        mediaRecorder.reset();

        if (virtualDisplay == null) {
            return;
        }

        virtualDisplay.release();
        Log.i("spacequest", "Recording stopped");
    }

    private VirtualDisplay createVirtualDisplay() {
        return mediaProjection.createVirtualDisplay("GamePlayActivity", metrics.widthPixels, metrics.heightPixels,
                screenDensity, DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR, mediaRecorder.getSurface(), null, null);
    }

    private MediaProjection.Callback mediaProjectionCallback = new MediaProjection.Callback() {
        @Override
        public void onStop() {
            super.onStop();

            mediaRecorder.stop();
            mediaRecorder.reset();
            initRecorder();
            mediaProjection = null;
            virtualDisplay.release();
        }
    };
}
