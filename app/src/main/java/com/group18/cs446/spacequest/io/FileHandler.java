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

    public static File createVideoFile(Context context) {
        File root = context.getFilesDir();
        File images = new File(root, "images");
        if(!images.exists()){
            images.mkdir();
        }
        String id = "_"+System.currentTimeMillis();
        File file = new File(images, "savedVideo"+id+"."+"mp4");
        return file;
    }

    public static void clearVideoRepos(Context context){
        File root = context.getFilesDir();
        File images = new File(root, "images");
        if(!images.exists()){
            images.mkdir();
        }
        for(File f : images.listFiles()){
            f.delete();
        }

    }
}