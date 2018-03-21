package com.group18.cs446.spacequest.social;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.group18.cs446.spacequest.Constants;
import com.group18.cs446.spacequest.MainActivity;
import com.group18.cs446.spacequest.R;
import com.group18.cs446.spacequest.ShopActivity;
import com.group18.cs446.spacequest.game.objects.player.PlayerInfo;

import java.util.Objects;


public class FacebookActivity extends AppCompatActivity {

    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private AccessToken accessToken;

    private String nextActivityName;
    private PlayerInfo playerInfo;

    private Button continueButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facebook_activity);

        View root = findViewById(android.R.id.content);
        root.setSystemUiVisibility(Constants.BASE_UI_VISIBILITY);

        nextActivityName = (String) getIntent().getSerializableExtra("nextActivity");
        playerInfo = (PlayerInfo) getIntent().getSerializableExtra("PlayerInfo");

        continueButton = findViewById(R.id.continue_button);
        continueButton.setOnClickListener(this::nextActivity);


        callbackManager = CallbackManager.Factory.create();

        loginButton = findViewById(R.id.login_button);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                accessToken = loginResult.getAccessToken();
                Log.i("spacequest", accessToken.toString());
            }

            @Override
            public void onCancel() {
                Log.i("spacequest", "Facebook login cancelled");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.e("spacequest", "Facebook login failed", exception);
            }
        });
    }

    private void nextActivity(View view) {
        Intent intent;
        if (Objects.equals(nextActivityName, "shop"))
            intent = new Intent(this, ShopActivity.class);
        else if (Objects.equals(nextActivityName, "main"))
            intent = new Intent(this, MainActivity.class);
        else
            intent = new Intent(this, MainActivity.class); // Default to main
        intent.putExtra("PlayerInfo", playerInfo);
        this.startActivity(intent);
        this.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

}
