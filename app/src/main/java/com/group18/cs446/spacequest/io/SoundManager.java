package com.group18.cs446.spacequest.io;

import android.content.Context;
import android.media.MediaPlayer;

import com.group18.cs446.spacequest.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class SoundManager {
    public static final int MENU_MUSIC = R.raw.sos_020;
    public static final int GAME_MUSIC_1 = R.raw.dstt_2ndballad;
    private static Map<Integer, MediaPlayer> songs = new HashMap<>();
    private static Map<MediaPlayer, Float> vols = new HashMap<>();

    public static void startSongLoop(int song, Context context){
        System.out.println("Start Song Loop");
        MediaPlayer mp = songs.get(song);
        if(mp == null){
            System.out.println("New Song");
            mp = MediaPlayer.create(context, song);
            songs.put(song,mp);
            vols.put(mp, 1f);
        }
        if(!mp.isPlaying()) {
            if(!mp.isLooping()) mp.setLooping(true);
            mp.start();
        }
        for(MediaPlayer prevMP : songs.values()){
            if(prevMP.isPlaying() && prevMP != mp) {
                final Timer timer = new Timer(true);
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        prevMP.setVolume(vols.get(prevMP), vols.get(prevMP));
                        //Cancel and Purge the Timer if the desired volume has been reached
                        decrementVolume(prevMP);
                        System.out.println("decremented");
                        if (vols.get(prevMP) < 1f/10f) {
                            System.out.println("terminating");
                            vols.put(prevMP, 1f);
                            prevMP.setVolume(vols.get(prevMP), vols.get(prevMP));
                            prevMP.pause();
                            timer.cancel();
                            timer.purge();
                        }
                    }
                };

                timer.schedule(timerTask, 55, 55);
            }
        }
    }
    private static void decrementVolume(MediaPlayer prevMP){
        vols.put(prevMP, vols.get(prevMP) - (1f/10f));
    }
    public static void endSongLoop(int song){
        MediaPlayer mp = songs.get(song);
        if(mp != null && mp.isPlaying()) {
            mp.pause();
        }
    }
}
