package com.group18.cs446.spacequest.game.vfx;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.group18.cs446.spacequest.game.objects.Sector;

public class DamageFilter implements Filter {
    private Sector sector;
    private int duration;
    public DamageFilter(Sector sector){
        this.sector = sector;
        this.duration = 1;
    }
    @Override
    public void paint(Canvas c) {
        Paint paint = new Paint();
        paint.setColor(Color.argb(80, 255,10, 10));
        c.drawRect(c.getClipBounds(), paint);
    }

    @Override
    public void update(long gameTick) {
        if(duration-- <= 0) {
            sector.removeFilter(this);
        }
    }

}
