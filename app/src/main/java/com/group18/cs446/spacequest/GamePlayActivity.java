package com.group18.cs446.spacequest;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;

import com.group18.cs446.spacequest.view.GameView;

public class GamePlayActivity extends AppCompatActivity {

    private GameView gameView;

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
        // Handle pause event for gameview
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
        // handle resume for gameview
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);

        gameView = new GameView(this, size.x, size.y);

        setContentView(gameView);
    }

}
