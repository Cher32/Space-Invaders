package com.example.cherie.spaceinvaders2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

/**
 * Created by Cherie on 10/5/2016.
 */

public class Player {
    //character image
    private Bitmap bitmap;

    //coordinates
    private int x;
    private int y;


    private int speed = 0;


    private boolean boosting;


    private final int GRAVITY = -10;


    private int maxY;
    private int minY;


    private final int MIN_SPEED = 1;
    private final int MAX_SPEED = 20;

    private Rect detectCollision;


    public Player(Context context, int screenX, int screenY) {
        x = 75;
        y = 50;
        speed = 1;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.player);


        maxY = screenY - bitmap.getHeight();


        minY = 0;


        boosting = false;


        detectCollision =  new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }

    public void setBoosting() {
        boosting = true;
    }


    public void stopBoosting() {
        boosting = false;
    }

    public void update() {

        if (boosting) {

            speed += 2;
        } else {

            speed -= 5;
        }

        if (speed > MAX_SPEED) {
            speed = MAX_SPEED;
        }

        if (speed < MIN_SPEED) {
            speed = MIN_SPEED;
        }


        y -= speed + GRAVITY;


        if (y < minY) {
            y = minY;
        }
        if (y > maxY) {
            y = maxY;
        }

        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x + bitmap.getWidth();
        detectCollision.bottom = y + bitmap.getHeight();
    }

    //getting the rect object
    public Rect getDetectCollision() {

        return detectCollision;
    }


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
