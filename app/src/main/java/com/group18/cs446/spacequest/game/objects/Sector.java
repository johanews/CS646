package com.group18.cs446.spacequest.game.objects;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.SurfaceHolder;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Owen on 2018-02-09.
 */

public class Sector {
    // Properties
    static final int tickRate = 30;
    private int victoryFinalizeTime = 200;

    // Game Object Members
    private Player player;
    private GameState gameState = GameState.PAUSED;
    long gameTick = 0;
    private List<GameEntity> entities;
    private List<Point> stars;
    int starDimension = 3000;
    int numStars = 6000;

    // Graphics Members
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private int canvasWidth, canvasHeight;


    public Sector(Player player, Context context, SurfaceHolder surfaceHolder){
        this.player = player;
        this.surfaceHolder = surfaceHolder;
        this.paint = new Paint();
        GameEntity exitGate = new ExitGate(context, 0, 0);
        this.entities = new LinkedList<>();
        entities.add(exitGate);
        entities.add(player);
        stars = new LinkedList<>();
        for(int i = 0; i < numStars; i++){
            stars.add(new Point((int)(Math.random()*starDimension),(int)(Math.random()*starDimension)));
        }
    }

    public boolean run() {
        gameState = GameState.RUNNING;
        while (gameState == GameState.RUNNING || gameState == GameState.PAUSED) {
            long tickStart = System.currentTimeMillis();
            if(gameState != GameState.PAUSED){
                update();
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
                return false;
            default:
                throw new RuntimeException("ILLEGAL GAME STATE AT END OF LOOP");
        }
    }
    private void update(){
        gameTick++;
        for(GameEntity e : entities){
            e.update();
        }
        for(GameEntity e : entities){
            if(e != player && player.intersects(e)){
                triggerCollisionEvent(player, e);
            }
        }
        if(gameTick%15 == 0) {
            //ParticleEffect smoke = new ParticleEffect(player.getCenter().x, player.getCenter().y, 150);
            //entities.add(smoke);
        }
    }

    private void triggerCollisionEvent(Player player, GameEntity e){
        switch (e.getCollisionEvent()){
            case VICTORY:
                player.flyToTarget(e.getCoordinates(), victoryFinalizeTime);
                gameState = GameState.WON;
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

            // Draw any foreground entities (such as healthbars or effects (like a red filter over everything)
            paint.setColor(Color.RED);
            paint.setStrokeWidth(10);
            canvas.drawPoint(canvas.getWidth()/2, canvas.getHeight()/2, paint);

            // Draw coordinates, for help testing
            paint.setColor(Color.YELLOW);
            paint.setTextSize(40);
            canvas.drawText("("+player.getCoordinates().x+", "+player.getCoordinates().y+")", canvasWidth-300, canvasHeight-100, paint);
            surfaceHolder.unlockCanvasAndPost(canvas); // unlock and draw the frame
        }
    }

    private void drawStars(Canvas canvas, Paint paint, Point topLeftCorner){
        paint.setColor(Color.WHITE);
        int i = 0, width = 7;
        float divisor = Float.parseFloat("1.5");
        for(Point p : stars){
            if(i%1000 == 0){
                divisor+=1.5;
                width-=1;
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
            long remaining = 1000/tickRate - System.currentTimeMillis() - start;
            if(remaining > 0) Thread.sleep(remaining);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
