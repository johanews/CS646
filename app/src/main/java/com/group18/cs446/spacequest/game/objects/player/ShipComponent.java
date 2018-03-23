package com.group18.cs446.spacequest.game.objects.player;

import android.graphics.Bitmap;

import com.group18.cs446.spacequest.game.objects.GameEntity;

public interface ShipComponent {
    String getName();
    String getDescription();
    Bitmap getBitmap();
    void registerOwner(GameEntity e);
    Enum ID();
    int getPrice();

    int getImageID();
}
