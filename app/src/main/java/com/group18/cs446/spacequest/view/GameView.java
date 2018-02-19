package com.group18.cs446.spacequest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.group18.cs446.spacequest.PlayerCommand;
import com.group18.cs446.spacequest.game.objects.GameEntity;
import com.group18.cs446.spacequest.game.objects.ParticleEffect;
import com.group18.cs446.spacequest.game.objects.Player;
import com.group18.cs446.spacequest.game.objects.Sector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Owen on 2018-02-08.
 */

public class GameView extends SurfaceView implements Runnable {

    private boolean running = false;
    private Player player; // The player that is consistent between sectors
    private Sector sector; // The current sector
    private Thread gameThread = null;
    private int tickRate;

    private SurfaceHolder surfaceHolder;

    private int canvasWidth, canvasHeight;
    private long gameTick;

    public GameView(Context context, int screenX, int screenY) {
        super(context);
        player = new Player(context); // new player
        surfaceHolder = getHolder();

    }

    @Override
    public void run() {
        while(running) {
            sector = new Sector(player, getContext(), surfaceHolder);
            boolean successfulSector = sector.run();
            if(successfulSector) { // returns true if successful, false otherwise
                // Do all the store stuff
                player.reset();

            } else {
                // Update Highscores
                player.reset();// this will reset the player for the next sector - do things like keep gold be delete damage and upgrades etc
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasWidth = w;
        canvasHeight = h;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float x = e.getX();
        boolean left = (x < canvasWidth/2);
        int pointerIndex = e.getActionIndex();
        int pointerId = e.getPointerId(pointerIndex);
        int maskedAction = e.getActionMasked();
        switch (maskedAction) {

            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN: {
                player.addCommand(left ? PlayerCommand.LEFT : PlayerCommand.RIGHT);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                player.addCommand(left ? PlayerCommand.LEFT : PlayerCommand.RIGHT);
                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:{
                player.removeCommand(left ? PlayerCommand.LEFT : PlayerCommand.RIGHT);
                break;
            }
        }
        return true;
    }

    public void pause(){
        running = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
        }
    }
    public void resume(){
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }
}
