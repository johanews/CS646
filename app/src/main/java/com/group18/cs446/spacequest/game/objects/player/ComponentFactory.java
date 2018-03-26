package com.group18.cs446.spacequest.game.objects.player;

import android.content.Context;

import com.group18.cs446.spacequest.game.enums.Engines;
import com.group18.cs446.spacequest.game.enums.Hulls;
import com.group18.cs446.spacequest.game.enums.Shields;
import com.group18.cs446.spacequest.game.enums.Weapons;
import com.group18.cs446.spacequest.game.objects.player.components.engine.BasicEngine;
import com.group18.cs446.spacequest.game.objects.player.components.hull.BasicHull;
import com.group18.cs446.spacequest.game.objects.player.components.weapon.BasicLaser;
import com.group18.cs446.spacequest.game.objects.player.components.shield.BasicShield;
import com.group18.cs446.spacequest.game.objects.player.components.weapon.ChainLaser;
import com.group18.cs446.spacequest.game.objects.player.components.shield.DamageReductionShield;
import com.group18.cs446.spacequest.game.objects.player.components.weapon.DualLaser;
import com.group18.cs446.spacequest.game.objects.player.components.engine.FastEngine;
import com.group18.cs446.spacequest.game.objects.player.components.shield.LaserOnlyShield;
import com.group18.cs446.spacequest.game.objects.player.components.engine.ReverseEngine;
import com.group18.cs446.spacequest.game.objects.player.components.hull.RockSmasherHull;
import com.group18.cs446.spacequest.game.objects.player.components.shield.StrongShield;
import com.group18.cs446.spacequest.game.objects.player.components.hull.StrongerHull;
import com.group18.cs446.spacequest.game.objects.player.components.hull.SuperStrongHull;
import com.group18.cs446.spacequest.game.objects.player.components.weapon.TripleLaser;
import com.group18.cs446.spacequest.game.objects.player.components.engine.TronEngine;
import com.group18.cs446.spacequest.game.objects.player.components.hull.VeryStrongHull;

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
            case REVERSE_ENGINE:
                return new ReverseEngine(context);
            case TRON_ENGINE:
                return new TronEngine(context);
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
            case TRIPLE_LASER:
                return new TripleLaser(context);
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
            case VERY_STRONG_HULL:
                return new VeryStrongHull(context);
            case VERY_VERY_STRONG_HULL:
                return new SuperStrongHull(context);
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
            case STRONG_SHIELD:
                return new StrongShield(context);
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
