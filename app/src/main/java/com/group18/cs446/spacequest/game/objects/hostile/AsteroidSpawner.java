package com.group18.cs446.spacequest.game.objects.hostile;

import android.content.Context;
import android.graphics.Point;

import com.group18.cs446.spacequest.game.objects.Sector;
import com.group18.cs446.spacequest.game.objects.player.ComponentFactory;

import java.util.LinkedList;
import java.util.List;

public class AsteroidSpawner {
    private Sector sector;
    private Context context;
    private int sectorID;
    protected int maxAsteroids;
    protected int existingAsteroids;
    AsteroidFactory asteroidFactory;

    public AsteroidSpawner(int sectorID, Sector sector, Context context){
        this.sector = sector;
        this.context = context;
        this.sectorID = sectorID;
        this.maxAsteroids = 20+2*sectorID;
        this.existingAsteroids = 0;
        asteroidFactory = new BasicAsteroidFactoryImpl(sectorID);
    }
    public void spawnAsteroids(){
        while(existingAsteroids < maxAsteroids) {
            Asteroid a = asteroidFactory.getAsteroid(sector.getPlayer().getCoordinates(), context, sector);
            a.registerSpawner(this);
            sector.addEntityFront(a);
            existingAsteroids++;
        }
    }

    public void reportDeath(Asteroid a) {
        existingAsteroids--;
    }
}
