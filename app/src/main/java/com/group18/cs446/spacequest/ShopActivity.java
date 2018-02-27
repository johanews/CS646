package com.group18.cs446.spacequest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ShopActivity extends AppCompatActivity {

    ImageView WeaponIcon;
    ImageView ShieldIcon;
    ImageView EngineIcon;

    ImageView weapon1;
    TextView weapon1Text;
    ImageView weapon1CurrencyImage;

    ImageView weapon2;
    TextView weapon2Text;
    ImageView weapon2CurrencyImage;

    TextView weapon3Text;
    ImageView weapon3CurrencyImage;

    Button purchaseButton;


    ImageView shield;
    ImageView addButton;
    ImageView removeButton;
    TextView shieldLevel;

    ImageView Engine1;
    ImageView Engine2;
    ImageView Engine3;

    ImageView backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        weapon1 = findViewById(R.id.weapon1);
        weapon1Text = findViewById(R.id.weapon1_text);
        weapon1CurrencyImage = findViewById(R.id.weapon1_currency_image);

        weapon2 = findViewById(R.id.weapon2);
        weapon2Text = findViewById(R.id.weapon2_text);
        weapon2CurrencyImage = findViewById(R.id.weapon2_currency_image);

        weapon3Text = findViewById(R.id.weapon3_text);
        weapon3CurrencyImage = findViewById(R.id.weapon3_currency_image);

        purchaseButton = findViewById(R.id.purchase_button);

        shield= findViewById(R.id.shield_image);
        addButton = findViewById(R.id.add_button);
        removeButton = findViewById(R.id.remove_button);
        shieldLevel = findViewById(R.id.shield_level_value_text);

        Engine1 = findViewById(R.id.engine1);
        Engine2 = findViewById(R.id.engine2);
        Engine3 = findViewById(R.id.engine3);

        WeaponIcon = findViewById(R.id.weapon_icon);
        WeaponIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setEngineVisible(false);
                setShieldVisible(false);
                setWeaponVisible(true);
            }
        });

        ShieldIcon = findViewById(R.id.shield_icon);
        ShieldIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setWeaponVisible(false);
                setEngineVisible(false);
                setShieldVisible(true);
            }
        });

        EngineIcon = findViewById(R.id.engine_icon);
        EngineIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setWeaponVisible(false);
                setShieldVisible(false);
                setEngineVisible(true);
            }
        });

        setShieldVisible(false);
        setEngineVisible(false);
        setWeaponVisible(true);

        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    public void setWeaponVisible(boolean b) {
        if (b) {
            weapon1.setVisibility(View.VISIBLE);
            weapon1Text.setVisibility(View.VISIBLE);
            weapon1CurrencyImage.setVisibility(View.VISIBLE);
            weapon2.setVisibility(View.VISIBLE);
            weapon2Text.setVisibility(View.VISIBLE);
            weapon2CurrencyImage.setVisibility(View.VISIBLE);
            purchaseButton.setVisibility(View.VISIBLE);
        } else {
            weapon1.setVisibility(View.INVISIBLE);
            weapon1Text.setVisibility(View.INVISIBLE);
            weapon1CurrencyImage.setVisibility(View.INVISIBLE);
            weapon2.setVisibility(View.INVISIBLE);
            weapon2Text.setVisibility(View.INVISIBLE);
            weapon2CurrencyImage.setVisibility(View.INVISIBLE);
            purchaseButton.setVisibility(View.INVISIBLE);
        }
    }

    public void setShieldVisible(boolean b) {
        if (b) {
            shield.setVisibility(View.VISIBLE);
            addButton.setVisibility(View.VISIBLE);
            removeButton.setVisibility(View.VISIBLE);
            shieldLevel.setVisibility(View.VISIBLE);
        } else {
            shield.setVisibility(View.INVISIBLE);
            addButton.setVisibility(View.INVISIBLE);
            removeButton.setVisibility(View.INVISIBLE);
            shieldLevel.setVisibility(View.INVISIBLE);
        }
    }

    public void setEngineVisible(boolean b) {
        if (b) {
            Engine1.setVisibility(View.VISIBLE);
            weapon1Text.setVisibility(View.VISIBLE);
            weapon1CurrencyImage.setVisibility(View.VISIBLE);

            Engine2.setVisibility(View.VISIBLE);
            weapon2Text.setVisibility(View.VISIBLE);
            weapon2CurrencyImage.setVisibility(View.VISIBLE);

            Engine3.setVisibility(View.VISIBLE);
            weapon3Text.setVisibility(View.VISIBLE);
            weapon3CurrencyImage.setVisibility(View.VISIBLE);
        } else {
            Engine1.setVisibility(View.INVISIBLE);
            weapon1Text.setVisibility(View.INVISIBLE);
            weapon1CurrencyImage.setVisibility(View.INVISIBLE);

            Engine2.setVisibility(View.INVISIBLE);
            weapon2Text.setVisibility(View.INVISIBLE);
            weapon2CurrencyImage.setVisibility(View.INVISIBLE);

            Engine3.setVisibility(View.INVISIBLE);
            weapon3Text.setVisibility(View.INVISIBLE);
            weapon3CurrencyImage.setVisibility(View.INVISIBLE);
        }
    }
}
