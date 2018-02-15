package com.group18.cs446.spacequest.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.group18.cs446.spacequest.game.objects.Player;

public class GameView extends SurfaceView implements Runnable {

    private Player player;
    private Thread gameThread = null;

    private boolean running = false;
    private int tickRate;

    private Paint paint;
    private SurfaceHolder surfaceHolder;

    public GameView(Context context) {
        super(context);
    }

    public GameView(Context context, AttributeSet attrs) {

        super(context,attrs);
        tickRate = 60;

        player = new Player(context);

        surfaceHolder = getHolder();
        paint = new Paint();
    }

    @Override
    public void run() {

        while(running) {

            long tickStart = System.currentTimeMillis();
            update();
            draw();
            control(tickStart);
        }
    }

    private void update() {
        player.update();
    }

    private void draw() {

        if (surfaceHolder.getSurface().isValid()) {

            Canvas canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.BLACK);

            canvas.save(); // Saves the current transformation matrix (identity matrix in R2)

            // Creates a transformation matrix that transforms each (x,y) coordinate to
            // a new location in R2, imposed by the given angle and with respect to the
            // center of the screen.
            canvas.rotate(player.getAngle(), canvas.getWidth()/2, canvas.getHeight()/2);

            // When drawn onto the screen, each pixel of the bitmap is transformed into
            // its 'new' location by multiplication with the transformation matrix.
            canvas.drawBitmap(
                    player.getBitmap(),
                    canvas.getWidth()/2 - player.getBitmap().getWidth()/2,
                    canvas.getHeight()/2 - player.getBitmap().getHeight()/2,
                    paint);

            canvas.restore(); // Restores the saved identity matrix

            canvas.save();
            canvas.rotate(-player.getAngle(), canvas.getWidth()/2, canvas.getHeight()/2);

            paint.setStrokeWidth(10);
            paint.setColor(Color.RED);
            canvas.drawPoint(canvas.getWidth()/2 + 100, canvas.getHeight()/2 + 100, paint);

            canvas.restore();

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control(long start){

        try {

            long remaining = 1000/tickRate - (System.currentTimeMillis() - start);

            if(remaining > 0)
                Thread.sleep(remaining);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {

        running = false;

        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void resume() {

        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public Player getPlayer() {
        return player;
    }

}