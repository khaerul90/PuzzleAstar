package com.example.erul.puzzleastar;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

/**
 * Created by epril on 29/11/2017.
 */

public class PuzzleTile {
    private Bitmap bitmap;
    private int number;
    public PuzzleTile(Bitmap bitmap, int number){
        this.bitmap = bitmap;
        this.number = number;
    }

    public int getNumber() {

        return number;
    }

    public void draw(Canvas canvas, int x, int y) {
        canvas.drawBitmap(bitmap, x * bitmap.getWidth(), y * bitmap.getHeight(), null);
    }

    public boolean isClicked(float clickX, float clickY, int tileX, int tileY) {
        int tileX0 = tileX * bitmap.getWidth();
        int tileX1 = (tileX + 1) * bitmap.getWidth();
        int tileY0 = tileY * bitmap.getHeight();
        int tileY1 = (tileY + 1) * bitmap.getHeight();
        return (clickX >= tileX0) && (clickX < tileX1) && (clickY >= tileY0) && (clickY < tileY1);
    }

}
