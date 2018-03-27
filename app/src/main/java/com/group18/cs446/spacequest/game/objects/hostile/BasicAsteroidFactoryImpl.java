package com.group18.cs446.spacequest.game.objects.hostile;

import android.content.Context;
import android.graphics.Point;

import com.group18.cs446.spacequest.game.objects.Sector;
import com.group18.cs446.spacequest.game.objects.hostile.npship.BasicEnemy;
import com.group18.cs446.spacequest.game.objects.hostile.npship.CarrierEnemy;
import com.group18.cs446.spacequest.game.objects.hostile.npship.DualLaserEnemy;
import com.group18.cs446.spacequest.game.objects.hostile.npship.MachineLaserEnemy;
import com.group18.cs446.spacequest.game.objects.hostile.npship.SniperEnemy;
import com.group18.cs446.spacequest.game.objects.hostile.npship.SuicideEnemy;
import com.group18.cs446.spacequest.game.objects.player.ComponentFactory;

import java.util.Random;

class BasicAsteroidFactoryImpl implements AsteroidFactory {
    Random r;

    public BasicAsteroidFactoryImpl(int difficulty){
        r = new Random();
    }

    @Override
    public Asteroid getAsteroid(Point p, Context context, Sector sector) {
        double spawnAngle = r.nextDouble() * 2 * Math.PI;
        double spawnDistance = Asteroid.MIN_DISTANCE + r.nextDouble() * (Asteroid.MAX_DISTANCE - Asteroid.MIN_DISTANCE);
        Point coordinates = new Point((int)(p.x+Math.cos(spawnAngle)*spawnDistance),
                (int)(p.y+Math.sin(spawnAngle)*spawnDistance));
        return new Asteroid(sector, coordinates, context);
    }
}
