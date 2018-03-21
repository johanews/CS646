package com.group18.cs446.spacequest.game.vfx;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

import com.group18.cs446.spacequest.game.objects.player.Player;

public class ShieldBar implements HUDComponent {
    private Player player;
    private int currentShield, maxShield;
    private String message;
    private int xTextPos, yTextPos;
    private Paint textPaint, outlinePaint, fillPaint;
    private Point topLeft = new Point(0, 90);
    private int width = 450;
    private int height = 80;
    private Point[] outlineCoords = {
            new Point(topLeft.x, topLeft.y),
            new Point(topLeft.x +width, topLeft.y),
            new Point( topLeft.x +width - 50, topLeft.y+height),
            new Point(topLeft.x, topLeft.y+height)
    };
    private Point[] fillCoords = {
            new Point(topLeft.x, topLeft.y),
            new Point(topLeft.x +width, topLeft.y),
            new Point( topLeft.x +width - 50, topLeft.y+height),
            new Point(topLeft.x, topLeft.y+height)
    };
    public ShieldBar(Player player){
        this.player = player;
        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(60);
        outlinePaint = new Paint();
        outlinePaint.setColor(Color.CYAN);
        outlinePaint.setStyle(Paint.Style.STROKE);
        outlinePaint.setStrokeWidth(10);
        fillPaint = new Paint();
        fillPaint.setColor(Color.argb(155, 0, 255, 255));
        update();
    }

    @Override
    public void update() {
        currentShield = player.getCurrentShield();
        maxShield = player.getMaxShield();
        message = currentShield +"/"+ maxShield;
        xTextPos = topLeft.x + (width)/2 - (int)(textPaint.measureText(message)/2);
        yTextPos = topLeft.y + (int) (height/2 - ((textPaint.descent() + textPaint.ascent()) / 2)) ;

        fillCoords[1].set(topLeft.x +(width* currentShield / maxShield),
                topLeft.y);
        fillCoords[2].set(topLeft.x +((width - 50)* currentShield / maxShield),
                topLeft.y+height);
  }

    @Override
    public void paint(Canvas canvas) {
        Path fillPath = new Path();
        fillPath.moveTo(fillCoords[0].x, fillCoords[0].y);
        for(int i = 1; i < fillCoords.length; i++){
            fillPath.lineTo(fillCoords[i].x, fillCoords[i].y);
        }
        canvas.drawPath(fillPath, fillPaint);
        Path outlinePath = new Path();
        outlinePath.moveTo(outlineCoords[0].x, outlineCoords[0].y);
        for(int i = 1; i < outlineCoords.length; i++){
            outlinePath.lineTo(outlineCoords[i].x, outlineCoords[i].y);
        }
        canvas.drawPath(outlinePath, outlinePaint);
        canvas.drawText(message, xTextPos, yTextPos, textPaint);
    }
}
