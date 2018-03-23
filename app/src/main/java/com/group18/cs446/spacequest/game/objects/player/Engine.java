package com.group18.cs446.spacequest.game.objects.player;

import android.graphics.Bitmap;

public interface Engine extends ShipComponent{
    int getTurnSpeed();
    int getSpeed();
    void update(long gameTick);
    void doSpecial(long gameTick);
    void refresh();
}
