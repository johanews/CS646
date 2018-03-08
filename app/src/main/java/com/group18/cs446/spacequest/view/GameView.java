package com.group18.cs446.spacequest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.group18.cs446.spacequest.Constants;
import com.group18.cs446.spacequest.R;
import com.group18.cs446.spacequest.game.enums.PlayerCommand;
import com.group18.cs446.spacequest.game.objects.player.Player;
import com.group18.cs446.spacequest.game.objects.Sector;

public class GameView extends SurfaceView implements Runnable {

    private boolean running = false;
    private Player player; // The player that is consistent between sectors
    private Sector sector; // The current sector
    private Thread gameThread = null;
    private int tickRate;

    private SurfaceHolder surfaceHolder;

    private int canvasWidth, canvasHeight;
    private long gameTick;

    private int currentSector;

    public GameView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        player = new Player(context); // new player
        surfaceHolder = getHolder();
        setSystemUiVisibility(Constants.BASE_UI_VISIBILITY);
        currentSector = 0;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public void run() {
        while(running) {
            currentSector++;
            sector = new Sector(player, getContext(), surfaceHolder, currentSector);
            boolean successfulSector = sector.run();
            System.out.println("SECTOR END");
            if(successfulSector) { // returns true if successful, false otherwise
                // Do all the store stuff
                player.reset();

            } else {
                // Update Highscores
                player = new Player(getContext());
                currentSector = 0;
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasWidth = w;
        canvasHeight = h;
    }

    public boolean handleButtonEvent(int buttonId, MotionEvent e){
        if(sector != null){
            sector.unpause();
        }
        int maskedAction = e.getActionMasked();
        if(buttonId == R.id.go_left || buttonId == R.id.go_right) {
            switch (maskedAction) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN: {
                    player.addCommand((buttonId == R.id.go_left) ? PlayerCommand.LEFT : PlayerCommand.RIGHT);
                    break;
                }
                case MotionEvent.ACTION_MOVE: {
                    player.addCommand((buttonId == R.id.go_left) ? PlayerCommand.LEFT : PlayerCommand.RIGHT);
                    break;
                }
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP:
                case MotionEvent.ACTION_CANCEL: {
                    player.removeCommand((buttonId == R.id.go_left) ? PlayerCommand.LEFT : PlayerCommand.RIGHT);
                    break;
                }
            }
        } else if(buttonId == R.id.activate_ability){
            switch (maskedAction) {
                case MotionEvent.ACTION_DOWN:
                    player.doAction();
                    break;
                case MotionEvent.ACTION_UP:
                    player.stopAction();
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
            }
        } else { // Unknown button
            return false;
        }
        return true;
    }

    public void pause(){
        if(sector != null) sector.pause();
    }
    public void start(){
        running = true;
        if(gameThread == null || !gameThread.isAlive()) {
            gameThread = new Thread(this);
            gameThread.start();
        }
    }
    public void resume(){
        //if(sector != null) sector.unpause();
    }
    public void stop(){
        /*running = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }
}
