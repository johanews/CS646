package com.group18.cs446.spacequest.game.objects.player;

import com.group18.cs446.spacequest.game.collision.Damage;

public interface Hull {
    void update(long gameTick);
    void refresh();
    void takeDamage(Damage damage);
    int getCurrentHealth();
    int getMaxHealth();
}
