package com.group18.cs446.spacequest.io;

import android.content.Context;

import com.group18.cs446.spacequest.game.objects.Player;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileHandler {

    private static final String PLAYER_FILE_NAME = "player_data";

    public static boolean savePlayer(Player player, Context context) {
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
        System.out.println("Player saved!");
        return true;
    }

    public static Player loadPlayer(Context context) {
        Player player = null;
        try {
            FileInputStream fis = context.openFileInput(PLAYER_FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            player = (Player) ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return player;
    }
}
