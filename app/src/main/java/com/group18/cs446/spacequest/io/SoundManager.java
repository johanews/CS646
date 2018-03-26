package com.group18.cs446.spacequest.io;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.media.audiofx.AudioEffect;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.group18.cs446.spacequest.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class SoundManager {
    public static final int MENU_MUSIC = R.raw.sos_020;
    public static final int GAME_MUSIC_1 = R.raw.dstt_2ndballad;

    public static final int PLAYER_DEATH = R.raw.playerdeath;
    public static final int FIRE_WEAPON = R.raw.fireweapon;
    private static Map<Integer, Integer> sounds = new HashMap<>();
    private static Map<Integer, MediaPlayer> songs = new HashMap<>();
    private static Map<MediaPlayer, Float> vols = new HashMap<>();

    private static float MAX_MUSIC_VOLUME = 0.5f;
    private static AudioAttributes attributes = new AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build();
    private static SoundPool soundPool = soundPool = new SoundPool.Builder()
            .setAudioAttributes(attributes)
            .build();

    public static void init(Context context){
        int sound = FIRE_WEAPON;
        int soundId = soundPool.load(context, sound, 1);
        sounds.put(sound,soundId);
        sound = PLAYER_DEATH;
        soundId = soundPool.load(context, sound, 1);
        sounds.put(sound,soundId);
    }

    public static void playSound(int sound, Context context){

        System.out.println("Sound");
        Integer soundId = sounds.get(sound);
        if(soundId == null){
            System.out.println("New Sound");
            soundId = soundPool.load(context, sound, 1);
            sounds.put(sound,soundId);
        }
        soundPool.play(soundId, 1, 1, 0, 0, 1);
    }

    public static void startSongLoop(int song, Context context){
        System.out.println("Start Song Loop");
        MediaPlayer mp = songs.get(song);
        if(mp == null){
            System.out.println("New Song");
            mp = MediaPlayer.create(context, song);
            songs.put(song,mp);
            vols.put(mp, MAX_MUSIC_VOLUME);
            mp.setVolume(MAX_MUSIC_VOLUME, MAX_MUSIC_VOLUME);
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
                        if (vols.get(prevMP) < 0.1f) {
                            System.out.println("terminating");
                            vols.put(prevMP, MAX_MUSIC_VOLUME);
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
        vols.put(prevMP, vols.get(prevMP) - (0.05f));
    }
    public static void endSongLoop(int song){
        MediaPlayer mp = songs.get(song);
        if(mp != null && mp.isPlaying()) {
            mp.pause();
        }
    }
}
