package com.group18.cs446.spacequest.game.objects.hostile.npship;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import com.group18.cs446.spacequest.R;
import com.group18.cs446.spacequest.game.collision.CollisionEvent;
import com.group18.cs446.spacequest.game.collision.Damage;
import com.group18.cs446.spacequest.game.collision.DamageType;
import com.group18.cs446.spacequest.game.enums.Weapons;
import com.group18.cs446.spacequest.game.objects.GameEntity;
import com.group18.cs446.spacequest.game.objects.Sector;
import com.group18.cs446.spacequest.game.objects.SmokeParticle;
import com.group18.cs446.spacequest.game.objects.hostile.Enemy;
import com.group18.cs446.spacequest.game.objects.hostile.EnemySpawner;
import com.group18.cs446.spacequest.game.objects.loot.MoneyDrop;
import com.group18.cs446.spacequest.game.objects.player.ComponentFactory;
import com.group18.cs446.spacequest.game.objects.player.ShipComponent;
import com.group18.cs446.spacequest.game.objects.player.Weapon;
import com.group18.cs446.spacequest.game.vfx.CanvasComponent;

import java.util.Random;

public class MachineLaserEnemy implements Enemy {
    private int maxHealth, currentHealth;
    private int speed;
    private int turnSpeed;
    private int angle;
    private int sightDistance;
    private int fireDistance;
    private int hoverDistance;
    private Point coordinates;
    private GameEntity target;
    private Weapon weapon;
    private Sector sector;
    private static Bitmap bitmap;
    private Random random = new Random();
    private Damage collisionDamage = new Damage(DamageType.PHYSICAL, 100);
    private CollisionEvent collisionEvent = new CollisionEvent(CollisionEvent.DAMAGE, collisionDamage);
    private Context context;
    private EnemySpawner spawner;

    public MachineLaserEnemy(Point spawnPoint, Context context, ComponentFactory componentFactory, Sector currentSector){
        this.context = context;
        this.coordinates = new Point(spawnPoint);
        this.turnSpeed = 3;
        this.sightDistance = 3000;
        this.sector = currentSector;
        if(bitmap == null) bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy_chain);
        this.angle = 0;
        ShipComponent newWeapon = componentFactory.getWeaponComponent(Weapons.CHAIN_LASER, context);
        this.fireDistance = 900;
        this.hoverDistance = 600;
        this.maxHealth = 50;
        this.speed = 13;
        newWeapon.registerOwner(this);
        this.weapon = (Weapon) newWeapon;

        this.currentHealth = maxHealth;
    }

    @Override
    public Point getCoordinates() {
        return coordinates;
    }

    @Override
    public void update(long gameTick) {
        if(target == null){
            // Attempt to lock on player
            int dx = sector.getPlayer().getCoordinates().x - coordinates.x;
            int dy = sector.getPlayer().getCoordinates().y - coordinates.y;
            if(dx*dx + dy*dy < sightDistance*sightDistance){
                setTarget(sector.getPlayer());
            }
        }
        if(target != null){
            int dx = target.getCoordinates().x - coordinates.x;
            int dy = target.getCoordinates().y - coordinates.y;
            int distanceToTarget = (int)Math.sqrt(dx*dx + dy*dy);
            if(distanceToTarget > sightDistance){
                target = null;
            } else {
                // Aim for where the player is going
                dx = (target.getCoordinates().x - coordinates.x) - (int)(4*(Math.sin(target.getAngle() * Math.PI / 180) * target.getSpeed()));
                dy = (target.getCoordinates().y - coordinates.y) - (int)(4*(Math.cos(target.getAngle() * Math.PI / 180) * target.getSpeed()));
                angle = AIUtils.getNewAngleFromTarget(dx, dy, angle, turnSpeed);
                int adjustedSpeed = speed;
                if(distanceToTarget < hoverDistance){
                    adjustedSpeed = (adjustedSpeed*(2*distanceToTarget-hoverDistance))/hoverDistance;
                }
                if(distanceToTarget != 0) { // Note they fly towards you - not where they are pointing (that is where they shoot)

                    // If always towards target coordinates.offset((dx * adjustedSpeed / distanceToTarget), (dy * adjustedSpeed / distanceToTarget));
                    // If straight
                    coordinates.y -= Math.cos(angle * Math.PI / 180) * adjustedSpeed;
                    coordinates.x -= Math.sin(angle * Math.PI / 180) * adjustedSpeed;
                }
                if(distanceToTarget < fireDistance){
                    weapon.fire(gameTick);
                }
            }
            if(currentHealth < maxHealth){
                sector.addEntityToBack(new SmokeParticle(sector, coordinates.x, coordinates.y, bitmap.getWidth()/4, Color.GRAY,20));
            }
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
        canvas.restore();
    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }

    @Override
    public boolean intersects(GameEntity e) {
        return false;
    }

    @Override
    public Rect getBounds() {
        return new Rect(coordinates.x - bitmap.getWidth()/2, coordinates.y-bitmap.getWidth()/2, coordinates.x+bitmap.getWidth()/2, coordinates.y+bitmap.getHeight()/2);
    }

    @Override
    public CollisionEvent getCollisionEvent(GameEntity e) {
        return collisionEvent;
    }

    @Override
    public void takeDamage(Damage damage) {
        int damageAmount = damage.getAmount();
        switch (damage.getType()){
            case PHYSICAL:
                damageAmount *= 0.8;
                break;
            case LASER:
            default:
                break;
        }
        this.currentHealth -= damageAmount;
        if(this.currentHealth <= 0){ // Trigger death, later replace this with somehting else
            sector.removeEntity(this);
            sector.addEntityToBack(new SmokeParticle(sector, coordinates.x, coordinates.y, bitmap.getWidth()/2, Color.GRAY,30));
            if(random.nextBoolean()) sector.addEntityToBack(new SmokeParticle(sector, coordinates.x-20, coordinates.y-10, bitmap.getWidth()/6, Color.GRAY,20));
            if(random.nextBoolean()) sector.addEntityToBack(new SmokeParticle(sector, coordinates.x+20, coordinates.y-30, bitmap.getWidth()/4, Color.RED,50));
            if(random.nextBoolean()) sector.addEntityToBack(new SmokeParticle(sector, coordinates.x-15, coordinates.y+30, bitmap.getWidth()/5, Color.DKGRAY,30));
            if(random.nextBoolean()) sector.addEntityToBack(new SmokeParticle(sector, coordinates.x+15, coordinates.y+10, bitmap.getWidth()/7, Color.DKGRAY,10));
            if(random.nextBoolean()) sector.addEntityToBack(new SmokeParticle(sector, coordinates.x-1, coordinates.y+10, bitmap.getWidth()/3, Color.DKGRAY,40));

            // Drop Money
            MoneyDrop moneyDrop = new MoneyDrop(sector, coordinates, context, 15);
            sector.addEntityFront(moneyDrop);
            // Notify Spawner
            if(spawner != null) {
                spawner.reportDeath(this);
            }
        }
    }

    @Override
    public int getAngle() {
        return angle;
    }

    @Override
    public Sector getCurrentSector() {
        return sector;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public void setTarget(GameEntity e) {
        this.target = e;
    }

    @Override
    public void registerSpawner(EnemySpawner enemySpawner) {
        this.spawner = enemySpawner;
    }
}
