package com.group18.cs446.spacequest.game.vfx;

public interface Filter {
    void paint(CanvasComponent c);
    void update(long gameTick);
}
