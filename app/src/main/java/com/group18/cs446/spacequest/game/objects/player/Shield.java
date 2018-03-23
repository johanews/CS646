package com.group18.cs446.spacequest.game.objects.player;

import android.graphics.Paint;
import android.graphics.Point;

import com.group18.cs446.spacequest.game.collision.Damage;
import com.group18.cs446.spacequest.game.vfx.CanvasComponent;

public interface Shield extends ShipComponent{
    void update(long gameTick);
    void refresh();
    Damage takeDamage(Damage damage);
    int getCurrentShield();
    int getMaxShield();
    void paint(CanvasComponent canvas, Paint paint, Point topLeftCorner);
}
