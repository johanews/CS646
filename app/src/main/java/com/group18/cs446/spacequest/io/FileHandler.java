package com.group18.cs446.spacequest.io;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.group18.cs446.spacequest.game.objects.player.PlayerInfo;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileHandler {

    private static final String PLAYER_FILE_NAME = "player_data";
    public static final String VIDEO_FILE_NAME = "spacequest-recording.mp4";

    public static String longVideoFileName = "";

    public static boolean wipeSave(Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(PLAYER_FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(null);
            oos.close();
            fos.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean savePlayer(PlayerInfo player, Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(PLAYER_FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(player);
            oos.close();
            fos.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static PlayerInfo loadPlayer(Context context) {
        PlayerInfo player = null;
        try {
            FileInputStream fis = context.openFileInput(PLAYER_FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            player = (PlayerInfo) ois.readObject();
            ois.close();
            fis.close();
        } catch (FileNotFoundException e) {
            // File not created
            createSaveFile(context);
        } catch (EOFException e) {
            // No saved data found
            return null;
        } catch (IOException | ClassNotFoundException e) {
            // Actual error reading or closing the streams
            e.printStackTrace();
        }

        return player;
    }

    private static void createSaveFile(Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(PLAYER_FILE_NAME, Context.MODE_PRIVATE);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean createVideoFile(Context context) {
        File videoFile = new File(context.getFilesDir(), Long.toString(System.currentTimeMillis()) + VIDEO_FILE_NAME);
        longVideoFileName = videoFile.getAbsolutePath();

        return true;
        /*boolean result = false;

        if (!isExternalStorageWritable()) {
            System.out.println("Can't write to external storage");
            return false;
        }

        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_MOVIES),
                Long.toString(System.currentTimeMillis()) + VIDEO_FILE_NAME);

        longVideoFileName = file.getAbsolutePath();
        Log.i("spacequest", "Video file path: " + longVideoFileName);

        if (file.exists()) {
            result = file.delete();
        }

            /*if (!file.exists() || result)
                result = file.createNewFile();

        if (!file.mkdirs()) {
            Log.e("spacequest", "Directory not created");
            result = false;
        }

        return result;*/
    }

    /* Checks if external storage is available for read and write */
    private static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

}
