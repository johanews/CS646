package com.group18.cs446.spacequest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentManager;

import com.group18.cs446.spacequest.game.objects.player.Player;
import com.group18.cs446.spacequest.view.GameView;

public class ShopActivity extends AppCompatActivity {

    GameView view;
    Player player;

    SelectedItems selectedItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_layout);

        view = findViewById(R.id.game_view);

        selectedItems = (SelectedItems) getSupportFragmentManager().
                        findFragmentById(R.id.selected_items_fragment);

        if(view != null)
            player = view.getPlayer();

        getWeapon();
        getEngine();
    }

    public void getWeapon() {
        selectedItems.getWeaponFragment().setTitle("Test Weapon");
    }

    public void getEngine() {
        selectedItems.getEngineFragment().setTitle("Test Engine");
    }
}
