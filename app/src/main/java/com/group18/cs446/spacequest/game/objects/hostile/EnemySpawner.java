package com.group18.cs446.spacequest.game.objects.hostile;

import android.content.Context;
import android.graphics.Point;

import com.group18.cs446.spacequest.game.objects.Sector;
import com.group18.cs446.spacequest.game.objects.hostile.npship.BasicEnemy;
import com.group18.cs446.spacequest.game.objects.player.ComponentFactory;

import java.util.LinkedList;
import java.util.List;

public class EnemySpawner {
    private Sector sector;
    private ComponentFactory componentFactory;
    private Context context;
    private int sectorID;
    private int maxEnemies, spawnedEnemies;
    private List<Point> possibleSpawnPoints;

    public EnemySpawner(int sectorID, Sector sector, ComponentFactory componentFactory, Context context){
        this.sector = sector;
        this.componentFactory = componentFactory;
        this.context = context;
        this.sectorID = sectorID;
        this.maxEnemies = sectorID * 2 + 1;
        this.spawnedEnemies = 0;
        possibleSpawnPoints = new LinkedList<>();
        possibleSpawnPoints.add(new Point(0, 0));
        for(int i = -1; i < 2; i++){
            for(int j = -1; j < 2; j++){
                if(i*j==0)continue;
                possibleSpawnPoints.add(new Point(750*i, 750*j));
                possibleSpawnPoints.add(new Point(1500*i, 1500*j));
                possibleSpawnPoints.add(new Point(2500*i, 2500*j));
            }
        }
        spawnEnemies();
    }
    public void spawnEnemies(){
        while(spawnedEnemies < maxEnemies) {
            Enemy e = null;
            for (Point p : possibleSpawnPoints) {
                int dx = sector.getPlayer().getCoordinates().x - p.x;
                int dy = sector.getPlayer().getCoordinates().y - p.y;
                int distanceFromSpawn = dx * dx + dy * dy;
                if (distanceFromSpawn > 2000 * 2000) {
                    spawnedEnemies++;
                    e = new BasicEnemy(new Point(p), context, componentFactory, sector);
                    e.registerSpawner(this);
                    sector.addEntityFront(e);
                    if(spawnedEnemies >= maxEnemies) break;
                }
            }
        }
    }

    public void reportDeath(BasicEnemy basicEnemy) {
        spawnedEnemies--;
    }
}
