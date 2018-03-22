package com.group18.cs446.spacequest;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.group18.cs446.spacequest.game.enums.Engines;
import com.group18.cs446.spacequest.game.enums.Hulls;
import com.group18.cs446.spacequest.game.enums.Shields;
import com.group18.cs446.spacequest.game.enums.Weapons;
import com.group18.cs446.spacequest.game.objects.player.PlayerInfo;
import com.group18.cs446.spacequest.io.FileHandler;
import com.group18.cs446.spacequest.io.SoundManager;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ShareSocialActivity extends AppCompatActivity {

    private PlayerInfo playerInfo;
    private String videoURI;
    private Button continueButton;
    private Button tweetButton;

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_social_activity);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View root = findViewById(android.R.id.content);
        root.setSystemUiVisibility(Constants.BASE_UI_VISIBILITY);
        playerInfo = (PlayerInfo) getIntent().getSerializableExtra("PlayerInfo");
        videoURI = (String) getIntent().getSerializableExtra("VideoURI");
        continueButton = findViewById(R.id.continue_button);
        continueButton.setOnClickListener(this::goToNextActivity);
        tweetButton = findViewById(R.id.tweet_button);
        tweetButton.setOnClickListener(this::tweet);
    }

    private String generateTweetMessage(){
        String msg = "";
        if(playerInfo.getCurrentSector() == -1){
            msg += "Check out the sector that defeated me in SpaceQuest";
        } else {
            msg += "Check out this sector run I did in SpaceQuest";
        }
        return msg;
    }

    private void tweet(View v){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.setPackage("com.twitter.android"); // if the app isn't installed TODO
        String message = generateTweetMessage();
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if(videoURI != null){
            intent.putExtra(Intent.EXTRA_STREAM, videoURI);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setType("video/*");
        }
        try{
            startActivity(intent);
        } catch (android.content.ActivityNotFoundException e){
            e.printStackTrace();
        }

//        String tweetUrl = "https://twitter.com/intent/tweet?text=Check out my most recent sector attempt&url="
//                + "https://www.google.com";
//        Uri uri = Uri.parse(tweetUrl);
//        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    private void goToNextActivity(View v){
        if(playerInfo.getCurrentSector() == -1){ // Game Over go to MainActivity
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(this, ShopActivity.class);
            intent.putExtra("PlayerInfo", playerInfo);
            startActivity(intent);
            finish();


        }
    }

}