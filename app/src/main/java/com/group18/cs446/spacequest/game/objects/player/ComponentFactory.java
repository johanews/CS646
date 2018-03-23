package com.group18.cs446.spacequest.game.objects.player;

import android.content.Context;

import com.group18.cs446.spacequest.game.enums.Engines;
import com.group18.cs446.spacequest.game.enums.Hulls;
import com.group18.cs446.spacequest.game.enums.Shields;
import com.group18.cs446.spacequest.game.enums.Weapons;
import com.group18.cs446.spacequest.game.objects.player.components.BasicEngine;
import com.group18.cs446.spacequest.game.objects.player.components.BasicHull;
import com.group18.cs446.spacequest.game.objects.player.components.BasicLaser;
import com.group18.cs446.spacequest.game.objects.player.components.BasicShield;
import com.group18.cs446.spacequest.game.objects.player.components.ChainLaser;
import com.group18.cs446.spacequest.game.objects.player.components.DamageReductionShield;
import com.group18.cs446.spacequest.game.objects.player.components.DualLaser;
import com.group18.cs446.spacequest.game.objects.player.components.FastEngine;
import com.group18.cs446.spacequest.game.objects.player.components.LaserOnlyShield;
import com.group18.cs446.spacequest.game.objects.player.components.RockSmasherHull;
import com.group18.cs446.spacequest.game.objects.player.components.StrongerHull;

public class ComponentFactory {

    //public static final int BASIC_ENGINE = 1;
    //public static final int FAST_ENGINE = 2;
    //public static final int BASIC_HULL = 100;
    //public static final int BASIC_LASER = 200;
    //public static final int CHAIN_LASER = 201;
    //public static final int DUAL_LASER = 202;
    //public static final int BASIC_SHIELD = 300;
    //public static final int LASER_ONLY_SHIELD = 301;

    public Engine getEngineComponent(Engines engine, Context context) {

        switch(engine){

            case BASIC_ENGINE:
                return new BasicEngine(context);
            case FAST_ENGINE:
                return new FastEngine(context);
            default:
                return null;
        }
    }

    public Weapon getWeaponComponent(Weapons weapon, Context context) {

        switch(weapon) {
            case BASIC_LASER:
                return new BasicLaser(context);
            case CHAIN_LASER:
                return new ChainLaser(context);
            case DUAL_LASER:
                return new DualLaser(context);
            default:
                return null;
        }
    }

    public Hull getHullComponent(Hulls hull, Context context) {

        switch(hull) {
            case BASIC_HULL:
                return new BasicHull(context);
            case STRONGER_HULL:
                return new StrongerHull(context);
            case ROCKSMASHER_HULL:
                return new RockSmasherHull(context);
            default:
                return null;
        }
    }

    public Shield getShieldComponent(Shields shield, Context context) {

        switch(shield) {
            case BASIC_SHIELD:
                return new BasicShield(context);
            case LASER_ONLY_SHIELD:
                return new LaserOnlyShield(context);
            case DAMAGE_REDUCTION_SHIELD:
                return new DamageReductionShield(context);
            default:
                return null;
        }
    }

    public Weapons[] getWeaponIDs() {
        return Weapons.values();
    }

    public Engines[] getEngineIDs() {
        return Engines.values();
    }

    public Shields[] getShieldIDs() {
        return Shields.values();
    }

    public Hulls[] getHullIDs() {
        return Hulls.values();
    }
}
