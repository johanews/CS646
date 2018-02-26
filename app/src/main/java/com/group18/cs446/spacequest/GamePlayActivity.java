package com.group18.cs446.spacequest;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.group18.cs446.spacequest.view.GameView;

public class GamePlayActivity extends AppCompatActivity implements View.OnTouchListener{

    private GameView gameView;

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_activity);
        gameView = findViewById(R.id.game_view);
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
