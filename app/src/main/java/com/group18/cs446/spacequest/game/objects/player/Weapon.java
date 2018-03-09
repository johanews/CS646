package com.group18.cs446.spacequest.game.objects.player;

import android.graphics.Bitmap;

public interface Weapon {
    void fire(long gameTick);
    void refresh();
    String getVersion();
    Bitmap getImage();
}
