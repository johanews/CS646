package com.group18.cs446.spacequest.game.objects;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.SurfaceHolder;

import com.group18.cs446.spacequest.game.CollisionEvent;
import com.group18.cs446.spacequest.game.enums.GameState;
import com.group18.cs446.spacequest.game.objects.hostile.Asteroid;
import com.group18.cs446.spacequest.game.objects.hostile.Enemy;
import com.group18.cs446.spacequest.game.objects.hostile.npship.BasicEnemy;
import com.group18.cs446.spacequest.game.vfx.Filter;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class Sector {
    // Properties
    static final int tickRate = 30;
    private int victoryFinalizeTime = 200;
    private int defeatFinalizeTime = 50;
    private int sectorID;

    // Game Object Members
    private Player player;
    private GameState gameState = GameState.PAUSED;
    private GameState previousGameState = GameState.RUNNING;
    long gameTick = 0;
    private List<GameEntity> entities;
    private List<Point> stars;
    private List<Filter> filters;
    int starDimension = 3000;
    int numStars = 6000;

    // Graphics Members
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private int canvasWidth, canvasHeight;

    private Context context;


    public Sector(Player player, Context context, SurfaceHolder surfaceHolder, int sectorID){
        this.gameState = GameState.PAUSED;
        this.context = context;
        this.player = player;
        player.setCurrentSector(this);
        this.surfaceHolder = surfaceHolder;
        this.sectorID = sectorID;
        this.paint = new Paint();
        GameEntity exitGate = new ExitGate(context, 0, 0);
        this.entities = new CopyOnWriteArrayList<>();
        this.filters = new CopyOnWriteArrayList<>();
        addEntityFront(exitGate);
        addEntityFront(player);
        stars = new LinkedList<>();
        for(int i = 0; i < numStars; i++){
            stars.add(new Point((int)(Math.random()*starDimension),(int)(Math.random()*starDimension)));
        }

        for(int x = -5; x <= 5; x+=2){
            for(int y = -5; y <= 5; y+=2){
                if((Math.random()*(sectorID+5))>5) {
                    Enemy e = new BasicEnemy(new Point(x * 600, y * 600), context, this);
                    addEntityFront(e);
                }
            }
        }
    }

    public void pause(){
        previousGameState = gameState;
        gameState = GameState.PAUSED;
    }
    public void unpause(){
        if(gameState == GameState.PAUSED) {
            gameState = previousGameState;
        }
    }
    public void addEntityFront(GameEntity e){ // painted last, in the foreground (ie enemy ship)
        entities.add(e);
    }
    public void addEntityToBack(GameEntity e){ // painted first, in the background (ie smoke effect)
        entities.add(0, e);
    }
    public void addFilter(Filter filter){
        filters.add(filter);
    }
    public void removeFilter(Filter filter){
        filters.remove(filter);
    }
    public List<GameEntity> getEntities(){
        return entities;
    }
    public void removeEntity(GameEntity e){
        entities.remove(e);
    }
    public boolean run() {
        int droppedFrames = 0;
        int threshhold = 5;
        while (gameState == GameState.RUNNING || gameState == GameState.PAUSED) {
            long tickStart = System.currentTimeMillis();
            if(gameState != GameState.PAUSED){
                update();
            }
            if(1000/tickRate < System.currentTimeMillis() - tickStart){
                droppedFrames++;
                if(droppedFrames > threshhold) {
                    droppedFrames = 0;
                } else {
                    continue;
                }
            } else {
                droppedFrames = 0;
            }
            draw();
            control(tickStart);
        }
        switch (gameState){
            case WON:
                while (victoryFinalizeTime > 0) {
                    victoryFinalizeTime--;
                    long tickStart = System.currentTimeMillis();
                    update();
                    draw();
                    control(tickStart);
                }
                return true;
            case LOST:
                while (defeatFinalizeTime > 0) {
                    defeatFinalizeTime--;
                    long tickStart = System.currentTimeMillis();
                    update();
                    draw();
                    control(tickStart);
                }
                return false;
            default:
                throw new RuntimeException("ILLEGAL GAME STATE AT END OF LOOP");
        }
    }
    private void update(){
        gameTick++;
        for(Filter f : filters){
            f.update(gameTick);
        }
        for(GameEntity e : entities){
            e.update(gameTick);
        }
        for(GameEntity e : entities){
            if(e != player && e.getCollisionEvent(player).getEvent() != CollisionEvent.NOTHING && player.intersects(e)){
                triggerCollisionEvent(player, e);
            }
        }
        if(gameTick%10 == 0){ // TODO put enemy entity spawning in a factory
            addEntityFront(new Asteroid(this, player.getCoordinates(), context));
        }
    }

    public Player getPlayer(){
        return player;
    }

    public void triggerDefeat(){
        if(gameState != GameState.WON) {
            player.explode(defeatFinalizeTime);
            previousGameState = gameState;
            gameState = GameState.LOST;
        }
    }

    private void triggerCollisionEvent(Player player, GameEntity e){
        CollisionEvent event = e.getCollisionEvent(player);
        switch (event.getEvent()){
            case CollisionEvent.VICTORY:
                gameState = GameState.WON;
                player.flyToTarget(e.getCoordinates(), victoryFinalizeTime);
                break;
            case CollisionEvent.DAMAGE:
                player.takeDamage(event.getValue());
                break;
            case CollisionEvent.DEFEAT:
                triggerDefeat();
                break;
        }
    }

    private void draw(){ // This draw function will be responsible for drawing each frame
        if (surfaceHolder.getSurface().isValid()) { // acquire the canvas
            canvas = surfaceHolder.lockCanvas();
            canvasWidth = canvas.getWidth(); // so that we only have to do this once
            canvasHeight = canvas.getHeight();

            Point topLeftCorner = new Point(player.getCoordinates().x-canvasWidth/2,
                    player.getCoordinates().y-canvasHeight/2); // the point which will be the top left corner
            // ie, if (100, 100) is the top left corner, an entity at (100, 200) would be on the left screen 100 down,
            // and an entity at (0, 0) would be off the screen (



            // Draw background
            canvas.drawColor(Color.BLACK);

            // Draw the stars
            drawStars(canvas, paint, topLeftCorner);

            // Draw all the game entities
            for(GameEntity e : entities) {
                e.paint(canvas, paint, topLeftCorner);
            }

            for(Filter filter : filters){
                filter.paint(canvas);
            }

            // Draw coordinates, for help testing
            paint.setColor(Color.YELLOW);
            paint.setTextSize(40);
            canvas.drawText("("+player.getCoordinates().x+", "+player.getCoordinates().y+")", canvasWidth-300, canvasHeight-100, paint);
            paint.setColor(Color.CYAN);
            paint.setTextSize(60);
            canvas.drawText("Current Sector: "+sectorID, canvasWidth-600, 70, paint);
            paint.setColor(Color.RED);
            canvas.drawText(player.getCurrentHealth()+"/"+player.getMaxHealth(), 70, 70, paint);
            paint.setColor(Color.CYAN);
            canvas.drawText(player.getCurrentShield() + "/" + player.getMaxShield(), 70, 140, paint);


            paint.reset();

            surfaceHolder.unlockCanvasAndPost(canvas); // unlock and draw the frame
        }
    }

    private void drawStars(Canvas canvas, Paint paint, Point topLeftCorner){
        paint.setColor(Color.WHITE);
        int i = 0;
        float width = 4;
        float divisor = Float.parseFloat("2.5");
        for(Point p : stars){
            if(i%1000 == 0){
                divisor+=1.5;
                width-=0.5;
            }
            i++;
            canvas.drawCircle((p.x-topLeftCorner.x/divisor+starDimension)%starDimension,
                    (p.y-topLeftCorner.y/divisor+starDimension)%starDimension,
                    width,
                    paint);
        }

    }
    private void control(long start){
        try {
            long remaining = (1000/tickRate) -(System.currentTimeMillis() - start);
            if(remaining > 0) Thread.sleep(remaining);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}