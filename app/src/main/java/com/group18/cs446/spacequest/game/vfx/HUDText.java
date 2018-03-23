package com.group18.cs446.spacequest.game.vfx;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import com.group18.cs446.spacequest.game.objects.player.Player;

public class HUDText implements HUDComponent {
    private Player player;
    private int prevMoney = -1;
    private String sectorMessage, moneyMessage;
    private Paint sectorTextPaint, moneyTextPaint;
    private Point sectorLocationOffset, moneyLocationOffset;

    public HUDText(Player player, int sectorNumber){
        this.player = player;
        sectorMessage = "Sector " + sectorNumber;
        sectorTextPaint = new Paint();
        sectorTextPaint.setColor(Color.CYAN);
        sectorTextPaint.setTextSize(60);
        sectorLocationOffset = new Point(-20, 10);

        moneyTextPaint = new Paint();
        moneyTextPaint.setColor(Color.YELLOW);
        moneyTextPaint.setTextSize(60);
        moneyLocationOffset = new Point(-20, 80);

        update();
    }

    @Override
    public void update() {
        if(player.getMoney() != prevMoney){
            prevMoney = player.getMoney();
            moneyMessage = prevMoney + ((prevMoney == 1) ? " Credit" : " Credits");
        }
  }

    @Override
    public void paint(CanvasComponent canvas) {
        canvas.drawText(sectorMessage,
                canvas.getWidth() - sectorTextPaint.measureText(sectorMessage) + sectorLocationOffset.x,
                sectorLocationOffset.y - (sectorTextPaint.descent() + sectorTextPaint.ascent()),
                sectorTextPaint);
        canvas.drawText(moneyMessage,
                canvas.getWidth() - moneyTextPaint.measureText(moneyMessage) + moneyLocationOffset.x,
                moneyLocationOffset.y - (moneyTextPaint.descent() + moneyTextPaint.ascent()),
                moneyTextPaint);

    }
}
