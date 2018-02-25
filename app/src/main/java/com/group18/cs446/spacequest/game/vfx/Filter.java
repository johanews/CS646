package com.group18.cs446.spacequest.game.vfx;

import android.graphics.Canvas;

public interface Filter {
    void paint(Canvas c);
    void update(long gameTick);
}
