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
    EnemyFactory enemyFactory;

    public EnemySpawner(int sectorID, Sector sector, ComponentFactory componentFactory, Context context){
        this.sector = sector;
        this.componentFactory = componentFactory;
        this.context = context;
        this.sectorID = sectorID;
        this.maxEnemies = sectorID+ 1;
        this.spawnedEnemies = 0;
        possibleSpawnPoints = new LinkedList<>();
        possibleSpawnPoints.add(new Point(0, 0));
        for(int dist = 750; dist < 4000; dist += 750) {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if (i * j == 0) continue;
                    possibleSpawnPoints.add(new Point(dist * i, dist * j));
                }
            }
        }
        enemyFactory = new BasicEnemyFactoryImpl(sectorID);
        spawnEnemies();
    }
    public void spawnEnemies(){
        while(spawnedEnemies < maxEnemies) {
            Enemy e = null;
            for (int i = 0; i < possibleSpawnPoints.size(); i++) {
                Point p = possibleSpawnPoints.get(i);
                int dx = sector.getPlayer().getCoordinates().x - p.x;
                int dy = sector.getPlayer().getCoordinates().y - p.y;
                int distanceFromSpawn = dx * dx + dy * dy;
                if (distanceFromSpawn > 3000 * 3000) {
                    possibleSpawnPoints.remove(p);
                    possibleSpawnPoints.add(p);
                    System.out.println("Spawning enemy at ("+p.x+", "+p.y+")");
                    spawnedEnemies++;
                    e = enemyFactory.getEnemy(new Point(p), context, componentFactory, sector);
                    e.registerSpawner(this);
                    sector.addEntityFront(e);
                    break;
                }
            }
        }
    }

    public void reportDeath(Enemy enemy) {
        spawnedEnemies--;
    }
}
