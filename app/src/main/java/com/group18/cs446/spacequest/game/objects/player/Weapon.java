package com.group18.cs446.spacequest.game.objects.player;

import android.graphics.Paint;
import android.graphics.Point;

import com.group18.cs446.spacequest.game.vfx.CanvasComponent;

public interface Weapon extends ShipComponent {
    void fire(long gameTick);
    void refresh();
    void paint(CanvasComponent canvas, Point topLeftCorner);
}
