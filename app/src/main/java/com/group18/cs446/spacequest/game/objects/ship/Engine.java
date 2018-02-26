package com.group18.cs446.spacequest.game.objects.ship;

public interface Engine {
    int getTurnSpeed();
    int getSpeed();
    void update(long gameTick);
    void doSpecial(long gameTick);
    void refresh();
}
