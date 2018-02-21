package com.group18.cs446.spacequest.game.objects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import com.group18.cs446.spacequest.R;
import com.group18.cs446.spacequest.game.enums.CollisionEvent;

/**
 * Created by Owen on 2018-02-08.
 */

public class ExitGate implements GameEntity{
    Point coordinates; // Center of the gate
    Bitmap bitmap;

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
    public CollisionEvent getCollisionEvent(){
        return CollisionEvent.VICTORY;
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
    public void paint(Canvas canvas, Paint paint, Point topLeftCorner) {

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
