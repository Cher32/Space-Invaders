package com.example.cherie.spaceinvaders2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * Created by Cherie on 10/5/2016.
 */

public class GameView extends SurfaceView implements Runnable {

    //game is playing or not
    volatile boolean playing;

    //the game thread
    private Thread gameThread = null;

    private Player player;


    //These objects will be used for drawing
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    //enemies for object array
    private Enemy[] enemies;

    //Adding 3 enemies for now
    private int enemyCount = 3;

    //stars list
    private ArrayList<Star> stars = new
            ArrayList<Star>();

    //defining a boom object
    private Boom boom;



    public GameView(Context context, int screenX, int screenY) {
        super(context);


        player = new Player(context, screenX, screenY);


        surfaceHolder = getHolder();
        paint = new Paint();

        //adding 100 stars
        int starNums = 100;
        for (int i = 0; i < starNums; i++) {
            Star s  = new Star(screenX, screenY);
            stars.add(s);
        }


        enemies = new Enemy[enemyCount];
        for(int i=0; i < enemyCount; i++){
            enemies[i] = new Enemy(context, screenX, screenY);
        }

        //initializing boom object
        boom = new Boom(context);
    }

    @Override
    public void run() {
        while (playing) {
            update();

            draw();

            control();
        }
    }


    private void update() {
        player.update();


        boom.setX(-250);
        boom.setY(-250);

        //Updating the stars with player speed
        for (Star s : stars) {
            s.update(player.getSpeed());
        }
        //updating the enemy coordinate with player speed
        for (int i = 0; i < enemyCount; i++) {
            enemies[i].update(player.getSpeed());

            //if player collision happens
            if (Rect.intersects(player.getDetectCollision(), enemies[i].getDetectCollision())) {

                //displaying boom at that location
                boom.setX(enemies[i].getX());
                boom.setY(enemies[i].getY());

                //moving enemy outside the left edge
                enemies[i].setX(-200);
            }
        }
    }

    private void draw() {

        if (surfaceHolder.getSurface().isValid()) {

            canvas = surfaceHolder.lockCanvas();

            canvas.drawColor(Color.BLACK);


            paint.setColor(Color.WHITE);


            for (Star s : stars) {
                paint.setStrokeWidth(s.getStarWidth());
                canvas.drawPoint(s.getX(), s.getY(), paint);
            }

            //the player
            canvas.drawBitmap(
                    player.getBitmap(),
                    player.getX(),
                    player.getY(),
                    paint);

            //the enemies
            for (int i = 0; i < enemyCount; i++) {
                canvas.drawBitmap(
                        enemies[i].getBitmap(),
                        enemies[i].getX(),
                        enemies[i].getY(),
                        paint
                );
            }

            //boom image
            canvas.drawBitmap(
                    boom.getBitmap(),
                    boom.getX(),
                    boom.getY(),
                    paint
            );


            surfaceHolder.unlockCanvasAndPost(canvas);


        }
    }
    private void control() {
        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        //the game is paused
        playing = false;
        try {
            //stopping the thread
            gameThread.join();
        } catch (InterruptedException e) {
        }
    }

    public void resume() {
        //the game is resumed
        //starting the thread again
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                //stopping the boosting when screen is released
                player.stopBoosting();
                break;
            case MotionEvent.ACTION_DOWN:
                //boosting the space jet when screen is pressed
                player.setBoosting();
                break;
        }
        return true;
    }

}