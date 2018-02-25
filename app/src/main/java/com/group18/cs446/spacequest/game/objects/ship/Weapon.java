package com.group18.cs446.spacequest.game.objects.ship;

import com.group18.cs446.spacequest.game.objects.GameEntity;

public interface Weapon {
    void fire(long gameTick);
    void refresh();
}
