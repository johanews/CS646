package com.group18.cs446.spacequest.game.objects.player;

import android.graphics.Bitmap;

import com.group18.cs446.spacequest.game.collision.Damage;

public interface Hull extends ShipComponent{
    void update(long gameTick);
    void refresh();
    void takeDamage(Damage damage);
    int getCurrentHealth();
    int getMaxHealth();
}
