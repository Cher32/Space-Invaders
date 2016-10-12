package com.example.cherie.spaceinvaders2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by Cherie on 10/5/2016.
 */

public class Enemy {

    //bitmap for the enemy
    private Bitmap bitmap;


    private int x;
    private int y;


    private int speed = 1;


    private int maxX;
    private int minX;

    private int maxY;
    private int minY;


    private Rect detectCollision;


    public Enemy(Context context, int screenX, int screenY) {

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy);


        maxX = screenX;
        maxY = screenY;
        minX = 0;
        minY = 0;


        Random generator = new Random();
        speed = generator.nextInt(6) + 10;
        x = screenX;
        y = generator.nextInt(maxY) - bitmap.getHeight();

        //initializing rect object
        detectCollision = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());

    }

    public void update(int playerSpeed) {

        x -= playerSpeed;
        x -= speed;

        if (x < minX - bitmap.getWidth()) {

            Random generator = new Random();
            speed = generator.nextInt(10) + 10;
            x = maxX;
            y = generator.nextInt(maxY) - bitmap.getHeight();


            detectCollision.left = x;
            detectCollision.top = y;
            detectCollision.right = x + bitmap.getWidth();
            detectCollision.bottom = y + bitmap.getHeight();
        }
    }

    //setter for x collision
    public void setX(int x) {

        this.x = x;
    }

    //getting the rect object
    public Rect getDetectCollision() {

        return detectCollision;
    }

    //getters
    public Bitmap getBitmap() {

        return bitmap;
    }

    public int getX() {

        return x;
    }

    public int getY() {

        return y;
    }

    public int getSpeed() {

        return speed;
    }

}
