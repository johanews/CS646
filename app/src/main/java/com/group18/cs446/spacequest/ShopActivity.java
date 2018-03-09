package com.group18.cs446.spacequest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.group18.cs446.spacequest.game.objects.player.Engine;
import com.group18.cs446.spacequest.game.objects.player.Hull;
import com.group18.cs446.spacequest.game.objects.player.Player;
import com.group18.cs446.spacequest.game.objects.player.Shield;
import com.group18.cs446.spacequest.game.objects.player.Weapon;

public class ShopActivity extends AppCompatActivity {

    Player player;

    SelectedItems selectedItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_layout);

        selectedItems = (SelectedItems) getSupportFragmentManager().
                        findFragmentById(R.id.selected_items_fragment);

        getWeapon();
        getEngine();
        getShield();
        getHull();
    }

    public void getWeapon() {

        // Weapon weapon = player.getWeapon();

        Bitmap test = BitmapFactory.decodeResource(getResources(),R.drawable.weapon);

        selectedItems.getWeaponFragment().setImage(test);
        selectedItems.getWeaponFragment().setTitle("Basic Laser");
        // selectedItems.getWeaponFragment().setImage(weapon.getImage());
        // selectedItems.getWeaponFragment().setTitle(weapon.getVersion());
    }

    public void getEngine() {

        // Engine engine = player.getEngine();

        Bitmap test = BitmapFactory.decodeResource(getResources(),R.drawable.engine);

        selectedItems.getEngineFragment().setImage(test);
        selectedItems.getEngineFragment().setTitle("Basic Engine");
        // selectedItems.getEngineFragment().setImage(engine.getImage());
        // selectedItems.getEngineFragment().setTitle(engine.getVersion());
    }

    public void getShield() {

        // Shield shield = player.getShield();

        Bitmap test = BitmapFactory.decodeResource(getResources(),R.drawable.shield);

        selectedItems.getShieldFragment().setImage(test);
        selectedItems.getShieldFragment().setTitle("Basic Shield");
        // selectedItems.getShieldFragment().setImage(shield.getImage());
        // selectedItems.getShieldFragment().setTitle(shield.getVersion());
    }

    public void getHull() {

        // Hull hull = player.getHull();

        Bitmap test = BitmapFactory.decodeResource(getResources(),R.drawable.hull);

        selectedItems.getHullFragment().setImage(test);
        selectedItems.getHullFragment().setTitle("Basic Hull");
        // selectedItems.getHullFragment().setImage(hull.getImage());
        // selectedItems.getHullFragment().setTitle(hull.getVersion());
    }

}
