package com.group18.cs446.spacequest;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.group18.cs446.spacequest.game.objects.player.PlayerInfo;
import com.group18.cs446.spacequest.io.SoundManager;
import com.group18.cs446.spacequest.view.GameView;

public class GamePlayActivity extends AppCompatActivity implements View.OnTouchListener {

    private GameView gameView;
    private PlayerInfo playerInfo;

    private Boolean right = false;
    private Boolean left = false;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        gameView.getScreenRecorder().handleActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        System.out.println("PAUSING");
        super.onPause();
        gameView.pause();

    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("STOPPING");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("RESTARTING");
    }
    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("STARTING");
        gameView.start();
        SoundManager.startSongLoop(SoundManager.GAME_MUSIC_1, getBaseContext());
    }

    @Override
    protected void onResume() {
        System.out.println("RESUMING");
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        playerInfo = (PlayerInfo) getIntent().getSerializableExtra("PlayerInfo");

        setContentView(R.layout.play_activity);
        gameView = findViewById(R.id.game_view);
        gameView.init(playerInfo, this);
        Button right = findViewById(R.id.go_right);
        Button left = findViewById(R.id.go_left);
        Button action = findViewById(R.id.activate_ability);
        right.setOnTouchListener(this);
        left.setOnTouchListener(this);
        action.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gameView.handleButtonEvent(v.getId(), event);
    }
}
