package com.group18.cs446.spacequest.game.objects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;
import android.view.SurfaceHolder;

import com.group18.cs446.spacequest.game.enums.GameState;
import com.group18.cs446.spacequest.game.objects.hostile.Asteroid;
import com.group18.cs446.spacequest.game.objects.hostile.AsteroidSpawner;
import com.group18.cs446.spacequest.game.objects.hostile.EnemySpawner;
import com.group18.cs446.spacequest.game.objects.player.ComponentFactory;
import com.group18.cs446.spacequest.game.objects.player.Player;
import com.group18.cs446.spacequest.game.vfx.CanvasComponent;
import com.group18.cs446.spacequest.game.vfx.Filter;
import com.group18.cs446.spacequest.game.vfx.HUDComponent;
import com.group18.cs446.spacequest.game.vfx.HUDText;
import com.group18.cs446.spacequest.game.vfx.HealthBar;
import com.group18.cs446.spacequest.game.vfx.ShieldBar;
import com.group18.cs446.spacequest.io.ScreenRecorder;
import com.group18.cs446.spacequest.io.SoundManager;
import com.group18.cs446.spacequest.view.GameView;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class Sector {
    // Properties
    static final int tickRate = 25;
    private int victoryFinalizeTime = 120;
    private int defeatFinalizeTime = 50;
    private int sectorID;

    // Game Object Members
    private Player player;
    private GameState gameState = GameState.PAUSED;
    private GameState previousGameState = GameState.RUNNING;
    long gameTick = 0;
    private List<GameEntity> entities;
    private List<HUDComponent> hud;
    private List<Point> stars;
    private List<Filter> filters;
    int starDimension = 3000;
    int numStars = 6000;
    private EnemySpawner enemySpawner;
    private AsteroidSpawner asteroidSpawner;

    // Graphics Members
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private int canvasWidth, canvasHeight;

    private Context context;
    private ComponentFactory componentFactory;
    private GameView gameView;
    private ScreenRecorder screenRecorder;

    public Sector(Player player, Context context, SurfaceHolder surfaceHolder, int sectorID, GameView gameView){
        this.gameView = gameView;
        this.componentFactory = new ComponentFactory();
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
        hud = new LinkedList<>();
        hud.add(new HealthBar(player));
        hud.add(new ShieldBar(player));
        hud.add(new HUDText(player, sectorID));

        for(HUDComponent hc : hud){
            player.registerObeserver(hc);
        }


        stars = new LinkedList<>();
        for(int i = 0; i < numStars; i++){
            stars.add(new Point((int)(Math.random()*starDimension),(int)(Math.random()*starDimension)));
        }

        enemySpawner = new EnemySpawner(sectorID, this, componentFactory, context);
        asteroidSpawner = new AsteroidSpawner(sectorID, this, context);
    }

    public void pause(){
        if(gameState != GameState.PAUSED) {
            previousGameState = gameState;
            gameState = GameState.PAUSED;
        }
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
        if(screenRecorder != null){
            screenRecorder.startRecording(gameView.getGamePlayActivity());
        }
        int droppedFrames = 0;
        int threshhold = 5;
        int framesThisSecond = 0;
        long thisSecondStart = System.currentTimeMillis();
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
            framesThisSecond++;
            if(System.currentTimeMillis() - 2000 > thisSecondStart){
                System.out.println("FPS: "+framesThisSecond/2);
                thisSecondStart = System.currentTimeMillis();
                framesThisSecond = 0;
            }
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
        asteroidSpawner.spawnAsteroids();
        enemySpawner.spawnEnemies();
    }

    public Player getPlayer(){
        return player;
    }

    private void draw(){ // This draw function will be responsible for drawing each frame
        CanvasComponent canvas = new CanvasComponent();
        if (surfaceHolder.getSurface().isValid()) { // acquire the canvas
            Canvas screenCanvas = surfaceHolder.lockCanvas();
            if(screenCanvas == null){
                return;
            }
            canvasWidth = screenCanvas.getWidth(); // so that we only have to do this once
            canvasHeight = screenCanvas.getHeight();
            canvas.addCanvas(screenCanvas);
            Point topLeftCorner = new Point(player.getCoordinates().x-canvasWidth/2,
                    player.getCoordinates().y-canvasHeight/2); // the point which will be the top left corner
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

            for(HUDComponent hc : hud){
                hc.paint(canvas);
            }

            if(gameState == GameState.PAUSED){
                paint.setColor(Color.RED);
                paint.setTextSize(200);
                String pausedMessage = "Game Paused";
                int xPos = canvasWidth/2 - (int)(paint.measureText(pausedMessage)/2);
                int yPos = (int) (canvasHeight/2 - ((paint.descent() + paint.ascent()) / 2)) ;
                canvas.drawText(pausedMessage, xPos, yPos, paint);
                paint.setColor(Color.WHITE);
                paint.setTextSize(100);
                pausedMessage = "Tap Screen to Resume";
                xPos = canvasWidth/2 - (int)(paint.measureText(pausedMessage)/2);
                yPos = (int) (canvasHeight/2 + 200 - ((paint.descent() + paint.ascent()) / 2)) ;
                canvas.drawText(pausedMessage, xPos, yPos, paint);
            } else if(gameState == GameState.WON){
                paint.setColor(Color.GREEN);
                paint.setTextSize(200);
                String victoryMessage = "Sector Cleared";
                int xPos = canvasWidth/2 - (int)(paint.measureText(victoryMessage)/2);
                int yPos = (int) (canvasHeight/2 - ((paint.descent() + paint.ascent()) / 2)) ;
                canvas.drawText(victoryMessage, xPos, yPos, paint);
                paint.setColor(Color.WHITE);
                paint.setTextSize(100);
                victoryMessage = "Jumping to next Sector";
                xPos = canvasWidth/2 - (int)(paint.measureText(victoryMessage)/2);
                yPos = (int) (canvasHeight/2 + 200 - ((paint.descent() + paint.ascent()) / 2)) ;
                canvas.drawText(victoryMessage, xPos, yPos, paint);
            }

            paint.reset();
            surfaceHolder.unlockCanvasAndPost(screenCanvas); // unlock and draw the frame
        }
    }

    private void drawStars(CanvasComponent canvas, Paint paint, Point topLeftCorner){
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
            long remaining = (1000 / tickRate) - (System.currentTimeMillis() - start);
            if (remaining > 0) {
                Thread.sleep(remaining);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void triggerVictory() {
        previousGameState = gameState;
        gameState = GameState.WON;
    }

    public void triggerDefeat(){
        if(gameState != GameState.WON) {
            player.explode(defeatFinalizeTime);
            previousGameState = gameState;
            gameState = GameState.LOST;
        }
    }

    public int getVictoryFinalizeTime() {
        return victoryFinalizeTime;
    }

    public void setScreenRecorder(ScreenRecorder screenRecorder) {
        this.screenRecorder = screenRecorder;
    }
}
