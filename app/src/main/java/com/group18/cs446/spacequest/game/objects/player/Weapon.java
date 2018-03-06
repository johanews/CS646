package com.group18.cs446.spacequest.game.objects.player;

public interface Weapon {
    void fire(long gameTick);
    void refresh();
}
