package com.group18.cs446.spacequest;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.group18.cs446.spacequest.game.objects.player.Engine;
import com.group18.cs446.spacequest.game.objects.player.Hull;
import com.group18.cs446.spacequest.game.objects.player.Player;
import com.group18.cs446.spacequest.game.objects.player.PlayerInfo;
import com.group18.cs446.spacequest.game.objects.player.Shield;
import com.group18.cs446.spacequest.game.objects.player.Weapon;

public class ShopActivity extends AppCompatActivity implements View.OnClickListener {

    SelectedItems selectedItems;
    PlayerInfo playerInfo;

    Button play_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_layout);

        View root = findViewById(android.R.id.content);
        root.setSystemUiVisibility(Constants.BASE_UI_VISIBILITY);

        selectedItems = (SelectedItems) getSupportFragmentManager().
                        findFragmentById(R.id.selected_items_fragment);

        play_button = findViewById(R.id.map_button);
        play_button.setOnClickListener(this);

        playerInfo = (PlayerInfo) getIntent().getSerializableExtra("PlayerInfo");

        getWeapon();
        getEngine();
        getShield();
        getHull();
    }

    public void getWeapon() {

        // int type = player.getWeapon();
        // Weapon weapon = EquipmentFactory().getWeapon(type);

        // selectedItems.getWeaponFragment().setImage(weapon.getImage());
        // selectedItems.getWeaponFragment().setTitle(weapon.getName());
    }

    public void getEngine() {

        // int type = player.getWeapon();
        // Weapon weapon = EquipmentFactory().getWeapon(type);

        // selectedItems.getWeaponFragment().setImage(weapon.getImage());
        // selectedItems.getWeaponFragment().setTitle(weapon.getName());
    }

    public void getShield() {

        // int type = player.getWeapon();
        // Weapon weapon = EquipmentFactory().getWeapon(type);

        // selectedItems.getWeaponFragment().setImage(weapon.getImage());
        // selectedItems.getWeaponFragment().setTitle(weapon.getName());
    }

    public void getHull() {

        // int type = player.getWeapon();
        // Weapon weapon = EquipmentFactory().getWeapon(type);

        // selectedItems.getWeaponFragment().setImage(weapon.getImage());
        // selectedItems.getWeaponFragment().setTitle(weapon.getName());
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(this, GamePlayActivity.class);
        intent.putExtra("PlayerInfo", playerInfo);

        startActivity(intent);
        finish();
    }

}
