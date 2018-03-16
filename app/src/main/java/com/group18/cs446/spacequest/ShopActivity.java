package com.group18.cs446.spacequest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.group18.cs446.spacequest.game.objects.player.ComponentFactory;
import com.group18.cs446.spacequest.game.objects.player.Engine;
import com.group18.cs446.spacequest.game.objects.player.PlayerInfo;
import com.group18.cs446.spacequest.game.objects.player.Weapon;

public class ShopActivity extends AppCompatActivity implements View.OnClickListener {

    SelectedItems selectedItems;
    PlayerInfo playerInfo;

    Button play_button;

    ComponentFactory factory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_layout);

        View root = findViewById(android.R.id.content);
        root.setSystemUiVisibility(Constants.BASE_UI_VISIBILITY);

        factory = new ComponentFactory(getBaseContext());

        selectedItems = (SelectedItems) getSupportFragmentManager().
                        findFragmentById(R.id.selected_items_fragment);

        play_button = findViewById(R.id.map_button);
        play_button.setOnClickListener(this);

        playerInfo = (PlayerInfo) getIntent().getSerializableExtra("PlayerInfo");

        getWeapon();
        getEngine();
        getShield();
        getHull();

        // Extra

        TabLayout tabLayout = findViewById(R.id.tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText("Weapon"));
        tabLayout.addTab(tabLayout.newTab().setText("Engine"));
        tabLayout.addTab(tabLayout.newTab().setText("Shield"));
        tabLayout.addTab(tabLayout.newTab().setText("Hull"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(),
                tabLayout.getTabCount());

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    public void getWeapon() {

        int type = playerInfo.getWeapon();
        Weapon weapon = (Weapon) factory.getShipComponent(type);

        selectedItems.getWeaponFragment().setImage(weapon.getBitmap());
        selectedItems.getWeaponFragment().setTitle(weapon.getName());
    }

    public void getEngine() {

        int type = playerInfo.getEngine();
        Engine engine = (Engine) factory.getShipComponent(type);

        selectedItems.getEngineFragment().setImage(engine.getBitmap());
        selectedItems.getEngineFragment().setTitle(engine.getName());
    }

    public void getShield() {

        int type = playerInfo.getWeapon();
        Weapon shield = (Weapon) factory.getShipComponent(type);

        selectedItems.getShieldFragment().setImage(shield.getBitmap());
        selectedItems.getShieldFragment().setTitle(shield.getName());
    }

    public void getHull() {

        int type = playerInfo.getWeapon();
        Weapon hull = (Weapon) factory.getShipComponent(type);

        selectedItems.getHullFragment().setImage(hull.getBitmap());
        selectedItems.getHullFragment().setTitle(hull.getName());
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(this, GamePlayActivity.class);
        intent.putExtra("PlayerInfo", playerInfo);

        startActivity(intent);
        finish();
    }

}
