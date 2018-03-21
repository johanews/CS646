package com.group18.cs446.spacequest.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.group18.cs446.spacequest.Constants;
import com.group18.cs446.spacequest.MainActivity;
import com.group18.cs446.spacequest.R;
import com.group18.cs446.spacequest.ShopActivity;
import com.group18.cs446.spacequest.game.enums.PlayerCommand;
import com.group18.cs446.spacequest.game.objects.Sector;
import com.group18.cs446.spacequest.game.objects.player.Player;
import com.group18.cs446.spacequest.game.objects.player.PlayerInfo;
import com.group18.cs446.spacequest.io.FileHandler;
import com.group18.cs446.spacequest.social.FacebookActivity;

public class GameView extends SurfaceView implements Runnable {

    private boolean running = false;
    private Player player; // The player that is consistent between sectors
    private Sector sector; // The current sector
    private Thread gameThread = null;
    private int tickRate;
    private Boolean right = false;
    private Boolean left = false;

    private SurfaceHolder surfaceHolder;

    private int canvasWidth, canvasHeight;
    private long gameTick;

    private PlayerInfo playerInfo;
    private Activity gameplayActivity;

    public GameView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        surfaceHolder = getHolder();
        setSystemUiVisibility(Constants.BASE_UI_VISIBILITY);
    }

    public void init(PlayerInfo playerInfo, Activity gameplayActivity){
        this.playerInfo = new PlayerInfo(playerInfo);
        this.gameplayActivity = gameplayActivity;
        this.player = new Player(getContext(), playerInfo); // new player
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public void run() {
        sector = new Sector(player, getContext(), surfaceHolder, playerInfo.getCurrentSector(), gameplayActivity);
        boolean successfulSector = sector.run();
        System.out.println("SECTOR END");
        if(successfulSector) { // returns true if successful, false otherwise
            // Do all the store stuff
            PlayerInfo newPlayerInfo = player.createPlayerInfo();
            newPlayerInfo.setCurrentSector(playerInfo.getCurrentSector()+1);

            if (!FileHandler.savePlayer(newPlayerInfo, getContext())){
                System.err.println("Failed to save player info");
            }

            Intent intent = new Intent(gameplayActivity, FacebookActivity.class);
            intent.putExtra("PlayerInfo", playerInfo);
            intent.putExtra("nextActivity", "shop");
            gameplayActivity.startActivity(intent);
            gameplayActivity.finish();
         } else {
            // Update Highscores and wipe save
            if (!FileHandler.wipeSave(getContext())){
                System.err.println("Failed to wipeplayer info");
            }
            PlayerInfo newPlayerInfo = player.createPlayerInfo();
            newPlayerInfo.setCurrentSector(playerInfo.getCurrentSector()+1);
            newPlayerInfo.setCurrentSector(-1);
            Intent intent = new Intent(gameplayActivity, FacebookActivity.class);
            intent.putExtra("PlayerInfo", playerInfo);
            intent.putExtra("nextActivity", "main");
            gameplayActivity.startActivity(intent);
            gameplayActivity.finish();
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
}