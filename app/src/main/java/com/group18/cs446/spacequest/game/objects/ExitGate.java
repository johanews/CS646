package com.group18.cs446.spacequest.game.objects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import com.group18.cs446.spacequest.R;
import com.group18.cs446.spacequest.game.collision.CollisionEvent;
import com.group18.cs446.spacequest.game.vfx.CanvasComponent;


public class ExitGate implements GameEntity {

    private Point coordinates; // Center of the gate
    private Bitmap bitmap;
    private Sector sector;
    private int beepRadi = 0;
    private CollisionEvent collisionEvent = new CollisionEvent(CollisionEvent.VICTORY);

    public ExitGate(Context context, int x, int y){
        coordinates = new Point(x, y);
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.exitgate_1);
    }
    @Override
    public Point getCoordinates() {
        return coordinates;
    }

    @Override
    public Rect getBounds(){
        return new Rect(coordinates.x-bitmap.getWidth()/2, coordinates.y-bitmap.getHeight()/2,
                coordinates.x + bitmap.getWidth()/2, coordinates.y + bitmap.getHeight()/2);
    }
    @Override
    public void update(long gameTick) {
    }

    @Override
    public CollisionEvent getCollisionEvent(GameEntity e){
        return collisionEvent;
    }

    @Override
    public Sector getCurrentSector() {
        return sector;
    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }

    @Override
    public boolean contains(Point p) {
        return false;
    }

    @Override
    public void paint(CanvasComponent canvas, Paint paint, Point topLeftCorner) {

        canvas.save();
        if(getCoordinates().x - topLeftCorner.x - getBitmap().getWidth() / 2 > canvas.getWidth() ||
                getCoordinates().y - topLeftCorner.y - getBitmap().getHeight() / 2 > canvas.getHeight() ||
                getCoordinates().x - topLeftCorner.x + getBitmap().getWidth() < 0 ||
                getCoordinates().y - topLeftCorner.y + getBitmap().getHeight()  < 0){
            // Draw direction indicator
            int indicatorX = Math.max(15, Math.min(getCoordinates().x - topLeftCorner.x, canvas.getWidth()-15));
            int indicatory = Math.max(15, Math.min(getCoordinates().y - topLeftCorner.y, canvas.getHeight()-15));
            paint.setColor(Color.CYAN);
            canvas.drawCircle(indicatorX, indicatory, 15, paint);
            if(beepRadi < 55){
                beepRadi = (beepRadi+1)%70;
                paint.setColor(Color.GREEN);
                paint.setAlpha(240);
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(5);
                canvas.drawCircle(indicatorX, indicatory, 15+(beepRadi*beepRadi/13), paint);
                paint.reset();
            }else{
                beepRadi = 0;
            }
        } else {
            // Draw exit gate
            canvas.drawBitmap(
                    getBitmap(),
                    getCoordinates().x - topLeftCorner.x - getBitmap().getWidth() / 2,
                    getCoordinates().y - topLeftCorner.y - getBitmap().getHeight() / 2,
                    paint);
        }
        canvas.restore();
    }
}
