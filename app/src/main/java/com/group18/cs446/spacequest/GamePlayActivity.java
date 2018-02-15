package com.group18.cs446.spacequest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.group18.cs446.spacequest.views.GameView;

public class GamePlayActivity extends AppCompatActivity implements View.OnTouchListener {

    private GameView gameView;
    private Button right;
    private Button left;

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

        if(event.getAction() == MotionEvent.ACTION_UP) {

            if(right.isPressed())
                setCommand(PlayerCommand.RIGHT);
            else if(left.isPressed())
                setCommand(PlayerCommand.LEFT);
            else
                setCommand(PlayerCommand.NONE);
        }

        else if(event.getAction() == MotionEvent.ACTION_DOWN) {

            switch(view.getId()) {

                case R.id.go_right:
                    if(!left.isPressed())
                        setCommand(PlayerCommand.RIGHT);
                    break;

                case R.id.go_left:
                    if(!right.isPressed())
                        setCommand(PlayerCommand.RIGHT);
                    break;

                default:
                    setCommand(PlayerCommand.NONE);
                    break;
            }
        }

        return false;
    }

    public void setCommand(PlayerCommand c) {
        gameView.getPlayer().setCurrentCommand(c);
    }


}
