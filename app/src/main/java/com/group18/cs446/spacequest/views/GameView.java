package com.group18.cs446.spacequest.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.group18.cs446.spacequest.Constants;
import com.group18.cs446.spacequest.game.objects.Asteroid;
import com.group18.cs446.spacequest.game.objects.Player;

import java.util.ArrayList;
import java.util.List;


public class GameView extends SurfaceView implements Runnable {

    private boolean running = false;
    private Player player;
    private List<Asteroid> asteroids = new ArrayList<>();
    private Thread gameThread = null;
    private int tickRate;

    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    public GameView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        tickRate = 30;

        player = new Player(context);

        for (int i = 0; i < 20; i++) {
            asteroids.add(new Asteroid(player.getCoordinates(), context));
        }

        surfaceHolder = getHolder();
        paint = new Paint();

        setSystemUiVisibility(Constants.BASE_UI_VISIBILITY);
    }

    @Override
    public void run() {

        while (running) {
            long tickStart = System.currentTimeMillis();
            update();
            draw();
            control(tickStart);
        }
    }

    private void update(){
        for (Asteroid asteroid: asteroids) {
            asteroid.update();
        }
        player.update();
    }

    private void draw(){

        if (surfaceHolder.getSurface().isValid()) {

            canvas = surfaceHolder.lockCanvas();

            Point topLeftCorner = new Point(player.getCoordinates().x-canvas.getWidth()/2,
                    player.getCoordinates().y-canvas.getHeight()/2);

            // Draw background
            canvas.drawColor(Color.BLACK);
            paint.setColor(Color.WHITE);

            canvas.save();

            // Drawing all stars
            for (int i = 0; i < 100 && i*200-topLeftCorner.x < canvas.getWidth(); i++) {
                for (int j = 0; j < 100&& j*200-topLeftCorner.y < canvas.getHeight(); j++) {
                    paint.setStrokeWidth(10);
                    canvas.drawPoint(i*200-topLeftCorner.x, j*200-topLeftCorner.y, paint);
                }
            }
            canvas.restore();

            drawAsteroids(topLeftCorner);

            //Alternatively, pass this to Player to paint themselves here
            canvas.save();
            canvas.rotate(-player.getAngle(), player.getCoordinates().x - topLeftCorner.x, player.getCoordinates().y - topLeftCorner.y);
            canvas.drawBitmap(
                    player.getBitmap(),
                    player.getCoordinates().x - topLeftCorner.x - player.getBitmap().getWidth()/2,
                    player.getCoordinates().y - topLeftCorner.y - player.getBitmap().getHeight()/2,
                    paint);
            canvas.restore();

            /*paint.setColor(Color.RED);
            paint.setStrokeWidth(10);
            canvas.drawPoint(canvas.getWidth()/2, canvas.getHeight()/2, paint);*/

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void drawAsteroids(Point topLeftCorner) {
        int cx, cy;
        for (Asteroid asteroid: asteroids) {
            cx = asteroid.getCoordinates().x - topLeftCorner.x;
            cy = asteroid.getCoordinates().y - topLeftCorner.y;
            canvas.save();
            canvas.rotate(asteroid.getAngle(), cx + asteroid.getBitmap().getWidth()/2, cy + asteroid.getBitmap().getHeight()/2);
            canvas.drawBitmap(asteroid.getBitmap(), cx, cy, paint);
            canvas.restore();
        }
    }

    private void control(long start){

        try {

            long remaining = 1000/tickRate - System.currentTimeMillis() - start;

            if(remaining > 0)
                Thread.sleep(remaining);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause(){
        running = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume(){
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public Player getPlayer() {
        return player;
    }
}