package com.group18.cs446.spacequest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView game_start_button;
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

//        shop_button = findViewById(R.id.shop_button);
//        shop_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(this, ShopActivity.class));
//            }
//        });

        game_start_button = findViewById(R.id.game_start_image);
        game_start_button.setOnClickListener(this);
        View root = findViewById(android.R.id.content);
        root.setSystemUiVisibility(Constants.BASE_UI_VISIBILITY);



    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this, ShopActivity.class));
    }

}
