package com.group18.cs446.spacequest.game.objects.hostile;

import android.content.Context;
import android.graphics.Point;

import com.group18.cs446.spacequest.game.objects.Sector;
import com.group18.cs446.spacequest.game.objects.player.ComponentFactory;

public interface AsteroidFactory {
    Asteroid getAsteroid(Point p, Context context, Sector sector);
}
