package com.group18.cs446.spacequest.game.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by Owen on 2018-02-08.
 */

public interface GameEntity {
    Point getCoordinates();
    void update();
    void paint(Canvas canvas, Paint paint, Point topLeftCorner);
    Bitmap getBitmap();
    default boolean contains(Point p){
        return false;
    }
    default boolean intersects(GameEntity e){
        return false;
    }
    Rect getBounds();
    default CollisionEvent getCollisionEvent(){
        return CollisionEvent.NOTHING;
    }

}
