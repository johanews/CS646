package com.group18.cs446.spacequest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.group18.cs446.spacequest.game.objects.player.ComponentFactory;
import com.group18.cs446.spacequest.game.objects.player.PlayerInfo;
import com.group18.cs446.spacequest.io.FileHandler;
import com.group18.cs446.spacequest.io.SoundManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView gameStartButton;
    ImageView shopButton;
    ImageView setting_button;

    PlayerInfo playerInfo;

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("MAIN RESUMING");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SoundManager.startSongLoop(SoundManager.MENU_MUSIC, getBaseContext());

        playerInfo = FileHandler.loadPlayer(getApplicationContext());
        if (playerInfo == null) {
            playerInfo = new PlayerInfo();
            playerInfo.setWeapon(ComponentFactory.BASIC_LASER);
            playerInfo.setShield(ComponentFactory.BASIC_SHIELD);
            playerInfo.setHull(ComponentFactory.BASIC_HULL);
            playerInfo.setEngine(ComponentFactory.BASIC_ENGINE);
            playerInfo.setMoney(0);
        }

        gameStartButton = findViewById(R.id.game_start_image);
        gameStartButton.setOnClickListener(this);

        View root = findViewById(android.R.id.content);
        root.setSystemUiVisibility(Constants.BASE_UI_VISIBILITY);

        shopButton = findViewById(R.id.shop_button);
        shopButton.setOnClickListener((View v) -> startActivity(
                new Intent(MainActivity.this, ShopActivity.class)));

        setting_button = findViewById(R.id.setting_button);
        setting_button.setOnClickListener((View v) -> startActivity(
                new Intent(MainActivity.this, SettingActivity.class)));
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,ShopActivity.class);
        intent.putExtra("PlayerInfo", playerInfo);
        startActivity(intent);
        finish();
    }
}
