package com.group18.cs446.spacequest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button game_start_button;

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("MAIN RESUMING");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        game_start_button = findViewById(R.id.game_start_button);
        game_start_button.setOnClickListener(this);
        View root = findViewById(android.R.id.content);
        root.setSystemUiVisibility(Constants.BASE_UI_VISIBILITY);
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this, GamePlayActivity.class));
    }
}
