package com.group18.cs446.spacequest.game.objects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
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
    public void update() {
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
        //Alternatively, pass this to Player to paint themselves here
        canvas.save();
        //canvas.rotate(angle, getCoordinates().x - topLeftCorner.x, getCoordinates().y - topLeftCorner.y);
        canvas.drawBitmap(
                getBitmap(),
                getCoordinates().x - topLeftCorner.x - getBitmap().getWidth() / 2,
                getCoordinates().y - topLeftCorner.y - getBitmap().getHeight() / 2,
                paint);
        canvas.restore();
    }
}
