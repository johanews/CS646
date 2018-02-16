package com.group18.cs446.spacequest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.group18.cs446.spacequest.views.GameView;

public class GamePlayActivity extends AppCompatActivity implements View.OnTouchListener {

    private GameView gameView;

    private Boolean right = false;
    private Boolean left = false;

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

        right.setOnTouchListener(this);
        left.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {

        if(view.getId() == R.id.go_right) {

            switch(event.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    right = true;
                    if(!left)
                        setCommand(PlayerCommand.RIGHT);
                    return true;

                case MotionEvent.ACTION_UP:
                    right = false;
                    if(left)
                        setCommand(PlayerCommand.LEFT);
                    else
                        setCommand(PlayerCommand.NONE);
                    return true;

                default: break;
            }
        }

        else {

            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    left = true;
                    if(!right)
                        setCommand(PlayerCommand.LEFT);
                    return true;

                case MotionEvent.ACTION_UP:
                    left = false;
                    if(right)
                        setCommand(PlayerCommand.RIGHT);
                    else
                        setCommand(PlayerCommand.NONE);
                    return true;

                default: break;
            }
        }

        if(left && right)
            setCommand(PlayerCommand.NONE);

        return true;
    }

    public void setCommand(PlayerCommand c) {
        gameView.getPlayer().setCurrentCommand(c);
    }

}
