package com.group18.cs446.spacequest.game.collision;

public class Damage {
    private int amount;
    private DamageType type;
    public Damage(DamageType type, int amount){
        this.amount = amount;
        this.type = type;
    }

    public int getAmount(){
        return amount;
    }
    public DamageType getType(){
        return type;
    }
}
