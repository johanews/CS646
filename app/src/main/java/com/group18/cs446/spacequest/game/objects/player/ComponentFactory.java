package com.group18.cs446.spacequest.game.objects.player;

import android.content.Context;

import com.group18.cs446.spacequest.game.objects.player.components.BasicEngine;
import com.group18.cs446.spacequest.game.objects.player.components.BasicHull;
import com.group18.cs446.spacequest.game.objects.player.components.BasicLaser;
import com.group18.cs446.spacequest.game.objects.player.components.BasicShield;
import com.group18.cs446.spacequest.game.objects.player.components.ChainLaser;
import com.group18.cs446.spacequest.game.objects.player.components.DualLaser;
import com.group18.cs446.spacequest.game.objects.player.components.FastEngine;
import com.group18.cs446.spacequest.game.objects.player.components.LaserOnlyShield;

public class ComponentFactory {

    public static final int BASIC_ENGINE = 1;
    public static final int FAST_ENGINE = 2;
    public static final int BASIC_HULL = 100;
    public static final int BASIC_LASER = 200;
    public static final int CHAIN_LASER = 201;
    public static final int DUAL_LASER = 202;
    public static final int BASIC_SHIELD = 300;
    public static final int LASER_ONLY_SHIELD = 301;

    private Context context;

    public ComponentFactory(Context context){
        this.context = context;
    }

    public ShipComponent getShipComponent(int id) {

        switch(id){

            case BASIC_ENGINE:
                return new BasicEngine(context);
            case FAST_ENGINE: // FastEngine
                return new FastEngine(context);
            case BASIC_HULL:
                return new BasicHull(context);
            case BASIC_LASER:
                return new BasicLaser(context);
            case CHAIN_LASER:
                return new ChainLaser(context);
            case DUAL_LASER:
                return new DualLaser(context);
            case BASIC_SHIELD:
                return new BasicShield(context);
            case LASER_ONLY_SHIELD:
                return new LaserOnlyShield(context);
            default:
                return null;
        }
    }
}
