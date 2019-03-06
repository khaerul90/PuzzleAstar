package com.example.erul.puzzleastar;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

/**
 * Created by epril on 29/11/2017.
 */

public class PapanPuzzle {
    public static int NUM_TILES = 3;
    private static final int[][] NEIGHBOUR_COORDS = {
            { -1, 0 },
            { 1, 0 },
            { 0, -1 },
            { 0, 1 }
    };

    private ArrayList<PuzzleTile> tiles;
    private int stepNumber;
    PapanPuzzle previousBoard;
    public PapanPuzzle getPreviousBoard() {
        return previousBoard;
    }
    public void setPreviousBoard(PapanPuzzle previousBoard) {
        this.previousBoard = previousBoard;
    }
    PapanPuzzle(Bitmap bitmap, int parentWidth, int parentHeight) {
        stepNumber = 0;
        int nomer=0;
        tiles = new ArrayList<>();
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap,
                parentWidth,
                parentHeight,
                true);
        for(int y=0; y<NUM_TILES ; y++)
        {
            for(int x=0 ; x<NUM_TILES ; x++)
            {
                int tileNumber = y*NUM_TILES +x;
                if(tileNumber!=NUM_TILES*NUM_TILES-1)
                {
                    int w = scaledBitmap.getWidth();
                    int h = scaledBitmap.getHeight();
                    Bitmap tileBitmap = Bitmap.createBitmap(scaledBitmap,
                            x*w/NUM_TILES ,
                            y*h/NUM_TILES,
                            parentWidth/NUM_TILES,
                            parentHeight/NUM_TILES);
                    Paint paint = new Paint();
                    Canvas d = new Canvas(tileBitmap);
                    paint.setStrokeWidth(10.0F);
                    paint.setColor(Color.CYAN);
                    d.drawLine(0.0f,0.0f,0.0f,w, paint);
                    d.drawLine(0.0f,0.0f,h,0.0f, paint);
                    paint.setColor(Color.TRANSPARENT);
                    d.drawRect(0, 0, 50, 50, paint);
                    paint.setTextSize(40);
                    paint.setColor(Color.RED);
                    paint.setTextAlign(Paint.Align.CENTER);
                    paint.setStyle(Paint.Style.FILL);
                    nomer++;
                    d.drawText(" "+String.valueOf(nomer), 20, 40, paint);
                    PuzzleTile tile = new PuzzleTile(tileBitmap,tileNumber);
                    tiles.add(tile);
                }
                else
                {
                    tiles.add(null);

                }
            }
        }
    }

    PapanPuzzle(PapanPuzzle otherBoard, int steps) {
        previousBoard = otherBoard;
        tiles = (ArrayList<PuzzleTile>) otherBoard.tiles.clone();
        this.stepNumber = steps+1 ;
    }

    public void reset() {

    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        return tiles.equals(((PapanPuzzle) o).tiles);
    }

    public void draw(Canvas canvas) {
        if (tiles == null) {
            return;

        }
        for (int i = 0; i < NUM_TILES * NUM_TILES; i++) {
            PuzzleTile tile = tiles.get(i);
            if (tile != null) {
                tile.draw(canvas, i % NUM_TILES, i / NUM_TILES);

            }
        }
    }

    public boolean click(float x, float y) {
        for (int i = 0; i < NUM_TILES * NUM_TILES; i++) {
            PuzzleTile tile = tiles.get(i);
            if (tile != null) {
                if (tile.isClicked(x, y, i % NUM_TILES, i / NUM_TILES)) {
                    return tryMoving(i % NUM_TILES, i / NUM_TILES);
                }
            }
        }
        return false;
    }

    public boolean resolved() {
        for (int i = 0; i < NUM_TILES * NUM_TILES - 1; i++) {
            PuzzleTile tile = tiles.get(i);


            if (tile == null || tile.getNumber() != i)
                return false;
        }
        return true;
    }

    private int XYtoIndex(int x, int y) {
        return x + y * NUM_TILES;
    }

    protected void swapTiles(int i, int j) {
        PuzzleTile temp = tiles.get(i);
        tiles.set(i, tiles.get(j));
        tiles.set(j, temp);
    }

    private boolean tryMoving(int tileX, int tileY) {
        for (int[] delta : NEIGHBOUR_COORDS) {
            int nullX = tileX + delta[0];
            int nullY = tileY + delta[1];
            if (nullX >= 0 && nullX < NUM_TILES && nullY >= 0 && nullY < NUM_TILES &&
                    tiles.get(XYtoIndex(nullX, nullY)) == null) {
                swapTiles(XYtoIndex(nullX, nullY), XYtoIndex(tileX, tileY));
                return true;
            }

        }
        return false;
    }

    public ArrayList<PapanPuzzle> neighbours() {
        ArrayList<PapanPuzzle> neighbours = new ArrayList<>();
        int emptyTileX=0;
        int emptyTileY=0;

        for(int i=0;i<NUM_TILES*NUM_TILES;i++)
        {
            if(tiles.get(i)==null)
            {
                emptyTileX=i%NUM_TILES;
                emptyTileY=i/NUM_TILES;
                break;
            }
        }

        for(int [] coordinates:NEIGHBOUR_COORDS)
        {
            int neighbourX=emptyTileX+coordinates[0];
            int neighbourY=emptyTileY+coordinates[1];

            if(neighbourX>=0 && neighbourX<NUM_TILES &&neighbourY>=0&&neighbourY<NUM_TILES)
            {
                PapanPuzzle neighbourBoard = new PapanPuzzle(this,stepNumber);

                neighbourBoard.swapTiles(XYtoIndex(neighbourX,neighbourY),XYtoIndex(emptyTileX,emptyTileY));

                neighbours.add(neighbourBoard);
            }
        }
        return neighbours;

    }

    public int priority()
    {
        int Manhatten_Distance =0;
        for(int i=0;i<NUM_TILES*NUM_TILES;i++)
        {
            PuzzleTile tile = tiles.get(i);
            if(tile!=null)
            {
                int correctPosition =tile.getNumber();
                int correctX=correctPosition%NUM_TILES;
                int correctY=correctPosition/NUM_TILES;
                int currentX=i%NUM_TILES;
                int currentY=i/NUM_TILES;
                Manhatten_Distance+=Math.abs(currentX-correctX)+Math.abs(currentY-correctY);
            }
        }
        return Manhatten_Distance+stepNumber;
    }
}
