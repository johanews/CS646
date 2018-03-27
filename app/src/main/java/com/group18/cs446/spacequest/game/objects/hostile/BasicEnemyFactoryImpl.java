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

class BasicEnemyFactoryImpl implements EnemyFactory {
    int basicWeight, machineWeight, dualWeight, sniperWeight, carrierWeight, suicideWeight;
    int totalWeight;
    Random r;

    public BasicEnemyFactoryImpl(int difficulty){
        basicWeight = Math.max(100-difficulty*2, 50);
        machineWeight = (difficulty+2)*2;
        dualWeight = difficulty*3;
        sniperWeight = difficulty;
        carrierWeight = difficulty/2;
        suicideWeight = difficulty/2;
        totalWeight = basicWeight + machineWeight + dualWeight + sniperWeight + carrierWeight + suicideWeight;
        r = new Random();
    }

    @Override
    public Enemy getEnemy(Point p, Context context, ComponentFactory componentFactory, Sector sector) {
        int type = r.nextInt(totalWeight);
        if(type < basicWeight) {
            return new BasicEnemy(new Point(p), context, componentFactory, sector);
        }
        type -= basicWeight;
        if(type < machineWeight){
            return new MachineLaserEnemy(new Point(p), context, componentFactory, sector);
        }
        type -= machineWeight;
        if(type < dualWeight){
            return new DualLaserEnemy(new Point(p), context, componentFactory, sector);
        }
        type -= dualWeight;
        if(type < sniperWeight){
            return new SniperEnemy(new Point(p), context, componentFactory, sector);
        }
        type -= sniperWeight;
        if(type < carrierWeight){
            return new CarrierEnemy(new Point(p), context, componentFactory, sector);
        }
        type -= carrierWeight;
        if(type < suicideWeight){
            return new SuicideEnemy(new Point(p), context, sector, 0);
        }
        type -= carrierWeight;
        return new BasicEnemy(new Point(p), context, componentFactory, sector); // Should not get here
    }
}
