package com.group18.cs446.spacequest.io;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class VideoCaptureBuffer {
    View view;
    Context context;
    List<File> savedFiles = new LinkedList<>();
    File savedImage = null;
    File images;
    int frame = 0;
    String filePrefix;
    public VideoCaptureBuffer(View view, Context context){
        this.view = view;
        this.context = context;
        File root = context.getFilesDir();
        images = new File(root, "images");
        if(!images.exists()){
            images.mkdir();
        }
        filePrefix = "Save"+System.currentTimeMillis()+"_";
    }
    private File getTempFile(Context context, String fileName, String suffix) {
        File file = null;
        file = new File(images, fileName+"."+suffix);
        //savedFiles.add(file);
        savedImage = file;
        return file;
    }
    public void captureFrame(Bitmap bitmap){
        int thisFrame = frame;
        AsyncTask.execute(() -> {
            File imageFile = getTempFile(context, filePrefix, "jpg");
            if(imageFile.exists()){
                imageFile.delete();
            }
            try {
                FileOutputStream outputStream = null;
                outputStream = new FileOutputStream(imageFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                outputStream.flush();
                outputStream.close();
                bitmap.recycle();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public File finalizeVideo(){
        return savedImage;//savedFiles.get(savedFiles.size()/2);
    }

}
