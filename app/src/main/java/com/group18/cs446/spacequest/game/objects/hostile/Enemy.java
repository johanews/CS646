package com.group18.cs446.spacequest.game.objects.hostile;

import com.group18.cs446.spacequest.game.objects.GameEntity;

public interface Enemy extends GameEntity {
    void setTarget(GameEntity e);
}
