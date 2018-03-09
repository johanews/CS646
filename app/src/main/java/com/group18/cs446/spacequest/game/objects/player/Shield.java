package com.group18.cs446.spacequest.game.objects.player;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.group18.cs446.spacequest.game.collision.Damage;

public interface Shield {
    void update(long gameTick);
    void refresh();
    boolean takeDamage(Damage damage);
    int getCurrentShield();
    int getMaxShield();
    void paint(Canvas canvas, Paint paint, Point topLeftCorner);
    String getVersion();
    Bitmap getImage();
}
