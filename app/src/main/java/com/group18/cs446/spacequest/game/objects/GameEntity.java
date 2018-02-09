package com.group18.cs446.spacequest.game.objects;

import android.graphics.Bitmap;
import android.graphics.Point;

/**
 * Created by Owen on 2018-02-08.
 */

public interface GameEntity {
    Point getCoordinates();
    void update();
    Bitmap getBitmap();
    default boolean contains(Point p){
        return false;
    }

}
