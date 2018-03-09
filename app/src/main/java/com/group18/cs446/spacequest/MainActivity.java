package com.group18.cs446.spacequest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button game_start_button;
    Button shop_button;

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

        game_start_button.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, GamePlayActivity.class)));

        shop_button = findViewById(R.id.shop_button);
        shop_button.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, ShopActivity.class)));

        View root = findViewById(android.R.id.content);
        root.setSystemUiVisibility(Constants.BASE_UI_VISIBILITY);
    }

}
