package com.group18.cs446.spacequest.game.objects.hostile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;

import com.group18.cs446.spacequest.R;
import com.group18.cs446.spacequest.game.collision.CollisionEvent;
import com.group18.cs446.spacequest.game.collision.Damage;
import com.group18.cs446.spacequest.game.collision.DamageType;
import com.group18.cs446.spacequest.game.objects.GameEntity;
import com.group18.cs446.spacequest.game.objects.Sector;
import com.group18.cs446.spacequest.game.objects.SmokeParticle;
import com.group18.cs446.spacequest.game.vfx.CanvasComponent;

import java.util.Random;

public class Asteroid implements GameEntity {
    private static final int MIN_DISTANCE = 1500;
    private static final int MAX_DISTANCE = 3000;
    private static Bitmap bitmap;
    private Point coordinates = new Point();
    private Point speed = new Point();
    private Sector currentSector;
    private float arcSpeed;
    private float angle;
    private int maxDurability;
    private int durability;
    private Damage damage = new Damage(DamageType.PHYSICAL, 100);
    private Context context;

    private Random random = new Random();

    public Asteroid(Sector sector, Point center, Context context){
        this.context = context;
        this.maxDurability = 150;
        this.currentSector = sector;
        if(bitmap == null) bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.asteroid_1);
        double spawnAngle = random.nextDouble() * 2 * Math.PI;
        double spawnDistance = MIN_DISTANCE + random.nextDouble() * (MAX_DISTANCE - MIN_DISTANCE);
        coordinates.set((int)(center.x+Math.cos(spawnAngle)*spawnDistance),
                (int)(center.y+Math.sin(spawnAngle)*spawnDistance));
        arcSpeed = random.nextBoolean() ? random.nextFloat() + 0.2F : -random.nextFloat();
        speed.x = random.nextBoolean() ? random.nextInt(5)+1 : -random.nextInt(5) -1;
        speed.y = random.nextBoolean() ? random.nextInt(5)+1 : -random.nextInt(5) -1;
        durability = maxDurability;
    }

    @Override
    public Point getCoordinates(){
        return coordinates;
    }


    @Override
    public CollisionEvent getCollisionEvent(GameEntity entity){
        return new CollisionEvent(CollisionEvent.DAMAGE, damage);
    }
    @Override
    public void update(long gameTick){
        coordinates.x += speed.x;
        coordinates.y += speed.y;
        angle = (angle+arcSpeed)%360;

        // If > max distance, despawn
        int deltaX = currentSector.getPlayer().getCoordinates().x - coordinates.x;
        int deltaY = currentSector.getPlayer().getCoordinates().y - coordinates.y;
        if((deltaX*deltaX)+(deltaY*deltaY) > MAX_DISTANCE*MAX_DISTANCE){
            currentSector.removeEntity(this);
        }
    }

    @Override
    public void takeDamage(Damage damage){
        int multiplier = 100;
        switch (damage.getType()){ // we can change this up as we add more damage types
            case LASER:
            case PHYSICAL:
                multiplier = 100;
                break;
            default:
                break;
        }
        durability -= (damage.getAmount() * multiplier)/100;
        if(durability <= 0){
            durability = 0;
            currentSector.addEntityToBack(new SmokeParticle(currentSector, coordinates.x, coordinates.y, bitmap.getWidth()/2, Color.DKGRAY, 10));
            currentSector.removeEntity(this);
            // TODO balance
            //MoneyDrop moneyDrop = new MoneyDrop(currentSector, coordinates, context, 5);
            //currentSector.addEntityFront(moneyDrop);
        }
    }

    @Override
    public void paint(CanvasComponent canvas, Paint paint, Point topLeftCorner) {
        canvas.save();
        canvas.rotate(-getAngle(), getCoordinates().x - topLeftCorner.x, getCoordinates().y - topLeftCorner.y);
        canvas.drawBitmap(
                getBitmap(),
                getCoordinates().x - topLeftCorner.x - getBitmap().getWidth() / 2,
                getCoordinates().y - topLeftCorner.y - getBitmap().getHeight() / 2,
                paint);
        paint.setColorFilter(new PorterDuffColorFilter(Color.argb((255*(maxDurability-durability)/(maxDurability+maxDurability)), 255, 10, 10), PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(
                getBitmap(),
                getCoordinates().x - topLeftCorner.x - getBitmap().getWidth() / 2,
                getCoordinates().y - topLeftCorner.y - getBitmap().getHeight() / 2,
                paint);
        paint.reset();
        canvas.restore();
    }

    @Override
    public Bitmap getBitmap(){
        return bitmap;
    }

    @Override
    public boolean contains(Point p) {
        Point centre = new Point(coordinates.x + bitmap.getWidth() / 2, coordinates.y + bitmap.getHeight()/2);
        return Math.abs(p.x - centre.x) < bitmap.getWidth() / 2 && Math.abs(p.y - centre.y) < bitmap.getHeight() / 2;
    }

    @Override
    public boolean intersects(GameEntity e) {
        return false;
    }

    @Override
    public int getAngle(){
        return (int)angle;
    }

    @Override
    public Sector getCurrentSector() {
        return currentSector;
    }

    @Override
    public int getSpeed() {
        return speed.x+speed.y;
    }

    @Override
    public Rect getBounds(){
        return new Rect(coordinates.x-bitmap.getWidth()/2, coordinates.y-bitmap.getHeight()/2,
                coordinates.x+bitmap.getWidth()/2, coordinates.y+bitmap.getHeight()/2);
    }
}
