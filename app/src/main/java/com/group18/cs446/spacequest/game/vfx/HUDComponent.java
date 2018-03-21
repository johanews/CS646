package com.group18.cs446.spacequest.game.vfx;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public interface HUDComponent {
    void update();
    void paint(Canvas canvas);
}
