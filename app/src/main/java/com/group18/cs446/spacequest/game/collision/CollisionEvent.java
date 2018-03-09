package com.group18.cs446.spacequest.game.collision;

public class CollisionEvent {
    public static final int VICTORY = 0;
    public static final int DEFEAT = 1;
    public static final int DAMAGE = 2;
    public static final int NOTHING = 3;

    private int event;
    private int value;
    private Damage damage;

    public CollisionEvent(int event, int value){
        this.event = event;
        this.value = value;
    }
    public CollisionEvent(int event, Damage damage){
        this.event = event;
        this.damage = damage;
    }

    public CollisionEvent(int event) {
        this.event = event;
        this.value = -1;
    }

    public int getEvent(){
        return event;
    }
    public Damage getDamage(){ return damage; }
    public int getValue(){
        return value;
    }
}
