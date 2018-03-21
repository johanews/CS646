package com.group18.cs446.spacequest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.group18.cs446.spacequest.game.enums.Engines;
import com.group18.cs446.spacequest.game.enums.Hulls;
import com.group18.cs446.spacequest.game.enums.Shields;
import com.group18.cs446.spacequest.game.enums.Weapons;
import com.group18.cs446.spacequest.game.objects.player.PlayerInfo;
import com.group18.cs446.spacequest.io.FileHandler;
import com.group18.cs446.spacequest.social.FacebookActivity;
import com.group18.cs446.spacequest.io.SoundManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView gameStartButton;
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
            playerInfo.setWeapon(Weapons.BASIC_LASER);
            playerInfo.setShield(Shields.BASIC_SHIELD);
            playerInfo.setHull(Hulls.BASIC_HULL);
            playerInfo.setEngine(Engines.BASIC_ENGINE);
            playerInfo.setMoney(0);
        }

        gameStartButton = findViewById(R.id.game_start_image);
        gameStartButton.setOnClickListener(this);

        View root = findViewById(android.R.id.content);
        root.setSystemUiVisibility(Constants.BASE_UI_VISIBILITY);

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
