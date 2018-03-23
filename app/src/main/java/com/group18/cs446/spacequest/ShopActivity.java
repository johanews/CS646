package com.group18.cs446.spacequest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.group18.cs446.spacequest.game.enums.Engines;
import com.group18.cs446.spacequest.game.enums.Hulls;
import com.group18.cs446.spacequest.game.enums.Shields;
import com.group18.cs446.spacequest.game.enums.Weapons;
import com.group18.cs446.spacequest.game.objects.player.ComponentFactory;
import com.group18.cs446.spacequest.game.objects.player.Engine;
import com.group18.cs446.spacequest.game.objects.player.Hull;
import com.group18.cs446.spacequest.game.objects.player.PlayerInfo;
import com.group18.cs446.spacequest.game.objects.player.Shield;
import com.group18.cs446.spacequest.game.objects.player.Weapon;

public class ShopActivity extends AppCompatActivity implements View.OnClickListener {

    SelectedItems selectedItems;
    TextView moneyField;
    private PlayerInfo playerInfo;
    ComponentFactory factory;
    Button play_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_layout);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View root = findViewById(android.R.id.content);
        root.setSystemUiVisibility(Constants.BASE_UI_VISIBILITY);

        factory = new ComponentFactory();

        selectedItems = (SelectedItems) getSupportFragmentManager().
                        findFragmentById(R.id.selected_items_fragment);

        moneyField = findViewById(R.id.money_field);

        play_button = findViewById(R.id.map_button);
        play_button.setOnClickListener(this);

        playerInfo = (PlayerInfo) getIntent().getSerializableExtra("PlayerInfo");

        refresh();
    }

    public PlayerInfo getPlayerInfo() {
        return playerInfo;
    }

    public void getMoney() {

        int money = playerInfo.getMoney();
        String text = getString(R.string.money) + ": " + money;
        moneyField.setText(text);
    }

    public void getWeapon() {

        Weapons type = playerInfo.getWeapon();
        Weapon weapon = factory.getWeaponComponent(type,getBaseContext());

        selectedItems.getWeaponFragment().setImage(weapon.getBitmap());
        selectedItems.getWeaponFragment().setTitle(weapon.getName());
    }

    public void getEngine() {

        Engines type = playerInfo.getEngine();
        Engine engine = factory.getEngineComponent(type, getBaseContext());

        selectedItems.getEngineFragment().setImage(engine.getBitmap());
        selectedItems.getEngineFragment().setTitle(engine.getName());
    }

    public void getShield() {

        Shields type = playerInfo.getShield();
        Shield shield = factory.getShieldComponent(type, getBaseContext());

        selectedItems.getShieldFragment().setImage(shield.getBitmap());
        selectedItems.getShieldFragment().setTitle(shield.getName());
    }

    public void getHull() {

        Hulls type = playerInfo.getHull();
        Hull hull = factory.getHullComponent(type, getBaseContext());

        selectedItems.getHullFragment().setImage(hull.getBitmap());
        selectedItems.getHullFragment().setTitle(hull.getName());
    }

    public void refresh() {
        getMoney();
        getWeapon();
        getEngine();
        getShield();
        getHull();
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(this, GamePlayActivity.class);
        intent.putExtra("PlayerInfo", playerInfo);

        startActivity(intent);
        finish();
    }

}
