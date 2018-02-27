package com.group18.cs446.spacequest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView gameStartButton;
    ImageView shopButton;
    ImageView setting_button;

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("MAIN RESUMING");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameStartButton = findViewById(R.id.game_start_image);
        gameStartButton.setOnClickListener(this);
        View root = findViewById(android.R.id.content);
        root.setSystemUiVisibility(Constants.BASE_UI_VISIBILITY);

        shopButton = findViewById(R.id.shop_button);
        shopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ShopActivity.class));
            }
        });

//        setting_button = findViewById(R.id.setting_button);
//        setting_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this, SettingActivity.class));
//            }
//        });

    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this, ShopActivity.class));
    }

}
